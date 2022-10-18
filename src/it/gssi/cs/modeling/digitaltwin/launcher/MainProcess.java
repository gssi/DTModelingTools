package it.gssi.cs.modeling.digitaltwin.launcher;

import it.gssi.cs.modeling.digitaltwin.shadow.MqttPub;
import it.gssi.cs.modeling.digitaltwin.shadow.Subscriber;

public class MainProcess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ArrayBlockingQueue<E> q = new
		Subscriber sub =new Subscriber();
		MqttPub pub = new MqttPub();
		
		EplEvlStandaloneExample engine  = new EplEvlStandaloneExample();
		
		
	}

}
