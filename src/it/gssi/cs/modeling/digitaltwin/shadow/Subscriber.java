package it.gssi.cs.modeling.digitaltwin.shadow;

import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

import org.eclipse.epsilon.eol.types.EolSequence;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class Subscriber implements Runnable{
	

private String subjectfile;

	public Subscriber(String subjectfile) {
	super();
	this.subjectfile = subjectfile;
}

	public static void main(String[] args) throws URISyntaxException {
		// TODO Auto-generated method stub
		ArrayBlockingQueue<DigitalShadowElement> q = new ArrayBlockingQueue<DigitalShadowElement> (1000);
		String subjectfile ="models/smartbuilding/gssi.model";
		Subscriber s = new Subscriber(subjectfile);
		s.run();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			MqttClient client=new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
			
			try {
				client.setCallback( new SimpleMqttCallback(this.subjectfile) );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
