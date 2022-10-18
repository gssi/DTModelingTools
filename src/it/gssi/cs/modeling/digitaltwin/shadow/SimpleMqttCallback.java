package it.gssi.cs.modeling.digitaltwin.shadow;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import it.gssi.cs.modeling.digitaltwin.launcher.EplEvlStandaloneExample;

public class SimpleMqttCallback implements MqttCallback {
	 
	  public void connectionLost(Throwable throwable) {
	    System.out.println("Connection to MQTT broker lost!");
	  }
	 
	  public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
	    System.out.println("Value detected: "+s+":"
	    		+new String(mqttMessage.getPayload()) );
	   
	    DigitalShadowInjector ds = new DigitalShadowInjector();
	  
	    float value =  (float) Double.parseDouble(new String(mqttMessage.getPayload()));
	    
	    if(ds.updateDigitalShadow("models/smartbuilding/gssi.model", "models/smartbuilding/smartBuildingDL.ecore","Office 1", s,value)){
		   
	    } 
		
	    
	    
	    
	  }

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}
	 
	  
	  
	}