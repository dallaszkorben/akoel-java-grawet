package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.enums.Tag;

public class XMLMissingAttributePharseException extends XMLPharseException{

	private static final long serialVersionUID = -4598121737499359506L;

	private Tag rootTag;
	private Tag tag;
	private String attributeName;
	
	public XMLMissingAttributePharseException( Tag rootTag, Tag tag, String attributeName ){
		//super( "'" + attributeName + "'" + " attribute is missing from the <" + tag.getName() + "> tag in the <" + rootTag.getName() + "> block" );
		super("\n<" + rootTag.getName() + ">" + "\n   <" + tag.getName() + " " + attributeName + "='...'>" + "\n\nAttribute is missing");
		this.rootTag = rootTag;
		this.tag = tag;
		this.attributeName = attributeName;		
	}
	
	public Tag getTag() {
		return tag;
	}

	public Tag getRootTag() {
		return rootTag;
	}

	public String getAttributeName() {
		return attributeName;
	}
	
}
