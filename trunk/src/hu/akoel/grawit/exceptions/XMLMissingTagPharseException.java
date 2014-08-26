package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.enums.Tag;

public class XMLMissingTagPharseException extends XMLPharseException{

	private static final long serialVersionUID = -4598121737499359506L;

	private Tag rootTag;
	private Tag tag;
	private String missingTagName;

	/**
	 * Nincs "name" attribute
	 * 
	 * @param rootTag
	 * @param tag
	 */
	public XMLMissingTagPharseException( Tag rootTag, Tag tag, String nameAttribute, Tag missingTagName ){
		super("Under\n<" + rootTag.getName() + ">" + "\n   <" + tag.getName() + " name='" + nameAttribute + "'>" + "\n\n the <" + missingTagName + "> is missing");
		this.rootTag = rootTag;
		this.tag = tag;
		this.missingTagName = missingTagName.getName();
	}
	
	public Tag getTag() {
		return tag;
	}

	public Tag getRootTag() {
		return rootTag;
	}

	public String getMissingTagName() {
		return missingTagName;
	}
	
}
