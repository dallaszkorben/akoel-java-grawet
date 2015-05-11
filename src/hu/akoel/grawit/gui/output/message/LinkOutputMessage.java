package hu.akoel.grawit.gui.output.message;

import java.awt.Color;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

public class LinkOutputMessage extends OutputMessageAdapter{

	private DataModelAdapter linkToDataModel;
	private SimpleAttributeSet attribute;

	public static final String LINK_ATTRIBUTE = "LinkAttribute";
	
	public LinkOutputMessage( DataModelAdapter linkToDataModel ){
		this.linkToDataModel = linkToDataModel;
		attribute = new SimpleAttributeSet();
		attribute.addAttribute( LINK_ATTRIBUTE, this.linkToDataModel );
		StyleConstants.setForeground( attribute, new Color( 0, 0, 153 ) );
		StyleConstants.setUnderline( attribute, true);
	}
	
	public LinkOutputMessage( DataModelAdapter linkToDataModel, SimpleAttributeSet attribute ){
		this.linkToDataModel = linkToDataModel;
		this.attribute = attribute;
		attribute.addAttribute( LINK_ATTRIBUTE, this.linkToDataModel );
	}
	
	@Override
	public String getMessage() {
		return linkToDataModel.getName();
	}
	
/*	@Override
	public void printOut(DefaultStyledDocument document) {
		try {
*/			
/*			Style linkStyle = document.addStyle( "link", null );
			StyleConstants.setForeground( linkStyle, new Color( 0, 0, 153 ) );
			StyleConstants.setUnderline( linkStyle, true);			
			linkStyle.addAttribute( LINK_ATTRIBUTE, this.link );			
			document.insertString( document.getLength(), link.getName(), linkStyle );
*/
/*			document.insertString( document.getLength(), link.getName(), attribute );
		} catch (BadLocationException e) {}		
	}
*/	

	@Override
	public SimpleAttributeSet getAttribute() {		
		return this.attribute;
	}
}
