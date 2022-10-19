package it.gssi.cs.modeling.digitaltwin.launcher;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.emc.emf.EmfUtil;

import it.gssi.cs.modeling.digitaltwin.shadow.DigitalShadowElement;
import it.gssi.cs.modeling.digitaltwin.shadow.MqttPub;
import it.gssi.cs.modeling.digitaltwin.shadow.Subscriber;

public class MainProcess {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Path root, qesRoot, mmRoot;
		root = Paths.get(MainProcess.class.getResource("").toURI());
		qesRoot = root.getParent().resolve("qes");
		mmRoot = root.getParent().resolve("metamodels");
	
		org.eclipse.emf.common.util.URI smartbuildingmm = org.eclipse.emf.common.util.URI.createURI(new File("models/smartbuilding/smartBuildingDL.ecore").getAbsolutePath());
		org.eclipse.emf.common.util.URI kpimm = org.eclipse.emf.common.util.URI.createURI( mmRoot.resolve("kpi.ecore").toAbsolutePath().toUri().toString());
		org.eclipse.emf.common.util.URI DTLmm = org.eclipse.emf.common.util.URI.createURI( mmRoot.resolve("DTMM.ecore").toAbsolutePath().toUri().toString());
		
		
		EmfUtil.register(smartbuildingmm, EPackage.Registry.INSTANCE);
		EmfUtil.register(kpimm, EPackage.Registry.INSTANCE);
		EmfUtil.register(DTLmm, EPackage.Registry.INSTANCE);
		
		String subjectfile = "models/smartbuilding/gssi.model";
		
		Subscriber sub =new Subscriber(subjectfile);
		Thread t2 = new Thread(sub);
		t2.run();
		
		
		MqttPub pub = new MqttPub();
		Thread t3 = new Thread(pub);
		t3.run();
		
		
		

		
		
		
	}

}
