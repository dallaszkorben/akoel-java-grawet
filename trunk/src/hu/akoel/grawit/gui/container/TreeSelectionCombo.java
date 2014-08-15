package hu.akoel.grawit.gui.container;

import hu.akoel.grawit.gui.tree.datamodel.PageBaseNodeDataModel;
import hu.akoel.grawit.gui.tree.datamodel.PageBasePageDataModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TreeSelectionCombo extends JPanel{

	private static final long serialVersionUID = 6475998839112722226L;

	private JButton button;
	private JTextField field = new JTextField();
	
	private PageBasePageDataModel pageBase = null;
	
	private JFrame parent;
	
	private void common( JFrame parent ){
		this.parent = parent;		
		this.setLayout(new BorderLayout());
		
		this.add( field, BorderLayout.CENTER);
		this.add( button, BorderLayout.EAST );
	
		button = new JButton("...");
		
		this.button.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
	
				TreeSelector selector = new TreeSelector( TreeSelectionCombo.this.parent, null );
			}
		} );

	}
	
	
	public TreeSelectionCombo( JFrame parent, PageBasePageDataModel pageBaseNodel ){
		super();
	
		field.setText( pageBaseNodel.getPageBase().getName() );
	
		common( parent );		
	}
	
	public TreeSelectionCombo( JFrame parent ){
		super();
		
		field.setText("");
		
		common( parent );
				
	}
	
	public void sePageBase( PageBasePageDataModel pageBase ){
		this.pageBase = pageBase;
		this.field.setText( pageBase.getPageBase().getName() );
	}
}

class TreeSelector extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1607956458285776550L;
	
	public TreeSelector( JFrame parent, PageBaseNodeDataModel nodeDataModel ){
		this.add( new JLabel("hello"));
		
		this.pack();
		this.setVisible( true );
	}
	
	public void actionPerformed(ActionEvent e) {
	    setVisible(false); 
	    dispose(); 
	  }
}
	