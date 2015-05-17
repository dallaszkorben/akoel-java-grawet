package hu.akoel.grawit.gui.tree;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hu.akoel.grawit.CommonOperations;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel{

	private static final long serialVersionUID = 4573456891692096378L;
	private static final int POSITION_START = 0;
	private static final int POSITION_STOP = 1;
	private static final int POSITION_NEXT = 2;
	
	
	public static enum Status{
		RUNNING( "Running" ),
		PAUSING( "Paused" ),
		STADY( "Steady" );
		
		private String readableName;
		
		Status( String readableName ){
			this.readableName = readableName;
		}
		
		public String getReadableName(){
			return this.readableName;
		}
		
	}
	
	public static enum Action{
		START_ACTION,
		STOP_ACTION,
		PAUSE_ACTION,
		RESUME_ACTION
	}
	
	private ImageIcon startIcon = CommonOperations.createImageIcon("control/control-play.png");
	private ImageIcon startDisabledIcon = CommonOperations.createImageIcon("control/control-play-disabled.png");
	private ImageIcon startRolloverIcon = CommonOperations.createImageIcon("control/control-play-rollover.png");
	private ImageIcon startPressedIcon = CommonOperations.createImageIcon("control/control-play-pressed.png");
	private ImageIcon startSelectedIcon = CommonOperations.createImageIcon("control/control-play-selected.png");		
	
	private ImageIcon stopIcon = CommonOperations.createImageIcon("control/control-stop.png");
	private ImageIcon stopDisabledIcon = CommonOperations.createImageIcon("control/control-stop-disabled.png");
	private ImageIcon stopRolloverIcon = CommonOperations.createImageIcon("control/control-stop-rollover.png");
	private ImageIcon stopPressedIcon = CommonOperations.createImageIcon("control/control-stop-pressed.png");
	private ImageIcon stopSelectedIcon = CommonOperations.createImageIcon("control/control-stop-selected.png");
	
	private ImageIcon pauseIcon = CommonOperations.createImageIcon("control/control-pause.png");
	private ImageIcon pauseDisabledIcon = CommonOperations.createImageIcon("control/control-pause-disabled.png");
	private ImageIcon pauseRolloverIcon = CommonOperations.createImageIcon("control/control-pause-rollover.png");
	private ImageIcon pausePressedIcon = CommonOperations.createImageIcon("control/control-pause-pressed.png");
	private ImageIcon pauseSelectedIcon = CommonOperations.createImageIcon("control/control-pause-selected.png");
	
	private ImageIcon resumeIcon = CommonOperations.createImageIcon("control/control-resume.png");
	private ImageIcon resumeDisabledIcon = CommonOperations.createImageIcon("control/control-resume-disabled.png");
	private ImageIcon resumeRolloverIcon = CommonOperations.createImageIcon("control/control-resume-rollover.png");
	private ImageIcon resumePressedIcon = CommonOperations.createImageIcon("control/control-resume-pressed.png");
	private ImageIcon resumeSelectedIcon = CommonOperations.createImageIcon("control/control-resume-selected.png");
	
	private ImageIcon nextIcon = CommonOperations.createImageIcon("control/control-next.png");
	private ImageIcon nextDisabledIcon = CommonOperations.createImageIcon("control/control-next-disabled.png");
	private ImageIcon nextRolloverIcon = CommonOperations.createImageIcon("control/control-next-rollover.png");
	private ImageIcon nextPressedIcon = CommonOperations.createImageIcon("control/control-next-pressed.png");
	private ImageIcon nextSelectedIcon = CommonOperations.createImageIcon("control/control-next-selected.png");
	
	private ImageIcon emptyIcon = CommonOperations.createImageIcon("control/control-empty.png");
	
	private JButton emptyButton;
	private JButton startButton;
	private JButton stopButton;
	private JButton pauseButton;
	private JButton resumeButton;
	private JButton nextButton;
	
	private boolean needToStop = false;	
	private boolean needToPause = false;
	
	private ButtonActionInterface buttonAction;
	
	private Status status;
	
	public ControlPanel(){
		super();
		
		//
		// EMPTY
		//
		emptyButton = new JButton();
		
		emptyButton.setBorderPainted( false );
		emptyButton.setBorder( null );
		emptyButton.setFocusable( false );
		emptyButton.setMargin( new Insets( 0, 0, 0, 0 ) );
		emptyButton.setContentAreaFilled( false );

		emptyButton.setSelectedIcon( emptyIcon );
		emptyButton.setRolloverIcon( emptyIcon );
		emptyButton.setPressedIcon( emptyIcon );
		emptyButton.setDisabledIcon( emptyIcon );		
		emptyButton.setIcon( emptyIcon);
		
		//
		// START
		//
		startButton = new JButton();
		
		startButton.setBorderPainted(false);
		startButton.setBorder(null);
		startButton.setFocusable(false);
		startButton.setMargin(new Insets(0, 0, 0, 0));
		startButton.setContentAreaFilled(false);

		startButton.setSelectedIcon(startSelectedIcon);
		startButton.setRolloverIcon((startRolloverIcon));
		startButton.setPressedIcon(startPressedIcon);
		startButton.setDisabledIcon(startDisabledIcon);		
		startButton.setIcon(startIcon);
		
		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
								
				new Thread( new Runnable() {

					@Override
					public void run() {
						
						changeStatusByAction( Action.START_ACTION );
						
						//START RUNNING
						buttonAction.doStartButtonAction();

						changeStatusByAction( Action.STOP_ACTION );
							
					}				 
				}).start();
			}						
		});		

		//
		// STOP
		//
		stopButton = new JButton();
		
		stopButton.setBorderPainted(false);
		stopButton.setBorder(null);
		stopButton.setFocusable(false);
		stopButton.setMargin(new Insets(0, 0, 0, 0));
		stopButton.setContentAreaFilled(false);

		stopButton.setSelectedIcon(stopSelectedIcon);
		stopButton.setRolloverIcon((stopRolloverIcon));
		stopButton.setPressedIcon(stopPressedIcon);
		stopButton.setDisabledIcon(stopDisabledIcon);
		stopButton.setIcon(stopIcon);

		stopButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
								
				//Csak jelzi a figyelo szamara, hogy allitsa meg a futast
				setNeedToStop( true );
				setNeedToPause( false );
			}						
		});	
		
		//
		// PAUSE
		//
		pauseButton = new JButton();
		
		pauseButton.setBorderPainted(false);
		pauseButton.setBorder(null);
		pauseButton.setFocusable(false);
		pauseButton.setMargin(new Insets(0, 0, 0, 0));
		pauseButton.setContentAreaFilled(false);

		pauseButton.setSelectedIcon(pauseSelectedIcon);
		pauseButton.setRolloverIcon((pauseRolloverIcon));
		pauseButton.setPressedIcon(pausePressedIcon);		
		pauseButton.setDisabledIcon(pauseDisabledIcon);
		pauseButton.setIcon(pauseIcon);
		
		pauseButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					
				changeStatusByAction( Action.PAUSE_ACTION );
			}						
		});	
		
		//
		// RESUME
		//
		resumeButton = new JButton();
		
		resumeButton.setBorderPainted(false);
		resumeButton.setBorder(null);
		resumeButton.setFocusable(false);
		resumeButton.setMargin(new Insets(0, 0, 0, 0));
		resumeButton.setContentAreaFilled(false);

		resumeButton.setSelectedIcon( resumeSelectedIcon );
		resumeButton.setRolloverIcon( resumeRolloverIcon );
		resumeButton.setPressedIcon( resumePressedIcon );		
		resumeButton.setDisabledIcon( resumeDisabledIcon );
		resumeButton.setIcon( resumeIcon );
		
		resumeButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					
				changeStatusByAction( Action.RESUME_ACTION );
			}						
		});
		
		//
		// NEXT
		//
		nextButton = new JButton();
		
		nextButton.setBorderPainted(false);
		nextButton.setBorder(null);
		nextButton.setFocusable(false);
		nextButton.setMargin(new Insets(0, 0, 0, 0));
		nextButton.setContentAreaFilled(false);

		nextButton.setSelectedIcon( nextSelectedIcon );
		nextButton.setRolloverIcon( nextRolloverIcon );
		nextButton.setPressedIcon( nextPressedIcon );		
		nextButton.setDisabledIcon( nextDisabledIcon );
		nextButton.setIcon( nextIcon );
		
		nextButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					
//				changeStatusByAction( Action.RESUME_ACTION );
			}						
		});		
		
		
		//Gombok elhelyezese
		//this.setLayout( new FlowLayout() );
		this.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = POSITION_START;
		c.gridy = 0;
		c.insets = new Insets( 0, 5, 0, 5 );
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;		
		this.add(startButton, c);
		
		c.gridx = POSITION_STOP;
		c.insets = new Insets( 0, 0, 0, 5 );
		this.add(stopButton, c);
		
		c.gridx = POSITION_NEXT;
		c.insets = new Insets( 0, 0, 0, 5 );
		this.add( nextButton, c );

		//Indulasi beallitas
		stopButton.setEnabled( false );
		pauseButton.setEnabled( false );
		nextButton.setEnabled( false );
		startButton.setEnabled( true );		
		
		//Ha nincs Driver definialva, akkor nem indulhat el egy teszt sem
//		if( null == ((TestcaseRootDataModel)selectedTestcase.getRoot()).getDriverDataModel() ){
//			startButton.setEnabled( false );
//		}else{
//			startButton.setEnabled( true );
//		}

		this.status = Status.STADY;
		
	}
	
	private void changeStatusByAction( Action action ){
		
		//START
		if( action.equals( Action.START_ACTION ) ){
			
			//Ha az aktualis status STEADY
			if( status.equals( Status.STADY ) ){
				
				//Akkor most RUNNING kovetkezik
				this.status = Status.RUNNING;
				
				//A figyelok szamara jelzem, hogy ne allitsak meg es ne pause-oljak a futast
				setNeedToStop( false );
				setNeedToPause( false );
				
				//Letiltja a Inditas gombot
				startButton.setEnabled( false );

				//Engedelyezi a Stop gombot
				stopButton.setEnabled( true );

				//Engedelyezi a Pause gombot
				pauseButton.setEnabled( true );
				
				//Tiltja a Continue gombot
		    	resumeButton.setEnabled( false );
		    	
		    	//Tiltja a Next gombot
		    	nextButton.setEnabled( false );		    	   	
				
				//Lecserelem a START gombot PAUSE gombra
				this.remove( startButton );
				GridBagConstraints c = new GridBagConstraints();				
				c.gridx = POSITION_START;
				c.gridy = 0;
				c.insets = new Insets(0, 5, 0, 5);
				c.gridwidth = 1;
				c.gridheight = 1;
				c.fill = GridBagConstraints.NONE;
				c.weightx = 0;
				c.weighty = 0;
				c.anchor = GridBagConstraints.FIRST_LINE_START;
				this.add( pauseButton, c );
				this.revalidate();
				this.repaint();
				
			}else{
				System.err.println( "gaz van a statuszokkal");
				System.exit( -1 );
			}
			
		//STOP
		}else if( action.equals( Action.STOP_ACTION ) ){
			
			//Ha az aktualis status RUNNING
			if( status.equals( Status.RUNNING ) ){
				
				this.status = Status.STADY;
				
				//A figyelok szamara jelzem, hogy ne allitsak meg es ne pause-oljak a futast
				//setNeedToStop( false );
				//setNeedToPause( false );
				
				//Engedelyezi az Inditas gombot
		    	startButton.setEnabled( true );
		    	
		    	//Tiltja a Stop gombot
		    	stopButton.setEnabled( false );
		    	
		    	//Tiltja a Pause gombot
		    	pauseButton.setEnabled( false );	
		    	
				//Tiltja a Continue gombot
		    	resumeButton.setEnabled( false );
		    	
		    	//Tiltja a Next gombot
		    	nextButton.setEnabled( false );
		    	
				//Lecserelem a PAUSE gombot START gombra
				this.remove( pauseButton );
				GridBagConstraints c = new GridBagConstraints();				
				c.gridx = POSITION_START;
				c.gridy = 0;
				c.insets = new Insets(0, 5, 0, 5);
				c.gridwidth = 1;
				c.gridheight = 1;
				c.fill = GridBagConstraints.NONE;
				c.weightx = 0;
				c.weighty = 0;
				c.anchor = GridBagConstraints.FIRST_LINE_START;
				this.add( startButton, c );
				this.revalidate();
				this.repaint();
			
			//Ha az aktualis status PAUSE
			}else if( status.equals( Status.PAUSING ) ){
				
				this.status = Status.STADY;
				
				//A figyelok szamara jelzem, hogy ne allitsak meg es ne pause-oljak a futast
				//setNeedToStop( false );
				//setNeedToPause( false );
				
				//Engedelyezi az Inditas gombot
		    	startButton.setEnabled( true );
		    	
		    	//Tiltja a Stop gombot
		    	stopButton.setEnabled( false );
		    	
		    	//Tiltja a Pause gombot
		    	pauseButton.setEnabled( false );
		    	
				//Tiltja a Continue gombot
		    	resumeButton.setEnabled( false );
		    	
		    	//Tiltja a Next gombot
		    	nextButton.setEnabled( false );
		    	
				//Lecserelem a RESUME gombot START gombra
				this.remove( resumeButton );
				GridBagConstraints c = new GridBagConstraints();				
				c.gridx = POSITION_START;
				c.gridy = 0;
				c.insets = new Insets(0, 5, 0, 5);
				c.gridwidth = 1;
				c.gridheight = 1;
				c.fill = GridBagConstraints.NONE;
				c.weightx = 0;
				c.weighty = 0;
				c.anchor = GridBagConstraints.FIRST_LINE_START;
				this.add( startButton, c );
				this.revalidate();
				this.repaint();
				
			}else{
				System.err.println( "gaz van a statuszokkal");
				System.exit( -1 );
			}
			
		//PAUSE
		}else if( action.equals( Action.PAUSE_ACTION ) ){
			
			//Ha az aktualis status RUNNING
			if( status.equals( Status.RUNNING ) ){
				
				this.status = Status.PAUSING;
				
				//Jelzi a figyelo szamara, hogy pause-olja a futast
				setNeedToPause( true );
				
				//Engedelyezi a folytatas gombot
		    	startButton.setEnabled( true );
		    	
		    	//Engedelyeze a Stop gombot
		    	stopButton.setEnabled( true );
		    	
				//Letiltja a Pause gombot
				pauseButton.setEnabled( false );
				
				//Engedelyeze a Continue gombot
		    	resumeButton.setEnabled( true );

		    	//Engedelyezem a Next gombot
		    	nextButton.setEnabled( true );

				//Lecserelem a PAUSE gombot CONTINUE gombra
				this.remove( pauseButton );
				GridBagConstraints c = new GridBagConstraints();				
				c.gridx = POSITION_START;
				c.gridy = 0;
				c.insets = new Insets(0, 5, 0, 5);
				c.gridwidth = 1;
				c.gridheight = 1;
				c.fill = GridBagConstraints.NONE;
				c.weightx = 0;
				c.weighty = 0;
				c.anchor = GridBagConstraints.FIRST_LINE_START;
				this.add( resumeButton, c );
				this.revalidate();
				this.repaint();

			}else{
				System.err.println( "gaz van a statuszokkal");
				System.exit( -1 );
			}
			
		//CONTINUE
		}else if( action.equals( Action.RESUME_ACTION ) ){
			
			//Ha az aktualis status PAUSING
			if( status.equals( Status.PAUSING ) ){
			
				this.status = Status.RUNNING;
				
				setNeedToPause( false );	

				//Tiltja az Inditas gombot
		    	startButton.setEnabled( false );
		    	
		    	//Engedelyeze a Stop gombot
		    	stopButton.setEnabled( true );
		    	
				//Engedelyezem a Pause gombot
				pauseButton.setEnabled( true );

		    	//Tiltja a Next gombot
		    	nextButton.setEnabled( false );

				//Lecserelem a CONTINUE gombot PAUSE gombra
				this.remove( resumeButton );
				GridBagConstraints c = new GridBagConstraints();				
				c.gridx = POSITION_START;
				c.gridy = 0;
				c.insets = new Insets(0, 5, 0, 5);
				c.gridwidth = 1;
				c.gridheight = 1;
				c.fill = GridBagConstraints.NONE;
				c.weightx = 0;
				c.weighty = 0;
				c.anchor = GridBagConstraints.FIRST_LINE_START;
				this.add( pauseButton, c );
				this.revalidate();
				this.repaint();
			
			}else{
				System.err.println( "gaz van a statuszokkal");
				System.exit( -1 );
			}
		}
		
	}

	public void setButtonAction( ButtonActionInterface buttonAction ) {
		this.buttonAction = buttonAction;
	}
	
	public void setNeedToStop( boolean needToStop ){
		this.needToStop = needToStop;
	}
	
	public void setNeedToPause( boolean needToPause ){
		this.needToPause = needToPause;
	}
	
	public boolean neededToStop(){
		return needToStop;
	}
	
	public boolean neededToPause(){
		return needToPause;
	}
}
