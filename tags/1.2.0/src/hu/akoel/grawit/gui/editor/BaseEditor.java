package hu.akoel.grawit.gui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class BaseEditor extends JPanel{

	private static final long serialVersionUID = -4669982467734711318L;
	
	private JPanel labelSection;
	private JLabel operationSection;
	private JPanel dataSection = new JPanel();
	private boolean scrollEnabled = true;
	
	public JPanel getLabelSection(){
		return labelSection;
	}
	
	public JLabel getOperationSection(){
		return operationSection;
	}
	
	public JPanel getDataSection(){
		return dataSection;
	}
	
	public boolean isScrollEnabled(){
		return scrollEnabled;
	}
	
	public void setScrollEnabled( boolean enabledScroll ){
		this.scrollEnabled = enabledScroll;
	}
	
	public BaseEditor( String element ){
		
		this.setLayout( new BorderLayout() );
		this.setBorder( BorderFactory.createLoweredBevelBorder());
		
		//
		// Headline terulet
		//
		JPanel headlinePanel = new JPanel();
		if( null != element ){
			headlinePanel.setBackground( Color.white );
		}	
		headlinePanel.setLayout( new BorderLayout());	
		super.add( headlinePanel, BorderLayout.NORTH );
		
		//
		// Cim szekcio
		//
		labelSection = new JPanel();
		if( null != element ){
			labelSection.setBackground( Color.white );
		}
		headlinePanel.add( labelSection, BorderLayout.CENTER );
		
		JLabel label = new JLabel( element );		
		label.setText( element );
		labelSection.add( label );
		label.setFont(new Font( label.getFont().getName(), Font.BOLD, 20 ));
		
		//
		// Operation icon section
		//
		operationSection = new JLabel();
		operationSection.setBorder( BorderFactory.createEmptyBorder(7, 10, 7, 10));
		headlinePanel.add( operationSection, BorderLayout.WEST );
		
		//
		// Adat szekcio
		//
		getDataSection().setLayout( new GridBagLayout() );
		getDataSection().setBorder( BorderFactory.createEmptyBorder( 20, 10, 10, 10 ) );
		super.add( getDataSection(), BorderLayout.CENTER );
		
	}

}
