package it.gssi.cs.modeling.digitaltwin.shadow;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class Subscriber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			MqttClient client=new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
			client.setCallback( new SimpleMqttCallback() );
			
			MqttConnectOptions option = new MqttConnectOptions();
		    option.setKeepAliveInterval(30);
		    client.connect(option);
		    
		    //query the subject model to get parameters
		    
			 client.subscribe("co2");
			 client.subscribe("humidity");
			 client.subscribe("temperature");
			 client.subscribe("noise");
			 client.subscribe("illuminance");
			 
		} catch (MqttSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
