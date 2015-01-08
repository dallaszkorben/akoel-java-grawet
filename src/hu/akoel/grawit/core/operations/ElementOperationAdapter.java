package hu.akoel.grawit.core.operations;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.Settings;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.enums.SelectorType;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidSelectorException;
import hu.akoel.grawit.exceptions.ElementNotFoundSelectorException;
import hu.akoel.grawit.exceptions.ElementTimeoutException;
import hu.akoel.grawit.exceptions.ElementUnreachableBrowserException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public abstract class ElementOperationAdapter implements Cloneable{
		
	public abstract String getName();
		
	public abstract void setXMLAttribute( Document document, Element element );

	public abstract void doOperation( WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress ) throws ElementException, CompilationException;
	
	/**
	 * Make it visible
	 */
    public abstract Object clone();
  
	public void doAction( WebDriver driver, BaseElementDataModelAdapter baseElement, ElementProgressInterface elementProgress ) throws ElementException, CompilationException{
				
		if( null != elementProgress ){
			elementProgress.elementStarted( baseElement.getName() );
		}

		if( baseElement instanceof NormalBaseElementDataModel ){			
elementProgress.outputCommand("		//" + baseElement.getName() );

			By by = null;
			WebElement webElement = null;
		
			//WAITING TIME FOR APPEARANCE
			Integer waitingTimeForAppearance = ((NormalBaseElementDataModel)baseElement).getWaitingTimeForAppearance();
			if( null == waitingTimeForAppearance ){
				waitingTimeForAppearance = Settings.getInstance().getWaitingTime();
			}
			
			//WAITING TIME BEFORE OPERATION
			Integer waitingTimeBeforeOperation = ((NormalBaseElementDataModel)baseElement).getWaitingTimeBeforeOperation();
			if( null == waitingTimeBeforeOperation ){
				waitingTimeBeforeOperation = 0;
			}

			//WAITING TIME AFTER OPERATION
			Integer waitingTimeAfterOperation = ((NormalBaseElementDataModel)baseElement).getWaitingTimeAfterOperation();
			if( null == waitingTimeAfterOperation ){
				waitingTimeAfterOperation = 0;
			}
			
elementProgress.outputCommand( "		wait = new WebDriverWait(driver, " + waitingTimeForAppearance + ");" );
			WebDriverWait wait = new WebDriverWait(driver, waitingTimeForAppearance);
						
			//Selector meszerzese
			if( ((NormalBaseElementDataModel)baseElement).getSelectorType().equals(SelectorType.ID)){
				
elementProgress.outputCommand( "		by = By.id( \"" + ((NormalBaseElementDataModel)baseElement).getSelector() + "\" );" );
				by = By.id( ((NormalBaseElementDataModel)baseElement).getSelector() );
				//CSS
			}else if( ((NormalBaseElementDataModel)baseElement).getSelectorType().equals(SelectorType.CSS)){
elementProgress.outputCommand( "		by = By.cssSelector( \"" + ((NormalBaseElementDataModel)baseElement).getSelector() + "\" );" );
				by = By.cssSelector( ((NormalBaseElementDataModel)baseElement).getSelector() );
			}
						
			//Varakozik az elem megjeleneseig - WAITING TIME FOR APPEARANCE
			try{
				
				wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
				//wait.until(ExpectedConditions.elementToBeClickable( by ) );
elementProgress.outputCommand( "		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));" );		
			
			//Ha nem jelenik meg idoben, akkor hibajelzessel megall
			}catch( org.openqa.selenium.TimeoutException timeOutException ){
				throw new ElementTimeoutException( baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), timeOutException );
			}catch(org.openqa.selenium.remote.UnreachableBrowserException unreachableBrowserException){
				throw new ElementUnreachableBrowserException( unreachableBrowserException);
			}
		
			//Beazonositja az elemet
			try{
elementProgress.outputCommand( "		webElement = driver.findElement( by );" );					
				webElement = driver.findElement( by );
			}catch ( org.openqa.selenium.InvalidSelectorException invalidSelectorException ){
				throw new ElementInvalidSelectorException(baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), invalidSelectorException );
			}catch ( org.openqa.selenium.NoSuchElementException noSuchElementException ){
				throw new ElementNotFoundSelectorException( baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), noSuchElementException );
			}
		
			if( null == webElement ){
				throw new ElementNotFoundSelectorException( baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), new Exception() );
			}
		
			//Varakozik, ha szukseges a muvelet elott
			try {Thread.sleep(waitingTimeBeforeOperation);} catch (InterruptedException e) {}			
			
			//OPERATION
			doOperation( driver, baseElement, webElement, elementProgress );
elementProgress.outputCommand("");	

			//Varakozik, ha szukseges a muvelet utan
			try {Thread.sleep(waitingTimeAfterOperation);} catch (InterruptedException e) {}			

		}else if( baseElement instanceof ScriptBaseElementDataModel ){
			
			//OPERATION
			doOperation( driver, baseElement, null, elementProgress );
			
		}

		if( null != elementProgress ){
			elementProgress.elementEnded( baseElement.getName() );
		}		
		
	}

}
