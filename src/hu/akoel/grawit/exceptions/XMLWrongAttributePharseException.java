package hu.akoel.grawit.exceptions;

public class XMLWrongAttributePharseException extends XMLPharseException{

	private static final long serialVersionUID = -4598121737499359506L;

	private String blockName;
	private String tagName;
	private String attributeName;
	private String receivedValue;	
	
	public XMLWrongAttributePharseException( String blockName, String tagName, String attributeName, String receivedValue ){
		super( "'" + attributeName + "'" + " attribute has a wrong value in the <" + tagName + "> tag in the <" + blockName + "> block. Value: '" + receivedValue + "'" );
		this.blockName = blockName;
		this.tagName = tagName;
		this.attributeName = tagName;
		this.receivedValue = receivedValue;
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

	public String getReceivedValue() {
		return receivedValue;
	}
	
}
