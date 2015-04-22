package hu.akoel.grawit.core.operations;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.enums.list.ListSelectionByListEnum;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.ElementInvalidOperationException;
import hu.akoel.grawit.exceptions.ElementNotFoundComponentException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;

public abstract class SelectOperationAdapter extends ElementOperationAdapter{	
	
	public abstract ListSelectionByListEnum getSelectionBy();
	
	public abstract String getStringToSelection();
	
	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress, String tab) throws ElementException {
		
		if( baseElement instanceof NormalBaseElementDataModel ){

			//I do not know why but it have to be here
			elementProgress.printCommand( tab + "webElement.sendKeys(Keys.TAB);" );
			elementProgress.printCommand( tab + "webElement.sendKeys(Keys.SHIFT, Keys.TAB);" );
			elementProgress.printCommand( tab + "select = new Select(webElement);" );
			if( getSelectionBy().equals( ListSelectionByListEnum.BYVALUE ) ){
				elementProgress.printCommand( tab + "select.selectByValue( \"" + getStringToSelection() + "\" );" );
			}else if( getSelectionBy().equals( ListSelectionByListEnum.BYINDEX ) ){
				elementProgress.printCommand( tab + "index = 0;" );
				elementProgress.printCommand( tab + "try{" );
				elementProgress.printCommand( tab + "index = Integer.valueOf( " + getStringToSelection() + " );" );
				elementProgress.printCommand( tab + "}catch( Exception e){}" );
				elementProgress.printCommand( tab + "select.selectByIndex( index  );" );
			}else if( getSelectionBy().equals( ListSelectionByListEnum.BYVISIBLETEXT ) ){
				elementProgress.printCommand( tab + "select.selectByVisibleText( \"" + getStringToSelection() + "\" );" );
			}			
			
			Select select = null;
			try{

				//I do not know why but it have to be here
				webElement.sendKeys(Keys.TAB);
			    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
				
				select = new Select(webElement);				
				
			}catch (UnexpectedTagNameException e){
				throw new ElementInvalidOperationException( "List Selection", baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), e );			
			}
		
			try{
//TODO most nem  erdekel, hogy ha SelectBaseElementOperation volt akkor a selectByValue-nak nem  String-nek,
//hanem a valtozo nevenek kell lenni. Kesobb ez megoldando
				if( getSelectionBy().equals( ListSelectionByListEnum.BYVALUE ) ){

					select.selectByValue( getStringToSelection() );
						
				}else if( getSelectionBy().equals( ListSelectionByListEnum.BYINDEX ) ){

					Integer index = 0;
				
					try{
						index = Integer.valueOf( getStringToSelection() );
					}catch( Exception e){}

					select.selectByIndex( index );
			
				}else if( getSelectionBy().equals( ListSelectionByListEnum.BYVISIBLETEXT ) ){

					select.selectByVisibleText( getStringToSelection() );
				}
			
			}catch(NoSuchElementException e ){
			
				throw new ElementNotFoundComponentException( getStringToSelection(), getSelectionBy(), baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), e );

			}catch (Exception e ){
				
			}
		}
	}
}
