package hu.akoel.grawit.gui.editors.component.fileselector;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.gui.editors.component.EditorComponentInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileSelectorComponent extends JPanel implements EditorComponentInterface{

	private static final long serialVersionUID = -7847132460513127519L;
	
	private static final Color FIELD_BACKGROUND = new Color(209,224,224);
	private JButton button;
	private JTextField field = new JTextField();
	
	private File selectedFile;
	
	/**
	 * Uj rogzites
	 * 
	 * @param rootDataModel
	 */
	public FileSelectorComponent( FileNameExtensionFilter filter ){
		super();
	
		common( filter, null );		
	}
	
	/**
	 * Modositas
	 * 
	 * @param rootDataModel
	 * @param selectedDataModel
	 */
	public FileSelectorComponent( FileNameExtensionFilter filter, File selectedFile ){
		super();
	
		common( filter, selectedFile );

		setSelectedDataModelToField( selectedFile );
		
	}
	
	private void common( final FileNameExtensionFilter filter, final File selectedFile ){	
		
		this.setLayout(new BorderLayout());
		
		field.setEditable( false );
		field.setBackground(FIELD_BACKGROUND);
		button = new JButton("...");
		
		//Ha benyomom a gombot
		this.button.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc;
				if (null == selectedFile) {
					fc = new JFileChooser(System.getProperty("user.dir"));
				} else {
					fc = new JFileChooser( selectedFile );
				}
				
				//Dialogusablak cime
				fc.setDialogTitle( CommonOperations.getTranslation( "window.title.opentestsuit"));
				
				// Csak a parameterkent megkapott kiterjesztesu fajlokat lathatom
				fc.setFileFilter(filter);

				// Nem engedi meg az "All" filter hasznalatat
				fc.setAcceptAllFileFilterUsed(false);

				// Dialogus ablak inditasa
				int returnVal = fc.showOpenDialog( FileSelectorComponent.this );
				
				//File valasztas tortent
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					
					setSelectedDataModelToField( fc.getSelectedFile() );
				
				}			
			}
		} );

		this.add( field, BorderLayout.CENTER);
		this.add( button, BorderLayout.EAST );
	}
	

	@Override
	public void setEnableModify(boolean enable) {
		button.setEnabled( enable );
	}

	@Override
	public Component getComponent() {	
		return this;
	}
	
	public File getSelectedFile(){
		if( null == selectedFile ){
			return null;
		}		
		return selectedFile;
	}

	/**
	 * A parameterkent megkapott kivalasztott filet elhelyezi a valtozoban e megjeleniti a mezoben
	 * 
	 * @param selectedDataModel
	 */
	private void setSelectedDataModelToField( File selectedFile ){
		this.selectedFile = selectedFile;	
		field.setText( selectedFile.getName() );
	}
	
}


	