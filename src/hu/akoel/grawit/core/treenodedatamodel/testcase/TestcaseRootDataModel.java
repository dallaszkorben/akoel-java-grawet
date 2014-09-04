package hu.akoel.grawit.core.treenodedatamodel.testcase;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.special.SpecialRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLExtraRootTagPharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestcaseRootDataModel extends TestcaseNodeDataModel{

	private static final long serialVersionUID = 5361088361756620748L;

	private static final Tag TAG = Tag.TESTCASEROOT;
	
	public static final String ATTR_NAME = "";
	
	public TestcaseRootDataModel(){
		super( "", "" );
	}
	
	public TestcaseRootDataModel( Document doc, VariableRootDataModel variableRootDataModel, ParamRootDataModel paramRootDataModel, SpecialRootDataModel specialRootDataModel ) throws XMLPharseException{
		super("","");
		
		NodeList nList = doc.getElementsByTagName( TAG.getName() );
		
		//Ha tobb mint  1 db basepage tag van, akkor az gaz
		if( nList.getLength() > 1 ){
			
			throw new XMLExtraRootTagPharseException( TAG );
			
		}else if( nList.getLength() == 1 ){
		
			Node testcaseRootNode = nList.item(0);
			if (testcaseRootNode.getNodeType() == Node.ELEMENT_NODE) {
			
				NodeList nodeList = testcaseRootNode.getChildNodes();
				for( int i = 0; i < nodeList.getLength(); i++ ){
			
					Node testcaseNode = nodeList.item( i );
				
					if (testcaseNode.getNodeType() == Node.ELEMENT_NODE) {
						Element testcaseElement = (Element)testcaseNode;
					
						//Ha ujabb TESTCASENODE van alatta
						if( testcaseElement.getTagName().equals( Tag.TESTCASENODE.getName() ) ){
							this.add(new TestcaseNodeDataModel(testcaseElement, specialRootDataModel, paramRootDataModel ));
						}
					}
				}
			}
		}
	}
	
	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag() {
		//return TAG;
		return getTagStatic();
	}

	@Override
	public String getName(){
		return "Test Case Root";
	}
	
	@Override
	public String getModelNameToShow(){
		return CommonOperations.getTranslation( "tree.nodetype.testcase.root");
	}
	
	@Override
	public Element getXMLElement(Document document) {
		
		//TestcaseElement
		Element testcaseElement = document.createElement( TAG.getName() );

		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof TestcaseDataModelInterface ){
				
				Element element = ((TestcaseDataModelInterface)object).getXMLElement( document );
				testcaseElement.appendChild( element );		    		
		    	
			}
		}

		return testcaseElement;		
	}
	
}
