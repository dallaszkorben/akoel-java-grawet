package hu.akoel.grawit.gui.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.operations.CompareListToVariableOperation;
import hu.akoel.grawit.core.operations.CompareTextToStoredElementOperation;
import hu.akoel.grawit.core.operations.CompareTextToVariableOperation;
import hu.akoel.grawit.core.operations.CompareValueToVariableOperation;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.operations.HasVariableOperationInterface;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.VariableDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamLoopCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseParamContainerDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableFolderNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.variable.VariableElementEditor;
import hu.akoel.grawit.gui.editor.variable.VariableNodeEditor;
import hu.akoel.grawit.gui.editor.EmptyEditor;

public class VariableTree extends Tree{

	private static final long serialVersionUID = 6810815920672285062L;
	private GUIFrame guiFrame;
	ParamRootDataModel paramRootDataModel;
	
	public VariableTree(GUIFrame guiFrame, VariableRootDataModel variableRootDataModel, ParamRootDataModel paramRootDataModel) {
		super(guiFrame, variableRootDataModel);
		this.guiFrame = guiFrame;
		
		this.paramRootDataModel = paramRootDataModel;
	}

	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded) {

    	//ImageIcon pageIcon = CommonOperations.createImageIcon("tree/pagebase-page-icon.png");
    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/variable-element-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/variable-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/variable-node-open-icon.png");
    	ImageIcon rootIcon = CommonOperations.createImageIcon("tree/root-icon.png");
  
    	//Iconja a NODE-nak
    	if( actualNode instanceof VariableRootDataModel){
            return rootIcon;
            
    	}else if( actualNode instanceof VariableFolderNodeDataModel){
    		if( expanded ){
    			return nodeOpenIcon;
    		}else{
    			return nodeClosedIcon;
    		}
        
    	}if( actualNode instanceof VariableElementDataModel ){
    		return elementIcon;
    	}
    	return null;
	}

	@Override
	public void doViewWhenSelectionChanged(DataModelAdapter selectedNode) {
	
		//Ha egyaltalan valamilyen egergombot benyomtam
		if( selectedNode instanceof VariableRootDataModel ){
			EmptyEditor emptyPanel = new EmptyEditor();								
			guiFrame.showEditorPanel( emptyPanel );
		
		}else if( selectedNode instanceof VariableFolderNodeDataModel ){
			VariableNodeEditor variableNodeEditor = new VariableNodeEditor(this, (VariableFolderNodeDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( variableNodeEditor);								
		
		}else if( selectedNode instanceof VariableElementDataModel ){
			VariableElementEditor variableElementEditor = new VariableElementEditor( this, (VariableElementDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( variableElementEditor);		
								
		}		
	}

	@Override
	public void doModifyWithPopupEdit(DataModelAdapter selectedNode) {
		
		if( selectedNode instanceof VariableFolderNodeDataModel ){
			
			VariableNodeEditor variableNodeEditor = new VariableNodeEditor( VariableTree.this, (VariableFolderNodeDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( variableNodeEditor);								
			
		}else if( selectedNode instanceof VariableElementDataModel ){
			
			VariableElementEditor variableElementEditor = new VariableElementEditor( VariableTree.this, (VariableElementDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( variableElementEditor);	
		}
	}

	@Override
	public void doPopupInsert( final JPopupMenu popupMenu, final DataModelAdapter selectedNode) {

		//
		// Csomopont eseten
		//
		if( selectedNode instanceof VariableFolderNodeDataModel ){

			//Insert Node
			JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.folder") );
			insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNodeMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					VariableNodeEditor variableNodeEditor = new VariableNodeEditor( VariableTree.this, (VariableFolderNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( variableNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNodeMenu );

			//Insert Element
			JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.variable.element") );
			insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
							
					VariableElementEditor variableElementEditor = new VariableElementEditor( VariableTree.this, (VariableFolderNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( variableElementEditor);								
				
				}
			});
			popupMenu.add ( insertElementMenu );
			
		}	
	
		
	}

	@Override
	public void doDuplicate( final JPopupMenu popupMenu, final DataModelAdapter selectedNode, final int selectedRow, final DefaultTreeModel totalTreeModel) {
		
		JMenuItem duplicateMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.duplicate") );
		duplicateMenu.setActionCommand( ActionCommand.DUPLICATE.name());
		duplicateMenu.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				//Ha a kivalasztott csomopont szuloje VariableDataModel - annak kell lennie :)
				if( selectedNode.getParent() instanceof VariableDataModelAdapter ){
					
					//Akkor megduplikalja 
					VariableDataModelAdapter duplicated = (VariableDataModelAdapter)selectedNode.clone();
					
					//!!! Ki kell torolni a szulot, hiszen a kovetkezo add() fuggveny fogja ezt neki adni !!!
					duplicated.setParent(null);

					//Es hozzaadja a szulohoz
					((VariableDataModelAdapter)selectedNode.getParent()).add( duplicated );

					//Felfrissitem a Tree-t
					VariableTree.this.changed();
				
				}

			}
		});
		popupMenu.add ( duplicateMenu );
	}
	
	@Override
	public void doPopupDelete( final JPopupMenu popupMenu, final DataModelAdapter selectedNode, final int selectedRow, final DefaultTreeModel totalTreeModel ) {
	
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
				
				int n = 0;
				
				//Eloszor is vegig vizsgalom, hogy van-e hivatkozas a Variable-re vagy valamelyik gyermekere a Param-ban
				//Akkor megnezi, hogy van-e hivatkozas a tartalmazott elemekre a Testcase fastrukturaban
				ArrayList<ParamDataModelAdapter> foundParamNodeList = findAllVariableInParam( (VariableDataModelAdapter)selectedNode, paramRootDataModel, new ArrayList<ParamDataModelAdapter>() );
				
				StringBuilder listMessage = new StringBuilder();
				int rows = 0;
				
				//Ha van eleme a listanak, akkor volt hivatkozas, es ossze kell gyujteni a hivatkozasi pontokat (max 10 db)
				for( ParamDataModelAdapter foundParamNode: foundParamNodeList ){

					StringBuilder pathToParamNodeString = new StringBuilder();	
					TreeNode[] pathArray = foundParamNode.getPath();
		
					if( foundParamNode instanceof ParamElementDataModel ){
						
						pathToParamNodeString.append( "(" + ((ParamElementDataModel)foundParamNode).getBaseElement().getName() + ") <= "  ); //Mit talalat
						
					}else if( foundParamNode instanceof ParamLoopCollectorDataModel ){
						
						pathToParamNodeString.append( "(" + ((ParamLoopCollectorDataModel)foundParamNode).getCompareBaseElement().getName() + ") <= "  ); //Mit talalat
						
					}
					
					for( int i = 0; i < pathArray.length; i++ ){							
						pathToParamNodeString.append( (i == 0 ? "": " -> ") );
						pathToParamNodeString.append( ( (ParamDataModelAdapter)pathArray[i] ).getName() ); //Hol talalhato
					}
					
					listMessage.append( pathToParamNodeString.toString() + "\n");

					rows++;
					if( rows >= 10 ){
						listMessage.append( "...\n" );
						break;
					}

				}
				
				//Ha van fuggo elem akkor nem torolheto
				if( listMessage.length() != 0 ){
					
					JOptionPane.showMessageDialog(
							guiFrame,
							"Nem torolheto\n Hivatkozas tortenik a (Base elemre) => a megadott eleresu Param elemekben\n\n"+ 
							
							listMessage,
							"ablak cime",
							JOptionPane.ERROR_MESSAGE
					);
				
				//kulonben egy megerosito kerdest kell feltennem
				}else{								
				
					//Egy ures elemrol van szo, nyugodtan torolhetem
					if( selectedNode.getChildCount() == 0 ){

						n = JOptionPane.showOptionDialog(guiFrame,							
								MessageFormat.format( 
										CommonOperations.getTranslation("mesage.question.delete.treeelement.alone"), 
										selectedNode.getNodeTypeToShow(),
										selectedNode.getName()
								),							
								CommonOperations.getTranslation("editor.windowtitle.confirmation.delete"),
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null,
								options,
								options[0]);
	
					//ha vannak gyermekei, akkor mas a kerdes
					}else{
					
						n = JOptionPane.showOptionDialog(
							guiFrame,							
							MessageFormat.format( 
									CommonOperations.getTranslation("mesage.question.delete.treeelement.withchildren"), 
									selectedNode.getNodeTypeToShow(),
									selectedNode.getName()
							),							
							CommonOperations.getTranslation("editor.windowtitle.confirmation.delete"),
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.WARNING_MESSAGE,
							null,
							options,
							options[0]);					
					}
				
				
					//Ha megengedi a torlest a felhasznalo 
					if( n == 1 ){
												 					
						//Tulajdonkeppen csak levalasztom a fastrukturarol
//						totalTreeModel.removeNodeFromParent( selectedNode );
//						VariableTree.this.setSelectionRow(selectedRow - 1);
						
					}										
				}
				
				
				
				
				
				
				
			}
											
				
		});
		popupMenu.add ( deleteMenu );
			
	}

	private ArrayList<ParamDataModelAdapter> findAllVariableInParam( VariableDataModelAdapter nodeToDelete, ParamDataModelAdapter rootParamDataModel, ArrayList<ParamDataModelAdapter> foundDataModel ){
		
		//Megnezi, hogy az adott BaseNode-ra van-e hivatkozas a Param strukturaban
		findOneVariableInParam( nodeToDelete, rootParamDataModel, foundDataModel );
		
		//Most pedig vegig megy az adott VariableNode gyermekein is
		Enumeration<VariableDataModelAdapter> enumForVariableModel = nodeToDelete.children();
		while( enumForVariableModel.hasMoreElements() ){
		
			VariableDataModelAdapter childrenOfParam = enumForVariableModel.nextElement();
		
			//Es megnezi, hogy van-e ra hivatkozas a ParamData strukturaban
			findAllVariableInParam( childrenOfParam, rootParamDataModel, foundDataModel );
		}
		return foundDataModel;
	}
	
	
	private ArrayList<ParamDataModelAdapter> findOneVariableInParam( VariableDataModelAdapter nodeToDelete, ParamDataModelAdapter paramDataModel, ArrayList<ParamDataModelAdapter> foundDataModel ){
		
		ParamDataModelAdapter copyParamModel = (ParamDataModelAdapter) paramDataModel.clone();
		@SuppressWarnings("unchecked")
//Enumeration<ParamDataModelAdapter> enumForParamModel = copyParamModel.children();
		Enumeration<ParamDataModelAdapter> enumForParamModel = paramDataModel.children();
		//BaseElementDataModelAdapter baseCollector;
		ElementOperationAdapter operation;
		VariableElementDataModel variableElement;
		
		//Vegig megy a param fastrukturan es megnezi, hogy az ott levo Node hivatkozik-e a megadott nodeToDelet-re
		while( enumForParamModel.hasMoreElements() ){
			ParamDataModelAdapter nextParamModel = (ParamDataModelAdapter)enumForParamModel.nextElement();
		
			//Ha ParamElementDataModel a vizsgalt node
			if( nextParamModel instanceof ParamElementDataModel ){
				
				//Megszerzi a ParamElement muveletet
				operation = ((ParamElementDataModel)nextParamModel).getElementOperation();
				
				//Van benne Variable vonatkozas
				if( operation instanceof HasVariableOperationInterface ){
					variableElement = ((HasVariableOperationInterface)operation).getVariableElement();
				
					//Es ha ez megegyezik a keresett nodeToDelete-vel
					if( variableElement.equals( nodeToDelete ) ){
						foundDataModel.add((ParamElementDataModel)nextParamModel);
					}					
				}				
			
			//Ha ParamLoop
			}else if( nextParamModel instanceof ParamLoopCollectorDataModel ){
				
				//Megszerzi a hivatkoztott VariableElement-et
				operation = ((ParamLoopCollectorDataModel)nextParamModel).getElementOperation();
				
				//Van benne Variable vonatkozas
				if( operation instanceof HasVariableOperationInterface ){
					variableElement = ((HasVariableOperationInterface)operation).getVariableElement();
				
					//Es ha ez megegyezik a keresett nodeToDelete-vel
					if( variableElement.equals( nodeToDelete ) ){
						foundDataModel.add((ParamElementDataModel)nextParamModel);
					}					
				}				
			}
			
			//Ha vannak gyerekei a Testcase Node-nak
			if( !nextParamModel.isLeaf() ){
				
				//Akkor tovabb keres
				findOneVariableInParam( nodeToDelete, nextParamModel, foundDataModel );
			}
		}

		return foundDataModel; 
	}
	
	@Override
	public void doPopupRootInsert( JPopupMenu popupMenu, final DataModelAdapter selectedNode ) {

		//Insert Node
		JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.folder") );
		insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
		insertNodeMenu.addActionListener( new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				VariableNodeEditor variableNodeEditor = new VariableNodeEditor( VariableTree.this, (VariableFolderNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( variableNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );
		
		
		//Insert Element
		JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.variable.element" ) );
		insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
		insertElementMenu.addActionListener( new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
							
				VariableElementEditor variableElementEditor = new VariableElementEditor( VariableTree.this, (VariableFolderNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( variableElementEditor);								
			
			}
		});
		popupMenu.add ( insertElementMenu );
		
	}

	@Override
	public boolean possibleHierarchy(DefaultMutableTreeNode draggedNode, Object dropObject) {

		//Node elhelyezese Node-ba
		if( draggedNode instanceof VariableFolderNodeDataModel && dropObject instanceof VariableFolderNodeDataModel ){
			return true;
		
		//Element elhelyezese Node-ba
		}else if( draggedNode instanceof VariableElementDataModel && dropObject instanceof VariableFolderNodeDataModel ){
			return true;
		
		//Node elhelyezese Root-ba			
		}else if( draggedNode instanceof VariableFolderNodeDataModel && dropObject instanceof VariableRootDataModel ){
			return true;
		
		//Elem elhelyezese Root-ba	
		}else if( draggedNode instanceof VariableElementDataModel && dropObject instanceof VariableRootDataModel ){
			return true;
			
		}

		//Minden egyeb eset tilos
		return false;
	}

}





