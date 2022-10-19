package it.gssi.cs.modeling.digitaltwin.shadow;

import java.io.IOException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.BrickletAmbientLightV3;
import com.tinkerforge.BrickletCO2V2;
import com.tinkerforge.BrickletSoundPressureLevel;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NetworkException;
import com.tinkerforge.TinkerforgeException;


public class MqttPub implements Runnable{
	private static final String HOST = "172.16.5.2";
    private static final int PORT = 4223;

    // Change XYZ to the UID of your CO2 Bricklet 2.0
    private static final String COUID = "YLg";
    private static final String ILLUID = "P8L";
    private static final String NOISEUID = "XFQ";

    private static final int callbackinterval = 60000;
	public static void main(String[] args) {
		MqttPub p= new MqttPub();
		Thread t1 =new Thread(p);  
		t1.start();  
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		MqttClient client;
		try {
			client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
			
			
			
		    // Note: To make the example code cleaner we do not handle exceptions. Exceptions
		    //       you might normally want to catch are described in the documentation
		   
		        IPConnection ipcon = new IPConnection(); // Create IP connection
		        BrickletCO2V2 co2 = new BrickletCO2V2(COUID, ipcon); // Create device object
		        BrickletAmbientLightV3 al = new BrickletAmbientLightV3(ILLUID, ipcon); // Create device object
		        BrickletSoundPressureLevel spl =
		                new BrickletSoundPressureLevel(NOISEUID, ipcon); // Create device object

		      
				ipcon.connect(HOST, PORT);
				
				client.connect();
				
				
		        // Add all values listener
		        co2.addAllValuesListener(new BrickletCO2V2.AllValuesListener() {
		            public void allValues(int co2Concentration, int temperature, int humidity) {
		              //System.out.println("CO2 Concentration: " + co2Concentration + " ppm");
		               try {
		            	
		                MqttMessage message = new MqttMessage();
		    			message.setPayload((co2Concentration+"").getBytes());
		    							
						client.publish("CO2", message);
						
						message = new MqttMessage();
		    			message.setPayload((temperature/100.0+"").getBytes());
		    							
		    			client.publish("temperature", message);
						
						message = new MqttMessage();
		    			message.setPayload((humidity/100.0+"").getBytes());
		    							
						client.publish("humidity", message);
						
						
						} catch ( MqttException e ) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
		    			
		    			
		                
		            }
		        });

		        // Set period for all values callback to 1s (1000ms)
		        co2.setAllValuesCallbackConfiguration(callbackinterval, true);

		     // Add illuminance listener
		        al.addIlluminanceListener(new BrickletAmbientLightV3.IlluminanceListener() {
		            public void illuminance(long illuminance) {
		                //System.out.println("Illuminance: " + illuminance/100.0 + " lx");
		                MqttMessage message = new MqttMessage();
		    			message.setPayload((illuminance/100.0+"").getBytes());
		    							
						try {
							client.publish("illuminance", message);
							
						} catch (MqttPersistenceException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (MqttException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
		            }
		        });

		        // Set period for illuminance callback to 1s (1000ms) without a threshold
		        al.setIlluminanceCallbackConfiguration(callbackinterval, true, 'x', 0, 0);

		     // Add decibel listener
		        spl.addDecibelListener(new BrickletSoundPressureLevel.DecibelListener() {
		            public void decibel(int decibel) {
		               // System.out.println("Decibel: " + decibel/10.0 + " dB(A)");
		                MqttMessage message = new MqttMessage();
		    			message.setPayload((decibel/100.0+"").getBytes());
		    							
						try {
							client.publish("noise", message);
							
						} catch (MqttPersistenceException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (MqttException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		        });

		        // Set period for decibel callback to 1s (1000ms) without a threshold
		        spl.setDecibelCallbackConfiguration(callbackinterval, true, 'x', 0, 0);

		        
		        System.out.println("Press key to exit"); System.in.read();
		        client.disconnect();
		        ipcon.disconnect();
			
		}catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NetworkException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} catch (AlreadyConnectedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} // Connect to brickd
    // Don't use device before ipcon is connected
		catch (TinkerforgeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	}
	

}
	

