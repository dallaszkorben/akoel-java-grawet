package hu.akoel.grawit.gui.editor.component;

import hu.akoel.grawit.core.parameter.ElementParameter;
import hu.akoel.grawit.core.parameter.RandomDecimalParameter;
import hu.akoel.grawit.core.parameter.RandomIntegerRangeParameter;
import hu.akoel.grawit.core.parameter.RandomStringParameter;
import hu.akoel.grawit.core.parameter.StringParameter;
import hu.akoel.grawit.enums.VariableType;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class VariableTypeComponent extends JPanel implements EditorComponentInterface{

	private static final long serialVersionUID = -3681477011797868779L;

	//Combobox
	private ComboBoxComponent<String> fieldVariableType;
	
	//Adatmodel
	private ElementParameter elementParameter;
	
	private  ArrayList<JTextField> valueList = new ArrayList<>();
	
	public VariableTypeComponent(){
		super();
		
		this.setLayout( new GridBagLayout());
		
		this.elementParameter = new StringParameter("", "");
		
		fieldVariableType = new ComboBoxComponent<String>();
		fieldVariableType.addItem( VariableType.getVariableParameterTypeByIndex(0).getTranslatedName() );
		fieldVariableType.addItem( VariableType.getVariableParameterTypeByIndex(1).getTranslatedName() );
		fieldVariableType.addItem( VariableType.getVariableParameterTypeByIndex(2).getTranslatedName() );
		fieldVariableType.addItem( VariableType.getVariableParameterTypeByIndex(3).getTranslatedName() );
				
		fieldVariableType.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
			
				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 
					
					valueList = new ArrayList<>();
					
					int index = fieldVariableType.getSelectedIndex();
					
					if( VariableType.getVariableParameterTypeByIndex(index).equals( VariableType.STRING_PARAMETER ) ){
						elementParameter = new StringParameter();
					}else if( VariableType.getVariableParameterTypeByIndex(index).equals( VariableType.RANDOM_STRING_PARAMETER ) ){
						elementParameter = new RandomStringParameter();
					}else if( VariableType.getVariableParameterTypeByIndex(index).equals( VariableType.RANDOM_INTEGER_PARAMETER ) ){
						elementParameter = new RandomIntegerRangeParameter();
					}else if( VariableType.getVariableParameterTypeByIndex(index).equals( VariableType.RANDOM_DECIMAL_PARAMETER ) ){
						elementParameter = new RandomDecimalParameter();
					}
					
					//Eltunteti az osszes Component-t
					VariableTypeComponent.this.removeAll();
							
					//Majd ujra feltolti
					int gridY = 0;
					GridBagConstraints c = new GridBagConstraints();
					c.insets = new Insets(0,0,0,0);
					c.gridy = gridY;
					c.gridx = 0;
					c.gridwidth = 2;
					c.weighty = 0;
					c.fill = GridBagConstraints.HORIZONTAL;
					c.weightx = 0;
					c.anchor = GridBagConstraints.WEST;
					VariableTypeComponent.this.add( fieldVariableType, c );
					
					for( int i = 0; i < elementParameter.getParameterNumber(); i++ ){
						gridY++;
						c.insets = new Insets(0,0,0,0);
						c.gridy = gridY;
						c.gridx = 0;
						c.gridwidth = 1;
						c.weighty = 0;
						c.fill = GridBagConstraints.NONE;
						c.weightx = 0;
						c.anchor = GridBagConstraints.WEST;						
						VariableTypeComponent.this.add( new JLabel( elementParameter.getParameterName(i) + ": "), c);
						
						c.insets = new Insets(0,0,0,0);
						c.gridy = gridY;
						c.gridx = 1;
						c.gridwidth = 1;
						c.weighty = 0;
						c.fill = GridBagConstraints.HORIZONTAL;
						c.weightx = 1;
						c.anchor = GridBagConstraints.FIRST_LINE_START;		
						JTextField textValue = new JTextField( elementParameter.getParameterValue(i).toString());
						valueList.add( textValue );

						//textValue.getDocument().addDocumentListener( new ValueDocumentListener(textValue, i));							
						((AbstractDocument)textValue.getDocument()).setDocumentFilter( new ValueDocumentFilter( textValue, i));
						VariableTypeComponent.this.add( textValue, c);						
					}
					VariableTypeComponent.this.revalidate();	
					
				}				
			}
		});
		
		fieldVariableType.setSelectedIndex(1);
		fieldVariableType.setSelectedIndex(0);
		
	}

	class ValueDocumentFilter extends DocumentFilter{
		private  int index;
		private JTextField textValue;
		
		public ValueDocumentFilter( JTextField textValue, int index ){
			super();
			this.textValue = textValue;
			this.index = index;		
		}
		 @Override
	        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {

			 
			 
			 	StringBuffer buffer = new StringBuffer(text);
		        for (int i = buffer.length() - 1; i >= 0; i--) {
		            char ch = buffer.charAt(i);
		            if (!Character.isDigit(ch)) {
		                buffer.deleteCharAt(i);
		            }
		        }
System.err.println("inserting: " + buffer.toString());		        
		        super.insertString(fb, offset, buffer.toString(), attr);
			 
			 
				//super.insertString(fb, offset, text, attr);
				//common();
	        }

	        @Override
	        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
	        	if( fb.getDocument().getLength() - length >= 1)	        	
	        		super.remove(fb, offset, length);
	        	//common();
	        	
	        }
//TODO formatedtextfield
	        @Override
	        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
System.err.println("replace: " + length); 
	        	 if (length > 0) 
	        		 fb.remove(offset, length);
	        	 insertString(fb, offset, text, attrs);
	             
				//super.replace(fb, offset, length, text, attrs);
				//common();
	        }
	        
	        private void common(){
	        	Class clazz = elementParameter.getParameterClass(index);	        
	        	if( clazz.equals( Integer.class ) ){
				
	        		Runnable doSetParameterValue = new Runnable() {
	        			@Override
	        			public void run() {
	        				valueList.get(index).setText("1234");
	        				//setParameterValue( 1230, index);
	        			}
	        		};       
	        		SwingUtilities.invokeLater(doSetParameterValue);
	        	}
	        }
	}
	



		    
/*			Class clazz = elementParameter.getParameterClass(index);
			if( clazz.equals( String.class ) ){
				elementParameter.setParameterValue( textValue.getText(), index);
			}else if( clazz.equals( Integer.class ) ){
				try{
					elementParameter.setParameterValue( Integer.valueOf( textValue.getText()), index);
				}catch( NumberFormatException f){
					Runnable doSetParameterValue = new Runnable() {
				        @Override
				        public void run() {
				        	valueList.get(index).setText("0");
				        	//setParameterValue( 0, index);
				        }
				    };       
				    SwingUtilities.invokeLater(doSetParameterValue);
					
										
				}
			}
*/			
		
	
	public ElementParameter getElementParameter(){
		return elementParameter;
	}
	
	public String getParameterName(){
		return elementParameter.getName();
	}
	
	public void setParameterName( String name ){
		elementParameter.setName(name);
	}
	
	public void setType( VariableType type ){
		
		fieldVariableType.setSelectedIndex(type.getIndex());
	}
	
	//TODO atalakitas
	public void setParameterValue( Object value, int index ){
		valueList.get(index).setText( value.toString() );
		elementParameter.setParameterValue(value, index);
	}
	
	public Object getParameter( int index ){
		return elementParameter.getParameterValue(index);
	}
	
	public int getParameterNumbers(){
		return elementParameter.getParameterNumber();
	}
	
	@Override
	public void setEnableModify(boolean enable) {
		fieldVariableType.setEnabled( enable );		
	}

	@Override
	public Component getComponent() {
		return this;
	}
	
}
