package hu.akoel.grawit.gui.interfaces.progress;

public interface ElementProgressInterface {
	
	public void elementStarted( String name, String operation );
	
	public void elementEnded( String name, String operation );
	
	public void outputValue( String outputValue, String message );
	
	public void outputCommand( String command );
}
