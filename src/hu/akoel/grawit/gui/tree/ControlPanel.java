package hu.akoel.grawit.gui.tree;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hu.akoel.grawit.CommonOperations;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel{

	private static final long serialVersionUID = 4573456891692096378L;
	
	public static enum Status{
		RUNNING,
		PAUSING,
		STADY		
	}
	
	public static enum Action{
		START_ACTION,
		STOP_ACTION,
		PAUSE_ACTION,
		CONTINUE_ACTION
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
	
	private ImageIcon continueIcon = CommonOperations.createImageIcon("control/control-continue.png");
	private ImageIcon continueDisabledIcon = CommonOperations.createImageIcon("control/control-continue-disabled.png");
	private ImageIcon continueRolloverIcon = CommonOperations.createImageIcon("control/control-continue.png");
	private ImageIcon continuePressedIcon = CommonOperations.createImageIcon("control/control-continue.png");
	private ImageIcon continueSelectedIcon = CommonOperations.createImageIcon("control/control-continue.png");

	private JButton startButton;
	private JButton stopButton;
	private JButton pauseButton;
	private JButton continueButton;
	
	private boolean needToStop = false;	
	private boolean needToPause = false;
	
	private ButtonActionInterface buttonAction;
	
	private Status status;
	
	public ControlPanel(){
		super();
		
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
		// CONTINUE
		//
		continueButton = new JButton();
		
		continueButton.setBorderPainted(false);
		continueButton.setBorder(null);
		continueButton.setFocusable(false);
		continueButton.setMargin(new Insets(0, 0, 0, 0));
		continueButton.setContentAreaFilled(false);

		continueButton.setSelectedIcon(continueSelectedIcon);
		continueButton.setRolloverIcon((continueRolloverIcon));
		continueButton.setPressedIcon(continuePressedIcon);		
		continueButton.setDisabledIcon(continueDisabledIcon);
		continueButton.setIcon(continueIcon);
		
		continueButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
	
				changeStatusByAction( Action.CONTINUE_ACTION );
	
			}						
		});	
		
		//Gombok elhelyezese
		this.setLayout( new FlowLayout() );
		this.add(startButton);
		this.add(stopButton);
		this.add( pauseButton );
		this.add( continueButton );		

		//Indulasi beallitas
		stopButton.setEnabled( false );
		pauseButton.setEnabled( false );
		continueButton.setEnabled( false );
		
		//Ha nincs Driver definialva, akkor nem indulhat el egy teszt sem
//		if( null == ((TestcaseRootDataModel)selectedTestcase.getRoot()).getDriverDataModel() ){
//			startButton.setEnabled( false );
//		}else{
			startButton.setEnabled( true );
//		}

		this.status = Status.STADY;
		
	}
	
	private void changeStatusByAction( Action action ){
		
		//START
		if( action.equals( Action.START_ACTION ) ){
			
			//Ha az aktualis satus STADY
			if( status.equals( Status.STADY ) ){
				
				//Akkor most RUNNING kovetkezik
				this.status = Status.RUNNING;
				
				//Letiltja a Inditas gombot
				startButton.setEnabled( false );
								
				//Engedelyezi a Stop gombot
				stopButton.setEnabled( true );

				//Engedelyezi a Pause gombot
				pauseButton.setEnabled( true );
				
				//Tiltja a Continue gombot
				continueButton.setEnabled( false );

				//A figyelok szamara jelzem, hogy ne allitsak meg es ne pause-oljak a futast
				setNeedToStop( false );
				setNeedToPause( false );
				
			}else{
				System.err.println( "gaz van a statuszokkal");
				System.exit( -1 );
			}
			
		//STOP
		}else if( action.equals( Action.STOP_ACTION ) ){
			
			//Ha az aktualis status RUNNING
			if( status.equals( Status.RUNNING ) ){
				
				this.status = Status.STADY;
				
				//Engedelyezi az Inditas gombot
		    	startButton.setEnabled( true );
		    	
		    	//Tiltja a Stop gombot
		    	stopButton.setEnabled( false );
		    	
		    	//Tiltja a Pause gombot
		    	pauseButton.setEnabled( false );		
		    	
				//Tiltja a Continue gombot
				continueButton.setEnabled( false );
				
				//A figyelok szamara jelzem, hogy ne allitsak meg es ne pause-oljak a futast
				setNeedToStop( false );
				setNeedToPause( false );
				
			}else{
				System.err.println( "gaz van a statuszokkal");
				System.exit( -1 );
			}
			
		//PAUSE
		}else if( action.equals( Action.PAUSE_ACTION ) ){
			
			//Ha az aktualis status RUNNING
			if( status.equals( Status.RUNNING ) ){
				
				this.status = Status.PAUSING;
				
				//Tiltja az Inditas gombot
		    	startButton.setEnabled( false );
		    	
		    	//Engedelyeze a Stop gombot
		    	stopButton.setEnabled( true );
		    	
				//Letiltja a Pause gombot
				pauseButton.setEnabled( false );
				
				//Engedelyezi a Continue gombot
				continueButton.setEnabled( true );
				
				//Jelzi a figyelo szamara, hogy pause-olja a futast
				setNeedToPause( true );

			}else{
				System.err.println( "gaz van a statuszokkal");
				System.exit( -1 );
			}
			
		//CONTINUE
		}else if( action.equals( Action.CONTINUE_ACTION ) ){
			
			//Ha az aktualis status PAUSING
			if( status.equals( Status.PAUSING ) ){
			
				this.status = Status.RUNNING;
				
				//Tiltja az Inditas gombot
		    	startButton.setEnabled( false );
		    	
		    	//Engedelyeze a Stop gombot
		    	stopButton.setEnabled( true );
		    	
				//Engedelyezem a Pause gombot
				pauseButton.setEnabled( true );
				
				//Tiltom a Continue gombot
				continueButton.setEnabled( false );
				
				setNeedToPause( false );	
				
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
