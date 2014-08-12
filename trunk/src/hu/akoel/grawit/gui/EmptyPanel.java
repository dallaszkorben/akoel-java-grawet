package hu.akoel.grawit.gui;

public class EmptyPanel extends DataPanel{
	
	private static final long serialVersionUID = 165396704460481021L;

	public EmptyPanel( ){
		super( EditMode.SHOW, "" );
		
		//TODO a feher fejlecet el kell torolni
	}

	@Override
	public void save(){
	}
	
}
