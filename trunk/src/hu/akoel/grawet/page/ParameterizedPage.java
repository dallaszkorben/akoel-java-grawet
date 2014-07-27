package hu.akoel.grawet.page;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import hu.akoel.grawet.element.ParameterizedElement;
import hu.akoel.grawet.element.PureElement;
import hu.akoel.grawet.operation.ElementOperation;

/**
 * 
 * @author akoel
 *
 */
public class ParameterizedPage implements PurePageChangeListener{
	private PurePage purePage;
	private String name;
	private ArrayList<ParameterizedElement> elementSet = new ArrayList<>(); 

	/**
	 * 
	 * @param purePage
	 */
	public ParameterizedPage( String name, PurePage purePage ) {
		this.name = name;
		this.purePage = purePage;
		this.purePage.addChangeListener(this);
	}

	public String getName(){
		return name;
	}
	
	/**
	 * 
	 * A new PureElement-ElementOperation pair added to this ParemeterizedPage
	 * 
	 * @param element
	 * @param operation
	 */
	public ParameterizedElement addElement( PureElement element, ElementOperation operation ){
		
		//Ha letezik egyaltalan a PurePage listajaban
		if( purePage.getIndex( element ) >= 0 ){
			
			ParameterizedElement eop = new ParameterizedElement(element, operation);
			
			//Csak ha meg nem szerepelt a listaban
			if( elementSet.indexOf( eop ) == -1){
				
				//Akkor elhelyezi
				elementSet.add( eop );
				
				return eop;
			}
			
		}else{
			System.err.println("hiba - Nincs a PurePage listaban az elem: " + this.getClass().getName() );
			//TODO hibageneralas
		}
		
		return null;
	}
	
	/**
	 * 
	 * Remove the PureElement from the ParameterizedPage list
	 * 
	 * @param element
	 */
	public void removeElement( PureElement element ){
		
		//Az adott elem sorszama
		int index = elementSet.indexOf( new ParameterizedElement( element, null ) );
		
		elementSet.remove( index );
	}
	
	/**
	 * 
	 * Move the ParameterizedElement up (forward to the beginning) one position 
	 * 
	 * @param element
	 */
	public void upElement( PureElement element ){
		
		//Az adott elem sorszama
		int index = elementSet.indexOf( new ParameterizedElement( element, null ) );
	
		//Ha nem az elso a listaban
		if( index > 0 ){
			
			//Akkor felcserelem az ot megelozo elemmel
			Collections.swap( elementSet, index, index-1 );
		}
	}
	
	/**
	 * 
	 * Move the ParameterizedElement down (forward to the end) one position
	 * 
	 * @param element 
	 */
	public void downElement( PureElement element ){

		//Az adott elem sorszama
		int index = elementSet.indexOf( new ParameterizedElement( element, null ) );
	
		//Ha nem az utolso a listaban, de azert a listaban szerepel
		if( index >= 0 && index < elementSet.size() - 1 ){
			
			//Akkor felcserelem az ot koveto elemmel
			Collections.swap( elementSet, index, index+1 );
		}
		
	}
	
	/**
	 * 
	 * Get the position of the given PureElement in the ParameterizedPage
	 * 
	 * @param element
	 * @return
	 */
	public int getPosition( PureElement element ){
		return elementSet.indexOf( new ParameterizedElement(element, null));
	}
	
	/**
	 * 
	 * 
	 * 
	 */
	public void execute(){
				
System.out.println(getName() + "is started...");

		for( ParameterizedElement eop: elementSet ){
			
			//Ha az alapertelmezettol kulonbozo frame van meghatarozva, akkor valt
			String frame = eop.getElement().getFrame();
			if( null != frame ){
				eop.getElement().getDriver().switchTo().defaultContent();
				eop.getElement().getDriver().switchTo().frame("menuFrame");		
			}
			
			eop.doAction();
		}
		
System.err.println( getName() + " is DONE");		
		
	}

	/**
	 * 
	 * If this object is not needed anymore, you have to call this method
	 * in order to remove the changeListener from the PurePage (was set in the constructor)
	 * 
	 */
	public void destroy(){
		this.purePage.removeChangeListener(this);
	}

	/**
	 * 
	 * The PurePage calls this method when an PureElement is removed there.
	 * In this case the same PureElement, connected to an ElementOperation,
	 * in this the ParameterizedPage must be removed as well.
	 * 
	 */
	@Override
	public void removePureElement(PureElement element) {
		removeElement(element);		
	}

	
	
	
}
