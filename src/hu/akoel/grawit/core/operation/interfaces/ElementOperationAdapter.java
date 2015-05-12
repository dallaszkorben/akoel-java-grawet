package hu.akoel.grawit.core.operation.interfaces;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.ScriptBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.step.StepElementDataModel;
import hu.akoel.grawit.enums.SelectorType;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidSelectorException;
import hu.akoel.grawit.exceptions.ElementNotFoundBySelectorException;
import hu.akoel.grawit.exceptions.ElementNotHandledException;
import hu.akoel.grawit.exceptions.ElementTimeoutException;
import hu.akoel.grawit.exceptions.ElementUnreachableBrowserException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

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

	public abstract void doOperation( WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ProgressIndicatorInterface progressIndicator, String tab, Set<String> definedElementSet, boolean needToPrintSource ) throws ElementException, CompilationException;
	
	/**
	 * Make it visible
	 */
    public abstract Object clone();

     /**
     * 
     * Kozvetlenul a StepElement hivja
     * 
     * @param driver
     * @param stepElement
     * @param progressIndicator
     * @param tab
     * @param isInLoopCollector
     * @throws ElementException
     * @throws CompilationException
     */
    public void doAction( WebDriver driver, StepElementDataModel stepElement, ProgressIndicatorInterface progressIndicator, String tab, Set<String> definedElementSet, boolean needToPrintSource ) throws ElementException, CompilationException{

    	BaseElementDataModelAdapter baseElement = stepElement.getBaseElement();
    	
		//Uzenet az Operation Indulasarol
		if( null != progressIndicator ){
			progressIndicator.elementStarted( stepElement );
		}
		
		doAction( driver, baseElement, progressIndicator, tab, definedElementSet, needToPrintSource );

		if( null != progressIndicator ){
			sendelementEndedMessage( progressIndicator, stepElement );
		}		
    }

    
    /**
     * 
     * Kozvetlenul a Loop hivja mint kiertekelendo muveletet
     * Kozvetve pedig az itteni doAction akit viszont a StepElement hiv
     * 
     * @param driver
     * @param baseElement
     * @param progressIndicator
     * @param tab
     * @param definedElementSet
     * @throws ElementException
     * @throws CompilationException
     */
	public  void doAction( WebDriver driver, BaseElementDataModelAdapter baseElement, ProgressIndicatorInterface progressIndicator, String tab, Set<String> definedElementSet, boolean needToPrintSource ) throws ElementException, CompilationException{
 
		//
		//Szukseges az elem beazonositasa
		//
		if( baseElement instanceof NormalBaseElementDataModel ){			

			if( needToPrintSource ){
				progressIndicator.printSourceLn( tab + "//Element: " + baseElement.getName() + " (" + this.getName() + ") - " + baseElement.getNameAsVariable()  );
			}

			By by = null;
			WebElement webElement = null;
			WebDriverWait wait = null;
			//FluentWait<WebDriver>  wait = null;
						
			//Selector megszerzese
			if( ((NormalBaseElementDataModel)baseElement).getSelectorType().equals(SelectorType.ID)){
				
				if( needToPrintSource ){
					progressIndicator.printSourceLn( tab + "by = By.id( \"" + ((NormalBaseElementDataModel)baseElement).getSelector() + "\" );" );
				}
				
				by = By.id( ((NormalBaseElementDataModel)baseElement).getSelector() );
				
			//CSS
			}else if( ((NormalBaseElementDataModel)baseElement).getSelectorType().equals(SelectorType.CSS)){
				
				if( needToPrintSource ){
					progressIndicator.printSourceLn( tab + "by = By.cssSelector( \"" + ((NormalBaseElementDataModel)baseElement).getSelector() + "\" );" );
				}
				
				by = By.cssSelector( ((NormalBaseElementDataModel)baseElement).getSelector() );			
			}
				
			//WAITING TIME FOR APPEARANCE
			Integer waitingTimeForAppearance = ((NormalBaseElementDataModel)baseElement).getWaitingTimeForAppearance();
			if( null != waitingTimeForAppearance ){
	
				////FLUENT WAIT
				//elementProgress.printCommand( tab + "wait = new FluentWait<WebDriver>(driver).withTimeout( waitingTimeForAppearance, TimeUnit.SECONDS ).pollingEvery( 500, TimeUnit.MILLISECONDS ).ignoring( NoSuchElementException.class ); //EXPLICIT WAIT" );
				//wait = new FluentWait<WebDriver>(driver).withTimeout( waitingTimeForAppearance, TimeUnit.SECONDS ).pollingEvery( 500, TimeUnit.MILLISECONDS ).ignoring( NoSuchElementException.class );
				
				//EXPLICIT WAIT
				if( needToPrintSource ){
					progressIndicator.printSourceLn( tab + "wait = new WebDriverWait(driver, " + waitingTimeForAppearance + "); //EXPLICIT WAIT" );
				}
				wait = new WebDriverWait(driver, waitingTimeForAppearance);
				
			}			
			
			//WAITING TIME BEFORE OPERATION
			Integer waitingTimeBeforeOperation = ((NormalBaseElementDataModel)baseElement).getWaitingTimeBeforeOperation();
			
			//WAITING TIME AFTER OPERATION
			Integer waitingTimeAfterOperation = ((NormalBaseElementDataModel)baseElement).getWaitingTimeAfterOperation();
			
			//Varakozik, ha szukseges a muvelet elott, a megjelenes elott es a beazonositas elott is
			if(null != waitingTimeBeforeOperation ){
				waitingTimeBeforeOperation *= 1000;
				
				if( needToPrintSource ){
					progressIndicator.printSourceLn( tab + "try {Thread.sleep( " + waitingTimeBeforeOperation + " );} catch (InterruptedException e) {} //BEFORE WAIT" );
				}
				
				try {Thread.sleep(waitingTimeBeforeOperation);} catch (InterruptedException e) {}
			}			
			
			//
			//Varakozik az elem megjeleneseig - WAITING TIME FOR APPEARANCE
			//
			if( null != waitingTimeForAppearance ){
				try{
					
					if( needToPrintSource ){
						progressIndicator.printSourceLn( tab + "wait.until(ExpectedConditions.visibilityOfElementLocated( by ));" );
					}					
					wait.until(ExpectedConditions.visibilityOfElementLocated( by ));		
			
				//Ha nem jelenik meg idoben, akkor hibajelzessel megall
				}catch( org.openqa.selenium.TimeoutException timeOutException ){
					throw new ElementTimeoutException( (NormalBaseElementDataModel)baseElement, timeOutException );
	
				}catch(org.openqa.selenium.remote.UnreachableBrowserException unreachableBrowserException){	
					throw new ElementUnreachableBrowserException( (NormalBaseElementDataModel)baseElement, unreachableBrowserException );

				//Egyeb hiba
				}catch( Exception e ){
					throw new ElementNotHandledException( (NormalBaseElementDataModel)baseElement, e );
				}
			}
			
			//
			//Beazonositja az elemet
			//
			try{
				if( needToPrintSource ){
					progressIndicator.printSourceLn( tab + "webElement = driver.findElement( by );" );
				}				
				webElement = driver.findElement( by );
			
			}catch ( org.openqa.selenium.InvalidSelectorException invalidSelectorException ){
				throw new ElementInvalidSelectorException( (NormalBaseElementDataModel)baseElement, invalidSelectorException );
			
			}catch ( org.openqa.selenium.NoSuchElementException noSuchElementException ){
				throw new ElementNotFoundBySelectorException( (NormalBaseElementDataModel)baseElement, noSuchElementException );

			}catch ( org.openqa.selenium.NoSuchWindowException noSuchWindowExceptioin ){
				throw new ElementUnreachableBrowserException( (NormalBaseElementDataModel)baseElement, noSuchWindowExceptioin );
				
			//Egyeb hiba. Peldaul kilottek alola a bongeszot
			}catch( Exception e ){
				throw new ElementNotHandledException( (NormalBaseElementDataModel)baseElement, e );					
			}
		
			if( null == webElement ){
				throw new ElementNotFoundBySelectorException( (NormalBaseElementDataModel)baseElement, new Exception() );
			}
		
/*			//Varakozik, ha szukseges a muvelet elott
			if(null != waitingTimeBeforeOperation ){
				waitingTimeBeforeOperation *= 1000;
				
				if( needToPrintSource ){
					progressIndicator.printSourceLn( tab + "try {Thread.sleep( " + waitingTimeBeforeOperation + " );} catch (InterruptedException e) {}" );
				}
				
				try {Thread.sleep(waitingTimeBeforeOperation);} catch (InterruptedException e) {}
			}
*/		
			try{

				//OPERATION
				doOperation( driver, baseElement, webElement, progressIndicator, tab, definedElementSet, needToPrintSource );

			}catch( StaleElementReferenceException e ){
				
				//TODO valahogy veget kell vetni a vegtelen ciklus lehetosegenek	
				if( needToPrintSource ){
					progressIndicator.printSourceLn("Ujrahivja a doAction() metodust, mert StaleElementReferenceException volt\n");
				}

				//Ujra hiv
				doAction( driver, baseElement, progressIndicator, tab, definedElementSet, needToPrintSource );
				
			//Ha az operation vegrehajtasa soran kivetel generalodott
			}catch(   ElementException e ){
				
				//Ha az osszehasonlitas generalta a hibat es ettol fuggetlenul kell lezaras
/*				if( needElementEndedAtException && null != elementProgress && e instanceof ElementCompareOperationException ){
					sendelementEndedMessage( elementProgress, baseElement );
				}	
*/				
				//Tovabb kuldi a kivetelt
				throw e;				
			}			
		
			//Varakozik, ha szukseges a muvelet utan
			if( null != waitingTimeAfterOperation ){
				waitingTimeAfterOperation *= 1000;
				
				if( needToPrintSource ){
					progressIndicator.printSourceLn( tab + "try {Thread.sleep( " + waitingTimeAfterOperation + " );} catch (InterruptedException e) {} //AFTER WAIT" );
				}
				
				try {Thread.sleep(waitingTimeAfterOperation);} catch (InterruptedException e) {}
			}
			
			if( needToPrintSource ){
				progressIndicator.printSourceLn("");
			}

		//
		//Script elem eseten nincs szukseg azonositasra
		//
		}else if( baseElement instanceof ScriptBaseElementDataModel ){			

			//OPERATION
			//doOperation( driver, baseElement, null, elementProgress, tab );		
			doOperation( driver, baseElement, null, progressIndicator, tab, definedElementSet, needToPrintSource );
		}	
	}	
	
	//private void sendelementEndedMessage( ElementProgressInterface elementProgress, BaseElementDataModelAdapter baseElement ){
	private void sendelementEndedMessage( ProgressIndicatorInterface elementProgress, StepElementDataModel stepElement ){		
		elementProgress.elementEnded( stepElement );
	}

}
