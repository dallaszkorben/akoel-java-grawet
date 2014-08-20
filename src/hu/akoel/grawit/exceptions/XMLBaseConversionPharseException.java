package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.enums.Tag;

public class XMLBaseConversionPharseException extends XMLPharseException{

	private static final long serialVersionUID = -4598121737499359506L;

	private Tag rootTag;
	private Tag tag;
	private String nameAttributeName;
	private String nameAttributeValue;
	private String attributeName;
	private String attributeValue;	
	
	/**
	 * Egy hibauzenet kisereteben keletkezett a hiba konverzio vegrehajtasakor
	 * 
	 * @param rootTag
	 * @param tag
	 * @param nameAttributeName
	 * @param nameAttributeValue
	 * @param attributeName
	 * @param attributeValue
	 * @param e
	 */
	public XMLBaseConversionPharseException( Tag rootTag, Tag tag, String nameAttributeName, String nameAttributeValue, String attributeName, String attributeValue, Throwable e ){

		//TODO internationalization es meg a tobbi XMLExceptiont is megcsinalni
		super("\n<" + rootTag.getName() + ">" + "\n   <" + tag.getName() + " " + nameAttributeName + "=\"" + nameAttributeValue + "\" " + attributeName + "=\"" + attributeValue + "\">" + "\n\nCan not convert to Document" + "\n" + e.getMessage());
		this.rootTag = rootTag;
		this.tag = tag;
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
	}

	/**
	 * Nem sikerult a megadott ut alapjan megtalalni a BASEPAGE-et
	 * 
	 * @param rootTag
	 * @param tag
	 * @param nameAttributeName
	 * @param nameAttributeValue
	 * @param attributeName
	 * @param attributeValue
	 */
	public XMLBaseConversionPharseException( Tag rootTag, Tag tag, String nameAttributeName, String nameAttributeValue, String attributeName, String attributeValue ){

		//TODO internationalization es meg a tobbi XMLExceptiont is megcsinalni
		super("\n<" + rootTag.getName() + ">" + "\n   <" + tag.getName() + " " + nameAttributeName + "=\"" + nameAttributeValue + "\" " + attributeName + "=\"" + attributeValue + "\">" + "\n\nCan find the path by the attribute");
		this.rootTag = rootTag;
		this.tag = tag;
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
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

	public String getAttributeValue() {
		return attributeValue;
	}
	
}
