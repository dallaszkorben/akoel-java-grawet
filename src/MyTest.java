import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.*;

public class MyTest extends JFrame{
	private static final long serialVersionUID = 8039927621268023511L;
	private DefaultStyledDocument outputDocument;
	private JTextPane outputPanel;
	private ArrayList<PrintOut> printoutList = new ArrayList<>();
	
	public String IDENTIFIER_NAME = "identifier";

	public Color COLOR_LABEL = Color.black;
	public Color COLOR_TEXT = Color.blue;
	
	MyLabel label;
	MyTextField textField;
	
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
		
		//Feltoltom szoveggel a listat
		label = new MyLabel( "Label: " );
		printoutList.add( new NormalMessage( label, COLOR_LABEL ) );
		textField = new MyTextField("normal text" );
		printoutList.add( new NormalMessage( textField, COLOR_TEXT ) );
		
		//A listat megjelenitem a panelen
		for( PrintOut printOut: printoutList ){
			printOut.printOut( outputDocument );
		}		
	       
		JButton selectLabelButton = new JButton( "Select Label" );
		selectLabelButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Element foundElement = checkElement( outputDocument.getDefaultRootElement(), label );
				
				if( null != foundElement ){
					
					//Az adott test case kivalasztasra kerul
					outputPanel.setSelectionStart( foundElement.getStartOffset() );
					outputPanel.setSelectionEnd( foundElement.getEndOffset() );
					outputPanel.getCaret().setSelectionVisible( true );
				}				
			}
		});
		
		JButton selectTextButton = new JButton( "Select text" );
		selectTextButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Element foundElement = checkElement( outputDocument.getDefaultRootElement(), textField );
				
				if( null != foundElement ){
					
					//Az adott test case kivalasztasra kerul
					outputPanel.setSelectionStart( foundElement.getStartOffset() );
					outputPanel.setSelectionEnd( foundElement.getEndOffset() );
					outputPanel.getCaret().setSelectionVisible( true );
				}				
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout( new GridLayout( 2, 1 ) );
		buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add( selectLabelButton );
		buttonPanel.add( selectTextButton );		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize( 300, 200 );
		this.setLayout( new BorderLayout() );
		this.add( outputScrollablePanel, BorderLayout.CENTER );
		this.add( buttonPanel, BorderLayout.WEST );
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private <E> Element checkElement( Element element, E component ){
		
		int length = element.getElementCount();

		//Ha level
		if( length == 0 ){			
			AttributeSet attr = element.getAttributes();
			E dataModel = (E)attr.getAttribute( IDENTIFIER_NAME );
			if(dataModel != null && dataModel.equals( component ) ){
				return element;
			}
		}
		for( int i = 0; i < length; i++ ){
			Element el = checkElement(element.getElement(i), component );
			if( null != el ){
				return el;
			}
		}
		return null;
	}
	
	interface PrintOut{
		public void printOut(DefaultStyledDocument document);
	}

	class NormalMessage implements PrintOut{
		private TextInterface component;
		private SimpleAttributeSet attributeSet;
		public NormalMessage( TextInterface component, Color color  ){
			this.component = component;			
			attributeSet = new SimpleAttributeSet();
			StyleConstants.setForeground( attributeSet, color );
			this.attributeSet.addAttribute( IDENTIFIER_NAME, component );
		}
		public void printOut(DefaultStyledDocument document) {
			try {
				document.insertString( document.getLength(), component.getString(), attributeSet );
			} catch (BadLocationException e) {}		
		}
	}
	
	interface TextInterface{
		public String getString();
	}
	class MyLabel extends JLabel implements TextInterface{
		private static final long serialVersionUID = -7386528427304831104L;
		public MyLabel(String string) {
			super( string );
		}
		@Override
		public String getString() {			
			return this.getText();
		}		
	}
	class MyTextField extends JTextField implements TextInterface{
		private static final long serialVersionUID = -2576120738093248914L;
		public MyTextField(String string) {
			super( string );
		}
		@Override
		public String getString() {
			return this.getText();
		}		
	}
}