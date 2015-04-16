package hu.akoel.grawit.core.operations;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.WorkingDirectory;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.enums.SelectorType;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.ElementCompareOperationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidSelectorException;
import hu.akoel.grawit.exceptions.ElementNotFoundSelectorException;
import hu.akoel.grawit.exceptions.ElementTimeoutException;
import hu.akoel.grawit.exceptions.ElementUnreachableBrowserException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public abstract class ElementOperationAdapter implements Cloneable{
		
	/**
	 * Name for XML
	 * @return
	 */
	public abstract String getName();
		
	/**
	 * Name for display
	 * @return
	 */
	public abstract String getOperationNameToString();
	
	public abstract void setXMLAttribute( Document document, Element element );

	public abstract void doOperation( WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress, String tab ) throws ElementException, CompilationException;
	
	/**
	 * Make it visible
	 */
    public abstract Object clone();

    public void doAction( WebDriver driver, BaseElementDataModelAdapter baseElement, ElementProgressInterface elementProgress ) throws ElementException, CompilationException{
    	
    	doAction( driver, baseElement, elementProgress, false ); 
    }
    
	public void doAction( WebDriver driver, BaseElementDataModelAdapter baseElement, ElementProgressInterface elementProgress, boolean needElementEndedAtException ) throws ElementException, CompilationException{
		
		ArrayList<String> elementOperationList = new ArrayList<>();
		
		//Uzenet az Operation Indulasarol
		if( null != elementProgress ){
			elementProgress.elementStarted( baseElement.getName(), getOperationNameToString() );
		}

		//
		//Szukseges az elem beazonositasa
		//
		if( baseElement instanceof NormalBaseElementDataModel ){
			
			elementOperationList
elementProgress.outputCommand("		//" + baseElement.getName() );

			By by = null;
			WebElement webElement = null;
		
			//WAITING TIME FOR APPEARANCE
			Integer waitingTimeForAppearance = ((NormalBaseElementDataModel)baseElement).getWaitingTimeForAppearance();
			if( null == waitingTimeForAppearance ){
				waitingTimeForAppearance = WorkingDirectory.getInstance().getWaitingTime();
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
				
			//
			//Varakozik az elem megjeleneseig - WAITING TIME FOR APPEARANCE
			//
			try{			
				wait.until(ExpectedConditions.visibilityOfElementLocated( by ));			
				//wait.until(ExpectedConditions.elementToBeClickable( by ) );
elementProgress.outputCommand( "		wait.until(ExpectedConditions.visibilityOfElementLocated( by ));" );		
			
			//Ha nem jelenik meg idoben, akkor hibajelzessel megall
			}catch( org.openqa.selenium.TimeoutException timeOutException ){
				throw new ElementTimeoutException( baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), timeOutException );
	
			}catch(org.openqa.selenium.remote.UnreachableBrowserException unreachableBrowserException){		
				throw new ElementUnreachableBrowserException( unreachableBrowserException );

			//Egyeb hiba
			}catch( Exception e ){				
				System.out.println("!!!!!!!!!!! Not handled exception while waitionf for appearance of element - MUST implement. " + e.getMessage() + "!!!!!!!!!!!!!");	
			}
		
			//
			//Beazonositja az elemet
			//
			try{
elementProgress.outputCommand( "		webElement = driver.findElement( by );" );					
				webElement = driver.findElement( by );
elementProgress.outputCommand( "		//Done" );					
			
			}catch ( org.openqa.selenium.InvalidSelectorException invalidSelectorException ){
				throw new ElementInvalidSelectorException(baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), invalidSelectorException );
			
			}catch ( org.openqa.selenium.NoSuchElementException noSuchElementException ){
				throw new ElementNotFoundSelectorException( baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), noSuchElementException );

			}catch ( org.openqa.selenium.NoSuchWindowException noSuchWindowExceptioin ){
				throw new ElementUnreachableBrowserException( noSuchWindowExceptioin );
				
			//Egyeb hiba. Peldaul kilottek alola a bongeszot
			}catch( Exception e ){
				System.out.println("!!!!!!!!!!! Not handled exception while Identifying - MUST implement. " + e.getMessage() + "!!!!!!!!!!!!!");					
			}
		
			if( null == webElement ){
				throw new ElementNotFoundSelectorException( baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), new Exception() );
			}
		
			//Varakozik, ha szukseges a muvelet elott
			try {Thread.sleep(waitingTimeBeforeOperation);} catch (InterruptedException e) {}			
		
			try{

				//OPERATION
a				ArrayList<String> operationList = doOperation( driver, baseElement, webElement, elementProgress );

			}catch( StaleElementReferenceException e ){
				
				//TODO valahogy veget kell vetni a vegtelen ciklus lehetosegenek				
				elementProgress.outputCommand("Ujrahivja a doAction() metodust, mert StaleElementReferenceException volt\n");	

				//Ujra hiv
				doAction( driver, baseElement, elementProgress );
				
			//Ha az operation vegrehajtasa soran kivetel generalodott
			}catch(   ElementException e ){
				
				//Ha az osszehasonlitas generalta a hibat es ettol fuggetlenul kell lezaras
				if( needElementEndedAtException && null != elementProgress && e instanceof ElementCompareOperationException ){
					sendelementEndedMessage( elementProgress, baseElement );
				}	
				
				//De vegul megis csak tovabb kuldi a kivetelt
				throw e;
				
			}
			
			
elementProgress.outputCommand("");	

			//Varakozik, ha szukseges a muvelet utan
			try {Thread.sleep(waitingTimeAfterOperation);} catch (InterruptedException e) {}			

		//
		//Script elem eseten nincs szukseg azonositasra
		//
		}else if( baseElement instanceof ScriptBaseElementDataModel ){
			

			//OPERATION
			doOperation( driver, baseElement, null, elementProgress );
						
		}

		if( null != elementProgress ){
			sendelementEndedMessage( elementProgress, baseElement );
		}		
		
	}	
	
	private void sendelementEndedMessage( ElementProgressInterface elementProgress, BaseElementDataModelAdapter baseElement ){
		elementProgress.elementEnded( baseElement.getName(), getOperationNameToString() );
	}

}
