package hu.akoel.grawit.gui.editor;


public class EmptyEditor extends DataEditor{
	
	private static final long serialVersionUID = 165396704460481021L;

	public EmptyEditor( ){
		super( EditMode.NO, null );
	}

	@Override
	public void save(){
	}
	
}
