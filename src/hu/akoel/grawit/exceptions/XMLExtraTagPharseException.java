package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.enums.Tag;

public class XMLExtraTagPharseException extends XMLPharseException{

	private static final long serialVersionUID = -4598121737499359506L;
	
	private Tag rootTag;
	private Tag tag;
	
	public XMLExtraTagPharseException( Tag rootTag, Tag tag ){
		//TODO internationalization
		super( "Too many <" + tag.getName() + "> tags are the <" + rootTag.getName() + "> root" );
		
		this.rootTag = rootTag;
		this.tag = tag;
	}

	public Tag getRootTag() {
		return rootTag;
	}
	
	public Tag getTag() {
		return tag;
	}

}
