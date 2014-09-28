package hu.akoel.grawit;

public interface ElementProgressInterface {

	public void elementStarted( String name, String value  );
	
	public void elementStarted( String name );
		
	public void elementEnded( String name, String value );
	
	public void elementEnded( String name );
	
	public void getMessage( String message );
	
}
