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
import hu.akoel.grawit.core.treenodedatamodel.CollectorDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.collector.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.collector.ParamLoopDataModel;
import hu.akoel.grawit.core.treenodedatamodel.collector.ParamNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.collector.ParamPageDataModel;
import hu.akoel.grawit.core.treenodedatamodel.collector.ParamRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.EmptyEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.param.ParamElementEditor;
import hu.akoel.grawit.gui.editor.param.ParamLoopEditor;
import hu.akoel.grawit.gui.editor.param.ParamNodeEditor;
import hu.akoel.grawit.gui.editor.param.ParamPageEditor;

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

		ImageIcon pageIcon = CommonOperations.createImageIcon("tree/param-page-icon.png");
		ImageIcon pageSpecificIcon = CommonOperations.createImageIcon("tree/param-page-specific-icon.png");
		ImageIcon pageNonSpecificIcon = CommonOperations.createImageIcon("tree/param-page-nonspecific-icon.png");
    	ImageIcon normalElementIcon = CommonOperations.createImageIcon("tree/param-element-normal-icon.png");
    	ImageIcon scriptElementIcon = CommonOperations.createImageIcon("tree/param-element-script-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/param-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/param-node-open-icon.png");
    	ImageIcon loopOpenIcon = CommonOperations.createImageIcon("tree/param-loop-icon.png");
    	ImageIcon loopClosedIcon = CommonOperations.createImageIcon("tree/param-loop-icon.png");
    	
    	//Iconja a NODE-nak
    	if( actualNode instanceof ParamPageDataModel){
    		
    		if(null == ((ParamPageDataModel)actualNode).getBasePage() ){
    			
    			return pageNonSpecificIcon;
    		
    		}else{
    			
    			return pageSpecificIcon;
    		}

    	}else if( actualNode instanceof ParamElementDataModel ){
    		
    		if( ((ParamElementDataModel)actualNode).getBaseElement() instanceof NormalBaseElementDataModel ){
    			return normalElementIcon;
    		}else if( ((ParamElementDataModel)actualNode).getBaseElement() instanceof ScriptBaseElementDataModel ){
    			return scriptElementIcon;
    		}
    		
    	}else if( actualNode instanceof ParamLoopDataModel ){

    		if( expanded ){
    			return loopOpenIcon;
    		}else{
    			return loopClosedIcon;
    			
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
    	ImageIcon loopOffIcon = CommonOperations.createImageIcon("tree/param-loop-off-icon.png");
    	
    	//Iconja a NODE-nak
    	if( actualNode instanceof ParamElementDataModel ){
    		if( ((ParamElementDataModel)actualNode).getBaseElement() instanceof NormalBaseElementDataModel ){
    			return elementNormalOffIcon;
    		}else if( ((ParamElementDataModel)actualNode).getBaseElement() instanceof ScriptBaseElementDataModel ){
    			return elementSpecialOffIcon;
    		}
    		
    	}else if( actualNode instanceof ParamLoopDataModel ){
            return loopOffIcon;            

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

		}else if( selectedNode instanceof ParamPageDataModel ){
			ParamPageEditor paramPageEditor = new ParamPageEditor( this, (ParamPageDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( paramPageEditor);
							
		}else if( selectedNode instanceof ParamElementDataModel ){
			ParamElementEditor pageBaseElementEditor = new ParamElementEditor( this, (ParamElementDataModel)selectedNode, baseRootDataModel, paramRootDataModel, variableRootDataModel, EditMode.VIEW );	
			guiFrame.showEditorPanel( pageBaseElementEditor);									
			
		}else if( selectedNode instanceof ParamLoopDataModel ){
			ParamLoopEditor testcaseControlLoopEditor = new ParamLoopEditor( this, (ParamLoopDataModel)selectedNode, baseRootDataModel, EditMode.VIEW );
			guiFrame.showEditorPanel( testcaseControlLoopEditor);									
			
		}
		
	}

	@Override
	public void doModifyWithPopupEdit(DataModelAdapter selectedNode) {
		if( selectedNode instanceof ParamNodeDataModel ){
			
			ParamNodeEditor paramNodeEditor = new ParamNodeEditor( this, (ParamNodeDataModel)selectedNode, EditMode.MODIFY );								
			guiFrame.showEditorPanel( paramNodeEditor);								
			
		}else if( selectedNode instanceof ParamPageDataModel ){
			
			ParamPageEditor paramPageEditor = new ParamPageEditor( this, (ParamPageDataModel)selectedNode, EditMode.MODIFY );							                                            
			guiFrame.showEditorPanel( paramPageEditor);		
			
		}else if( selectedNode instanceof ParamElementDataModel ){

			ParamElementEditor paramElementEditor = new ParamElementEditor( this, (ParamElementDataModel)selectedNode, baseRootDataModel, paramRootDataModel, variableRootDataModel, EditMode.MODIFY );
			guiFrame.showEditorPanel( paramElementEditor);		
				
		}else if( selectedNode instanceof ParamLoopDataModel ){
			ParamLoopEditor testcaseControlLoopEditor = new ParamLoopEditor( this, (ParamLoopDataModel)selectedNode, baseRootDataModel, EditMode.MODIFY );
			guiFrame.showEditorPanel( testcaseControlLoopEditor);									

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
			
			//Insert Control Loop
			JMenuItem insertLoopMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.param.control.loop") );
			insertLoopMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertLoopMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ParamLoopEditor testcaseControlLoopEditor = new ParamLoopEditor( ParamTree.this, (ParamNodeDataModel)selectedNode, baseRootDataModel );
					guiFrame.showEditorPanel( testcaseControlLoopEditor);			
					
				
				}
			});
			popupMenu.add ( insertLoopMenu );			
/*			
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
*/			
		}		
		
		//
		// Page eseten
		//
		if( selectedNode instanceof ParamPageDataModel ){

			//Insert Relative Element
			JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.param.element") );
			insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ParamElementEditor paramPageNodeEditor = new ParamElementEditor( ParamTree.this, (ParamPageDataModel)selectedNode, baseRootDataModel, paramRootDataModel, variableRootDataModel );								
					guiFrame.showEditorPanel( paramPageNodeEditor);								
				
				}
			});
			popupMenu.add ( insertElementMenu );		
		}
		
		//
		// Control LOOP eseten
		//
		if( selectedNode instanceof ParamLoopDataModel ){

			//Insert Page
			JMenuItem insertParamPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.param.element") );
			insertParamPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertParamPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ParamElementEditor testcaseParamPageEditor = new ParamElementEditor( ParamTree.this, (ParamLoopDataModel)selectedNode, baseRootDataModel, paramRootDataModel, variableRootDataModel );								
					guiFrame.showEditorPanel( testcaseParamPageEditor);								
				
				}
			});
			popupMenu.add ( insertParamPageMenu );
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
				if( selectedNode.getParent() instanceof CollectorDataModelAdapter ){
					
					//Akkor megduplikalja 
					CollectorDataModelAdapter duplicated = (CollectorDataModelAdapter)selectedNode.clone();
					
					//Es hozzaadja a szulohoz
					((CollectorDataModelAdapter)selectedNode.getParent()).add( duplicated );

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

		//Param Page elhelyezese Node-ba de nem Root-ba	
		}else if( draggedNode instanceof ParamPageDataModel && dropObject instanceof ParamNodeDataModel && !( dropObject instanceof ParamRootDataModel ) ){
			return true;

		//Elem elhelyezese Specific Page-be	
		}else if( draggedNode instanceof ParamElementDataModel && dropObject instanceof ParamPageDataModel ){
			return true;

		//Loop elhelyezese Node-ban
		}else if( draggedNode instanceof ParamLoopDataModel && dropObject instanceof ParamNodeDataModel ){
			return true;

		//Element elhelyezese Loop-ban
		}else if( draggedNode instanceof ParamElementDataModel && dropObject instanceof ParamLoopDataModel ){
			return true;

		}	
		
		return false;
	}

}
