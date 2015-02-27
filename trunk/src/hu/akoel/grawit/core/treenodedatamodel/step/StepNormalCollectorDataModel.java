package hu.akoel.grawit.core.treenodedatamodel.step;

import java.util.Vector;

import javax.swing.tree.MutableTreeNode;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.Player;
import hu.akoel.grawit.WorkingDirectory;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.StepDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.exceptions.StoppedByUserException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;
import hu.akoel.grawit.gui.interfaces.progress.PageProgressInterface;

public class StepNormalCollectorDataModel extends StepCollectorDataModelAdapter {
	
	private static final long serialVersionUID = -5098304990124055586L;
	
	public static final Tag TAG = Tag.STEPNORMALELEMENTCOLLECTOR;
	
	private static final String ATTR_ON = "on";
	
	private BaseElementDataModelAdapter lastBaseElement = null;	
	
	public StepNormalCollectorDataModel( String name, String details ){		
		
		super( name, details );
		
	}

	/**
	 * XML alapjan gyartja le az objektumot
	 * 
	 * @param element
	 * @throws XMLPharseException
	 */
	public StepNormalCollectorDataModel( Element element, BaseRootDataModel baseRootDataModel, ConstantRootDataModel constantRootDataModel ) throws XMLPharseException{
		
		super(element, baseRootDataModel, constantRootDataModel);
				
		//========
		//
		// On
		//
		//========		
		if( !element.hasAttribute( ATTR_ON ) ){
			this.setOn( Boolean.TRUE );
		}else{
			String onString = element.getAttribute( ATTR_ON );
			this.setOn( Boolean.parseBoolean( onString ));
		}				
		
		try{
			lastBaseElement = (BaseElementDataModelAdapter) getBaseDataModelFromPath(element, baseRootDataModel, TAG, getName() );
		}catch (XMLBaseConversionPharseException e){
			lastBaseElement = null;
		}
				
	    //========
		//
		// Gyermekei
		//
	    //========
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element paramElement = (Element)node;
				if( paramElement.getTagName().equals( Tag.STEPELEMENT.getName() )){					
						
					this.add(new StepElementDataModel(paramElement, baseRootDataModel, constantRootDataModel ));
						
				}
			}			
		}	
	}
	
	@Override
	public void add(StepDataModelAdapter node) {
		super.add( (MutableTreeNode)node );
	}

	public static Tag getTagStatic(){
		return TAG;
	}
	
	@Override
	public Tag getTag() {
		return getTagStatic();
	}
	
	@Override
	public BaseElementDataModelAdapter getLastBaseElement(){
		return lastBaseElement;
	}
	
	@Override
	public void setLastBaseElement( BaseElementDataModelAdapter lastBaseElement ){
		this.lastBaseElement = lastBaseElement;
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.step.normalcollector");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}
	
	@Override
	public void doAction( WebDriver driver, Player player, PageProgressInterface pageProgress, ElementProgressInterface elementProgress ) throws PageException, CompilationException, StoppedByUserException {
		
		StepElementDataModel parameterElement;
		
		//Jelzi, hogy elindult az oldal feldolgozasa
		if( null != pageProgress ){
			pageProgress.pageStarted( getName(), getNodeTypeToShow() );
		}	

		int childrenCount = this.getChildCount();
		for( int i = 0; i < childrenCount; i++ ){

			//TODO BaseElement Waiting time ... atadhato lenne parameterkent a doAction szamara
			
			if( player.isStopped() ){
				throw new StoppedByUserException();
			}
			
			//Parameterezett elem
			parameterElement = (StepElementDataModel)this.getChildAt( i );
			
			//Ha a parameterezett elem be van kapcsolva
			if( parameterElement.isOn() ){
			
				//Bazis elem
				BaseElementDataModelAdapter baseElement = parameterElement.getBaseElement();
			
				//Ha NORMAL
				if( baseElement instanceof NormalBaseElementDataModel ){
				
					//TODO lehet, hogy ennek a framere varakozo idonek kulonboznie kellene
					//a Bazis elemhez tartozo warakozasi ido
					Integer waitingTime = ((NormalBaseElementDataModel)baseElement).getWaitingTimeForAppearance();
					if( null == waitingTime ){
						waitingTime = WorkingDirectory.getInstance().getWaitingTime();
					}
					WebDriverWait wait = new WebDriverWait(driver, waitingTime);
			
					// Ha az alapertelmezettol kulonbozo frame van meghatarozva, akkor valt			
/*					String frameName = ((NormalBaseElementDataModel)parameterElement.getBaseElement()).getFrame();

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
*/									
				}
				
				try{			
					parameterElement.doAction( driver, elementProgress );
			
				//Ha nem futott le rendesen a teszteset
				}catch (ElementException e){
					
					//Akkor becsomagolja egy PageException-ba es tovabb kuldi
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

		Element pageElement = super.getXMLElement(document);
				
		//========
		//
		// On
		//
		//========
		attr = document.createAttribute( ATTR_ON );
		attr.setValue( this.isOn().toString() );
		pageElement.setAttributeNode(attr);
		
		//========
		//
		//LASTBASEELEMENT attributum
		//
		//========
		//if( null != lastBaseElement ){
			attr = document.createAttribute( ATTR_LAST_BASE_ELEMENT_PATH );
			if( null != lastBaseElement ){
				attr.setValue( lastBaseElement.getPathTag() );
			}
			pageElement.setAttributeNode(attr);		
		//}
		
		return pageElement;	
	}
	
	@Override
	public Object clone(){
		
		//Leklonozza a ParamPage-et
		StepNormalCollectorDataModel cloned = (StepNormalCollectorDataModel)super.clone();
		
		//Ha vannak gyerekei (NODE vagy PAGE)
		if( null != this.children ){
							
			//Akkor azokat is leklonozza
			cloned.children = new Vector<>();
							
			for( Object o : this.children ){
								
				if( o instanceof StepDataModelAdapter ){
							
					StepDataModelAdapter child = (StepDataModelAdapter) ((StepDataModelAdapter)o).clone();
							
					//Szulo megadasa, mert hogy nem lett hozzaadva direkt modon a Tree-hez
					child.setParent( cloned );					
							
					cloned.children.add(child);
							
				}
			}
		}
		
		return cloned;
		
	}
	

}
