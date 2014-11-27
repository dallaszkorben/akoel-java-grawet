package hu.akoel.grawit.core.treenodedatamodel.param;

import java.util.Vector;

import javax.swing.tree.MutableTreeNode;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.ExecutablePageInterface;
import hu.akoel.grawit.PageProgressInterface;
import hu.akoel.grawit.Settings;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public abstract class ParamPageDataModelAdapter  extends ParamDataModelAdapter implements ExecutablePageInterface{
	
	private static final long serialVersionUID = -5098304990124055586L;
	
	private String name;
	
	public ParamPageDataModelAdapter( String name ){
		super();
		
		this.name = name;

	}

	/**
	 * XML alapjan gyartja le az objektumot
	 * 
	 * @param element
	 * @throws XMLPharseException
	 */
	public ParamPageDataModelAdapter( Element element ) throws XMLPharseException{
		
		//========
		//
		//name
		//
		//========
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), getTag(), ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );		
		this.name = nameString;
	}
	
	@Override
	public void add(ParamDataModelAdapter node) {
		super.add( (MutableTreeNode)node );
	}

	@Override
	public String getName() {		
		return name;
	}
	
	public void setName( String name ){
		this.name = name;
	}

	@Override
	public void doAction( WebDriver driver, PageProgressInterface pageProgress, ElementProgressInterface elementProgress ) throws PageException, CompilationException {
		
		ParamElementDataModel parameterElement;
		
		//Jelzi, hogy elindult az oldal feldolgozasa
		if( null != pageProgress ){
			pageProgress.pageStarted( getName(), getNodeTypeToShow() );
		}	

		int childrenCount = this.getChildCount();
		for( int i = 0; i < childrenCount; i++ ){

			//TODO BaseElement Waiting time ... atadhato lenne parameterkent a doAction szamara
			
			//Parameterezett elem
			parameterElement = (ParamElementDataModel)this.getChildAt( i );
			
			//Ha a parameterezett elem be van kapcsolva
			if( parameterElement.isOn() ){
			
				//Bazis elem
				BaseElementDataModelAdapter baseElement = parameterElement.getBaseElement();
			
				//Ha NORMAL
				if( baseElement instanceof NormalBaseElementDataModel ){
				
					//TODO lehet, hogy ennek a framere varakozo idonek kulonboznie kellene
					//a Bazis elemhez tartozo warakozasi ido
					Integer waitingTime = ((NormalBaseElementDataModel)baseElement).getWaitingTime();
					if( null == waitingTime ){
						waitingTime = Settings.getInstance().getWaitingTime();
					}
					WebDriverWait wait = new WebDriverWait(driver, waitingTime);
			
					// Ha az alapertelmezettol kulonbozo frame van meghatarozva, akkor valt			
					String frameName = ((NormalBaseElementDataModel)parameterElement.getBaseElement()).getFrame();

					if( null != frameName && frameName.trim().length() > 0 ){				
			
elementProgress.outputCommand( "		//Switch to the '" + frameName + "' frame" );
elementProgress.outputCommand( "		driver.switchTo().defaultContent();" );
elementProgress.outputCommand( "		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( \"" + frameName + "\" ) );" );
elementProgress.outputCommand( "		driver.switchTo().defaultContent();" );
elementProgress.outputCommand( "		driver.switchTo().frame( \"" + frameName + "\" );" );
elementProgress.outputCommand( "" );
						
						driver.switchTo().defaultContent();
						wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
						driver.switchTo().defaultContent();
						driver.switchTo().frame( frameName );		
					}				
				}
				
				try{			
					parameterElement.doAction( driver, elementProgress );
			
					//Ha nem futott le rendesen a teszteset
				}catch (ElementException e){
					throw new PageException( this.getName(), e.getElementName(), e.getElementSelector(), e);
			
				}
			
			}
				
		}
		
		//Jelzi, hogy befejezodott az oldal feldolgozasa
		if( null != pageProgress ){
			pageProgress.pageEnded( getName(), getNodeTypeToShow() );
		}
		
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;

		//========
		//
		//Node element
		//
		//========
		Element pageElement = document.createElement(getTag().getName());
		
		//========
		//
		//NAME attributum
		//
		//========
		attr = document.createAttribute(ATTR_NAME);
		attr.setValue( getName() );
		pageElement.setAttributeNode(attr);	
		
		return pageElement;	
	}
	
	@Override
	public Object clone(){
		
		//Leklonozza a ParamPage-et
		ParamPageDataModelAdapter cloned = (ParamPageDataModelAdapter)super.clone();

		//Ha vannak gyerekei (ELEMENT)
		if( null != this.children ){
							
			//Akkor azokat is leklonozza
			cloned.children = new Vector<>();
							
			for( Object o : this.children ){
								
				if( o instanceof ParamDataModelAdapter ){
					
					ParamDataModelAdapter child = (ParamDataModelAdapter) ((ParamDataModelAdapter)o).clone();
					
					//Szulo megadasa, mert hogy nem lett hozzaadva direkt modon a Tree-hez
					child.setParent( cloned );					
					
					cloned.children.add(child);
			
				}
			}
		}
		
		return cloned;
		
	}
	
	@Override
	public Object cloneWithParent() {
		
		ParamPageDataModelAdapter cloned = (ParamPageDataModelAdapter) this.clone();
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
	}
	
}
