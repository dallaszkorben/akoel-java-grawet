package hu.akoel.grawit.core.operations;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
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
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ElementProgressInterface elementProgress) throws ElementException {

		if( baseElement instanceof NormalBaseElementDataModel ){

			Select select = null;
			try{
				
elementProgress.outputCommand( "		try{" );
elementProgress.outputCommand( "			select = new Select(webElement);" );
elementProgress.outputCommand( "		}catch(UnexpectedTagNameException e){" );
elementProgress.outputCommand( "		}" );				
				
				select = new Select(webElement);
			}catch (UnexpectedTagNameException e){
				throw new ElementInvalidOperationException( "List Selection", baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), e );			
			}
		
			try{

				if( getSelectionBy().equals( ListSelectionByListEnum.BYVALUE ) ){

elementProgress.outputCommand( "		select.selectByValue( \"" + getStringToSelection() + "\" );" );					
					select.selectByValue( getStringToSelection() );
						
				}else if( getSelectionBy().equals( ListSelectionByListEnum.BYINDEX ) ){

					Integer index = 0;
				
					try{
						index = Integer.valueOf( getStringToSelection() );
					}catch( Exception e){}
				
					select.selectByIndex( index );
					
elementProgress.outputCommand( "		index = " + index + ";" );	
elementProgress.outputCommand( "		select.selectByIndex( index );" );					
			
				}else if( getSelectionBy().equals( ListSelectionByListEnum.BYVISIBLETEXT ) ){
					
elementProgress.outputCommand( "		select.selectByVisibleText( \"" + getStringToSelection() + "\" );" );						
					select.selectByVisibleText( getStringToSelection() );
				}
			
			}catch(NoSuchElementException e ){
			
				throw new ElementNotFoundComponentException( getStringToSelection(), getSelectionBy(), baseElement.getName(), ((NormalBaseElementDataModel)baseElement).getSelector(), e );

			}catch (Exception e ){
				
			}
		}
	}

}
