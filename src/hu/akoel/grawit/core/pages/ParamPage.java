package hu.akoel.grawit.core.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.openqa.selenium.WebDriver;

import hu.akoel.grawit.core.elements.BaseElement;
import hu.akoel.grawit.core.elements.ParamElement;
import hu.akoel.grawit.core.operations.ElementOperationInterface;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.exceptions.ParameterError;

/**
 * 
 * @author akoel
 *
 */
public class ParamPage implements ExecutablePageInterface, BasePageChangeListener{
	private BasePage basePage;
	private String name;
	private ArrayList<ParamElement> elementSet = new ArrayList<>(); 
	private PageProgressInterface pageProgressInterface = null;

	/**
	 * 
	 * @param basePage
	 */
	public ParamPage( String name, BasePage basePage ) {
		this.name = name;
		this.basePage = basePage;
		if( null != this.basePage ){
			this.basePage.addChangeListener(this);
		}
	}

	public String getName(){
		return name;
	}
	
	public void setName( String name ){
		this.name = name;
	}
	
	public BasePage getBasePage(){
		return basePage;
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
	 * A new BaseElement-ElementOperationInterface pair added to this ParemeterizedPage
	 * 
	 * @param element
	 * @param operation
	 */
	public ParamElement addElement( String name, BaseElement element, ElementOperationInterface operation ){
		
		//Ha letezik egyaltalan a BasePage listajaban
		if( basePage.getIndex( element ) >= 0 ){
			
			ParamElement eop = new ParamElement(name, element, operation);
			
			//Csak ha meg nem szerepelt a listaban
			if( elementSet.indexOf( eop ) == -1){
				
				//Akkor elhelyezi
				elementSet.add( eop );
				
				return eop;
			}
			
		}else{
			new ParameterError("Nincs a " + BasePage.class.getSimpleName() + " listaban a(z) " + element.getClass().getSimpleName() + " elem.");
		}
		
		return null;
	}
	
	/**
	 * 
	 * Remove the BaseElement from the ParamPage list
	 * 
	 * @param element
	 */
	public void removeElement( BaseElement element ){
		
		//Az adott elem sorszama
		int index = elementSet.indexOf( new ParamElement( null, element, null ) );
		
		elementSet.remove( index );
	}
	
	/**
	 * 
	 * Move the ParamElement up (forward to the beginning) one position 
	 * 
	 * @param element
	 */
	public void upElement( BaseElement element ){
		
		//Az adott elem sorszama
		int index = elementSet.indexOf( new ParamElement( null, element, null ) );
	
		//Ha nem az elso a listaban
		if( index > 0 ){
			
			//Akkor felcserelem az ot megelozo elemmel
			Collections.swap( elementSet, index, index-1 );
		}
	}
	
	/**
	 * 
	 * Move the ParamElement down (forward to the end) one position
	 * 
	 * @param element 
	 */
	public void downElement( BaseElement element ){

		//Az adott elem sorszama
		int index = elementSet.indexOf( new ParamElement( null, element, null ) );
	
		//Ha nem az utolso a listaban, de azert a listaban szerepel
		if( index >= 0 && index < elementSet.size() - 1 ){
			
			//Akkor felcserelem az ot koveto elemmel
			Collections.swap( elementSet, index, index+1 );
		}
		
	}
	
	/**
	 * 
	 * Get the position of the given BaseElement in the ParamPage
	 * 
	 * @param element
	 * @return
	 */
	public int getPosition( BaseElement element ){
		return elementSet.indexOf( new ParamElement(null, element, null));
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
			
			
			
		//Kulonben normal ParamPage-kent az ParamElement-eken hajtja vegre sorban az ElementOperationInterface-okat
		}else{

			for( ParamElement eop: elementSet ){
			
				// Ha az alapertelmezettol kulonbozo frame van meghatarozva, akkor valt
				String frame = eop.getBaseElement().getFrame();
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
	 * in order to remove the changeListener from the BasePage (was set in the constructor)
	 * 
	 */
	public void destroy(){
		this.basePage.removeChangeListener(this);
	}

	/**
	 * 
	 * The BasePage calls this method when an BaseElement is removed there.
	 * In this case the same BaseElement, connected to an ElementOperationInterface,
	 * in this the ParamPage must be removed as well.
	 * 
	 */
	@Override
	public void removeBaseElement(BaseElement element) {
		removeElement(element);		
	}

}
