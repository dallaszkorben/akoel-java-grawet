package hu.akoel.grawit.gui.tree;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepLoopCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepNormalCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseCaseDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseRootDataModel;

public class TraceTree extends JTree {

	private static final long serialVersionUID = -7539183206534337777L;
	
	public TraceTree( DataModelAdapter root ) {		
		super( new DefaultTreeModel( root ) );
		
		this.setCellRenderer(new MyTreeCellRenderer() );
	}
	

	public ImageIcon getIcon(DataModelAdapter actualNode, boolean expanded) {

    	ImageIcon caseIcon = CommonOperations.createImageIcon("tree/testcase-case-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/testcase-folder-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/testcase-folder-open-icon.png");
    	ImageIcon rootIcon = CommonOperations.createImageIcon("tree/root-icon.png");
    	
    	ImageIcon normalElementIcon = CommonOperations.createImageIcon("tree/base-element-normal-icon.png");
    	ImageIcon scriptElementIcon = CommonOperations.createImageIcon("tree/base-element-script-icon.png");
    	
		ImageIcon pageSpecificIcon = CommonOperations.createImageIcon("tree/param-page-specific-icon.png");
    	ImageIcon loopOpenIcon = CommonOperations.createImageIcon("tree/param-loop-icon.png");
    	ImageIcon loopClosedIcon = CommonOperations.createImageIcon("tree/param-loop-icon.png");
    	  
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
    	}else if( actualNode instanceof StepNormalCollectorDataModel){    		
    		return pageSpecificIcon;
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
        }    	
		return null;
	}

	public ImageIcon getIconOff(DataModelAdapter actualNode, boolean expanded) {

    	ImageIcon caseIcon = CommonOperations.createImageIcon("tree/testcase-case-off-icon.png");
    	
    	if( actualNode instanceof TestcaseCaseDataModel){
            return caseIcon;
    	}else{
    		return getIcon(actualNode, expanded);
        }
	}
	
	public void select( DataModelAdapter dataModel ){
		
	}

	class MyTreeCellRenderer extends JLabel implements TreeCellRenderer {

		private static final long serialVersionUID = 1323618892737458100L;
		
		@Override
	    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean isLeaf, int row, boolean focused) {

			//Ha nem engedelyezett a Ki/Be kapcsolas vagy ha engedelyezett, de be van kapcsolava
			if( !((DataModelAdapter)value).isEnabledToTurnOnOff() || ((DataModelAdapter)value).isOn() ){
    			
				setIcon( TraceTree.this.getIcon( (DataModelAdapter)value, expanded ) );		
    			setForeground( Color.black );	   
    			
    		//Ha engedelyezett a Ki/Be kapcsolas es ki van kapcsolva	
    		}else{
    			
    			setIcon( TraceTree.this.getIconOff( (DataModelAdapter)value, expanded ) );
    			setForeground( Color.gray );   			
    		}				
							
			setText( ((DataModelAdapter)value).getName() );
			
			return this;
	    }	
	}
}
