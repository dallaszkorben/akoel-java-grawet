package hu.akoel.grawit.core.operations;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.parameter.ElementParameter;
import hu.akoel.grawit.core.treenodedatamodel.param.ParamElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableElementDataModel;
import hu.akoel.grawit.exceptions.ElementException;

public interface ElementOperationInterface {

	public static enum Operation{		
		FIELD( 0, CommonOperations.getTranslation( "editor.label.operation.field") ),
		LINK( 1, CommonOperations.getTranslation( "editor.label.operation.link") ),
		BUTTON( 2, CommonOperations.getTranslation( "editor.label.operation.button") ),
		RADIOBUTTON( 3, CommonOperations.getTranslation( "editor.label.operation.radiobutton") ),
		CHECKBOX( 4, CommonOperations.getTranslation( "editor.label.operation.checkbox") );
		
		private String translatedName;
		private int index;
		
		private Operation( int index, String translatedName ){
		
			this.index = index;
			this.translatedName = translatedName;
		}
		
		public String getTranslatedName(){
			return translatedName;
		}	
		
		public int getIndex(){
			return index;
		}
		
		public static Operation getOperationByIndex( int index ){
			switch (index){
			case 0:	return FIELD;
			case 1: return LINK;
			case 2: return BUTTON;
			case 3: return RADIOBUTTON;
			case 4: return CHECKBOX;
			default: return LINK;
			}
		}
	}
	
	public void doAction( WebDriver driver, ParamElementDataModel element ) throws ElementException;
	
	/**
	 * Visszaadja a muveletet
	 * @return
	 */
	public Operation getOperation();
	
	public VariableElementDataModel getVariableElement();
}
