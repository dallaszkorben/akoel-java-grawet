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
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamLoopCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamRootDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.base.NormalBaseElementEditor;
import hu.akoel.grawit.gui.editor.base.BaseFolderEditor;
import hu.akoel.grawit.gui.editor.base.BasePageEditor;
import hu.akoel.grawit.gui.editor.base.ScriptBaseElementEditor;
import hu.akoel.grawit.gui.editor.EmptyEditor;

public class BaseTree extends Tree{

	private static final long serialVersionUID = -5965897830877262588L;
	private GUIFrame guiFrame;
	ParamRootDataModel paramRootDataModel;
	
	public BaseTree(GUIFrame guiFrame, BaseRootDataModel rootDataModel, ParamRootDataModel paramRootDataModel ) {
		super(guiFrame, rootDataModel);
		this.guiFrame = guiFrame;
		this.paramRootDataModel = paramRootDataModel;
	}

	@Override
	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded) {

    	ImageIcon pageIcon = CommonOperations.createImageIcon("tree/base-page-icon.png");
    	ImageIcon normalElementIcon = CommonOperations.createImageIcon("tree/base-element-normal-icon.png");
    	ImageIcon scriptElementIcon = CommonOperations.createImageIcon("tree/base-element-script-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/base-folder-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/base-folder-open-icon.png");
    	ImageIcon rootIcon = CommonOperations.createImageIcon("tree/root-icon.png");
  
    	//Iconja a NODE-nak
    	if( actualNode instanceof BaseRootDataModel){
            return rootIcon;
    	}else if( actualNode instanceof BaseCollectorDataModel){
            return pageIcon;
    	}else if( actualNode instanceof NormalBaseElementDataModel ){
            return normalElementIcon;
    	}else if( actualNode instanceof ScriptBaseElementDataModel ){
            return scriptElementIcon;
    	}else if( actualNode instanceof BaseFolderDataModel){
    		if( expanded ){
    			return nodeOpenIcon;
    		}else{
    			return nodeClosedIcon;
    		}
        }
    	return null;
	}

	@Override
	public void doViewWhenSelectionChanged(DataModelAdapter selectedNode) {
	
		//Ha egyaltalan valamilyen egergombot benyomtam
		if( selectedNode instanceof BaseRootDataModel ){
			EmptyEditor emptyPanel = new EmptyEditor();								
			guiFrame.showEditorPanel( emptyPanel );
		
		}else if( selectedNode instanceof BaseFolderDataModel ){
			BaseFolderEditor baseNodeEditor = new BaseFolderEditor(this, (BaseFolderDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( baseNodeEditor);								
		
		}else if( selectedNode instanceof BaseCollectorDataModel ){
			BasePageEditor basePageEditor = new BasePageEditor( this, (BaseCollectorDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( basePageEditor);				
						
		}else if( selectedNode instanceof NormalBaseElementDataModel ){
			NormalBaseElementEditor normalBaseElementEditor = new NormalBaseElementEditor( this, (NormalBaseElementDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( normalBaseElementEditor);		

		}else if( selectedNode instanceof ScriptBaseElementDataModel ){
			ScriptBaseElementEditor specialBaseElementEditor = new ScriptBaseElementEditor( this, (ScriptBaseElementDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( specialBaseElementEditor);		
								
		}			
	}

	@Override
	public void doModifyWithPopupEdit(DataModelAdapter selectedNode) {
		
		if( selectedNode instanceof BaseFolderDataModel ){
							
			BaseFolderEditor baseNodeEditor = new BaseFolderEditor( this, (BaseFolderDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( baseNodeEditor);								
								
		}else if( selectedNode instanceof BaseCollectorDataModel ){
								
			BasePageEditor basePageEditor = new BasePageEditor( this, (BaseCollectorDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( basePageEditor);		
								
		}else if( selectedNode instanceof NormalBaseElementDataModel ){

			NormalBaseElementEditor normalBaseElementEditor = new NormalBaseElementEditor( this, (NormalBaseElementDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( normalBaseElementEditor);		
								
		}else if( selectedNode instanceof ScriptBaseElementDataModel ){

			ScriptBaseElementEditor specialBaseElementEditor = new ScriptBaseElementEditor( this, (ScriptBaseElementDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( specialBaseElementEditor);
								
		}	
	}

	@Override
	public void doPopupInsert( final JPopupMenu popupMenu, final DataModelAdapter selectedNode) {

		//
		// Csomopont eseten
		//
		if( selectedNode instanceof BaseFolderDataModel ){

			//Insert Folder
			JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.folder") );
			insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNodeMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					BaseFolderEditor baseNodeEditor = new BaseFolderEditor( BaseTree.this, (BaseFolderDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNodeMenu );

			//Insert Page
			JMenuItem insertPageMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.base.collector") );
			insertPageMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertPageMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					BasePageEditor baseNodeEditor = new BasePageEditor( BaseTree.this, (BaseFolderDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertPageMenu );
			
			//Insert Normal Element
			JMenuItem insertNormalElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.base.normalelement") );
			insertNormalElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNormalElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					NormalBaseElementEditor baseNodeEditor = new NormalBaseElementEditor( BaseTree.this, (BaseFolderDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNormalElementMenu );
			
			//Insert Script Element
			JMenuItem insertScriptElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.base.specialelement") );
			insertScriptElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertScriptElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ScriptBaseElementEditor baseNodeEditor = new ScriptBaseElementEditor( BaseTree.this, (BaseFolderDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertScriptElementMenu );
		}		
		
		
		//
		// Page eseten
		//
		
		if( selectedNode instanceof BaseCollectorDataModel ){

			//Insert Normal Element
			JMenuItem insertNormalElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.base.normalelement") );
			insertNormalElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNormalElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					NormalBaseElementEditor baseNodeEditor = new NormalBaseElementEditor( BaseTree.this, (BaseCollectorDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNormalElementMenu );

			//Insert Script Element
			JMenuItem insertScriptElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.base.specialelement") );
			insertScriptElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertScriptElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ScriptBaseElementEditor baseNodeEditor = new ScriptBaseElementEditor( BaseTree.this, (BaseCollectorDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertScriptElementMenu );
			
		}
		
	}

	@Override
	public void doDuplicate( final JPopupMenu popupMenu, final DataModelAdapter selectedNode, final int selectedRow, final DefaultTreeModel totalTreeModel) {
		
		JMenuItem duplicateMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.duplicate") );
		duplicateMenu.setActionCommand( ActionCommand.DUPLICATE.name());
		duplicateMenu.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				//Ha a kivalasztott csomopont szuloje BaseDataModel - annak kell lennie :)
				if( selectedNode.getParent() instanceof BaseDataModelAdapter ){
					
					//Akkor megduplikalja 
					BaseDataModelAdapter duplicated = (BaseDataModelAdapter)selectedNode.clone();
					
					//Es hozzaadja a szulohoz
					((BaseDataModelAdapter)selectedNode.getParent()).add( duplicated );

					//Felfrissitem a Tree-t
					BaseTree.this.changed();
				
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
				
				//Eloszor is vegig vizsgalom, hogy van-e hivatkozas az elemre vagy valamelyik gyermekere a Param-ben
				//Akkor megnezi, hogy van-e hivatkozas a tartalmazott elemekre a Testcase fastrukturaban
				ArrayList<ParamDataModelAdapter> foundParamNodeList = findAllBaseInParam( (BaseDataModelAdapter)selectedNode, paramRootDataModel, new ArrayList<ParamDataModelAdapter>() );

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
						totalTreeModel.removeNodeFromParent( selectedNode );
						BaseTree.this.setSelectionRow(selectedRow - 1);
						
					}										
				}
			}
		});
		popupMenu.add ( deleteMenu );	
		
	}

	/**
	 * A megadott nodeToDelete Node es az osszes gyermekenek megletet keresi a Param fastrukturaban.
	 * 
	 * @param nodeToDelete
	 * @param rootParamDataModel
	 * @param foundDataModel
	 * @return
	 */
	private ArrayList<ParamDataModelAdapter> findAllBaseInParam( BaseDataModelAdapter nodeToDelete, ParamDataModelAdapter rootParamDataModel, ArrayList<ParamDataModelAdapter> foundDataModel ){
		
		//Megnezi, hogy az adott BaseNode-ra van-e hivatkozas a Param strukturaban
		findOneBaseInParam( nodeToDelete, rootParamDataModel, foundDataModel );
		
		//Most pedig vegig megy az adott BaseNode gyermekein is
		Enumeration<BaseDataModelAdapter> enumForParamModel = nodeToDelete.children();
		while( enumForParamModel.hasMoreElements() ){
		
			BaseDataModelAdapter childrenOfParam = enumForParamModel.nextElement();
		
			//Es megnezi, hogy van-e ra hivatkozas a ParamData strukturaban
			findAllBaseInParam( childrenOfParam, rootParamDataModel, foundDataModel );
		}
		return foundDataModel;
	}
	
	
	private ArrayList<ParamDataModelAdapter> findOneBaseInParam( BaseDataModelAdapter nodeToDelete, ParamDataModelAdapter paramDataModel, ArrayList<ParamDataModelAdapter> foundDataModel ){
		
		ParamDataModelAdapter copyParamModel = (ParamDataModelAdapter) paramDataModel.clone();
		@SuppressWarnings("unchecked")
		Enumeration<ParamDataModelAdapter> enumForParamModel = copyParamModel.children();
		BaseElementDataModelAdapter baseCollector;
		
		//Vegig megy a param fastrukturan es megnezi, hogy az ott levo Node hivatkozik-e a megadott nodeToDelet-re
		while( enumForParamModel.hasMoreElements() ){
			ParamDataModelAdapter nextParamModel = (ParamDataModelAdapter)enumForParamModel.nextElement();
		
			//Ha ParamElementDataModel a vizsgalt node
			if( nextParamModel instanceof ParamElementDataModel ){
				
				//Megszerzi a hivatkoztott BaseElement-et
				baseCollector = ((ParamElementDataModel)nextParamModel).getBaseElement();
				
				//Ha ez megegyezik a keresett nodeToDelete-vel
				if( baseCollector.equals( nodeToDelete ) ){
					foundDataModel.add((ParamElementDataModel)nextParamModel);
				}
			
			//Ha ParamLoop
			}else if( nextParamModel instanceof ParamLoopCollectorDataModel ){
				
				//Megszerzi a hivatkoztott BaseElement-et
				baseCollector = ((ParamLoopCollectorDataModel)nextParamModel).getCompareBaseElement();
				
				//Ha ez megegyezik a keresett nodeToDelete-vel
				if( baseCollector.equals( nodeToDelete ) ){
					foundDataModel.add((ParamLoopCollectorDataModel)nextParamModel);
				}
				
			}
			
			//Ha vannak gyerekei a Testcase Node-nak
			if( !nextParamModel.isLeaf() ){
				findOneBaseInParam( nodeToDelete, nextParamModel, foundDataModel );
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
				
				
				BaseFolderEditor baseNodeEditor = new BaseFolderEditor( BaseTree.this, (BaseNodeDataModelAdapter)selectedNode );								
				guiFrame.showEditorPanel( baseNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );			
		
	}

	@Override
	public boolean possibleHierarchy(DefaultMutableTreeNode draggedNode, Object dropObject) {

		//Node elhelyezese Node-ba vagy Root-ba
		if( draggedNode instanceof BaseFolderDataModel && dropObject instanceof BaseFolderDataModel ){
			return true;

		//Page elhelyezese Node-ba de nem Root-ba	
		}else if( draggedNode instanceof BaseCollectorDataModel && dropObject instanceof BaseFolderDataModel && !( dropObject instanceof BaseRootDataModel ) ){
			return true;
		
		//NormalElement elhelyezese Page-ben	
		}else if( draggedNode instanceof NormalBaseElementDataModel && dropObject instanceof BaseCollectorDataModel ){
			return true;

		//SpecialElement elhelyezese Page-ben	
		}else if( draggedNode instanceof ScriptBaseElementDataModel && dropObject instanceof BaseCollectorDataModel ){
			return true;

		//NormalElement elhelyezese Node-ban	
		}else if( draggedNode instanceof NormalBaseElementDataModel && dropObject instanceof BaseFolderDataModel ){
			return true;

		//SpecialElement elhelyezese Node-ban	
		}else if( draggedNode instanceof ScriptBaseElementDataModel && dropObject instanceof BaseFolderDataModel ){
			return true;

		}
		
		return false;
	}

}
