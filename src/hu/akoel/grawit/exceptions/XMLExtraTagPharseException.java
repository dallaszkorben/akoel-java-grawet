package hu.akoel.grawit.exceptions;

public class XMLExtraTagPharseException extends XMLPharseException{

	private static final long serialVersionUID = -4598121737499359506L;
	
	private String blockName;
	private String tagName;
	
	public XMLExtraTagPharseException( String blockName, String tagName ){
		super( "Too many <" + tagName + "> tags are the <" + blockName + "> block" );
		
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
