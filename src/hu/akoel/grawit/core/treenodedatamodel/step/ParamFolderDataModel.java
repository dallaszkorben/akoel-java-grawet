package hu.akoel.grawit.core.treenodedatamodel.step;

import javax.swing.tree.MutableTreeNode;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParamFolderDataModel extends ParamNodeDataModelAdapter{

	private static final long serialVersionUID = -2466202302741284519L;
	
	public static final Tag TAG = Tag.PARAMFOLDER;
	
	public ParamFolderDataModel( String name, String details ){
		super( name, details );
	}
	
	public ParamFolderDataModel( Element element, BaseRootDataModel baseRootDataModel, VariableRootDataModel variableRootDataModel ) throws XMLPharseException{

		super( element, baseRootDataModel, variableRootDataModel );
		
	    //========
		//
		// Gyermekei
		//
	    //========
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element pageElement = (Element)node;
				
				//Ha PARAMPAGE van alatta
				if( pageElement.getTagName().equals( Tag.PARAMNORMALELEMENTCOLLECTOR.getName() )){					
					this.add(new ParamNormalCollectorDataModel(pageElement, baseRootDataModel, variableRootDataModel ) );

				//Ha PARAMLOOP van alatta
				}else if( pageElement.getTagName().equals( Tag.PARAMLOOPELEMENTCOLLECTOR.getName() )){					
					this.add(new ParamLoopCollectorDataModel(pageElement, variableRootDataModel, baseRootDataModel ) );
						
				//Ha ujabb PARAMNODE van alatta
				}else if( pageElement.getTagName().equals( Tag.PARAMFOLDER.getName() )){					
					this.add(new ParamFolderDataModel(pageElement, baseRootDataModel, variableRootDataModel ) );
				}
			}
		}
		
	}

	public static Tag getTagStatic(){
		return TAG;
	}
	
	@Override
	public Tag getTag(){
		return getTagStatic();
	}
	
	@Override
	public void add(ParamDataModelAdapter node) {
		super.add( (MutableTreeNode)node );
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.param.folder");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
		
		Element nodeElement = super.getXMLElement(document); 
				
		return nodeElement;		
	}


	
}
