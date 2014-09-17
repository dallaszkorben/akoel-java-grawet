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
import hu.akoel.grawit.enums.list.ListEnumListSelectionBy;
import hu.akoel.grawit.enums.list.Torlendo_Operation;
import hu.akoel.grawit.enums.list.ListEnumVariableSample;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidOperationException;
import hu.akoel.grawit.exceptions.ElementInvalidSelectorException;
import hu.akoel.grawit.exceptions.ElementNotFoundComponentException;
import hu.akoel.grawit.exceptions.ElementNotFoundSelectorException;
import hu.akoel.grawit.exceptions.ElementTimeoutException;

public class Torlendo_ListVariableOperation implements Torlendo_ElementOperationInterface{
	private VariableElementDataModel parameter;
	private ListEnumListSelectionBy listSelectionType;
	
	public Torlendo_ListVariableOperation( ListEnumListSelectionBy listSelectionType, VariableElementDataModel parameter ){
		this.listSelectionType = listSelectionType;
		this.parameter = parameter;
	}
	
	@Override
	public Torlendo_Operation getOperation() {
		return Torlendo_Operation.LIST_VARIABLE;
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
			throw new ElementInvalidOperationException( getOperation().getTranslatedName(), element.getName(), baseElement.getSelector(), e );			
		}
		
		
		try{

			if(listSelectionType.equals( ListEnumListSelectionBy.BYVALUE ) ){
		
				//Ha valtozokent van deffinialva es muvelet elott kell menteni az erteket
				if( baseElement.getVariableSample().equals( ListEnumVariableSample.PRE ) ){
					
					//Elmenti az elem tartalmat a valtozoba
					element.getBaseElement().setVariableValue( select.getFirstSelectedOption().getAttribute("value") );
				}			
			
				select.selectByValue( parameter.getValue() );
			
				//Ha valtozokent van deffinialva es muvelet utan kell menteni az erteket
				if( baseElement.getVariableSample().equals( ListEnumVariableSample.POST ) ){
					
					//Elmenti az elem tartalmat a valtozoba
					//webElement.sendKeys(Keys.TAB);
					element.getBaseElement().setVariableValue( webElement.getAttribute("value") );
			
				}
			
			}else if( listSelectionType.equals( ListEnumListSelectionBy.BYINDEX ) ){
			
				//TODO ki kell talalni, hogy hogyan szerezheto meg a kivalasztott sorszama

				select.selectByIndex( Integer.valueOf( parameter.getValue() ) );
			
			}else if( listSelectionType.equals( ListEnumListSelectionBy.BYVISIBLETEXT ) ){
			
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

	public VariableElementDataModel getVariableElement() {
		return parameter;
	}
	
	public ListEnumListSelectionBy getListSelectionType() {
		return listSelectionType;
	}
	
}
