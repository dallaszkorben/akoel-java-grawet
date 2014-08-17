package hu.akoel.grawit.core.pages;

import java.util.ArrayList;
import java.util.Collections;

import hu.akoel.grawit.core.elements.BaseElement;

public class BasePage {
	private String name ;
	private String details;
//	private String url;
//	private String frame;
	private ArrayList<BaseElement> elementList = new ArrayList<>();
	private ArrayList<BasePageChangeListener> changeListenerList = new ArrayList<>();
		
	public BasePage( String name, String details ){
		this.name = name;
		this.details = details;
		//common( name, null );
	}
		
	public String getName(){
		return name;
	}
	
	public String getDetails(){
		return details;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	/**
	 * 
	 * Add a new BaseElement into the end of this BasePage
	 * 
	 * @param element
	 */
	public void addElement( BaseElement element ){
		elementList.add( element );
	}
	
	/**
	 * 
	 * Remove the BaseElement on this BasePage
	 * @param element
	 */
	public void removeElement( BaseElement element ){
		elementList.remove( element );
	}
	
	/**
	 * 
	 * Move the BaseElement up (forward to the beginning) one position 
	 * @param element
	 */
	public void upElement( BaseElement element ){
		
		//Az adott elem sorszama
		int index = elementList.indexOf( element );
	
		//Ha nem az elso a listaban
		if( index > 0 ){
			
			//Akkor felcserelem az ot megelozo elemmel
			Collections.swap( elementList, index, index-1 );
		}
	}
	
	/**
	 * 
	 * Move the BaseElement down (forward to the end) one position
	 * @param element
	 */
	public void downElement( BaseElement element ){

		//Az adott elem sorszama
		int index = elementList.indexOf( element );
	
		//Ha nem az utolso a listaban, de azert a listaban szerepel
		if( index >= 0 && index < elementList.size() - 1 ){
			
			//Akkor felcserelem az ot koveto elemmel
			Collections.swap( elementList, index, index+1 );
		}
		
	}
		
	/**
	 * 
	 * The number of the PureElements on this BasePage
	 * 
	 * @return
	 */
	public int getSize(){
		return elementList.size();
	}
	
	/**
	 * 
	 * Gives back the BaseElement on this BasePage in the given index
	 * 
	 * @param index
	 * @return
	 */
	public BaseElement getElement( int index ){
		return elementList.get(index);
	}
	
	public int getIndex( BaseElement element ){
		return elementList.indexOf( element );
	}
	
	public void addChangeListener( BasePageChangeListener changeListener ){
		this.changeListenerList.add( changeListener );
	}
	
	public void removeChangeListener( BasePageChangeListener changeListener ){
		this.changeListenerList.remove( changeListener );
	}
	
	public String toString(){
		return name;
	}
}
