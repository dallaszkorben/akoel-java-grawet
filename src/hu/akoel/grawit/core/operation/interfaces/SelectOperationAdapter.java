package hu.akoel.grawit.core.operation.interfaces;

import java.util.Set;

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
import hu.akoel.grawit.exceptions.ElementListSelectionNotFoundComponentException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

public abstract class SelectOperationAdapter extends ElementOperationAdapter{	
	
	public abstract ListSelectionByListEnum getSelectionBy();
	
	public abstract String getStringToSelection();
	
	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ProgressIndicatorInterface elementProgress, String tab, Set<String> definedElementSet, boolean needToPrintSource ) throws ElementException {
		
		if( baseElement instanceof NormalBaseElementDataModel ){

			if( needToPrintSource ){

				//I do not know why but it have to be here
				elementProgress.printSourceLn( tab + "webElement.sendKeys(Keys.TAB);" );
				elementProgress.printSourceLn( tab + "webElement.sendKeys(Keys.SHIFT, Keys.TAB);" );
				elementProgress.printSourceLn( tab + "select = new Select(webElement);" );
				if( getSelectionBy().equals( ListSelectionByListEnum.BYVALUE ) ){
					elementProgress.printSourceLn( tab + "select.selectByValue( \"" + getStringToSelection() + "\" );" );
				}else if( getSelectionBy().equals( ListSelectionByListEnum.BYINDEX ) ){
					elementProgress.printSourceLn( tab + "index = 0;" );
					elementProgress.printSourceLn( tab + "try{" );
					elementProgress.printSourceLn( tab + "index = Integer.valueOf( " + getStringToSelection() + " );" );
					elementProgress.printSourceLn( tab + "}catch( Exception e){}" );
					elementProgress.printSourceLn( tab + "select.selectByIndex( index  );" );
				}else if( getSelectionBy().equals( ListSelectionByListEnum.BYVISIBLETEXT ) ){
					elementProgress.printSourceLn( tab + "select.selectByVisibleText( \"" + getStringToSelection() + "\" );" );
				}
			}
			
			Select select = null;
			try{

				//I do not know why but it have to be here
				webElement.sendKeys(Keys.TAB);
			    webElement.sendKeys(Keys.SHIFT, Keys.TAB);
				
				select = new Select(webElement);				
				
			}catch (UnexpectedTagNameException e){
				throw new ElementInvalidOperationException((NormalBaseElementDataModel)baseElement, this, e );
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
			
				throw new ElementListSelectionNotFoundComponentException( (NormalBaseElementDataModel)baseElement, getStringToSelection(), this, e ); 

			}catch (Exception e ){
				
			}
		}
	}
}
