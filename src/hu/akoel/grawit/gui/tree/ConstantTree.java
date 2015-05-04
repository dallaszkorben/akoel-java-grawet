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
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.operations.HasConstantOperationInterface;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantFolderNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.step.StepElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepLoopCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseNodeDataModelAdapter;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.constant.ConstantElementEditor;
import hu.akoel.grawit.gui.editor.constant.ConstantNodeEditor;
import hu.akoel.grawit.gui.editor.EmptyEditor;

public class ConstantTree extends Tree{

	private static final long serialVersionUID = 6810815920672285062L;
	private GUIFrame guiFrame;
	StepRootDataModel paramRootDataModel;
	
	public ConstantTree(  String functionName, GUIFrame guiFrame, ConstantRootDataModel constantRootDataModel, StepRootDataModel paramRootDataModel ) {
		super( functionName, guiFrame, constantRootDataModel );
		this.guiFrame = guiFrame;
		
		this.paramRootDataModel = paramRootDataModel;
	}

	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded) {

    	//ImageIcon pageIcon = CommonOperations.createImageIcon("tree/pagebase-page-icon.png");
    	ImageIcon elementIcon = CommonOperations.createImageIcon("tree/constant-element-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/constant-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/constant-node-open-icon.png");
    	ImageIcon rootIcon = CommonOperations.createImageIcon("tree/root-icon.png");
  
    	//Iconja a NODE-nak
    	if( actualNode instanceof ConstantRootDataModel){
            return rootIcon;
            
    	}else if( actualNode instanceof ConstantFolderNodeDataModel){
    		if( expanded ){
    			return nodeOpenIcon;
    		}else{
    			return nodeClosedIcon;
    		}
        
    	}if( actualNode instanceof ConstantElementDataModel ){
    		return elementIcon;
    	}
    	return null;
	}

	@Override
	public void doViewWhenSelectionChanged(DataModelAdapter selectedNode) {
	
		//Ha egyaltalan valamilyen egergombot benyomtam
		if( selectedNode instanceof ConstantRootDataModel ){
			EmptyEditor emptyPanel = new EmptyEditor();								
			guiFrame.showEditorPanel( emptyPanel );
		
		}else if( selectedNode instanceof ConstantFolderNodeDataModel ){
			ConstantNodeEditor constantNodeEditor = new ConstantNodeEditor(this, (ConstantFolderNodeDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( constantNodeEditor);								
		
		}else if( selectedNode instanceof ConstantElementDataModel ){
			ConstantElementEditor constantElementEditor = new ConstantElementEditor( this, (ConstantElementDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( constantElementEditor);		
								
		}		
	}

	@Override
	public void doModifyWithPopupEdit(DataModelAdapter selectedNode) {
		
		if( selectedNode instanceof ConstantFolderNodeDataModel ){
			
			ConstantNodeEditor constantNodeEditor = new ConstantNodeEditor( ConstantTree.this, (ConstantFolderNodeDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( constantNodeEditor);								
			
		}else if( selectedNode instanceof ConstantElementDataModel ){
			
			ConstantElementEditor constantElementEditor = new ConstantElementEditor( ConstantTree.this, (ConstantElementDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( constantElementEditor);	
		}
	}

	@Override
	public void doPopupInsert( final JPopupMenu popupMenu, final DataModelAdapter selectedNode) {

		//
		// Csomopont eseten
		//
		if( selectedNode instanceof ConstantFolderNodeDataModel ){

			//Insert Node
			JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.folder") );
			insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNodeMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ConstantNodeEditor constantNodeEditor = new ConstantNodeEditor( ConstantTree.this, (ConstantFolderNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( constantNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNodeMenu );

			//Insert Element
			JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.constant.element") );
			insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
							
					ConstantElementEditor constantElementEditor = new ConstantElementEditor( ConstantTree.this, (ConstantFolderNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( constantElementEditor);								
				
				}
			});
			popupMenu.add ( insertElementMenu );
			
		}	
	
		
	}

	@Override
	public void doPopupDuplicate( final JPopupMenu popupMenu, final DataModelAdapter selectedNode, final int selectedRow, final DefaultTreeModel totalTreeModel) {
		
		JMenuItem duplicateMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.duplicate") );
		duplicateMenu.setActionCommand( ActionCommand.DUPLICATE.name());
		duplicateMenu.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				//Ha a kivalasztott csomopont szuloje ConstantDataModel - annak kell lennie :)
				if( selectedNode.getParent() instanceof ConstantDataModelAdapter ){
					
					//Akkor megduplikalja 
					ConstantDataModelAdapter duplicated = (ConstantDataModelAdapter)selectedNode.clone();
					
					//!!! Ki kell torolni a szulot, hiszen a kovetkezo add() fuggveny fogja ezt neki adni !!!
					duplicated.setParent(null);

					//Es hozzaadja a szulohoz
					((ConstantDataModelAdapter)selectedNode.getParent()).add( duplicated );

					//Felfrissitem a Tree-t
					//ConstantTree.this.nodeChanged();
					//ConstantTree.this.refreshTreeAfterStructureChanged( (DataModelAdapter)selectedNode, (DataModelAdapter)selectedNode.getParent() );
					ConstantTree.this.refreshTreeAfterStructureChanged( (DataModelAdapter)selectedNode.getParent() );
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
				
				//Eloszor is vegig vizsgalom, hogy van-e hivatkozas a Constant-re vagy valamelyik gyermekere a Param-ban
				//Akkor megnezi, hogy van-e hivatkozas a tartalmazott elemekre a Testcase fastrukturaban
				ArrayList<StepDataModelAdapter> foundParamNodeList = findAllConstantInParam( (ConstantDataModelAdapter)selectedNode, paramRootDataModel, new ArrayList<StepDataModelAdapter>() );
				
				StringBuilder listMessage = new StringBuilder();
				int rows = 0;
				
				//Ha van eleme a listanak, akkor volt hivatkozas, es ossze kell gyujteni a hivatkozasi pontokat (max 10 db)
				for( StepDataModelAdapter foundParamNode: foundParamNodeList ){

					StringBuilder pathToParamNodeString = new StringBuilder();	
					TreeNode[] pathArray = foundParamNode.getPath();
		
					if( foundParamNode instanceof StepElementDataModel ){
						
						pathToParamNodeString.append( "(" + ((StepElementDataModel)foundParamNode).getBaseElement().getName() + ") <= "  ); //Mit talalat
						
					}else if( foundParamNode instanceof StepLoopCollectorDataModel ){
						
						pathToParamNodeString.append( "(" + ((StepLoopCollectorDataModel)foundParamNode).getCompareBaseElement().getName() + ") <= "  ); //Mit talalat
						
					}
					
					for( int i = 0; i < pathArray.length; i++ ){							
						pathToParamNodeString.append( (i == 0 ? "": " -> ") );
						pathToParamNodeString.append( ( (StepDataModelAdapter)pathArray[i] ).getName() ); //Hol talalhato
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
							"Nem torolheto\n Hivatkozas tortenik a (Constant-re) => a megadott eleresu Step elemekben\n\n"+ 
							
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
						totalTreeModel.removeNodeFromParent( selectedNode );
						ConstantTree.this.setSelectionRow(selectedRow - 1);
						
					}										
				}
			}
											
				
		});
		popupMenu.add ( deleteMenu );
			
	}

	private ArrayList<StepDataModelAdapter> findAllConstantInParam( ConstantDataModelAdapter nodeToDelete, StepDataModelAdapter rootParamDataModel, ArrayList<StepDataModelAdapter> foundDataModel ){
		
		//Megnezi, hogy az adott BaseNode-ra van-e hivatkozas a Param strukturaban
		findOneConstantInParam( nodeToDelete, rootParamDataModel, foundDataModel );
		
		//Most pedig vegig megy az adott ConstantNode gyermekein is
		Enumeration<ConstantDataModelAdapter> enumForConstantModel = nodeToDelete.children();
		while( enumForConstantModel.hasMoreElements() ){
		
			ConstantDataModelAdapter childrenOfParam = enumForConstantModel.nextElement();
		
			//Es megnezi, hogy van-e ra hivatkozas a ParamData strukturaban
			findAllConstantInParam( childrenOfParam, rootParamDataModel, foundDataModel );
		}
		return foundDataModel;
	}
	
	
	private ArrayList<StepDataModelAdapter> findOneConstantInParam( ConstantDataModelAdapter nodeToDelete, StepDataModelAdapter paramDataModel, ArrayList<StepDataModelAdapter> foundDataModel ){
		
		@SuppressWarnings("unchecked")
		Enumeration<StepDataModelAdapter> enumForParamModel = paramDataModel.children();
		ElementOperationAdapter operation;
		ConstantElementDataModel constantElement;
		
		//Vegig megy a param fastrukturan es megnezi, hogy az ott levo Node hivatkozik-e a megadott nodeToDelet-re
		while( enumForParamModel.hasMoreElements() ){
			StepDataModelAdapter nextParamModel = (StepDataModelAdapter)enumForParamModel.nextElement();
		
			//Ha ParamElementDataModel a vizsgalt node
			if( nextParamModel instanceof StepElementDataModel ){
				
				//Megszerzi a ParamElement muveletet
				operation = ((StepElementDataModel)nextParamModel).getElementOperation();
				
				//Van benne Constant vonatkozas
				if( operation instanceof HasConstantOperationInterface ){
					constantElement = ((HasConstantOperationInterface)operation).getConstantElement();
				
					//Es ha ez megegyezik a keresett nodeToDelete-vel
					if( constantElement.equals( nodeToDelete ) ){
						foundDataModel.add((StepElementDataModel)nextParamModel);
					}					
				}				
			
			//Ha ParamLoop
			}else if( nextParamModel instanceof StepLoopCollectorDataModel ){
				
				//Megszerzi a hivatkoztott ConstantElement-et
				operation = ((StepLoopCollectorDataModel)nextParamModel).getElementOperation();
				
				//Van benne Constant vonatkozas
				if( operation instanceof HasConstantOperationInterface ){
					constantElement = ((HasConstantOperationInterface)operation).getConstantElement();
				
					//Es ha ez megegyezik a keresett nodeToDelete-vel
					if( constantElement.equals( nodeToDelete ) ){
						foundDataModel.add((StepLoopCollectorDataModel)nextParamModel);
					}					
				}				
			}
			
			//Ha vannak gyerekei a Testcase Node-nak
			if( !nextParamModel.isLeaf() ){
				
				//Akkor tovabb keres
				findOneConstantInParam( nodeToDelete, nextParamModel, foundDataModel );
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
				
				ConstantNodeEditor constantNodeEditor = new ConstantNodeEditor( ConstantTree.this, (ConstantFolderNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( constantNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );
		
		
		//Insert Element
		JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.constant.element" ) );
		insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
		insertElementMenu.addActionListener( new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
							
				ConstantElementEditor constantElementEditor = new ConstantElementEditor( ConstantTree.this, (ConstantFolderNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( constantElementEditor);								
			
			}
		});
		popupMenu.add ( insertElementMenu );
		
	}

	@Override
	public boolean possibleHierarchy(DefaultMutableTreeNode draggedNode, Object targetObject) {

		//Sajat magaba nem helyezheto a Node
		if( draggedNode.equals( targetObject )){
			return false;

		//Ha ugyan oda akarom elhelyezni ahonnan elvettem
		}else if( targetObject instanceof ConstantDataModelAdapter && ((ConstantDataModelAdapter)targetObject).isNodeChild( draggedNode ) ){	
			return false;
			
		//Node elhelyezese Node-ba
		}else if( draggedNode instanceof ConstantFolderNodeDataModel && targetObject instanceof ConstantFolderNodeDataModel ){
			return true;
		
		//Element elhelyezese Node-ba
		}else if( draggedNode instanceof ConstantElementDataModel && targetObject instanceof ConstantFolderNodeDataModel ){
			return true;
		
		//Node elhelyezese Root-ba			
		}else if( draggedNode instanceof ConstantFolderNodeDataModel && targetObject instanceof ConstantRootDataModel ){
			return true;
		
		//Elem elhelyezese Root-ba	
		}else if( draggedNode instanceof ConstantElementDataModel && targetObject instanceof ConstantRootDataModel ){
			return true;
			
		}

		//Minden egyeb eset tilos
		return false;
	}

	@Override
	public void doPopupLink(JPopupMenu popupMenu, DataModelAdapter selectedNode) {
		// TODO Auto-generated method stub
		
	}

}





