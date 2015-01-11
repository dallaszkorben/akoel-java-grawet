package hu.akoel.grawit.exceptions;

public class XMLCastPharseException extends XMLPharseException{

	private static final long serialVersionUID = -6031453607713598029L;
	
	private String rootTag;
	private String tag;
	private String value;
	private String expectedType;
	
	public XMLCastPharseException( String rootTag, String tag, String value, String expectedType  ){
		
		//TODO internationalization es meg a tobbi XMLExceptiont is megcsinalni
		super("\n<" + rootTag + ">" + "\n   <" + tag + " value=\"" + value + "\" >" + "\n\nCan not convert to " + expectedType );
		
		this.rootTag = rootTag;
		this.tag = tag;
		this.value = value;
		this.expectedType = expectedType;
	}
	
	public String getTag() {
		return tag;
	}

	public String getRootTag() {
		return rootTag;
	}

	public String getValue() {
		return value;
	}

	public String getExpectedType() {
		return expectedType;
	}
	
}
