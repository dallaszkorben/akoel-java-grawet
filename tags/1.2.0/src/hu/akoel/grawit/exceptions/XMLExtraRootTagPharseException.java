package hu.akoel.grawit.exceptions;

import hu.akoel.grawit.enums.Tag;

public class XMLExtraRootTagPharseException extends XMLPharseException{

	private static final long serialVersionUID = -4598121737499359506L;
	
	private Tag tag;
	
	public XMLExtraRootTagPharseException( Tag tag ){
		//TODO internationalization
		super( "\n<" + tag.getName() + ">" +"\n\nThere are extra root tags" );
		
		this.tag = tag;
	}
	
	public Tag getTag() {
		return tag;
	}

}
