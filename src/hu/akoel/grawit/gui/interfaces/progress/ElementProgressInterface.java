package hu.akoel.grawit.gui.interfaces.progress;

public interface ElementProgressInterface {
	
	public void elementStarted( String name, String operation );
	
	public void elementEnded( String name, String operation );
	
	public void printOutput( String outputValue, String preMessage );
	
	public void printCommand( String command );
}
