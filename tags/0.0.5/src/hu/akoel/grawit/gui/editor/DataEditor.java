package hu.akoel.grawit.gui.editor;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.gui.editors.component.EditorComponentInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public abstract class DataEditor extends BaseEditor{

	private static final long serialVersionUID = -6084357053425935174L;

	public static enum EditMode{
		NO,
		VIEW,
		MODIFY
	}
	
	private final static int POS_NULL = 0;
	private final static int POS_LABEL = 0;
	private final static int POS_VALUE = 1;
	private final static int POS_SAVEBUTTON = 1;
	private final static int POS_STATISICON = 2;
	
//	private JPanel labelSection;
//	private JPanel dataSection = new JPanel();
	private GridBagConstraints c;
	private int gridY = 0;
	private JLabel filler = new JLabel();
	private EditMode mode = null;
	private JButton saveButton;
	
	private LinkedHashMap<Component, Component> statusIconList = new LinkedHashMap<>();
	
	public DataEditor( String element ){
		super( element );
		
		this.mode = null;
		
		common();
	}
	
	public DataEditor( EditMode mode, String element ){
		super( element );
		
		this.mode = mode;
		
		common();
		
	}
	
	private void common(){
		
//		this.setLayout( new BorderLayout() );
		//this.setBackground(Color.cyan);
//		this.setBorder( BorderFactory.createLoweredBevelBorder());

		//
		// Headline terulet
		//
/*		JPanel headlinePanel = new JPanel();
		headlinePanel.setBackground( Color.white );
		headlinePanel.setLayout( new BorderLayout());
	
		super.add( headlinePanel, BorderLayout.NORTH );
*/		
		//
		// Cim szekcio
		//
/*		labelSection = new JPanel();
		labelSection.setBackground( Color.white );
		if( null == mode || !mode.equals(EditMode.NO)){
			headlinePanel.add( labelSection, BorderLayout.CENTER );
		}
*/		
		//
		// Operation icon section
		//
/*		JLabel operationSection = new JLabel();
		operationSection.setBorder( BorderFactory.createEmptyBorder(7, 10, 7, 10));
		if( null == mode || !mode.equals(EditMode.NO)){
			headlinePanel.add( operationSection, BorderLayout.WEST );
		}
*/		
		ImageIcon pageIcon = null;
		//CAPTURE
		if( null == mode ) {
			pageIcon = CommonOperations.createImageIcon("headline/operation-insert.png");
		//MODIFY
		}else if( mode.equals( EditMode.MODIFY)){
			pageIcon = CommonOperations.createImageIcon("headline/operation-modify.png");
		//VIEW
		}else if( mode.equals( EditMode.VIEW )){
			pageIcon = CommonOperations.createImageIcon("headline/operation-view.png");
		}
		//operationSection.setIcon( pageIcon );
		getOperationSection().setIcon( pageIcon );
		
/*		
		JLabel label = new JLabel( element );		
		//CAPTURE
		if( null == mode ) {
			label.setText( element );
		//MODIFY
		}else if( mode.equals( EditMode.MODIFY)){
			label.setText( element );
		//VIEW
		}else if( mode.equals( EditMode.VIEW )){
			label.setText( element );		
		}
		labelSection.add( label );

		label.setFont(new Font( label.getFont().getName(), Font.BOLD, 20 ));
*/
		//
		// Adat szekcio
		//
		//dataSection.setBackground( Color.green );
/*		dataSection.setLayout( new GridBagLayout() );		
		dataSection.setBorder( BorderFactory.createEmptyBorder( 20, 10, 10, 10 ) );
		super.add( dataSection, BorderLayout.CENTER );
*/
		c = new GridBagConstraints();
		
		//Mentes gomb
		saveButton = new JButton( CommonOperations.getTranslation( "button.save" ) );
		
		saveButton.addActionListener( new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Eloszor mindenkit hibatlannak toltok fel, akar mi is volt elotte
				int row = 0;
				Iterator<Entry<Component, Component>> itr = statusIconList.entrySet().iterator();
				while (itr.hasNext()) {
				    Entry<Component, Component> entry = itr.next();
				    //Component key = entry.getKey();
				    JLabel statusLabel = (JLabel)entry.getValue();
				    
				    //dataSection.remove( errorIcon );
				    
				    c.gridy = row;
					c.gridx = POS_STATISICON;
					c.gridwidth = 1;
					c.fill = GridBagConstraints.NONE;
					c.weightx = 0;
					
					statusLabel.setIcon( null );
					statusLabel.setToolTipText( null );
				    
				    row++;
				}

				//Majd meghivom az osztalyt megvalosito objektum sajat save() metodusat, hogy vegezzen hibaellenorzest
				save();
			}
			
		});
		
	}
	
//	public void remove( Component c ){
//		dataSection.remove( c );
//	}
	
	public void remove( Component labelComponent, Component dataComponent ){
		getDataSection().remove( labelComponent );
		getDataSection().remove( dataComponent );
		getDataSection().revalidate();
	}
	
	/**
	 * Az osztaly leszarmazottaiban mar nem engedelyezem a Component, Object paros hozzaadasat
	 * Csak abban az esetben ha ez első parameter Component, a masodik pedig EditorComponentInterface leszarmazottja
	 */
	public void add( Component c, Object o ){
		add( c, (EditorComponentInterface)o );
	}
	
	/**
	 * Hozza ad egy cim-adat parost az oldalhoz
	 * 
	 * @param labelComponent
	 * @param valueComponent
	 */
	public void add( Component labelComponent, EditorComponentInterface valueComponent ){
	
		JPanel dataSection = getDataSection();
		
		//Ha csak megjelenitesrol van szo, akkor
		if( null != mode && mode.equals( EditMode.VIEW ) ){
			
			//a megjeleno VALUE-k nem modosithatoak
			valueComponent.setEnableModify( false );
		}		
		
		//Eltavolitom az elozo ADD funkcio utan az utlso sorba elhelyezett kitolto elemet (ha volt egyaltalan)
		dataSection.remove( filler );
		
		//Eltavolitom az elozo ADD funkcio utan az elhelyezett SAVE gombot (ha volt)
		dataSection.remove( saveButton );
		
		//Label elem
		c.insets = new Insets(0,0,0,0);
		c.gridy = gridY;
		c.gridx = POS_LABEL;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		dataSection.add( labelComponent, c );
	
		//Value elem
		c.gridx = POS_VALUE;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		dataSection.add( valueComponent.getComponent(), c );

		//Status icon	
		c.gridx = POS_STATISICON;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		
		//Nem adok hozza ikont, tehat alapesetben nincs hibajelzes
		JLabel statusIconLabel = new JLabel();		
		dataSection.add( statusIconLabel, c );
		statusIconLabel.addMouseListener( new StatusClickListener(statusIconLabel));
		statusIconList.put( valueComponent.getComponent(), statusIconLabel );
		
		gridY++;
		
		//Ha modositasra van nyitva az ablak
		if( null == mode || mode.equals( EditMode.MODIFY )){
			//Save gomb 
			c.insets = new Insets(20,0,0,0);
			c.gridy = gridY;
			c.gridx = POS_SAVEBUTTON;
			c.gridwidth = 1;
			c.fill = GridBagConstraints.NONE;
			c.weightx = 0;
			c.anchor = GridBagConstraints.LINE_END;  //Jobbra igazitom a Gombokat
			dataSection.add( saveButton, c );

			gridY++;
		}
		
		//Utolso sorba egy kitolto elem helyezese, hogy felfele legyen igazitva az oldal
		c.insets = new Insets(0,0,0,0);
		c.gridy = gridY;
		c.gridx = POS_NULL;
		c.gridwidth = 0;
		c.weighty = 1;		//Ezzel tol fel minden felette levot
		dataSection.add( filler, c );
		
		if( null != mode && mode.equals( EditMode.MODIFY ) ){
			
			//Torolni kell a gomb miatt megnovelt pozicio mutatot
			gridY--;
		}
		
		dataSection.revalidate();
	}
	
	/**
	 * There was an error in case of set of at least one element 
	 * 
	 * @param errorList
	 */
	public void errorAt( LinkedHashMap<Component, String> errorList ){
				
		int row = 0;
		Iterator<Entry<Component, Component>> itr = statusIconList.entrySet().iterator();
		while (itr.hasNext()) {
		    Entry<Component, Component> entry = itr.next();
		    Component key = entry.getKey();
		    JLabel statusLabel = (JLabel)entry.getValue();
		    
		    //dataSection.remove( errorIcon );
		    
		    c.gridy = row;
			c.gridx = POS_STATISICON;
			c.gridwidth = 1;
			c.fill = GridBagConstraints.NONE;
			c.weightx = 0;
			
			if( errorList.containsKey( key )){
				
				ImageIcon errorIcon = CommonOperations.createImageIcon("status-error.png");		
				statusLabel.setIcon( errorIcon );
				statusLabel.setToolTipText( errorList.get(key) );
			}
		    
		    row++;
		}
		
	}
	
	abstract public void save();

	class StatusClickListener implements MouseListener{
		private JLabel statusLabel;
		
		public StatusClickListener( JLabel statusLabel ){
			this.statusLabel = statusLabel;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			String tooltipText = statusLabel.getToolTipText();
			
			//Ha van tooltip
			if( null != tooltipText && tooltipText.length() != 0 ){
				
				//Akkor megjeleniti a hibauzenetet egy ablakban
				JOptionPane.showMessageDialog( DataEditor.this, tooltipText, CommonOperations.getTranslation( "editor.windowtitle.error" ), JOptionPane.ERROR_MESSAGE);

			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {}
		
		@Override
		public void mousePressed(MouseEvent e) {}
		
		@Override
		public void mouseExited(MouseEvent e) {}
		
		@Override
		public void mouseEntered(MouseEvent e) {}
	}
}
