package hu.akoel.grawit.exception.message;

import java.awt.Color;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

public class LinkMessage implements OutputMessage{

	private DataModelAdapter link;
//	private SimpleAttributeSet attribute;

	public static final String LINK_ATTRIBUTE = "LinkAttribute";
	
	public LinkMessage( DataModelAdapter link ){
		this.link = link;
//		attribute = new SimpleAttributeSet();
//		StyleConstants.setForeground( attribute, new Color( 0, 0, 153 ) );
//		StyleConstants.setUnderline( attribute, true);
	}
	
	@Override
	public String getMessage() {
		return link.getName();
	}
	
	@Override
	public void printOut(DefaultStyledDocument document) {
		try {
			
			Style linkStyle = document.addStyle( "link", null );
			StyleConstants.setForeground( linkStyle, new Color( 0, 0, 153 ) );
			StyleConstants.setUnderline( linkStyle, true);			
			linkStyle.addAttribute( LINK_ATTRIBUTE, this.link );			
			
			document.insertString( document.getLength(), link.getName(), linkStyle );
		} catch (BadLocationException e) {}		
	}
}
