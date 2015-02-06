package hu.akoel.grawit.core.treenodedatamodel.testcase;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestcaseFolderDataModel extends TestcaseNodeDataModelAdapter{

	private static final long serialVersionUID = -2139557326147525999L;
	
	public static final Tag TAG = Tag.TESTCASEFOLDER;
	
	public TestcaseFolderDataModel( String name, String details ){
		super( name, details );
	}
	
	/**
	 * XML alapjan legyartja a TESTCASENODE-ot es az alatta elofordulo 
	 * TESTCASENODE-okat, illetve TESTCASECASE-eket
	 * 
	 * @param element
	 * @throws XMLMissingAttributePharseException 
	 */
	public TestcaseFolderDataModel( Element element, VariableRootDataModel variableRootDataModel, BaseRootDataModel baseRootDataModel, ParamRootDataModel paramRootDataModel, DriverDataModelAdapter driverRootDataModel ) throws XMLPharseException{

		super( element, variableRootDataModel, baseRootDataModel, paramRootDataModel, driverRootDataModel  );
				
		//========
		//
		// Gyermekei
		//
		//========	
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element testcaseElement = (Element)node;
				
				//Ha TESTCASECASE van alatta
				if( testcaseElement.getTagName().equals( Tag.TESTCASECASE.getName() )){
					this.add(new TestcaseCaseDataModel(testcaseElement, variableRootDataModel, baseRootDataModel, paramRootDataModel, driverRootDataModel ));
				
				//Ha ujabb TESTCASEFOLDER van alatta
				}else if( testcaseElement.getTagName().equals( Tag.TESTCASEFOLDER.getName() )){
					this.add(new TestcaseFolderDataModel(testcaseElement, variableRootDataModel, baseRootDataModel, paramRootDataModel, driverRootDataModel ));
				}
			}
		}
		
	}
	
	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag() {
		return getTagStatic();
	}	
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.testcase.folder");
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
