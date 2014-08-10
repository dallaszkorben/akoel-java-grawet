package hu.akoel.grawit.gui;

import hu.akoel.grawit.CommonOperations;

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

public abstract class DataPanel extends JPanel{

	private static final long serialVersionUID = -6084357053425935174L;

	public static enum Mode{
		SHOW,
		MODIFY,
		CAPTURE
	}
	
	private final static int POS_NULL = 0;
	private final static int POS_TITLE = 0;
	private final static int POS_VALUE = 1;
	private final static int POS_SAVEBUTTON = 1;
	private final static int POS_STATISICON = 2;
	
	private JPanel titleSection;
	private JPanel dataSection = new JPanel();
	private GridBagConstraints c;
	private int gridY = 0;
	private JLabel filler = new JLabel();
	private Mode mode;
	private JButton saveButton;
//	private JLabel buttonFiller;
	
	private LinkedHashMap<Component, Component> statusIconList = new LinkedHashMap<>();
	
	public DataPanel( Mode mode, String element ){
		this.mode = mode;
		this.setLayout( new BorderLayout() );
		//this.setBackground(Color.cyan);
		this.setBorder( BorderFactory.createLoweredBevelBorder());

		//
		// Cim szekcio
		//
		titleSection = new JPanel();
		titleSection.setBackground( Color.white );
		this.add( titleSection, BorderLayout.NORTH );
		JLabel title = null;
		if( mode.equals( Mode.MODIFY)){
			//title = new JLabel( CommonOperations.getTranslation( "section.title.modify" ) + " - " + element );
			title = new JLabel( element );
		}else if( mode.equals( Mode.SHOW )){
			//title = new JLabel( CommonOperations.getTranslation( "section.title.modify" ) + " - " + element );
			title = new JLabel( element );
		}
		titleSection.add( title );

		title.setFont(new Font( title.getFont().getName(), Font.BOLD, 20 ));

		//
		// Adat szekcio
		//
		//dataSection.setBackground( Color.green );
		dataSection.setLayout( new GridBagLayout() );
		dataSection.setBorder( BorderFactory.createEmptyBorder( 20, 10, 10, 10 ) );
		c = new GridBagConstraints();
		
		this.add( dataSection, BorderLayout.CENTER );
	
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
	
	/**
	 * Hozza ad egy cim-adat parost az oldalhoz
	 * 
	 * @param titleComponent
	 * @param valueComponent
	 */
	public void add( Component titleComponent, Component valueComponent ){
		
		//Ha csak megjelenitesrol van szo, akkor
		if( mode.equals( Mode.SHOW ) ){
			
			//a megjeleno VALUE-k nem modosithatoak
			valueComponent.setEnabled( false );
		}		
		
		//Eltavolitom az elozo ADD funkcio utan az utlso sorba elhelyezett kitolto elemet (ha volt egyaltalan)
		dataSection.remove( filler );
		
		//Eltavolitom az elozo ADD funkcio utan az elhelyezett SAVE gombot (ha volt)
		dataSection.remove( saveButton );
//		dataSection.remove( buttonFiller );
		
		//Title elem
		c.insets = new Insets(0,0,0,0);
		c.gridy = gridY;
		c.gridx = POS_TITLE;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		dataSection.add( titleComponent, c );
	
		//Value elem
		c.gridx = POS_VALUE;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		dataSection.add( valueComponent, c );

		//Status icon	
		c.gridx = POS_STATISICON;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		
		//Nem adok hozza ikont, tehat alapesetben nincs hibajelzes
		JLabel statusIconLabel = new JLabel();		
		dataSection.add( statusIconLabel, c );
		statusIconLabel.addMouseListener( new StatusClickListener(statusIconLabel));
		statusIconList.put( valueComponent, statusIconLabel );
		
		gridY++;
		
		//Ha modositasra van nyitva az ablak
		if( mode.equals( Mode.MODIFY ) ){
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
		
		if( mode.equals( Mode.MODIFY ) ){
			
			//Torolni kell a gomb miatt megnovelt pozicio mutatot
			gridY--;
		}
		
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
				JOptionPane.showMessageDialog(null, tooltipText, CommonOperations.getTranslation( "section.errormessage.windowtitle.error" ), JOptionPane.ERROR_MESSAGE);

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
