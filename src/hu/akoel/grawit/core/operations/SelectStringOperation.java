package hu.akoel.grawit.core.operations;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.enums.list.ListSelectionByListEnum;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;

public class SelectStringOperation extends SelectOperationAdapter{
	
	private static final String NAME = "SELECTSTRING";
	private static final String ATTR_SELECTION_BY = "selectionby";
	private static final String ATTR_SELECT_STRING = "selectstring";
	
	//--- Data model
	private String string;
	private ListSelectionByListEnum selectionBy;
	//----
	
	public SelectStringOperation( String string, ListSelectionByListEnum selectionBy ){
		this.string = string;
		this.selectionBy = selectionBy;
	}
	
	public SelectStringOperation( Element element, Tag rootTag, Tag tag, String nameAttrName, String nameAttrValue ) throws XMLBaseConversionPharseException, XMLMissingAttributePharseException{
		
		//SELECTION BY
		if( !element.hasAttribute( ATTR_SELECTION_BY ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_SELECTION_BY );			
		}
		String stringSelectionBy = element.getAttribute( ATTR_SELECTION_BY );		
		if( stringSelectionBy.equals( ListSelectionByListEnum.BYINDEX.name() ) ){
			selectionBy = ListSelectionByListEnum.BYINDEX;
		}else if( stringSelectionBy.equals( ListSelectionByListEnum.BYVALUE.name() ) ){
			selectionBy = ListSelectionByListEnum.BYVALUE;
		}else if( stringSelectionBy.equals( ListSelectionByListEnum.BYVISIBLETEXT.name() ) ){
			selectionBy = ListSelectionByListEnum.BYVISIBLETEXT;
		}else{
			selectionBy = ListSelectionByListEnum.BYVISIBLETEXT;
		}		
		
		//SELECT VARIABLE ELEMENT
		if( !element.hasAttribute( ATTR_SELECT_STRING ) ){
			throw new XMLMissingAttributePharseException( rootTag, tag, ATTR_SELECT_STRING );
		}
		string = element.getAttribute(ATTR_SELECT_STRING);
		
	}
	
	@Override
	public ListSelectionByListEnum getSelectionBy() {
		return selectionBy;
	}

	@Override
	public String getStringToSelection() {	
		return string;
	}

	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}

	@Override
	public void setXMLAttribute(Document document, Element element) {
		Attr attr = document.createAttribute( ATTR_SELECT_STRING );
		attr.setValue( string );
		element.setAttributeNode( attr );		
		
		attr = document.createAttribute( ATTR_SELECTION_BY );
		attr.setValue( selectionBy.name() );
		element.setAttributeNode( attr );	
	}

	@Override
	public Object clone() {

		String string = new String( this.string );
		//ListSelectionByListEnum selectionBy = this.selectionBy;
		
		return new SelectStringOperation(string, selectionBy);
	}


}
