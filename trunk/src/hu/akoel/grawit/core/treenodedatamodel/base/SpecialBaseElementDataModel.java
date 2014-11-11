package hu.akoel.grawit.core.treenodedatamodel.base;

import java.util.ArrayList;
import java.util.Iterator;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.JavaSourceFromString;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public class SpecialBaseElementDataModel extends BaseElementDataModelAdapter{
	private static final long serialVersionUID = -8916078747948054716L;

	public static Tag TAG = Tag.SPECIALBASEELEMENT;
	
	private ElementTypeListEnum elementType = ElementTypeListEnum.SPECIAL;
	
	public static final String ATTR_ELEMENT_TYPE="elementtype";
	public static final String ATTR_SCRIPT = "script";
	
	private ArrayList<String> parameters = new ArrayList<>();
	
	private static final String codePre = 
			"import org.openqa.selenium.WebDriver;\n" +
			"public class CustomClass {\n" +		
			"   public CustomClass() {}\n" +		
			"   public void doAction(WebDriver driver, ArrayList<String> parameters) throws hu.akoel.grawit.exceptions.PageException{\n";
	private static final String codePost = 
			"\n   }\n" +
			"}\n";
	
	private JavaSourceFromString javaFile;
	private DiagnosticCollector<JavaFileObject> diagnostics;
	private String classOutputFolder = "";
	
	private String customClassName = "CustomClass";
	private String customMethodName = "doAction";
	
	//Adatmodel ---
	private String script;
	//----

	/**
	 * 
	 * Modify
	 * 
	 * @param name
	 * @param elementType
	 * @param identifier
	 * @param identificationType
	 * @param frame
	 */
	public SpecialBaseElementDataModel(String name, String script ){
		super( name );
		this.script = script;
	}

	/**
	 * Capture new
	 * 
	 * XML alapjan gyartja le a BASEELEMENT-et
	 * 
	 * @param element
	 * @throws XMLPharseException 
	 */
	public SpecialBaseElementDataModel( Element element ) throws XMLPharseException{
		super( element );
		
		//element type             
		if( !element.hasAttribute( ATTR_ELEMENT_TYPE ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), getTag(), ATTR_NAME, getName(), ATTR_ELEMENT_TYPE );			
		}
		String elementTypeString = element.getAttribute( ATTR_ELEMENT_TYPE );
		this.elementType = ElementTypeListEnum.valueOf( elementTypeString );
		
		//source
		if( !element.hasAttribute( ATTR_SCRIPT ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_SCRIPT );			
		}
		String scriptString = element.getAttribute( ATTR_SCRIPT );		
		this.script = scriptString;
				
	}
	
	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag() {
		return getTagStatic();
	}

	
	public ElementTypeListEnum getElementType(){
		return elementType;
	}
	
	
	public void addParameter( String parameter ){
		this.parameters.add( parameter );
	}
	
	public void clearParameters(){
		this.parameters.clear();
	}
	
	public Iterator<String> getParameterIterator(){
		return parameters.iterator();
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.base.specialelement");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}
	
	@Override
	public Element getXMLElement(Document document) {
		
		Element elementElement = super.getXMLElement(document);
		
		Attr attr;
		
		//Element type
		attr = document.createAttribute( ATTR_ELEMENT_TYPE );
		attr.setValue( getElementType().name() );
		elementElement.setAttributeNode(attr);	
/*
		//Node element
		attr = document.createAttribute( ATTR_FRAME);
		attr.setValue( getFrame() );
		elementElement.setAttributeNode(attr);	
		
		attr = document.createAttribute( ATTR_IDENTIFIER );
		attr.setValue( getSelector() );
		elementElement.setAttributeNode(attr);	
		
		attr = document.createAttribute( ATTR_ELEMENT_TYPE );
		attr.setValue( getElementType().name() );
		elementElement.setAttributeNode(attr);	

		attr = document.createAttribute( ATTR_IDENTIFICATION_TYPE );
		attr.setValue( getSelectorType().name() );
		elementElement.setAttributeNode(attr);	
		
		attr = document.createAttribute( ATTR_WAITINGTIME );
		if( null == getWaitingTime() ){
			attr.setValue( "" );	
		}else{
			attr.setValue( getWaitingTime().toString() );
		}
		
		elementElement.setAttributeNode(attr);	
*/
		return elementElement;	
	}

	@Override
	public Object clone(){
		
		//Leklonozza az BaseElement-et
		SpecialBaseElementDataModel cloned = (SpecialBaseElementDataModel)super.clone();
	
		cloned.elementType = this.elementType;	
		
		//Es a valtozoit is klonozni kell
/*		cloned.frame = new String(this.frame);
		cloned.identifier = new String( this.identifier );
		cloned.identificationType = this.identificationType;	//TODO kedes, hogy jo-e
		cloned.elementType = this.elementType;					//TODO Kerde, hogy jo-e
		if( null != this.waitingTime )
			cloned.waitingTime = new Integer(this.waitingTime);		
*/		
		return cloned;
		
	}
	
	@Override
	public Object cloneWithParent() {
		
		SpecialBaseElementDataModel cloned = (SpecialBaseElementDataModel) super.cloneWithParent();
		
		return cloned;
	}

}
