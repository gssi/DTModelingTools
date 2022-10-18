package it.gssi.cs.modeling.digitaltwin.shadow;

import java.net.URISyntaxException;
import java.util.Iterator;

import org.eclipse.epsilon.eol.types.EolSequence;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class Subscriber {

	public static void main(String[] args) throws URISyntaxException {
		// TODO Auto-generated method stub
		
		try {
			MqttClient client=new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
			
			client.setCallback( new SimpleMqttCallback() );
			
			MqttConnectOptions option = new MqttConnectOptions();
		    option.setKeepAliveInterval(30);
		    
		    option.setConnectionTimeout(30);
		    client.connect(option);
		    
		    
			
		    //query the subject model to get parameters
		    DigitalShadowInjector ds  = new DigitalShadowInjector();
		    Thread t1 =new Thread(ds);  
			t1.start();  
			
		    EolSequence parameterlist = ds.getRequiredDigitalShadow("models/smartbuilding/gssi.model", "models/smartbuilding/smartBuildingDL.ecore","Office 1");
			for (Iterator iterator = parameterlist.iterator(); iterator.hasNext();) {
				String par = (String) iterator.next();
				 client.subscribe(par);
				
			}
		   
		} catch (MqttSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
