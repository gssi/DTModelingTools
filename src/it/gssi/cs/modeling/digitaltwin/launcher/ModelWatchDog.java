package it.gssi.cs.modeling.digitaltwin.launcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ModelWatchDog  extends Thread{
	
	
	private String subjectfilepath;
	private File file = null;
    private AtomicBoolean stop = new AtomicBoolean(false);
    private EplEvlStandaloneExample evalengine;
    
    public static void main(String[] args) throws IOException {
    	String subjectfile = "models/smartbuilding/gssi.model";
    	ModelWatchDog watchdog = new ModelWatchDog(subjectfile);
    	watchdog.start();
    			
    	System.out.println("Press key to exit"); System.in.read();
    }
    
    
    public ModelWatchDog(String subjectfile) {
    	subjectfilepath = subjectfile;
        this.file = new File(subjectfile);
        evalengine = new EplEvlStandaloneExample();
    }

    public boolean isStopped() { return stop.get(); }
    public void stopThread() { stop.set(true); }

    public void doOnChange() throws Exception {
        // Do whatever action you want here
    	System.out.println("Triggering evaluation...");
    	
    	evalengine.runEval(subjectfilepath);
    }
    
    @Override
    public void run() {
        try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
            Path path = file.toPath().getParent();
            path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
            while (!isStopped()) {
                WatchKey key;
                try { key = watcher.poll(25, TimeUnit.MILLISECONDS); }
                catch (InterruptedException e) { return; }
                if (key == null) { Thread.yield(); continue; }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();

                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        Thread.yield();
                        continue;
                    } else if (kind == java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY
                            && filename.toString().equals(file.getName())) {
                        doOnChange();
                    }
                    boolean valid = key.reset();
                    if (!valid) { break; }
                }
                Thread.yield();
            }
        } catch (Throwable e) {
            // Log or rethrow the error
        }
    }
}
