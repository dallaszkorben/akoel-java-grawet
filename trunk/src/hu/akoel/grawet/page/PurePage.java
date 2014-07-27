package hu.akoel.grawet.page;

import java.util.ArrayList;
import java.util.Collections;

import hu.akoel.grawet.element.PureElement;

public class PurePage {
	private String name ;	
	private String url;
//	private String frame;
	private ArrayList<PureElement> elementList = new ArrayList<>();
	private ArrayList<PurePageChangeListener> changeListenerList = new ArrayList<>();
		
	public PurePage( String name ){
		common( name, null );
	}
	
	public PurePage( String name, String url ){
		common( name, url );
	}
	
/*	public PurePage( String name, String url, String frame ){
		common( name, url, frame );
	}
*/	
	private void common( String name, String url ){
		this.name = name;
		this.url = url;
	}
	
	public String getName(){
		return name;
	}
	
	public String getURL(){
		return url;		
	}
	
/*	public String getFrame(){
		return frame;
	}
*/	
	/**
	 * 
	 * Add a new PureElement into the end of this PurePage
	 * 
	 * @param element
	 */
	public void addElement( PureElement element ){
		elementList.add( element );
	}
	
	/**
	 * 
	 * Remove the PureElement on this PurePage
	 * @param element
	 */
	public void removeElement( PureElement element ){
		elementList.remove( element );
	}
	
	/**
	 * 
	 * Move the PureElement up (forward to the beginning) one position 
	 * @param element
	 */
	public void upElement( PureElement element ){
		
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
	 * Move the PureElement down (forward to the end) one position
	 * @param element
	 */
	public void downElement( PureElement element ){

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
	 * Remove the indexth PureElement on this PurePage
	 * @param index
	 */
/*	public void deleteElement( int index ){
		elementList.remove(index);
	}
*/	
	/**
	 * 
	 * The number of the PureElements on this PurePage
	 * 
	 * @return
	 */
	public int getSize(){
		return elementList.size();
	}
	
	/**
	 * 
	 * Gives back the PureElement on this PurePage in the given index
	 * 
	 * @param index
	 * @return
	 */
	public PureElement getElement( int index ){
		return elementList.get(index);
	}
	
	public int getIndex( PureElement element ){
		return elementList.indexOf( element );
	}
	
	public void addChangeListener( PurePageChangeListener changeListener ){
		this.changeListenerList.add( changeListener );
	}
	
	public void removeChangeListener( PurePageChangeListener changeListener ){
		this.changeListenerList.remove( changeListener );
	}
}
