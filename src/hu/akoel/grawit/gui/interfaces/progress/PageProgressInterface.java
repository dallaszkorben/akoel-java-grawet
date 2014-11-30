package hu.akoel.grawit.gui.interfaces.progress;

public interface PageProgressInterface {

	public void pageStarted( String pageName, String nodeType  );
	
	public void pageEnded( String pageName, String nodeType );
}
