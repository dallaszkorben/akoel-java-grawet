package hu.akoel.grawit.core.treenodedatamodel.driver;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelAdapter;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public abstract class DriverBrowserDataModelInterface<E> extends DriverDataModelAdapter{

	private static final long serialVersionUID = 7926898001139103501L;

	public abstract WebDriver getDriver( ElementProgressInterface elementProgres );
	
	public abstract void add( E node );
	
	public void add( DriverDataModelAdapter node ){
		add( (E)node );
	}
	
}
