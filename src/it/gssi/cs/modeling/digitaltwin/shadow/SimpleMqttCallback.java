package it.gssi.cs.modeling.digitaltwin.shadow;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ArrayBlockingQueue;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import it.gssi.cs.modeling.digitaltwin.launcher.EplEvlStandaloneExample;

public class SimpleMqttCallback implements MqttCallback {
	
	private String subjectfile;
	
	  public SimpleMqttCallback(String subjectfile) {
		// TODO Auto-generated constructor stub
		  this.subjectfile = subjectfile;
	}

	public void connectionLost(Throwable throwable) {
	    System.out.println("Connection to MQTT broker lost!");
	  }
	 
	  public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
	    System.out.println("Value detected: "+s+":"
	    		+new String(mqttMessage.getPayload()) );
	   
	    DigitalShadowInjector ds = new DigitalShadowInjector();
	  
	    float value =  (float) Double.parseDouble(new String(mqttMessage.getPayload()));
	    
	    if(ds.updateDigitalShadow(this.subjectfile, "models/smartbuilding/smartBuildingDL.ecore","Office 1", s,value)) {
	    	//copy subject file
	    
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