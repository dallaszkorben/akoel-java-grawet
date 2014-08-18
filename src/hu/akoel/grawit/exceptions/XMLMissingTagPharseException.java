package hu.akoel.grawit.exceptions;

public class XMLMissingTagPharseException extends XMLPharseException{

	private static final long serialVersionUID = -4598121737499359506L;
	
	private String blockName;
	private String tagName;
	
	public XMLMissingTagPharseException( String blockName, String tagName ){
		super( "<" + tagName + "> tag is missing in the <" + blockName + "> block" );
		
		this.blockName = blockName;
		this.tagName = tagName;
	}

	public String getBlockName() {
		return blockName;
	}
	
	public String getTagName() {
		return tagName;
	}

}
