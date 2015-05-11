import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.*;

public class MyTest extends JFrame{
	private static final long serialVersionUID = 8039927621268023511L;
	private DefaultStyledDocument outputDocument;
	private JTextPane outputPanel;
	private ArrayList<PrintOut> printoutList = new ArrayList<>();
	
	private static final String LINK_ATTRIBUTE = "LinkAttribute";
	
	public SimpleAttributeSet ATTRIBUTE_NORMAL;
	public SimpleAttributeSet ATTRIBUTE_ATTENTION;
	
	public static void main(String[] args) {
		new MyTest();
	}

	public MyTest() {
		super( "Test" );
        
		StyleContext outputStyleContext = new StyleContext();
		outputDocument = new DefaultStyledDocument(outputStyleContext);
		outputPanel = new JTextPane(outputDocument);
		outputPanel.setEditable(false);
		
		//Csak igy lehet megoldani, hogy ne torje a sorokat, ha hosszabb mint a rendelkezesre allo, hely
		JPanel outputNoWrapPanel = new JPanel( new BorderLayout() );
		outputNoWrapPanel.add( outputPanel );
		DefaultCaret outputCaret = (DefaultCaret)outputPanel.getCaret();
		outputCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JScrollPane outputScrollablePanel = new JScrollPane(outputNoWrapPanel );
		outputScrollablePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);	

		//Definialom az attributumokat
		ATTRIBUTE_NORMAL = new SimpleAttributeSet();
		StyleConstants.setForeground( ATTRIBUTE_NORMAL, Color.BLACK );
		StyleConstants.setBold( ATTRIBUTE_NORMAL, false );
		StyleConstants.setItalic( ATTRIBUTE_NORMAL, false );
		StyleConstants.setUnderline( ATTRIBUTE_NORMAL, false );		
		
		ATTRIBUTE_ATTENTION = new SimpleAttributeSet();
		StyleConstants.setForeground( ATTRIBUTE_ATTENTION, Color.RED );
		StyleConstants.setBold( ATTRIBUTE_ATTENTION, true );
		StyleConstants.setItalic( ATTRIBUTE_ATTENTION, false );
		StyleConstants.setUnderline( ATTRIBUTE_ATTENTION, false );
		StyleConstants.setFontSize( ATTRIBUTE_ATTENTION, 15 );
		
		//Feltoltom szoveggel a listat
		printoutList.add( new NormalMessage( "Attention: ", ATTRIBUTE_NORMAL ) );
		printoutList.add( new LinkMessage( "First Link") );
		printoutList.add( new NormalMessage( "\n", ATTRIBUTE_NORMAL ) );

		printoutList.add( new NormalMessage( "Second line: ", ATTRIBUTE_NORMAL ) );
		printoutList.add( new LinkMessage( "Second Link") );
		printoutList.add( new NormalMessage( "\n", ATTRIBUTE_NORMAL ) );
		
		printoutList.add( new NormalMessage( "This is a really long line which whould not be wrapped.", ATTRIBUTE_ATTENTION ) );
		
		//A listat megjelenitem a panelen
		for( PrintOut printOut: printoutList ){
			printOut.printOut( outputDocument );
		}		
		
		//Ha linkre mozgatom a kurzort (alahuzott), akkor egy tenyeret mutat a kurzor
		outputPanel.addMouseMotionListener( new MouseInputAdapter() {
			public void mouseMoved( MouseEvent e ) {
	               Element elem = outputDocument.getCharacterElement( outputPanel.viewToModel(e.getPoint()));
	               AttributeSet as = elem.getAttributes();
	               if(StyleConstants.isUnderline(as))
	                    outputPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
	               else
	                    outputPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	          }
		});
		
		//Ha linkre kattintok, akkor a megfelelo DataModelElement tree nyilik meg
		outputPanel.addMouseListener( new MouseAdapter( ) {
			public void mouseClicked( MouseEvent e ) {
				try{
					Element elem = outputDocument.getCharacterElement( outputPanel.viewToModel(e.getPoint()));
					AttributeSet as = elem.getAttributes();
					String link = (String)as.getAttribute( LINK_ATTRIBUTE );
					if( link != null ){
						System.err.println( link );
					}
				}
				catch(Exception x) {
					x.printStackTrace();
				}
			}
		});		
		       
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize( 300, 200 );
		this.setLayout( new BorderLayout() );
		this.add( outputScrollablePanel, BorderLayout.CENTER );	
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	class LinkMessage  implements PrintOut{
		private String msg;
		public LinkMessage( String msg ){
			this.msg = msg;
		}
		public void printOut(DefaultStyledDocument document) {
			try {				
				Style linkStyle = document.addStyle( "link", null );
				StyleConstants.setForeground( linkStyle, new Color( 0, 0, 153 ) );
				StyleConstants.setUnderline( linkStyle, true);			
				linkStyle.addAttribute( LINK_ATTRIBUTE, msg );				
				document.insertString( document.getLength(), msg, linkStyle );
			} catch (BadLocationException e) {}		
		}
	}	
	class NormalMessage implements PrintOut{
		private String message;
		private SimpleAttributeSet attribute;
		public NormalMessage( String message, SimpleAttributeSet attribute ){
			this.message = message;
			this.attribute = attribute;
		}
		public void printOut(DefaultStyledDocument document) {
			try {
				document.insertString( document.getLength(), message, attribute );
			} catch (BadLocationException e) {}		
		}
	}	
	interface PrintOut{
		public void printOut(DefaultStyledDocument document);
	}
}