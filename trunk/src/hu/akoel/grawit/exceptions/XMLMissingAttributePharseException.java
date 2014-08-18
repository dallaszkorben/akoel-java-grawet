package hu.akoel.grawit.exceptions;

public class XMLMissingAttributePharseException extends XMLPharseException{

	private static final long serialVersionUID = -4598121737499359506L;

	private String blockName;
	private String tagName;
	private String attributeName;
	
	public XMLMissingAttributePharseException( String blockName, String tagName, String attributeName ){
		super( "'" + attributeName + "'" + " attribute is missing from the <" + tagName + "> tag in the <" + blockName + "> block" );
		this.blockName = blockName;
		this.tagName = tagName;
		this.attributeName = tagName;
		
	}
	
	public String getTagName() {
		return tagName;
	}

	public String getBlockName() {
		return blockName;
	}

	public String getAttributeName() {
		return attributeName;
	}
	
}
