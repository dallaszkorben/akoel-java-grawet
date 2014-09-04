package hu.akoel.grawit.gui.editors.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ScriptComponent extends JPanel implements EditorComponentInterface{

	private static final long serialVersionUID = 4455598880902151255L;

	private JTextArea fieldScriptPre;
	private JTextArea fieldScript;
	private JTextArea fieldScriptPost;
	
	private static final Color STATIC_SCRIPT_COLOR = new Color(94, 129, 135 );
		
	public ScriptComponent( String scriptPre, String script, String scriptPost ){
		super();
		
		common( scriptPre, script, scriptPost );		
		
	}
	
	private void common( String scriptPre, String script, String scriptPost ){
		
		this.fieldScriptPre = new JTextArea( scriptPre );
		this.setBorder( this.fieldScriptPre.getBorder() );
		//this.setBackground( Color.white );
		
		this.fieldScriptPre.setEditable(false);
		this.fieldScriptPre.setBorder( BorderFactory.createEmptyBorder() );
		this.fieldScriptPre.setForeground( STATIC_SCRIPT_COLOR );
		this.fieldScriptPre.setLineWrap(true);
		
		this.fieldScript = new JTextArea( script );
		this.fieldScript.setBorder( BorderFactory.createEmptyBorder() );
		this.fieldScript.setTabSize(2);
		this.fieldScript.setLineWrap(true);
		
		this.fieldScriptPost = new JTextArea( scriptPost );
		this.fieldScriptPost.setEditable(false);
		this.fieldScriptPost.setBorder( BorderFactory.createEmptyBorder() );
		this.fieldScriptPost.setForeground( STATIC_SCRIPT_COLOR );
		this.fieldScriptPost.setLineWrap(true);
		
		this.setLayout( new GridBagLayout() );
		
		int gridY = 0;
		int gridX = 0;
		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets( -2, -2, -2, -2 );
		c.ipadx = 0;
		c.ipady = 0;
		
		c.gridy = gridY;
		c.gridx = gridX;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldScriptPre, c );
		
		gridY++;
		c.gridy = gridY;
		c.gridx = gridX;
		c.gridwidth = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldScript, c );		
		
		gridY++;
		c.gridy = gridY;
		c.gridx = gridX;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( fieldScriptPost, c );
		
	}	
	
	@Override
	public void setEnableModify(boolean enable) {
		fieldScript.setEditable( enable );		
	}

	@Override
	public Component getComponent() {
		return this;
	}


	public String getScript() {
		return fieldScript.getText();
	}

}
