package hu.akoel.grawit;

import java.awt.EventQueue;
import hu.akoel.grawit.gui.GUIFrame;

import org.openqa.selenium.WebDriver;

public class Grawit {
	private static final String title = "Grawit";
	private static final String version = "0.0.4";
	
	private static int frameWidth = 1000;
	private static int frameHeight = 600;
	
	WebDriver driver;

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	new Grawit();
            }
        });		
	}

	public Grawit(){
		
		//
		// Properties betoltese
		//
		Properties.getInstance().load();
		 
		//
		// Window letrehzasa
		//		
		new GUIFrame( title, version, frameWidth, frameHeight );
		
	}
}


