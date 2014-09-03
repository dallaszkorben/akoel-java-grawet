package hu.akoel.grawit;

public interface BrowserInterface {

	public enum BrowserType{
		EXPLORER,
		FIREFOX,
		CHROME
	}
	
	public void open();

	
/*	if( name.equals("E")){
		
		file = new File("c:/Programs/webdrivers/IEDriverServer.exe");
		System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
		DesiredCapabilities iecaps = DesiredCapabilities.internetExplorer();
		iecaps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

		return new InternetExplorerDriver();
		
	}else if( name.equals("F")){
		
		FirefoxProfile profile = new FirefoxProfile();
		
		//Megakadalyozza, hogy a sajat oldalan nyissa meg a nyomtatvanyt.
		//E helyett inkább csak letolti a nyomtatványt és nem torodik vele
//		profile.setPreference("browser.download.folderList", 2);	//0-desktop, 1-default download location, 2-custom folder
//		profile.setPreference("browser.download.dir", "C:\\tmp");
//		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");	//Dialog ablak
		profile.setPreference("pdfjs.disabled", true);				//Megakadályozza az allomany megtekinteset a beepitett bongeszovel
		
//		profile.setPreference("plugin.scan.plid.all", false);		//false-nem keresi vegeig es tolti be a plugin-okat			
//		profile.setPreference("plugin.scan.Acrobat", "99.0");		//minimum ezt az Adobe Acrobat verziot tolti be. 99.0 biztos hogy nincs
		
		profile.setPreference("media.navigator.permission.disabled", true);
		return new FirefoxDriver(profile);
		
	}else if( name.equals("C")){
		
		file = new File("c://Programs/webdrivers/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		
		return new ChromeDriver();
	}else{
		return null;
	}
*/	
}
