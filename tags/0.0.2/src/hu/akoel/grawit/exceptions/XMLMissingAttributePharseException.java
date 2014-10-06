package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.enums.Tag;

public class XMLMissingAttributePharseException extends XMLPharseException{

	private static final long serialVersionUID = -4598121737499359506L;

	private Tag rootTag;
	private Tag tag;
	private String nameAttributeName;
	private String nameAttributeValue;
	private String missingAttributeName;

	/**
	 * Nincs "name" attribute
	 * 
	 * @param rootTag
	 * @param tag
	 */
	public XMLMissingAttributePharseException( Tag rootTag, Tag tag, String nameAttributeName ){
		super("\n<" + rootTag.getName() + ">" + "\n   <" + tag.getName() + " " + nameAttributeName + ">" + "\n\nAttribute is missing");
		this.rootTag = rootTag;
		this.tag = tag;
		this.nameAttributeName = nameAttributeName;
		this.nameAttributeValue = null;
		this.missingAttributeName = nameAttributeName;
	}

	/**
	 * Nincs missingAttributeName attribute
	 * 
	 * @param rootTag
	 * @param tag
	 * @param nameAttributeName
	 * @param nameAttributeValue
	 * @param missingAttributeName
	 */
	public XMLMissingAttributePharseException( Tag rootTag, Tag tag, String nameAttributeName, String nameAttributeValue, String missingAttributeName ){
		super("\n<" + rootTag.getName() + ">" + "\n   <" + tag.getName() + " " + nameAttributeName + "=\"" + nameAttributeValue + "\" " + missingAttributeName +  ">" + "\n\nAttribute is missing");
		this.rootTag = rootTag;
		this.tag = tag;
		this.nameAttributeName = nameAttributeName;
		this.nameAttributeValue = nameAttributeValue;
		this.missingAttributeName = missingAttributeName;
	}
	
	public Tag getTag() {
		return tag;
	}

	public Tag getRootTag() {
		return rootTag;
	}

	public String getAttributeName() {
		return nameAttributeName;
	}
	
}
