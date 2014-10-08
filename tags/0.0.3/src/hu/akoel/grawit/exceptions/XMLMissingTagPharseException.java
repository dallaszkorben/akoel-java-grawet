package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.enums.Tag;

public class XMLMissingTagPharseException extends XMLPharseException{

	private static final long serialVersionUID = -4598121737499359506L;

	private String rootTag;
	private String tag;
	private String missingTagName;

	/**
	 * Nincs "name" attribute
	 * 
	 * @param rootTag
	 * @param tag
	 */
	public XMLMissingTagPharseException( String rootTag, String tag, String nameAttribute, String missingTagName ){
		super("Under\n<" + rootTag + ">" + "\n   <" + tag + " name='" + nameAttribute + "'>" + "\n\n the <" + missingTagName + "> is missing");
		this.rootTag = rootTag;
		this.tag = tag;
		this.missingTagName = missingTagName;
	}
	
	public String getTag() {
		return tag;
	}

	public String getRootTag() {
		return rootTag;
	}

	public String getMissingTagName() {
		return missingTagName;
	}
	
}
