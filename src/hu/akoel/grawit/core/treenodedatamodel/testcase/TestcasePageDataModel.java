package hu.akoel.grawit.core.treenodedatamodel.testcase;

import javax.swing.tree.MutableTreeNode;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamPageDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TestcasePageDataModel extends TestcaseDataModelInterface{

	private static final long serialVersionUID = 5313170692938571481L;

	public static final Tag TAG = Tag.TESTCASEPAGE;
	
	public static final String ATTR_DETAILS = "details";
	
	private String name;
	private String details;
	private ParamPageDataModel paramPage;
	
	public TestcasePageDataModel( String name, String details, ParamPageDataModel paramPage ){
		super( );
		this.name = name;
		this.details = details;
		this.paramPage = paramPage;
	}
	
	/**
	 * XML alapjan legyartja a TESTCASEPAGE-et es az alatta elofordulo 
	 * 
	 * @param element
	 * @throws XMLMissingAttributePharseException 
	 */
	public TestcasePageDataModel( Element element ) throws XMLPharseException{
		
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( TestcasePageDataModel.getRootTag(), Tag.TESTCASEPAGE, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );
		this.name = nameString;
		
		if( !element.hasAttribute( ATTR_DETAILS ) ){
			throw new XMLMissingAttributePharseException( TestcasePageDataModel.getRootTag(), Tag.TESTCASEPAGE, ATTR_NAME, getName(), ATTR_DETAILS );			
		}		
		String detailsString = element.getAttribute( ATTR_DETAILS );		
		this.details = detailsString;

	}
	
	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag() {
		return getTagStatic();
	}

	@Override
	public void add(TestcaseDataModelInterface node) {
		super.add( (MutableTreeNode)node );
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.casepage");
	}
	
	@Override
	public String getModelNameToShow(){
		return getModelNameToShowStatic();
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	public String getDetails(){
		return details;
	}
	
	public void setDetails( String details ){
		this.details = details;
	}
	
	public void setName( String name ){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}

	public void setParamPage( ParamPageDataModel paramPage ){
		this.paramPage = paramPage;		
	}
	
	public ParamPageDataModel getParamPage(){
		return paramPage;
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
		
		//Node element
		Element nodeElement = document.createElement( TestcasePageDataModel.this.getTag().getName() );
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		nodeElement.setAttributeNode(attr);	
		
		attr = document.createAttribute( ATTR_DETAILS );
		attr.setValue( getDetails() );
		nodeElement.setAttributeNode(attr);	
			
		return nodeElement;		
	}

}
