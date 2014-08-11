package hu.akoel.grawit.gui;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.gui.DataPanel.Mode;
import hu.akoel.grawit.pages.PageBase;
import hu.akoel.grawit.tree.PageBaseTree;
import hu.akoel.grawit.tree.datamodel.PageBaseDataModelPage;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class PageBasePagePanel extends DataPanel{
	
	private static final long serialVersionUID = -9038879802467565947L;

	private PageBaseTree tree; 
	private PageBaseDataModelPage selectedNode;
	private Mode mode;
	private PageBase pageBase;
	
	private JLabel labelName;
	private JTextField fieldName;
	private JLabel labelDetails;
	private JTextArea fieldDetails;
	
	public PageBasePagePanel( PageBaseTree tree, PageBaseDataModelPage selectedNode, Mode mode ){
		//super( mode, selectedNode.getPageBase().getName() + " :: " + CommonOperations.getTranslation("tree.pagebase"));
		super( mode, CommonOperations.getTranslation("tree.pagebase") );

		this.tree = tree;
		this.selectedNode = selectedNode;
		this.mode = mode;
		
		pageBase = selectedNode.getPageBase();
		
		//Name
		labelName = new JLabel( CommonOperations.getTranslation("section.title.name") + ": ");
		if( mode.equals( Mode.CAPTURE ) ){
			fieldName = new JTextField( "" );
		}else{
			fieldName = new JTextField( pageBase.getName());
		}
		
		//Details
		labelDetails = new JLabel( CommonOperations.getTranslation("section.title.details") + ": ");
		if( mode.equals( Mode.CAPTURE ) ){		
			fieldDetails = new JTextArea( "", 5, 15);
		}else{
			fieldDetails = new JTextArea( pageBase.getDetails(), 5, 15);
		}
		JScrollPane scrollDetails = new JScrollPane(fieldDetails);

		this.add( labelName, fieldName );
		this.add( labelDetails, scrollDetails );
	}
	
	@Override
	public void save() {
		
		//Az esetleges hibak szamara legyartva
		LinkedHashMap<Component, String> errorList = new LinkedHashMap<Component, String>();

		//Ertekek trimmelese
		fieldName.setText( fieldName.getText().trim() );
		fieldDetails.setText( fieldDetails.getText().trim() );
		
		//
		//Hibak eseten a hibas mezok osszegyujtese
		//
		
		if( fieldName.getText().length() == 0 ){
			errorList.put( 
					fieldName,
					MessageFormat.format(
							CommonOperations.getTranslation("section.errormessage.emptyfield"), 
							"'"+labelName.getText()+"'"
					)
			);
		}else{

			//Megnezi, hogy a szulo node-jaban van-e masik azonos nevu elem
			TreeNode parentNode = selectedNode.getParent();
			int childrenCount = parentNode.getChildCount();
			for( int i = 0; i < childrenCount; i++ ){
				TreeNode childrenNode = parentNode.getChildAt( i );
				
				//Ha Page-rol van szo (nyilvan az, nem lehet mas)
				if( childrenNode instanceof PageBaseDataModelPage ){
					
					//Ha azonos a nev
					if( ((PageBaseDataModelPage) childrenNode).getPageBase().getName().equals( fieldName.getText() ) ){
					
						//Ha rogzites van, vagy ha modositas, de a vizsgalt node kulonbozik a modositott-tol
						if( mode.equals( Mode.CAPTURE ) || ( mode.equals( Mode.MODIFY ) && !childrenNode.equals(selectedNode) ) ){
										
							errorList.put( 
								fieldName, 
								MessageFormat.format( 
										CommonOperations.getTranslation("section.errormessage.duplicateelement"), 
										fieldName.getText(), 
										CommonOperations.getTranslation("tree.pagebase") 
								) 
							);
							break;
						}
					}
				}
			}
		}
		
		//Volt hiba
		if( errorList.size() != 0 ){
			
			//Hibajelzes
			this.errorAt( errorList );
		
		//Ha nem volt hiba akkor a valtozok veglegesitese
		}else{
			
			//Modositas eseten
			if( mode.equals(Mode.MODIFY ) ){
				pageBase.setName( fieldName.getText() );
				pageBase.setDetails( fieldDetails.getText() );
			
			//Uj rogzites eseten
			}else if( mode.equals( Mode.CAPTURE ) ){
				
				DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)selectedNode.getParent();
				int selectedNodeIndex = parentNode.getIndex( selectedNode );
				PageBase pageBase = new PageBase( fieldName.getText(), fieldDetails.getText() );				
				PageBaseDataModelPage newPageBasePage = new PageBaseDataModelPage( pageBase );
				//parentNode.add( newPageBaseNode );
				parentNode.insert( newPageBasePage, selectedNodeIndex);
				
			}
			
			//A fa-ban is modositja a nevet (ha az valtozott)
			tree.changed();
		}
	}
}
