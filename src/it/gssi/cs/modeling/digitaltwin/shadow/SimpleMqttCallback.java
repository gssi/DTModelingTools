package it.gssi.cs.modeling.digitaltwin.shadow;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import it.gssi.cs.modeling.digitaltwin.launcher.EplEvlStandaloneExample;

public class SimpleMqttCallback implements MqttCallback {
	
	private String subjectfile;
	private Map<String, Integer> updateintervals;
	private Map<String, Date> lastupdate;
	
	  public SimpleMqttCallback(String subjectfile, Map<String, Integer> updateintervals) {
		// TODO Auto-generated constructor stub
		  this.subjectfile = subjectfile;
		  this.updateintervals = updateintervals;
		  lastupdate = new HashMap<String, Date>();
	}

	public void connectionLost(Throwable throwable) {
	    System.out.println("Connection to MQTT broker lost!");
	  }
	 
	  public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
	    System.out.println("Value detected: "+s+":"
	    		+new String(mqttMessage.getPayload()) + " at "+new Date().toLocaleString() );
	   
	    DigitalShadowInjector ds = new DigitalShadowInjector();
	  
	    float value =  (float) Double.parseDouble(new String(mqttMessage.getPayload()));
	    
	   
	    if(!lastupdate.containsKey(s) || ((Duration.between(lastupdate.get(s).toInstant(), new Date().toInstant()).toSeconds()>= this.updateintervals.get(s)) &&  ds.updateDigitalShadow(this.subjectfile, "models/smartbuilding/smartBuildingDL.ecore","Office 1", s,value))) {
	    	//copy subject file
	    	 if(lastupdate.putIfAbsent(s, new Date())!=null) lastupdate.replace(s, new Date()); 	
	    	
	        EplEvlStandaloneExample evalengine = new EplEvlStandaloneExample(this.subjectfile);
	    	Thread evalT = new Thread(evalengine);
			evalT.run();
			
			
			
			
	    }
		//this.q.add(new DigitalShadowElement(s, value));
	     
		
	    
	    
	    
	  }

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}
	 
	  
	  
	}