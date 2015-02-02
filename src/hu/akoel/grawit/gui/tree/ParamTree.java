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
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.TestcaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamCollectorDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamLoopCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamNodeDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamNormalCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseNodeDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseParamContainerDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.EmptyEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.param.ParamElementEditor;
import hu.akoel.grawit.gui.editor.param.ParamLoopCollectorEditor;
import hu.akoel.grawit.gui.editor.param.ParamFolderEditor;
import hu.akoel.grawit.gui.editor.param.ParamNormalCollectorEditor;

public class ParamTree extends Tree {

	private static final long serialVersionUID = -7537783206534337777L;
	private GUIFrame guiFrame;	
	private VariableRootDataModel variableRootDataModel;
	private BaseRootDataModel baseRootDataModel;
	private ParamRootDataModel paramRootDataModel;
	private TestcaseRootDataModel testcaseRootDataModel;
	
	public ParamTree(GUIFrame guiFrame, VariableRootDataModel variableRootDataModel, BaseRootDataModel baseRootDataModel, ParamRootDataModel paramRootDataModel, TestcaseRootDataModel testcaseRootDataModel ) {
		super(guiFrame, paramRootDataModel);
		
		this.guiFrame = guiFrame;
		this.baseRootDataModel = baseRootDataModel;
		this.variableRootDataModel = variableRootDataModel;
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
    	if( actualNode instanceof ParamRootDataModel){
            return rootIcon;
            
    	}else if( actualNode instanceof ParamNormalCollectorDataModel){
    		
    		//if(null == ((ParamNormalCollectorDataModel)actualNode).getBaseCollector() ){
    			
    			//return pageNonSpecificIcon;
    		
    		//}else{
    			
    			return pageSpecificIcon;
    		//}

    	}else if( actualNode instanceof ParamElementDataModel ){
    		
    		if( ((ParamElementDataModel)actualNode).getBaseElement() instanceof NormalBaseElementDataModel ){
    			return normalElementIcon;
    		}else if( ((ParamElementDataModel)actualNode).getBaseElement() instanceof ScriptBaseElementDataModel ){
    			return scriptElementIcon;
    		}
    		
    	}else if( actualNode instanceof ParamLoopCollectorDataModel ){

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
    	if( actualNode instanceof ParamElementDataModel ){
    		if( ((ParamElementDataModel)actualNode).getBaseElement() instanceof NormalBaseElementDataModel ){
    			return elementNormalOffIcon;
    		}else if( ((ParamElementDataModel)actualNode).getBaseElement() instanceof ScriptBaseElementDataModel ){
    			return elementSpecialOffIcon;
    		}
    		
    	}else if( actualNode instanceof ParamLoopCollectorDataModel ){
            return loopOffIcon;            

    	}else{
    		return getIcon(actualNode, expanded);
        }
    	
    	return null;
	}
	
	@Override
	public void doViewWhenSelectionChanged(DataModelAdapter selectedNode) {
		
		if( selectedNode instanceof ParamNormalCollectorDataModel ){
			ParamNormalCollectorEditor paramPageEditor = new ParamNormalCollectorEditor( this, (ParamNormalCollectorDataModel)selectedNode, baseRootDataModel, EditMode.VIEW );								
			guiFrame.showEditorPanel( paramPageEditor);
						
		}else if( selectedNode instanceof ParamElementDataModel ){
			ParamElementEditor pageBaseElementEditor = new ParamElementEditor( this, (ParamElementDataModel)selectedNode, baseRootDataModel, paramRootDataModel, variableRootDataModel, EditMode.VIEW );	
			guiFrame.showEditorPanel( pageBaseElementEditor);									
		
		}else if( selectedNode instanceof ParamLoopCollectorDataModel ){
			ParamLoopCollectorEditor testcaseControlLoopEditor = new ParamLoopCollectorEditor( this, (ParamLoopCollectorDataModel)selectedNode, baseRootDataModel, EditMode.VIEW );
			guiFrame.showEditorPanel( testcaseControlLoopEditor);	
		
		//Ha a root-ot valasztottam
		}else if( selectedNode instanceof ParamRootDataModel ){
			EmptyEditor emptyPanel = new EmptyEditor();								
			guiFrame.showEditorPanel( emptyPanel );
			
		}else if( selectedNode instanceof ParamFolderDataModel ){
			ParamFolderEditor paramNodeEditor = new ParamFolderEditor( this, (ParamFolderDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( paramNodeEditor);								

								
			
		}
		
	}

	@Override
	public void doModifyWithPopupEdit(DataModelAdapter selectedNode) {

		if( selectedNode instanceof ParamNormalCollectorDataModel ){
			
			ParamNormalCollectorEditor paramPageEditor = new ParamNormalCollectorEditor( this, (ParamNormalCollectorDataModel)selectedNode, baseRootDataModel, EditMode.MODIFY );							                                            
			guiFrame.showEditorPanel( paramPageEditor);		
			
		}else if( selectedNode instanceof ParamElementDataModel ){

			ParamElementEditor paramElementEditor = new ParamElementEditor( this, (ParamElementDataModel)selectedNode, baseRootDataModel, paramRootDataModel, variableRootDataModel, EditMode.MODIFY );
			guiFrame.showEditorPanel( paramElementEditor);		
				
		}else if( selectedNode instanceof ParamLoopCollectorDataModel ){
			ParamLoopCollectorEditor testcaseControlLoopEditor = new ParamLoopCollectorEditor( this, (ParamLoopCollectorDataModel)selectedNode, baseRootDataModel, EditMode.MODIFY );
			guiFrame.showEditorPanel( testcaseControlLoopEditor);									

		}else if( selectedNode instanceof ParamFolderDataModel ){
				
			ParamFolderEditor paramNodeEditor = new ParamFolderEditor( this, (ParamFolderDataModel)selectedNode, EditMode.MODIFY );								
			guiFrame.showEditorPanel( paramNodeEditor);								
				
		}		
	}

	@Override
	public void doPopupInsert( JPopupMenu popupMenu, final DataModelAdapter selectedNode) {
		
		//
		// Normal gyujto eseten
		//
		if( selectedNode instanceof ParamNormalCollectorDataModel ){

			//Insert Relative Element
			JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.param.element") );
			insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ParamElementEditor paramPageNodeEditor = new ParamElementEditor( ParamTree.this, (ParamNormalCollectorDataModel)selectedNode, baseRootDataModel, paramRootDataModel, variableRootDataModel );								
					guiFrame.showEditorPanel( paramPageNodeEditor);								
				
				}
			});
			popupMenu.add ( insertElementMenu );		
		
		//
		// Control LOOP eseten
		//
		} else if( selectedNode instanceof ParamLoopCollectorDataModel ){

			//Insert Page
			JMenuItem insertParamPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.param.element") );
			insertParamPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertParamPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ParamElementEditor testcaseParamPageEditor = new ParamElementEditor( ParamTree.this, (ParamLoopCollectorDataModel)selectedNode, baseRootDataModel, paramRootDataModel, variableRootDataModel );								
					guiFrame.showEditorPanel( testcaseParamPageEditor);								
				
				}
			});
			popupMenu.add ( insertParamPageMenu );
		
		
		//
		// Csomopont eseten
		//
		}else if( selectedNode instanceof ParamFolderDataModel ){

			//Insert Node
			JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.folder") );
			insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNodeMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ParamFolderEditor paramNodeEditor = new ParamFolderEditor( ParamTree.this, (ParamFolderDataModel)selectedNode );								
					guiFrame.showEditorPanel( paramNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNodeMenu );

			//Insert Normal Param Collector
			JMenuItem insertPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.param.normalcollector") );
			insertPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					ParamNormalCollectorEditor paramPageEditor = new ParamNormalCollectorEditor( ParamTree.this, (ParamFolderDataModel)selectedNode, ParamTree.this.baseRootDataModel );								
					guiFrame.showEditorPanel( paramPageEditor);								
				
				}
			});
			popupMenu.add ( insertPageMenu );
			
			//Insert Loop Param Collector
			JMenuItem insertLoopMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.param.loopcollector") );
			insertLoopMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertLoopMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ParamLoopCollectorEditor testcaseControlLoopEditor = new ParamLoopCollectorEditor( ParamTree.this, (ParamFolderDataModel)selectedNode, baseRootDataModel );
					guiFrame.showEditorPanel( testcaseControlLoopEditor);			
					
				
				}
			});
			popupMenu.add ( insertLoopMenu );			
			
		}
		
	}

	@Override
	public void doDuplicate( final JPopupMenu popupMenu, final DataModelAdapter selectedNode, final int selectedRow,	final DefaultTreeModel totalTreeModel) {
		
		JMenuItem duplicateMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.duplicate") );
		duplicateMenu.setActionCommand( ActionCommand.DUPLICATE.name());
		duplicateMenu.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Ha a kivalasztott csomopont szuloje ParamDataModel - annak kell lennie :)
				if( selectedNode.getParent() instanceof ParamDataModelAdapter ){
					
					//Akkor megduplikalja 
					ParamDataModelAdapter duplicated = (ParamDataModelAdapter)selectedNode.clone();
					
					//!!! Ki kell torolni a szulot, hiszen a kovetkezo add() fuggveny fogja ezt neki adni !!!
					duplicated.setParent(null);
					
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
				ArrayList<TestcaseParamContainerDataModel> foundTestcaseContainerList = findAllParamInTestcase( (ParamDataModelAdapter)selectedNode, testcaseRootDataModel, new ArrayList<TestcaseParamContainerDataModel>() );

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
						ParamTree.this.setSelectionRow(selectedRow - 1);
						
					}										
				}
			}			
			
		});
		popupMenu.add ( deleteMenu );
			
	
	}

	/**
	 * A megadott nodeToDelete Node es az osszes gyermekenek megletet keresi a Testcase fastrukturaban.
	 * 
	 * @param nodeToDelete
	 * @param rootTestcaseDataModel
	 * @param foundDataModel
	 * @return
	 */
	private ArrayList<TestcaseParamContainerDataModel> findAllParamInTestcase( ParamDataModelAdapter nodeToDelete, TestcaseDataModelAdapter rootTestcaseDataModel, ArrayList<TestcaseParamContainerDataModel> foundDataModel ){
		
		findOneParamInTestcase( nodeToDelete, rootTestcaseDataModel, foundDataModel );
		
		//Most pedig vegig megyek a torlendo ParamModel gyermekein is
		Enumeration<ParamDataModelAdapter> enumForParamModel = nodeToDelete.children();
		while( enumForParamModel.hasMoreElements() ){
		
			ParamDataModelAdapter childrenOfParam = enumForParamModel.nextElement();
		
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
	private ArrayList<TestcaseParamContainerDataModel> findOneParamInTestcase( ParamDataModelAdapter nodeToDelete, TestcaseDataModelAdapter testcaseDataModel, ArrayList<TestcaseParamContainerDataModel> foundDataModel ){
				
		//TestcaseDataModelAdapter copyTestcaseModel = (TestcaseDataModelAdapter) testcaseDataModel.clone();
		@SuppressWarnings("unchecked")
		//Enumeration<TestcaseDataModelAdapter> enumForTestcaseModel = copyTestcaseModel.children();
		Enumeration<TestcaseDataModelAdapter> enumForTestcaseModel = testcaseDataModel.children();
		ParamCollectorDataModelAdapter paramCollector;
		
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
				
				ParamFolderEditor paramNodeEditor = new ParamFolderEditor( ParamTree.this, (ParamNodeDataModelAdapter)selectedNode );								
				guiFrame.showEditorPanel( paramNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );
		
	}

	@Override
	public boolean possibleHierarchy(DefaultMutableTreeNode draggedNode, Object dropObject) {

		//Node elhelyezese Node-ba vagy Root-ba
		if( draggedNode instanceof ParamFolderDataModel && dropObject instanceof ParamFolderDataModel ){
			return true;

		//Param Page elhelyezese Node-ba de nem Root-ba	
		}else if( draggedNode instanceof ParamNormalCollectorDataModel && dropObject instanceof ParamFolderDataModel && !( dropObject instanceof ParamRootDataModel ) ){
			return true;

		//Elem elhelyezese Specific Page-be	
		}else if( draggedNode instanceof ParamElementDataModel && dropObject instanceof ParamNormalCollectorDataModel ){
			return true;

		//Loop elhelyezese Node-ban
		}else if( draggedNode instanceof ParamLoopCollectorDataModel && dropObject instanceof ParamFolderDataModel ){
			return true;

		//Element elhelyezese Loop-ban
		}else if( draggedNode instanceof ParamElementDataModel && dropObject instanceof ParamLoopCollectorDataModel ){
			return true;

		}	
		
		return false;
	}

}
