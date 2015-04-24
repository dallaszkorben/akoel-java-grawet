package hu.akoel.grawit.gui.tree;

import java.awt.Color;
import java.awt.Component;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepLoopCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepNormalCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseStepCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.trace.TraceDataModel;

public class TraceTree extends JTree {

	private static final long serialVersionUID = -7539183206534337777L;
	
	public TraceTree( TraceDataModel root ) {		
		super( new DefaultTreeModel( root ) );
		
		this.setCellRenderer(new MyTreeCellRenderer() );

/*		
		this.addTreeWillExpandListener( new TreeWillExpandListener() {
			
			@Override
			public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
				 throw new ExpandVetoException(event);
				
			}
			
			@Override
			public void treeWillCollapse(TreeExpansionEvent event)	throws ExpandVetoException {
				  throw new ExpandVetoException(event);				
			}
		});
*/		
	}
	

	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded) {

    	ImageIcon caseIcon = CommonOperations.createImageIcon("tree/testcase-case-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/testcase-folder-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/testcase-folder-open-icon.png");
    	ImageIcon rootIcon = CommonOperations.createImageIcon("tree/root-icon.png");
    	
    	ImageIcon normalElementIcon = CommonOperations.createImageIcon("tree/base-element-normal-icon.png");
    	ImageIcon scriptElementIcon = CommonOperations.createImageIcon("tree/base-element-script-icon.png");
    	
    	ImageIcon stepContainer = CommonOperations.createImageIcon("tree/testcase-container-icon.png");

    	ImageIcon pageSpecificIcon = CommonOperations.createImageIcon("tree/param-page-specific-icon.png");
    	ImageIcon loopOpenIcon = CommonOperations.createImageIcon("tree/param-loop-icon.png");
    	//ImageIcon loopClosedIcon = CommonOperations.createImageIcon("tree/param-loop-icon.png");
    	//ImageIcon pageSpecificIcon = CommonOperations.createImageIcon("tree/param-page-specific-icon.png");
    	  
    	if( actualNode instanceof TestcaseRootDataModel){
            return rootIcon;
    	}else if( actualNode instanceof TestcaseCaseDataModel){
            return caseIcon;
    	}else if( actualNode instanceof TestcaseFolderDataModel){
    		if( expanded ){
    			return nodeOpenIcon;
    		}else{
    			return nodeClosedIcon;
    		}   		
        }else if( actualNode instanceof StepRootDataModel){        	
            return rootIcon; 
        }else if( actualNode instanceof TestcaseStepCollectorDataModel ){    		
    		TestcaseStepCollectorDataModel testCasePage = (TestcaseStepCollectorDataModel)actualNode;
    	    if( testCasePage.getStepCollector() instanceof StepNormalCollectorDataModel ){
    	    	return pageSpecificIcon;
    	    	//return stepContainer;	
    	    }else if( testCasePage.getStepCollector() instanceof StepLoopCollectorDataModel ){
    	    	return loopOpenIcon;
    	    }
    	/*}else if( actualNode instanceof StepNormalCollectorDataModel){    		
    		return pageSpecificIcon;
      	}else if( actualNode instanceof StepLoopCollectorDataModel ){
    		if( expanded ){
    			return loopOpenIcon;
    		}else{
    			return loopClosedIcon;    			
    		}*/  
    	}else if( actualNode instanceof StepElementDataModel ){    		
    		if( ((StepElementDataModel)actualNode).getBaseElement() instanceof NormalBaseElementDataModel ){
    			return normalElementIcon;
    		}else if( ((StepElementDataModel)actualNode).getBaseElement() instanceof ScriptBaseElementDataModel ){
    			return scriptElementIcon;
    		}     	
        }  
    	
		return null;
	}

	public ImageIcon getIconOff(DataModelAdapter actualNode, boolean expanded) {

    	ImageIcon caseIcon = CommonOperations.createImageIcon("tree/testcase-case-off-icon.png");
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

    	}else if( actualNode instanceof TestcaseCaseDataModel){
                return caseIcon;
                
    	}else{
    		return getIcon(actualNode, expanded);
        }
    	
    	return null;

	}
	
	public void select( DataModelAdapter dataModel ){
		
/*		TestcaseCaseDataModel
		StepElementDataModel
		StepCollectorDataModelAdapter
*/
		
		TreeNode treeNode = getTreeNodeByDataModel( (TraceDataModel)this.getModel().getRoot(), dataModel );
		if( null != treeNode ){
			TreePath treePath = CommonOperations.getTreePathFromNode( treeNode );
			this.setSelectionPath( treePath );
		}	
	}
	
	public void collapse( DataModelAdapter dataModel ){
		
//		if( dataModel instanceof TestcaseCaseDataModel ){
			
			TreeNode treeNode = getTreeNodeByDataModel( (TraceDataModel)this.getModel().getRoot(), dataModel );
			if( null != treeNode ){
				TreePath treePath = CommonOperations.getTreePathFromNode( treeNode );
				this.collapsePath( treePath );
			}	
//		}		
	}
	
	/**
	 * 
	 * Visszaadja a CloneDataModel-ben talalhato node-ot megfeleltetve a DataModelAdapter-nek.
	 * Csak hot ugyan az a StepElement vagy StepCollcetor elofordulhat tobbszor is ugyan abban
	 * vagy mas TestcaseCase-ben. Igy tehat nem egyertelmu az osszerendeles.
	 * 
	 * A helyes es egyertelmu osszerendeles erdekeben a kovetkezot kell tenni
	 * TestCaseStepCollector keresesekor meg kell adni 
	 * 
	 * @param actualNode
	 * @param dataModelForSearch
	 * @return
	 */
	public TreeNode getTreeNodeByDataModel( TraceDataModel actualNode, DataModelAdapter dataModelForSearch ){
		
		if( actualNode.getDataModel().hashCode() == dataModelForSearch.hashCode() ){
			return actualNode;
		}
		
		Enumeration children = actualNode.children();

		if (children != null) {
			while (children.hasMoreElements()) {

				//Megvizsgalja a gyerekeit
				TraceDataModel nextElement = (TraceDataModel) children.nextElement();
				TreeNode treeNode = getTreeNodeByDataModel( nextElement, dataModelForSearch );
				
				//Ha valamelyik gyereke azonos a keresett elemmel
				if( null != treeNode ){
					
					//Akkor visszater
					return treeNode;
				}
			}
		}		
		return null;
	}

	class MyTreeCellRenderer extends JLabel implements TreeCellRenderer {

		private static final long serialVersionUID = 1323618892737458100L;
		
		@Override
	    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean isLeaf, int row, boolean focused) {

			if( value instanceof TraceDataModel ){
				TraceDataModel clonedValue = (TraceDataModel)value;
				DataModelAdapter dataModelValue = clonedValue.getDataModel();
			
				//Ha nem engedelyezett a Ki/Be kapcsolas vagy ha engedelyezett, de be van kapcsolava
				if( !dataModelValue.isEnabledToTurnOnOff() || dataModelValue.isOn() ){
    			
					setIcon( TraceTree.this.getIcon( dataModelValue, expanded ) );		
					setForeground( Color.black );	   
    			
					//Ha engedelyezett a Ki/Be kapcsolas es ki van kapcsolva	
				}else{
    			
					setIcon( TraceTree.this.getIconOff( dataModelValue, expanded ) );
					setForeground( Color.gray );   			
				}				
							
				setText( dataModelValue.getName() );
			
			}
			return this;
	    }	
	}
}
