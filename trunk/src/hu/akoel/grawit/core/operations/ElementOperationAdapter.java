package hu.akoel.grawit.core.operations;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.Settings;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.enums.SelectorType;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidSelectorException;
import hu.akoel.grawit.exceptions.ElementNotFoundSelectorException;
import hu.akoel.grawit.exceptions.ElementTimeoutException;
import hu.akoel.grawit.exceptions.ElementUnreachableBrowserException;

public abstract class ElementOperationAdapter implements Cloneable{
		
	public abstract String getName();
		
	public abstract void setXMLAttribute( Document document, Element element );

	public abstract void doOperation( WebDriver driver, ParamElementDataModel element, WebElement webElement, ElementProgressInterface elementProgress ) throws ElementException, CompilationException;
	
	/**
	 * Make it visible
	 */
    public abstract Object clone();
  
	public void doAction( WebDriver driver, ParamElementDataModel element, ElementProgressInterface elementProgress ) throws ElementException, CompilationException{
		
		if( element.getBaseElement() instanceof NormalBaseElementDataModel ){
				
			if( null != elementProgress ){
				elementProgress.elementStarted( element.getName() );
			}
	
elementProgress.outputCommand("		//" + element.getName() );

			BaseElementDataModelAdapter baseElement = element.getBaseElement();
			By by = null;
			WebElement webElement = null;
		
			//WAITING TIME
			Integer waitingTime = ((NormalBaseElementDataModel)baseElement).getWaitingTime();
			if( null == waitingTime ){
				waitingTime = Settings.getInstance().getWaitingTime();
			}
			
elementProgress.outputCommand( "		wait = new WebDriverWait(driver, " + waitingTime + ");" );
			WebDriverWait wait = new WebDriverWait(driver, waitingTime);
						
			//Selector meszerzese
			if( ((NormalBaseElementDataModel)baseElement).getSelectorType().equals(SelectorType.ID)){
				
elementProgress.outputCommand( "		by = By.id( \"" + ((NormalBaseElementDataModel)baseElement).getSelector() + "\" );" );
				by = By.id( ((NormalBaseElementDataModel)baseElement).getSelector() );
				//CSS
			}else if( ((NormalBaseElementDataModel)baseElement).getSelectorType().equals(SelectorType.CSS)){
elementProgress.outputCommand( "		by = By.cssSelector( \"" + ((NormalBaseElementDataModel)baseElement).getSelector() + "\" );" );
				by = By.cssSelector( ((NormalBaseElementDataModel)baseElement).getSelector() );
			}
						
			//Varakozik az elem megjeleneseig, de max 10 mp-ig
			try{
				
				wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
				//wait.until(ExpectedConditions.elementToBeClickable( by ) );
elementProgress.outputCommand( "		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));" );		
			}catch( org.openqa.selenium.TimeoutException timeOutException ){
				throw new ElementTimeoutException( element.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), timeOutException );
			}catch(org.openqa.selenium.remote.UnreachableBrowserException unreachableBrowserException){
				throw new ElementUnreachableBrowserException( unreachableBrowserException);
			}
		
			try{
elementProgress.outputCommand( "		webElement = driver.findElement( by );" );					
				webElement = driver.findElement( by );
			}catch ( org.openqa.selenium.InvalidSelectorException invalidSelectorException ){
				throw new ElementInvalidSelectorException(element.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), invalidSelectorException );
			}catch ( org.openqa.selenium.NoSuchElementException noSuchElementException ){
				throw new ElementNotFoundSelectorException( element.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), noSuchElementException );
			}
		
			if( null == webElement ){
				throw new ElementNotFoundSelectorException( element.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), new Exception() );
			}
		
			//OPERATION
			doOperation( driver, element, webElement, elementProgress );
elementProgress.outputCommand("");		
			if( null != elementProgress ){
				elementProgress.elementEnded( element.getName() );
			}
		}
		
	}

}
