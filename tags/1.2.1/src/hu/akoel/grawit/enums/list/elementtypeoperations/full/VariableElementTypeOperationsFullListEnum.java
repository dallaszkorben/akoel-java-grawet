package hu.akoel.grawit.enums.list.elementtypeoperations.full;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum VariableElementTypeOperationsFullListEnum implements ElementTypeOperationsListEnumInterface{
	SET_CONSTANT( 0, CommonOperations.getTranslation( "editor.label.step.elementtype.variable.setconstant") ),
	SET_STORED( 1, CommonOperations.getTranslation( "editor.label.step.elementtype.variable.setstored") ),
	SET_STRING( 2, CommonOperations.getTranslation( "editor.label.step.elementtype.variable.setstring") ),
	COMPAREVALUE_TO_CONSTANT( 3, CommonOperations.getTranslation( "editor.label.step.elementtype.variable.comparetoconstant") ),
	COMPAREVALUE_TO_STRING( 4, CommonOperations.getTranslation( "editor.label.step.elementtype.variable.comparetostring") ),
	OUTPUT( 5, CommonOperations.getTranslation( "editor.label.step.elementtype.variable.output") ),
	;
	
	private String translatedName;
	private int index;
	
	private VariableElementTypeOperationsFullListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return VariableElementTypeOperationsFullListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static VariableElementTypeOperationsFullListEnum getElementFieldOperationByIndex( int index ){
		switch (index){
		
		case 0:	return SET_CONSTANT;
		case 1:	return SET_STORED;
		case 2:	return SET_STRING;
		case 3:	return COMPAREVALUE_TO_CONSTANT;
		case 4:	return COMPAREVALUE_TO_STRING;
		case 5:	return OUTPUT;

		default: return OUTPUT;
		}
	}
	
}
