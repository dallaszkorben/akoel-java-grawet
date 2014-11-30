package hu.akoel.grawit.gui.interfaces.progress;

public interface ElementProgressInterface {
	
	public void elementStarted( String name );
	
	public void elementEnded( String name );
	
	public void outputValue( String outputValue, String message );
	
	public void outputCommand( String command );
}
