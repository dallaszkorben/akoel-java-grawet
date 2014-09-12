package hu.akoel.grawit.core.operations;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.enums.SelectorType;
import hu.akoel.grawit.enums.ListSelectionType;
import hu.akoel.grawit.enums.Operation;
import hu.akoel.grawit.enums.VariableSample;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidOperationException;
import hu.akoel.grawit.exceptions.ElementInvalidSelectorException;
import hu.akoel.grawit.exceptions.ElementNotFoundComponentException;
import hu.akoel.grawit.exceptions.ElementNotFoundSelectorException;
import hu.akoel.grawit.exceptions.ElementTimeoutException;

public class ListOperation implements ElementOperationInterface{
	private VariableElementDataModel parameter;
	private ListSelectionType listSelectionType;
	
	public ListOperation( ListSelectionType listSelectionType, VariableElementDataModel parameter ){
		this.listSelectionType = listSelectionType;
		this.parameter = parameter;
	}
	
	@Override
	public Operation getOperation() {
		return Operation.LIST;
	}
	
	/**
	 * 
	 * Executes the action on the WebElement (List)
	 * 
	 */
	@Override
	public void doAction( WebDriver driver, ParamElementDataModel element, ElementProgressInterface elementProgress ) throws ElementException{
		
		if( null != elementProgress ){
			elementProgress.elementStarted( element.getName() );
		}
		
		BaseElementDataModel baseElement = element.getBaseElement();
		By by = null;
		WebElement webElement = null;
		
		//Searching for the element - waiting for it
		WebDriverWait wait = new WebDriverWait(driver, 10);
						
		//Selector meszerzese
		if( baseElement.getSelectorType().equals(SelectorType.ID)){
			by = By.id( baseElement.getSelector() );
		//CSS
		}else if( baseElement.getSelectorType().equals(SelectorType.CSS)){
			by = By.cssSelector( baseElement.getSelector() );
		}
						
		//Varakozik az elem megjeleneseig, de max 10 mp-ig
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated( by ));
			//wait.until(ExpectedConditions.elementToBeClickable( by ) );
			//wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy( by ) );
			/*WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
		        public WebElement apply(WebDriver driver) {
		            return driver.findElement(by);
		        }
		    });*/
			
		
		}catch( org.openqa.selenium.TimeoutException timeOutException ){
			throw new ElementTimeoutException( element.getName(), baseElement.getSelector(), timeOutException );
		}
		
		//Megkeresi az elemet es letrehozza a WebElement-et belole
		try{
			webElement = driver.findElement( by );
		}catch ( org.openqa.selenium.InvalidSelectorException invalidSelectorException ){
			throw new ElementInvalidSelectorException(element.getName(), baseElement.getSelector(), invalidSelectorException );
		}catch ( org.openqa.selenium.NoSuchElementException noSuchElementException ){
			throw new ElementNotFoundSelectorException( element.getName(), baseElement.getSelector(), noSuchElementException );
		}		
		if( null == webElement ){
			throw new ElementNotFoundSelectorException( element.getName(), baseElement.getSelector(), new Exception() );
		}
/*		
		while( !webElement.isDisplayed() ){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}	
*/		
		//throw new ElementException( elementBase.getName(), elementBase.getBy().toString(), e );
		
		
		Select select = null;
		try{
			select = new Select(webElement);
		}catch (UnexpectedTagNameException e){
			throw new ElementInvalidOperationException( getOperation(), element.getName(), baseElement.getSelector(), e );			
		}
		
		
		try{

			if(listSelectionType.equals( ListSelectionType.BYVALUE ) ){
		
				//Ha valtozokent van deffinialva es muvelet elott kell menteni az erteket
				if( baseElement.getVariableSample().equals( VariableSample.PRE ) ){
					
					//Elmenti az elem tartalmat a valtozoba
					element.setVariableValue( select.getFirstSelectedOption().getAttribute("value") );
				}			
			
				select.selectByValue( parameter.getValue() );
			
				//Ha valtozokent van deffinialva es muvelet utan kell menteni az erteket
				if( baseElement.getVariableSample().equals( VariableSample.POST ) ){
					
					//Elmenti az elem tartalmat a valtozoba
					//webElement.sendKeys(Keys.TAB);
					element.setVariableValue( webElement.getAttribute("value") );
			
				}
			
			}else if( listSelectionType.equals( ListSelectionType.BYINDEX ) ){
			
				//TODO ki kell talalni, hogy hogyan szerezheto meg a kivalasztott sorszama

				select.selectByIndex( Integer.valueOf( parameter.getValue() ) );
			
			}else if( listSelectionType.equals( ListSelectionType.BYVISIBLETEXT ) ){
			
				//TODO ki kell talalni, hogy hogyan szerezheto meg a kivalasztott szovege
			
				select.selectByVisibleText( parameter.getValue() );
			}
			
		}catch(NoSuchElementException e ){
			
			throw new ElementNotFoundComponentException( parameter.getValue(), listSelectionType, element.getName(), baseElement.getSelector(), e );

		}catch (Exception e ){
			
		}
		
		if( null != elementProgress ){
			elementProgress.elementEnded( element.getName() );
		}
	}

	@Override
	public VariableElementDataModel getVariableElement() {
		return parameter;
	}
	
	@Override
	public ListSelectionType getListSelectionType() {
		return listSelectionType;
	}
	
}
