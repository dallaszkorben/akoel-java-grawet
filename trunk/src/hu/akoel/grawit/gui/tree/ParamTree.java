package hu.akoel.grawit.gui.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.SpecialBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamPageSpecificDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamPageNoSpecificDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.EmptyEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.param.ParamElementEditor;
import hu.akoel.grawit.gui.editor.param.ParamNodeEditor;
import hu.akoel.grawit.gui.editor.param.ParamPageEditor;
import hu.akoel.grawit.gui.editor.param.ParamPageNoSpecificEditor;

public class ParamTree extends Tree {

	private static final long serialVersionUID = -7537783206534337777L;
	private GUIFrame guiFrame;	
	private VariableRootDataModel variableRootDataModel;
	private BaseRootDataModel baseRootDataModel;
	private ParamRootDataModel paramRootDataModel;
	
	public ParamTree(GUIFrame guiFrame, VariableRootDataModel variableRootDataModel, BaseRootDataModel baseRootDataModel, ParamRootDataModel paramRootDataModel ) {
		super(guiFrame, paramRootDataModel);
		
		this.guiFrame = guiFrame;
		this.baseRootDataModel = baseRootDataModel;
		this.variableRootDataModel = variableRootDataModel;
		this.paramRootDataModel = paramRootDataModel;
		
	}

	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded) {

    	ImageIcon pageIcon = CommonOperations.createImageIcon("tree/param-page-specific-icon.png");
    	ImageIcon pageNoSpecificIcon = CommonOperations.createImageIcon("tree/param-page-nospecific-icon.png");
    	ImageIcon normalElementIcon = CommonOperations.createImageIcon("tree/param-element-normal-icon.png");
    	ImageIcon specialElementIcon = CommonOperations.createImageIcon("tree/param-element-special-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/param-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/param-node-open-icon.png");
    	
    	//Iconja a NODE-nak
    	if( actualNode instanceof ParamPageSpecificDataModel){
            return pageIcon;

    	}else if( actualNode instanceof ParamPageNoSpecificDataModel){
    		return pageNoSpecificIcon;

    	}else if( actualNode instanceof ParamElementDataModel ){
    		
    		if( ((ParamElementDataModel)actualNode).getBaseElement() instanceof NormalBaseElementDataModel ){
    			return normalElementIcon;
    		}else if( ((ParamElementDataModel)actualNode).getBaseElement() instanceof SpecialBaseElementDataModel ){
    			return specialElementIcon;
    		}
    		
    	}else if( actualNode instanceof ParamNodeDataModel){
    		if( expanded ){
    			return nodeOpenIcon;
    		}else{
    			return nodeClosedIcon;
    		}
        }
    	
		return null;
	}

	@Override
	public ImageIcon getIconOff(DataModelAdapter actualNode, boolean expanded) {

    	ImageIcon elementNormalOffIcon = CommonOperations.createImageIcon("tree/param-element-normal-off-icon.png");
    	ImageIcon elementSpecialOffIcon = CommonOperations.createImageIcon("tree/param-element-special-off-icon.png");
    	
    	//Iconja a NODE-nak
    	if( actualNode instanceof ParamElementDataModel ){
    		if( ((ParamElementDataModel)actualNode).getBaseElement() instanceof NormalBaseElementDataModel ){
    			return elementNormalOffIcon;
    		}else if( ((ParamElementDataModel)actualNode).getBaseElement() instanceof SpecialBaseElementDataModel ){
    			return elementSpecialOffIcon;
    		}
    	}else{
    		return getIcon(actualNode, expanded);
        }
    	
    	return null;
	}
	
	@Override
	public void doViewWhenSelectionChanged(DataModelAdapter selectedNode) {
		
		//Ha a root-ot valasztottam
		if( selectedNode instanceof ParamRootDataModel ){
			EmptyEditor emptyPanel = new EmptyEditor();								
			guiFrame.showEditorPanel( emptyPanel );
			
		}else if( selectedNode instanceof ParamNodeDataModel ){
			ParamNodeEditor paramNodeEditor = new ParamNodeEditor( this, (ParamNodeDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( paramNodeEditor);								
			
		}else if( selectedNode instanceof ParamPageSpecificDataModel ){
			ParamPageEditor paramPageEditor = new ParamPageEditor( this, (ParamPageSpecificDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( paramPageEditor);
			
		}else if( selectedNode instanceof ParamPageNoSpecificDataModel ){
			ParamPageNoSpecificEditor paramPageNoSpecificEditor = new ParamPageNoSpecificEditor( this, (ParamPageNoSpecificDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( paramPageNoSpecificEditor);					
							
		}else if( selectedNode instanceof ParamElementDataModel ){
			ParamElementEditor pageBaseElementEditor = new ParamElementEditor( this, (ParamElementDataModel)selectedNode, baseRootDataModel, paramRootDataModel, variableRootDataModel, EditMode.VIEW );	
			guiFrame.showEditorPanel( pageBaseElementEditor);									
			
		}
		
	}

	@Override
	public void doModifyWithPopupEdit(DataModelAdapter selectedNode) {
		if( selectedNode instanceof ParamNodeDataModel ){
			
			ParamNodeEditor paramNodeEditor = new ParamNodeEditor( this, (ParamNodeDataModel)selectedNode, EditMode.MODIFY );								
			guiFrame.showEditorPanel( paramNodeEditor);								
				
		}else if( selectedNode instanceof ParamPageSpecificDataModel ){
			
			ParamPageEditor paramPageEditor = new ParamPageEditor( this, (ParamPageSpecificDataModel)selectedNode, EditMode.MODIFY );							                                            
			guiFrame.showEditorPanel( paramPageEditor);		
				
		}else if( selectedNode instanceof ParamPageNoSpecificDataModel ){
			
			ParamPageNoSpecificEditor paramPageEditor = new ParamPageNoSpecificEditor( this, (ParamPageNoSpecificDataModel)selectedNode, EditMode.MODIFY );							                                            
			guiFrame.showEditorPanel( paramPageEditor);		
			
		}else if( selectedNode instanceof ParamElementDataModel ){

			ParamElementEditor paramElementEditor = new ParamElementEditor( this, (ParamElementDataModel)selectedNode, baseRootDataModel, paramRootDataModel, variableRootDataModel, EditMode.MODIFY );
			guiFrame.showEditorPanel( paramElementEditor);		
				
		}		
	}

	@Override
	public void doPopupInsert( JPopupMenu popupMenu, final DataModelAdapter selectedNode) {
		
		//
		// Csomopont eseten
		//
		if( selectedNode instanceof ParamNodeDataModel ){

			//Insert Node
			JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.node") );
			insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNodeMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ParamNodeEditor paramNodeEditor = new ParamNodeEditor( ParamTree.this, (ParamNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( paramNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNodeMenu );

			//Insert Page
			JMenuItem insertPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.param.page") );
			insertPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					ParamPageEditor paramPageEditor = new ParamPageEditor( ParamTree.this, (ParamNodeDataModel)selectedNode, ParamTree.this.baseRootDataModel );								
					guiFrame.showEditorPanel( paramPageEditor);								
				
				}
			});
			popupMenu.add ( insertPageMenu );
			
			//Insert NoSpecific Page
			JMenuItem insertNoSignificantPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.param.nospecificpage") );
			insertNoSignificantPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNoSignificantPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					ParamPageNoSpecificEditor paramPageEditor = new ParamPageNoSpecificEditor( ParamTree.this, (ParamNodeDataModel)selectedNode, ParamTree.this.baseRootDataModel );								
					guiFrame.showEditorPanel( paramPageEditor);								
				
				}
			});
			popupMenu.add ( insertNoSignificantPageMenu );
			
		}		
		
		//
		// Page eseten
		//
		if( selectedNode instanceof ParamPageSpecificDataModel ){

			//Insert Relative Element
			JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.param.relativeelement") );
			insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ParamElementEditor paramPageNodeEditor = new ParamElementEditor( ParamTree.this, (ParamPageSpecificDataModel)selectedNode, baseRootDataModel, paramRootDataModel, variableRootDataModel );								
					guiFrame.showEditorPanel( paramPageNodeEditor);								
				
				}
			});
			popupMenu.add ( insertElementMenu );

		}else if( selectedNode instanceof ParamPageNoSpecificDataModel ){
			
			//Insert Absolute Element
			JMenuItem insertAbsoluteElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.param.absoluteelement") );
			insertAbsoluteElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertAbsoluteElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ParamElementEditor paramPageNodeEditor = new ParamElementEditor( ParamTree.this, (ParamPageNoSpecificDataModel)selectedNode, baseRootDataModel, paramRootDataModel, variableRootDataModel );								
					guiFrame.showEditorPanel( paramPageNodeEditor);								
				
				}
			});
			popupMenu.add ( insertAbsoluteElementMenu );
			
		}
		
	}

	@Override
	public void doDuplicate( final JPopupMenu popupMenu, final DataModelAdapter selectedNode, final int selectedRow,	final DefaultTreeModel totalTreeModel) {
		
		JMenuItem duplicateMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.duplicate") );
		duplicateMenu.setActionCommand( ActionCommand.DUPLICATE.name());
		duplicateMenu.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				/*//Megerosito kerdes
				Object[] options = {
						CommonOperations.getTranslation("button.no"),
						CommonOperations.getTranslation("button.yes")								
				};
				
				int n = JOptionPane.showOptionDialog(guiFrame,							
						MessageFormat.format( 
								CommonOperations.getTranslation("mesage.question.delete.treeelement"), 
								selectedNode.getNodeTypeToShow(),									
								selectedNode.getName()
						),							
						CommonOperations.getTranslation("editor.windowtitle.confirmation.delete"),
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[0]);

				if( n == 1 ){
					totalTreeModel.removeNodeFromParent( selectedNode);
					ParamTree.this.setSelectionRow(selectedRow - 1);
				}
				*/
				
				//Ha a kivalasztott csomopont szuloje ParamDataModel - annak kell lennie :)
				if( selectedNode.getParent() instanceof ParamDataModelAdapter ){
					
					//Akkor megduplikalja 
					ParamDataModelAdapter duplicated = (ParamDataModelAdapter)selectedNode.clone();
					
					//Es hozzaadja a szulohoz
					((ParamDataModelAdapter)selectedNode.getParent()).add( duplicated );

					//Felfrissitem a Tree-t
					ParamTree.this.changed();
				
				}
				
			}
		});
		popupMenu.add ( duplicateMenu );
	}

	@Override
	public void doPopupDelete( final JPopupMenu popupMenu, final DataModelAdapter selectedNode, final int selectedRow,	final DefaultTreeModel totalTreeModel) {
	
		if( selectedNode.getChildCount() == 0 ){
			
			
			JMenuItem deleteMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.delete") );
			deleteMenu.setActionCommand( ActionCommand.UP.name());
			deleteMenu.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					//Megerosito kerdes
					Object[] options = {
							CommonOperations.getTranslation("button.no"),
							CommonOperations.getTranslation("button.yes")								
					};
					
					int n = JOptionPane.showOptionDialog(guiFrame,							
							MessageFormat.format( 
									CommonOperations.getTranslation("mesage.question.delete.treeelement"), 
									selectedNode.getNodeTypeToShow(),									
									selectedNode.getName()
							),							
							CommonOperations.getTranslation("editor.windowtitle.confirmation.delete"),
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							options[0]);

					if( n == 1 ){
						totalTreeModel.removeNodeFromParent( selectedNode);
						ParamTree.this.setSelectionRow(selectedRow - 1);
					}							
				}
			});
			popupMenu.add ( deleteMenu );
			
		}		
	}

	@Override
	public void doPopupRootInsert( JPopupMenu popupMenu, final DataModelAdapter selectedNode ) {
		
		//Insert Node
		JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.node") );
		insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
		insertNodeMenu.addActionListener( new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				ParamNodeEditor paramNodeEditor = new ParamNodeEditor( ParamTree.this, (ParamNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( paramNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );
		
	}

	@Override
	public boolean possibleHierarchy(DefaultMutableTreeNode draggedNode, Object dropObject) {

		//Node elhelyezese Node-ba vagy Root-ba
		if( draggedNode instanceof ParamNodeDataModel && dropObject instanceof ParamNodeDataModel ){
			return true;

		//Page elhelyezese Node-ba de nem Root-ba	
		}else if( draggedNode instanceof ParamPageSpecificDataModel && dropObject instanceof ParamNodeDataModel && !( dropObject instanceof ParamRootDataModel ) ){
			return true;
			
		//Elem elhelyezese Page-be	
		}else if( draggedNode instanceof ParamElementDataModel && dropObject instanceof ParamPageSpecificDataModel ){
			return true;
			
		}	
		
		return false;
	}

}
