package hu.akoel.grawit.gui.editor.component;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.Tag;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VariableParametersStringComponent extends JPanel implements VariableParametersComponentInterface{
	
	private static final long serialVersionUID = -5111211582850994473L;
	
	private final String ATTR_VALUE = "value";
	
	private JTextField fieldString;

	public VariableParametersStringComponent(){
		super();
		
		common();
	}
	
	private void common(){
		this.setLayout( new GridBagLayout() );
		
		JLabel labelString = new JLabel( CommonOperations.getTranslation("editor.title.variabletype.string.string") );
		JTextField fieldString = new JTextField("");
		
		int gridY = 0;
		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets(0,0,0,0);
		
		c.gridy = gridY;
		c.gridx = 0;
		c.gridwidth = 0;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelString, c );
		
		c.gridy = gridY;
		c.gridx = 1;
		c.gridwidth = 0;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldString, c );
		
	}
	
	@Override
	public void setEnableModify(boolean enable) {
		fieldString.setEditable( enable );		
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public Tag getTag() {
		return TAG;
	}
	
	@Override
	public void getXMLElement(Document document, Element elementNode ) {
		Attr attr;
		
		Element parameterNode;

		//Node element
		parameterNode = document.createElement( VariableParametersStringComponent.this.getTag().getName() );
		attr = document.createAttribute( ATTR_VALUE );
		attr.setValue( fieldString.getText() );
		parameterNode.setAttributeNode(attr);	
		elementNode.appendChild( parameterNode);

	}



}
