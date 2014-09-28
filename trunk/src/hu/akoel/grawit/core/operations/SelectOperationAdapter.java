package hu.akoel.grawit.core.operations;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;
import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.enums.SelectorType;
import hu.akoel.grawit.enums.list.ListSelectionByListEnum;
import hu.akoel.grawit.enums.list.VariableSampleListEnum;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidOperationException;
import hu.akoel.grawit.exceptions.ElementInvalidSelectorException;
import hu.akoel.grawit.exceptions.ElementNotFoundComponentException;
import hu.akoel.grawit.exceptions.ElementNotFoundSelectorException;
import hu.akoel.grawit.exceptions.ElementTimeoutException;

public abstract class SelectOperationAdapter implements ElementOperationInterface{
	
	
	public abstract ListSelectionByListEnum getSelectionBy();
	
	public abstract String getStringToSelection();
	
	/**
	 * 
	 * Executes the action on the WebElement (Field)
	 * 
	 */
	@Override
	public void doAction( WebDriver driver, ParamElementDataModel element, ElementProgressInterface elementProgress ) throws ElementException{
	
		BaseElementDataModel baseElement = element.getBaseElement();
		By by = null;
		WebElement webElement = null;
		
		if( null != elementProgress ){
			elementProgress.elementStarted( element.getName(), baseElement.getVariableValue() );
		}
		
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
		
		}catch( org.openqa.selenium.TimeoutException timeOutException ){
			throw new ElementTimeoutException( element.getName(), baseElement.getSelector(), timeOutException );
		}
		
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

		Select select = null;
		try{
			select = new Select(webElement);
		}catch (UnexpectedTagNameException e){
			throw new ElementInvalidOperationException( "List Selection", element.getName(), baseElement.getSelector(), e );			
		}
				
		try{

			if( getSelectionBy().equals( ListSelectionByListEnum.BYVALUE ) ){
		
				//Ha valtozokent van deffinialva es muvelet elott kell menteni az erteket
				if( baseElement.getVariableSample().equals( VariableSampleListEnum.PRE ) ){
					
					//Elmenti az elem tartalmat a valtozoba
					baseElement.setVariableValue( select.getFirstSelectedOption().getAttribute("value") );
				}			
			
				//Muvelet
				select.selectByValue( getStringToSelection() );
			
				//Ha valtozokent van deffinialva es muvelet utan kell menteni az erteket
				if( baseElement.getVariableSample().equals( VariableSampleListEnum.POST ) ){
					
					//Elmenti az elem tartalmat a valtozoba
					//webElement.sendKeys(Keys.TAB);
					baseElement.setVariableValue( webElement.getAttribute("value") );
			
				}
			
			}else if( getSelectionBy().equals( ListSelectionByListEnum.BYINDEX ) ){
			
				//TODO ki kell talalni, hogy hogyan szerezheto meg a kivalasztott sorszama

				select.selectByIndex( Integer.valueOf( getStringToSelection() ) );
			
			}else if( getSelectionBy().equals( ListSelectionByListEnum.BYVISIBLETEXT ) ){
			
				//TODO ki kell talalni, hogy hogyan szerezheto meg a kivalasztott szovege
			
				select.selectByVisibleText( getStringToSelection() );
			}
			
		}catch(NoSuchElementException e ){
			
			throw new ElementNotFoundComponentException( getStringToSelection(), getSelectionBy(), element.getName(), baseElement.getSelector(), e );

		}catch (Exception e ){
			
		}		
		
		if( null != elementProgress ){
			elementProgress.elementEnded( element.getName(), baseElement.getVariableValue() );
		}
	}

}
