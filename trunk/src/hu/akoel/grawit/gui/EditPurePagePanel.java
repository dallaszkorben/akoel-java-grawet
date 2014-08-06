package hu.akoel.grawit.gui;

import hu.akoel.grawit.tree.node.PageBaseDataModelNode;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditPurePagePanel extends JPanel{

	private static final long serialVersionUID = -6084357053425935174L;

	public EditPurePagePanel( PageBaseDataModelNode selectedNode ){
		JLabel label = new JLabel("name: ");
		JTextField textField = new JTextField( selectedNode.getName());
		this.add(label);
		this.add( textField );
		
	}
	
}
