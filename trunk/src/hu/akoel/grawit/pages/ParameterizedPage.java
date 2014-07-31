package hu.akoel.grawit.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.elements.ElementBase;
import hu.akoel.grawit.elements.ParameterizedElement;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.operations.ElementOperation;

/**
 * 
 * @author akoel
 *
 */
public class ParameterizedPage implements ExecutablePageInterface, BasePageChangeListener{
	private PageBase pageBase;
	private String name;
	private ArrayList<ParameterizedElement> elementSet = new ArrayList<>(); 
	private PageProgressInterface pageProgressInterface = null;

	/**
	 * 
	 * @param pageBase
	 */
	public ParameterizedPage( String name, PageBase pageBase ) {
		this.name = name;
		this.pageBase = pageBase;
		if( null != this.pageBase ){
			this.pageBase.addChangeListener(this);
		}
	}

	public String getName(){
		return name;
	}
	
	public PageBase getPurePage(){
		return pageBase;
	}
	
	public void setPageProgressInterface( PageProgressInterface pageProgressInterface ){
		this.pageProgressInterface = pageProgressInterface;
	}
	
	@Override
	public PageProgressInterface getPageProgressInterface() {		
		return this.pageProgressInterface;
	}
	
	/**
	 * 
	 * A new ElementBase-ElementOperation pair added to this ParemeterizedPage
	 * 
	 * @param element
	 * @param operation
	 */
	public ParameterizedElement addElement( ElementBase element, ElementOperation operation ){
		
		//Ha letezik egyaltalan a PageBase listajaban
		if( pageBase.getIndex( element ) >= 0 ){
			
			ParameterizedElement eop = new ParameterizedElement(element, operation);
			
			//Csak ha meg nem szerepelt a listaban
			if( elementSet.indexOf( eop ) == -1){
				
				//Akkor elhelyezi
				elementSet.add( eop );
				
				return eop;
			}
			
		}else{
			System.err.println("hiba - Nincs a PageBase listaban az elem: " + this.getClass().getName() );
			//TODO hibageneralas
		}
		
		return null;
	}
	
	/**
	 * 
	 * Remove the ElementBase from the ParameterizedPage list
	 * 
	 * @param element
	 */
	public void removeElement( ElementBase element ){
		
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
	public void upElement( ElementBase element ){
		
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
	public void downElement( ElementBase element ){

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
	 * Get the position of the given ElementBase in the ParameterizedPage
	 * 
	 * @param element
	 * @return
	 */
	public int getPosition( ElementBase element ){
		return elementSet.indexOf( new ParameterizedElement(element, null));
	}
	
	/**
	 * 
	 * 
	 * 
	 */
	public final void doAction(WebDriver driver) throws PageException{
	
//		//Jelzi, hogy elindult az oldal feldolgozasa
//		if( null != pageProgressInterface ){
//			pageProgressInterface.pageStarted( getName() );
//		}

		//Ha implementalta a Custom Page Interface-t, akkor a forraskodot kell vegrehajtania
		if( this instanceof CustomPageInterface ){
			
			//Megszerzi a forraskodot
			String script = ((CustomPageInterface)this).getSurce();
			
			//TODO megcsinalni a futasidoben a forditast es futtatast			
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
			
			//File javaFile = new File("c:/src/com/juixe/Entity.java");
			// getJavaFileObjects’ param is a vararg
			Iterable fileObjects = fileManager.getJavaFileObjects(script);
			compiler.getTask(null, fileManager, null, null, null, fileObjects).call();
			// Add more compilation tasks
			try {
				fileManager.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		//Kulonben normal ParameterizedPage-kent az ParameterizedElement-eken hajtja vegre sorban az ElementOperation-okat
		}else{

			for( ParameterizedElement eop: elementSet ){
			
				// Ha az alapertelmezettol kulonbozo frame van meghatarozva, akkor valt
				String frame = eop.getElement().getFrame();
				if( null != frame ){
					driver.switchTo().defaultContent();
					driver.switchTo().frame("menuFrame");		
				}
				
				try{			
					eop.doAction( driver );
				}catch (ElementException e){
					throw new PageException( this.getName(), e.getElementName(), e.getElementId(), e);
				}
				
			}
		}
		
//		//Jelzi, hogy befejezodott az oldal feldolgozasa
//		if( null != pageProgressInterface ){
//			pageProgressInterface.pageEnded( getName() );
//		}

	}

	/**
	 * 
	 * If this object is not needed anymore, you have to call this method
	 * in order to remove the changeListener from the PageBase (was set in the constructor)
	 * 
	 */
	public void destroy(){
		this.pageBase.removeChangeListener(this);
	}

	/**
	 * 
	 * The PageBase calls this method when an ElementBase is removed there.
	 * In this case the same ElementBase, connected to an ElementOperation,
	 * in this the ParameterizedPage must be removed as well.
	 * 
	 */
	@Override
	public void removePureElement(ElementBase element) {
		removeElement(element);		
	}

}
