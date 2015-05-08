package hu.akoel.grawit.exceptions;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;

public abstract class ElementException extends Exception{

	private static final long serialVersionUID = -7365125686038678803L;
	private BaseElementDataModelAdapter baseElement;
	
	public ElementException( BaseElementDataModelAdapter baseElement, String message, Throwable exception ){
		super( message, exception );
		this.baseElement = baseElement;
	}
	
	public BaseElementDataModelAdapter getBaseElement(){
		return baseElement;
	}
	
	public abstract String getMessage();
	
	public JComponent getLink() {
		
		//Gombot hozok letre inkabb a link helyett
		JButton elementLink = new JButton( getBaseElement().getName() );
		
		//Balra igazitja a szoveget a gombon
		elementLink.setHorizontalAlignment( SwingConstants.LEFT );
		
		//A gombnak nem rajzolom ki a keretet
		elementLink.setBorderPainted( false );
		
		//Atlatszova teszem
		elementLink.setOpaque( false );
		
		//Alahuzom es kekszinure festem
		Font font = elementLink.getFont();
		Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>(font.getAttributes() );
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		elementLink.setFont(font.deriveFont(attributes));
		elementLink.setForeground( new Color( 0, 0, 153 ) );		
		
		elementLink.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
System.err.println("Ugras az elemre");					
			}
		});
		
		return elementLink;
	}

	
}
