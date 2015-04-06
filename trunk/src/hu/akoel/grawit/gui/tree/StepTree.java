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
import hu.akoel.grawit.core.operations.CompareListToConstantOperation;
import hu.akoel.grawit.core.operations.CompareListToStoredElementOperation;
import hu.akoel.grawit.core.operations.CompareTextToConstantOperation;
import hu.akoel.grawit.core.operations.CompareTextToStoredElementOperation;
import hu.akoel.grawit.core.operations.CompareValueToConstantOperation;
import hu.akoel.grawit.core.operations.CompareValueToStoredElementOperation;
import hu.akoel.grawit.core.operations.ContainListConstantOperation;
import hu.akoel.grawit.core.operations.ContainListStoredElementOperation;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.operations.FillWithBaseElementOperation;
import hu.akoel.grawit.core.operations.FillWithConstantElementOperation;
import hu.akoel.grawit.core.operations.GainListToElementStorageOperation;
import hu.akoel.grawit.core.operations.GainTextToElementStorageOperation;
import hu.akoel.grawit.core.operations.GainValueToElementStorageOperation;
import hu.akoel.grawit.core.operations.HasConstantOperationInterface;
import hu.akoel.grawit.core.operations.HasElementOperationInterface;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepCollectorDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.step.StepDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.step.StepElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepLoopCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepNodeDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.step.StepNormalCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseNodeDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseParamContainerDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseRootDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.EmptyEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.step.StepElementEditor;
import hu.akoel.grawit.gui.editor.step.StepFolderEditor;
import hu.akoel.grawit.gui.editor.step.StepLoopCollectorEditor;
import hu.akoel.grawit.gui.editor.step.StepNormalCollectorEditor;

public class StepTree extends Tree {

	private static final long serialVersionUID = -7537783206534337777L;
	private GUIFrame guiFrame;	
	private ConstantRootDataModel constantRootDataModel;
	private BaseRootDataModel baseRootDataModel;
	private StepRootDataModel paramRootDataModel;
	private TestcaseRootDataModel testcaseRootDataModel;
	
	public StepTree(  String functionName, GUIFrame guiFrame, ConstantRootDataModel constantRootDataModel, BaseRootDataModel baseRootDataModel, StepRootDataModel paramRootDataModel, TestcaseRootDataModel testcaseRootDataModel ) {
		super( functionName, guiFrame, paramRootDataModel );
		
		this.guiFrame = guiFrame;
		this.baseRootDataModel = baseRootDataModel;
		this.constantRootDataModel = constantRootDataModel;
		this.paramRootDataModel = paramRootDataModel;
		this.testcaseRootDataModel = testcaseRootDataModel;
		
	}

	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded) {

//		ImageIcon pageIcon = CommonOperations.createImageIcon("tree/param-page-icon.png");
		ImageIcon pageSpecificIcon = CommonOperations.createImageIcon("tree/param-page-specific-icon.png");
//		ImageIcon pageNonSpecificIcon = CommonOperations.createImageIcon("tree/param-page-nonspecific-icon.png");
    	ImageIcon normalElementIcon = CommonOperations.createImageIcon("tree/param-element-normal-icon.png");
    	ImageIcon scriptElementIcon = CommonOperations.createImageIcon("tree/param-element-script-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/param-folder-closed-icon.png");
    	ImageIcon folderOpenIcon = CommonOperations.createImageIcon("tree/param-folder-open-icon.png");
    	ImageIcon loopOpenIcon = CommonOperations.createImageIcon("tree/param-loop-icon.png");
    	ImageIcon loopClosedIcon = CommonOperations.createImageIcon("tree/param-loop-icon.png");
    	ImageIcon rootIcon = CommonOperations.createImageIcon("tree/root-icon.png");
    	
    	//Iconja a NODE-nak
    	if( actualNode instanceof StepRootDataModel){
            return rootIcon;
            
    	}else if( actualNode instanceof StepNormalCollectorDataModel){
    		
    		//if(null == ((ParamNormalCollectorDataModel)actualNode).getBaseCollector() ){
    			
    			//return pageNonSpecificIcon;
    		
    		//}else{
    			
    			return pageSpecificIcon;
    		//}

    	}else if( actualNode instanceof StepElementDataModel ){
    		
    		if( ((StepElementDataModel)actualNode).getBaseElement() instanceof NormalBaseElementDataModel ){
    			return normalElementIcon;
    		}else if( ((StepElementDataModel)actualNode).getBaseElement() instanceof ScriptBaseElementDataModel ){
    			return scriptElementIcon;
    		}
    		
    	}else if( actualNode instanceof StepLoopCollectorDataModel ){

    		if( expanded ){
    			return loopOpenIcon;
    		}else{
    			return loopClosedIcon;
    			
    		}
    		
    	}else if( actualNode instanceof DataModelAdapter){
    		if( expanded ){
    			return folderOpenIcon;
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
    	if( actualNode instanceof StepElementDataModel ){
    		if( ((StepElementDataModel)actualNode).getBaseElement() instanceof NormalBaseElementDataModel ){
    			return elementNormalOffIcon;
    		}else if( ((StepElementDataModel)actualNode).getBaseElement() instanceof ScriptBaseElementDataModel ){
    			return elementSpecialOffIcon;
    		}
    		
    	}else if( actualNode instanceof StepLoopCollectorDataModel ){
            return loopOffIcon;            

    	}else{
    		return getIcon(actualNode, expanded);
        }
    	
    	return null;
	}
	
	@Override
	public void doViewWhenSelectionChanged(DataModelAdapter selectedNode) {
		
		if( selectedNode instanceof StepNormalCollectorDataModel ){
			StepNormalCollectorEditor paramPageEditor = new StepNormalCollectorEditor( this, (StepNormalCollectorDataModel)selectedNode, baseRootDataModel, EditMode.VIEW );								
			guiFrame.showEditorPanel( paramPageEditor);
						
		}else if( selectedNode instanceof StepElementDataModel ){
			StepElementEditor pageBaseElementEditor = new StepElementEditor( this, (StepElementDataModel)selectedNode, baseRootDataModel, paramRootDataModel, constantRootDataModel, EditMode.VIEW );	
			guiFrame.showEditorPanel( pageBaseElementEditor);									
		
		}else if( selectedNode instanceof StepLoopCollectorDataModel ){
			StepLoopCollectorEditor testcaseControlLoopEditor = new StepLoopCollectorEditor( this, (StepLoopCollectorDataModel)selectedNode, constantRootDataModel, baseRootDataModel, EditMode.VIEW );
			guiFrame.showEditorPanel( testcaseControlLoopEditor);	
		
		//Ha a root-ot valasztottam
		}else if( selectedNode instanceof StepRootDataModel ){
			EmptyEditor emptyPanel = new EmptyEditor();								
			guiFrame.showEditorPanel( emptyPanel );
			
		}else if( selectedNode instanceof StepFolderDataModel ){
			StepFolderEditor paramNodeEditor = new StepFolderEditor( this, (StepFolderDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( paramNodeEditor);								

								
			
		}
		
	}

	@Override
	public void doModifyWithPopupEdit(DataModelAdapter selectedNode) {

		if( selectedNode instanceof StepNormalCollectorDataModel ){
			
			StepNormalCollectorEditor paramPageEditor = new StepNormalCollectorEditor( this, (StepNormalCollectorDataModel)selectedNode, baseRootDataModel, EditMode.MODIFY );							                                            
			guiFrame.showEditorPanel( paramPageEditor);		
			
		}else if( selectedNode instanceof StepElementDataModel ){

			StepElementEditor paramElementEditor = new StepElementEditor( this, (StepElementDataModel)selectedNode, baseRootDataModel, paramRootDataModel, constantRootDataModel, EditMode.MODIFY );
			guiFrame.showEditorPanel( paramElementEditor);		
				
		}else if( selectedNode instanceof StepLoopCollectorDataModel ){
			StepLoopCollectorEditor testcaseControlLoopEditor = new StepLoopCollectorEditor( this, (StepLoopCollectorDataModel)selectedNode, constantRootDataModel, baseRootDataModel, EditMode.MODIFY );
			guiFrame.showEditorPanel( testcaseControlLoopEditor);									

		}else if( selectedNode instanceof StepFolderDataModel ){
				
			StepFolderEditor paramNodeEditor = new StepFolderEditor( this, (StepFolderDataModel)selectedNode, EditMode.MODIFY );								
			guiFrame.showEditorPanel( paramNodeEditor);								
				
		}		
	}

	@Override
	public void doPopupInsert( JPopupMenu popupMenu, final DataModelAdapter selectedNode) {
		
		//
		// Normal gyujto eseten
		//
		if( selectedNode instanceof StepNormalCollectorDataModel ){

			//Insert Relative Element
			JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.step.element") );
			insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					StepElementEditor paramPageNodeEditor = new StepElementEditor( StepTree.this, (StepNormalCollectorDataModel)selectedNode, baseRootDataModel, paramRootDataModel, constantRootDataModel );								
					guiFrame.showEditorPanel( paramPageNodeEditor);								
				
				}
			});
			popupMenu.add ( insertElementMenu );		
		
		//
		// Control LOOP eseten
		//
		} else if( selectedNode instanceof StepLoopCollectorDataModel ){

			//Insert Page
			JMenuItem insertParamPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.step.element") );
			insertParamPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertParamPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					StepElementEditor testcaseParamPageEditor = new StepElementEditor( StepTree.this, (StepLoopCollectorDataModel)selectedNode, baseRootDataModel, paramRootDataModel, constantRootDataModel );								
					guiFrame.showEditorPanel( testcaseParamPageEditor);								
				
				}
			});
			popupMenu.add ( insertParamPageMenu );
		
		
		//
		// Csomopont eseten
		//
		}else if( selectedNode instanceof StepFolderDataModel ){

			//Insert Node
			JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.folder") );
			insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNodeMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					StepFolderEditor paramNodeEditor = new StepFolderEditor( StepTree.this, (StepFolderDataModel)selectedNode );								
					guiFrame.showEditorPanel( paramNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNodeMenu );

			//Insert Normal Param Collector
			JMenuItem insertPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.step.normalcollector") );
			insertPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					StepNormalCollectorEditor paramPageEditor = new StepNormalCollectorEditor( StepTree.this, (StepFolderDataModel)selectedNode, StepTree.this.baseRootDataModel );								
					guiFrame.showEditorPanel( paramPageEditor);								
				
				}
			});
			popupMenu.add ( insertPageMenu );
			
			//Insert Loop Param Collector
			JMenuItem insertLoopMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.step.loopcollector") );
			insertLoopMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertLoopMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					StepLoopCollectorEditor testcaseControlLoopEditor = new StepLoopCollectorEditor( StepTree.this, (StepFolderDataModel)selectedNode, constantRootDataModel, baseRootDataModel );
					guiFrame.showEditorPanel( testcaseControlLoopEditor);			
					
				
				}
			});
			popupMenu.add ( insertLoopMenu );			
			
		}
		
	}

	@Override
	public void doPopupDuplicate( final JPopupMenu popupMenu, final DataModelAdapter selectedNode, final int selectedRow,	final DefaultTreeModel totalTreeModel) {
		
		JMenuItem duplicateMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.duplicate") );
		duplicateMenu.setActionCommand( ActionCommand.DUPLICATE.name());
		duplicateMenu.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Ha a kivalasztott csomopont szuloje ParamDataModel - annak kell lennie :)
				if( selectedNode.getParent() instanceof StepDataModelAdapter ){
					
					//Akkor megduplikalja 
					StepDataModelAdapter duplicated = (StepDataModelAdapter)selectedNode.clone();
					
					//!!! Ki kell torolni a szulot, hiszen a kovetkezo add() fuggveny fogja ezt neki adni !!!
					duplicated.setParent(null);
					
					//Es hozzaadja a szulohoz
					((StepDataModelAdapter)selectedNode.getParent()).add( duplicated );

					//Felfrissitem a Tree-t
					StepTree.this.refreshTreeAfterStructureChanged( (DataModelAdapter)selectedNode, (DataModelAdapter)selectedNode.getParent() );
					//StepTree.this.nodeChanged();
				
				}
				
			}
		});
		popupMenu.add ( duplicateMenu );
	}

	@Override
	public void doPopupDelete( final JPopupMenu popupMenu, final DataModelAdapter selectedNode, final int selectedRow,	final DefaultTreeModel totalTreeModel) {
	
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
					
				int n;
				
				//Eloszor is vegig vizsgalom, hogy van-e hivatkozas az elemre vagy valamelyik gyermekere a Testcase-ben
				//Akkor megnezi, hogy van-e hivatkozas a tartalmazott elemekre a Testcase fastrukturaban
				ArrayList<TestcaseParamContainerDataModel> foundTestcaseContainerList = findAllParamInTestcase( (StepDataModelAdapter)selectedNode, testcaseRootDataModel, new ArrayList<TestcaseParamContainerDataModel>() );

				StringBuilder listMessage = new StringBuilder();
				int rows = 0;
				
				//Ha van eleme a listanak, akkor volt hivatkozas, es ossze kell gyujteni a hivatkozasi pontokat (max 10 db)
				for( TestcaseParamContainerDataModel foundTestcaseContainer: foundTestcaseContainerList ){

					StringBuilder pathToTestCaseContainerString = new StringBuilder();	
					TreeNode[] pathArray = foundTestcaseContainer.getPath();
					pathToTestCaseContainerString.append( "(" + foundTestcaseContainer.getParamPage().getName() + ") <= "  ); //Mit talalat
					for( int i = 0; i < pathArray.length; i++ ){							
						pathToTestCaseContainerString.append( (i == 0 ? "": " -> ") );
						pathToTestCaseContainerString.append( ( (TestcaseDataModelAdapter)pathArray[i] ).getName() ); //Hol talalhato
					}
					
					listMessage.append( pathToTestCaseContainerString.toString() + "\n");
					
					
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
							"Nem torolheto\n Hivatkozas tortenik a (Parameter elemre) => a megadott eleresu Tesztesetekben\n\n"+ 
							
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
						StepTree.this.setSelectionRow(selectedRow - 1);
						
					}										
				}
			}			
			
		});
		popupMenu.add ( deleteMenu );
	
	}
	
	@Override
	public void doPopupLink(JPopupMenu popupMenu, DataModelAdapter selectedNode) {

		if( selectedNode instanceof StepElementDataModel ){
			
			BaseElementDataModelAdapter baseBaseElement = ((StepElementDataModel)selectedNode).getBaseElement();			
			
			JMenuItem linkToBaseElementMenu = new JMenuItem( CommonOperations.getTranslation( "Link to '" + baseBaseElement.getName() + "' Base element" ) );
			linkToBaseElementMenu.setActionCommand( ActionCommand.LINK.name());
			linkToBaseElementMenu.addActionListener( new LinkToElementListener( baseBaseElement ) );
				
			popupMenu.addSeparator();
			popupMenu.add( linkToBaseElementMenu );
			
			ElementOperationAdapter operation = ((StepElementDataModel)selectedNode).getElementOperation();
			if( operation instanceof HasConstantOperationInterface ){
				
				ConstantElementDataModel constantElement = ((HasConstantOperationInterface)operation).getConstantElement();
				JMenuItem linkToConstantElementMenu = new JMenuItem( CommonOperations.getTranslation( "Link to '" + constantElement.getName() + "' Constant element" ) );
				linkToConstantElementMenu.setActionCommand( ActionCommand.LINK.name());
				linkToConstantElementMenu.addActionListener( new LinkToElementListener( constantElement ) );
				popupMenu.add( linkToConstantElementMenu );
				
			}else if( operation instanceof HasElementOperationInterface ){
				
				BaseElementDataModelAdapter baseElement = ((HasElementOperationInterface)operation).getBaseElement();
				JMenuItem linkToConstantElementMenu = new JMenuItem( CommonOperations.getTranslation( "Link to '" + baseElement.getName() + "' Base element" ) );
				linkToConstantElementMenu.setActionCommand( ActionCommand.LINK.name());
				linkToConstantElementMenu.addActionListener( new LinkToElementListener( baseElement ) );
				popupMenu.add( linkToConstantElementMenu );
			}
			
		}
		
	}

	class LinkToElementListener implements ActionListener{
		DataModelAdapter dataModel;
		
		public LinkToElementListener( DataModelAdapter dataModel ){
			this.dataModel = dataModel;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			for( LinkToNodeInTreeListener listener: getLinkToNodeInTreeListeners() ){
				listener.linkToNode( this.dataModel );
			}
			
		}
		
	}

	/**
	 * A megadott nodeToDelete Node es az osszes gyermekenek megletet keresi a Testcase fastrukturaban.
	 * 
	 * @param nodeToDelete
	 * @param rootTestcaseDataModel
	 * @param foundDataModel
	 * @return
	 */
	private ArrayList<TestcaseParamContainerDataModel> findAllParamInTestcase( StepDataModelAdapter nodeToDelete, TestcaseDataModelAdapter rootTestcaseDataModel, ArrayList<TestcaseParamContainerDataModel> foundDataModel ){
		
		findOneParamInTestcase( nodeToDelete, rootTestcaseDataModel, foundDataModel );
		
		//Most pedig vegig megyek a torlendo ParamModel gyermekein is
		Enumeration<StepDataModelAdapter> enumForParamModel = nodeToDelete.children();
		while( enumForParamModel.hasMoreElements() ){
		
			StepDataModelAdapter childrenOfParam = enumForParamModel.nextElement();
		
			findAllParamInTestcase( childrenOfParam, rootTestcaseDataModel, foundDataModel );
		}
		return foundDataModel;
	}
	
	/**
	 * A megadott nodeToDelete Node-ot keresi meg a testcaseDataModel-ben.
	 * Vegig megy a testcaseDataModel-en. Ha talal egy TestcaseParamContainerDataModel tipusu Node-ot, akkor
	 * megnezi, hogy ebben a Node-ban van-e hivatkozas a nodeToDelete Node-ra. Ha igen, akkor elhelyezi a
	 * foundDataModel-ben a megtalalt TestcaseParamContainerDataModel-t
	 * 
	 * @param nodeToDelete
	 * @param testcaseDataModel
	 * @param foundDataModel
	 * @return
	 */
	private ArrayList<TestcaseParamContainerDataModel> findOneParamInTestcase( StepDataModelAdapter nodeToDelete, TestcaseDataModelAdapter testcaseDataModel, ArrayList<TestcaseParamContainerDataModel> foundDataModel ){
				
		@SuppressWarnings("unchecked")
		Enumeration<TestcaseDataModelAdapter> enumForTestcaseModel = testcaseDataModel.children();
		StepCollectorDataModelAdapter paramCollector;
		
		//Vegig megy a teszt fastrukturan es megnezi, hogy az ott levo Node hivatkozik-e a megadott nodeToDelet-re
		while( enumForTestcaseModel.hasMoreElements() ){
			TestcaseDataModelAdapter nextTestcaseModel = (TestcaseDataModelAdapter)enumForTestcaseModel.nextElement();
		
			if( nextTestcaseModel instanceof TestcaseParamContainerDataModel ){
				paramCollector = ((TestcaseParamContainerDataModel)nextTestcaseModel).getParamPage();
				
				//Ha igen, akkor az adott Testcase Node-ot elhelyezi a visszatero parameter-listaba
				if( paramCollector.equals( nodeToDelete ) ){
					foundDataModel.add((TestcaseParamContainerDataModel)nextTestcaseModel);
				}
			
			//Ha vannak gyerekei a Testcase Node-nak
			}else if( !nextTestcaseModel.isLeaf() ){
				findOneParamInTestcase( nodeToDelete, nextTestcaseModel, foundDataModel );
			}
		}

		return foundDataModel; 
	}
	
	
	@Override
	public void doPopupRootInsert( JPopupMenu popupMenu, final DataModelAdapter selectedNode ) {
		
		//Insert Folder
		JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.folder") );
		insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
		insertNodeMenu.addActionListener( new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				StepFolderEditor paramNodeEditor = new StepFolderEditor( StepTree.this, (StepNodeDataModelAdapter)selectedNode );								
				guiFrame.showEditorPanel( paramNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );
		
	}

	@Override
	public boolean possibleHierarchy(DefaultMutableTreeNode draggedNode, Object targetObject) {

		if( draggedNode.equals( targetObject )){
			return false;
		
		//Node elhelyezese Node-ba vagy Root-ba
		}else if( draggedNode instanceof StepFolderDataModel && targetObject instanceof StepFolderDataModel ){
			return true;

		//Param Page elhelyezese Node-ba de nem Root-ba	
		}else if( draggedNode instanceof StepNormalCollectorDataModel && targetObject instanceof StepFolderDataModel && !( targetObject instanceof StepRootDataModel ) ){
			return true;

		//Elem elhelyezese Specific Page-be	
		}else if( draggedNode instanceof StepElementDataModel && targetObject instanceof StepNormalCollectorDataModel ){
			return true;

		//Loop elhelyezese Node-ban
		}else if( draggedNode instanceof StepLoopCollectorDataModel && targetObject instanceof StepFolderDataModel ){
			return true;

		//Element elhelyezese Loop-ban
		}else if( draggedNode instanceof StepElementDataModel && targetObject instanceof StepLoopCollectorDataModel ){
			return true;

		}	
		
		return false;
	}


}
