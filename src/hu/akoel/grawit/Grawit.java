package hu.akoel.grawit;

import java.awt.EventQueue;
import hu.akoel.grawit.gui.GUIFrame;

import org.openqa.selenium.WebDriver;

public class Grawit {
	private static final String title = "Grawit";
	private static final String version = "1.0.0";
	private static final String designer = "akoelSoft";
	
	//private static final String grawitIniDirectory = ".grawit";
	
	private static int frameWidth = 1200;
	private static int frameHeight = 600;
	
	WebDriver driver;

	public static void main(String args[]) {
		//System.err.println(System.getProperty("sun.arch.data.model")); 
		//System.err.println(CommonOperations.getSettingsDirectory( grawitIniDirectory ));
		
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	new Grawit();
            }
        });		
	}

	public Grawit(){
		
		//
		// Settings betoltese
		//
		WorkingDirectory.getInstance();
		 
		//
		// Window letrehzasa
		//		
		new GUIFrame( title, version, designer, frameWidth, frameHeight );
		
	}
}


