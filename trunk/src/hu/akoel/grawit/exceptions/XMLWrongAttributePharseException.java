package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.enums.Tag;

public class XMLWrongAttributePharseException extends XMLPharseException{

	private static final long serialVersionUID = -4598121737499359506L;

	private Tag rootTag;
	private Tag tag;
	private String nameAttributeName;
	private String nameAttributeValue;
	private String attributeName;
	private String receivedValue;	
	
	public XMLWrongAttributePharseException( Tag rootTag, Tag tag, String nameAttributeName, String nameAttributeValue, String attributeName, String receivedValue ){
		
		//TODO internationalization es meg a tobbi XMLExceptiont is megcsinalni
		super("\n<" + rootTag.getName() + ">" + "\n   <" + tag.getName() + " " + nameAttributeName + "=\"" + nameAttributeValue + "\" " + attributeName + "=\"" + receivedValue + "\">" + "\n\nWrong attribute value");
		this.rootTag = rootTag;
		this.tag = tag;
		this.attributeName = attributeName;
		this.receivedValue = receivedValue;
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

	public String getReceivedValue() {
		return receivedValue;
	}
	
}
