package hu.akoel.grawit;

public interface PageProgressInterface {

	public void pageStarted( String pageName, String nodeType  );
	
	public void pageEnded( String pageName, String nodeType );
}
