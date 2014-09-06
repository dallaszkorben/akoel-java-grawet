package hu.akoel.grawit.core.treenodedatamodel.driver;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.core.treenodedatamodel.DriverDataModelInterface;

public abstract class DriverBrowserDataModelInterface extends DriverDataModelInterface{

	private static final long serialVersionUID = 7926898001139103501L;

	public abstract WebDriver getDriver();
}
