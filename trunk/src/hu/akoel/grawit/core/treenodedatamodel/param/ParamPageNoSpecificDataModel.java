package hu.akoel.grawit.core.treenodedatamodel.param;

import javax.swing.tree.MutableTreeNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLPharseException;

public class ParamPageNoSpecificDataModel extends ParamPageDataModelAdapter{ //  extends ParamDataModelAdapter implements ExecutablePageInterface{
	
	private static final long serialVersionUID = -5098304990124055586L;
	
	public static final Tag TAG = Tag.PARAMPAGENOSPECIFIC;
	
//	private String name;
//	private BasePageDataModel basePage;	
//	private ParamElementDataModel parameterElement;
	
	public ParamPageNoSpecificDataModel( String name ){
		super( name );
		
//		this.name = name;
//		this.basePage = basePage;

	}

	/**
	 * XML alapjan gyartja le az objektumot
	 * 
	 * @param element
	 * @throws XMLPharseException
	 */
	public ParamPageNoSpecificDataModel( Element element, BaseRootDataModel baseRootDataModel, VariableRootDataModel variableRootDataModel ) throws XMLPharseException{
		
		super( element );
		
		//========
		//
		//name
		//
		//========
/*		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );		
		this.name = nameString;
*/				
		//Vegig a PARAMELEMENT-ekent
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element paramElement = (Element)node;

				if( paramElement.getTagName().equals( Tag.PARAMELEMENT.getName() )){	
					this.add(new ParamElementDataModel(paramElement, baseRootDataModel, variableRootDataModel ));
				}
			}
		}	
	}

	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag() {
		return getTagStatic();
	}
	
	@Override
	public void add(ParamDataModelAdapter node) {
		super.add( (MutableTreeNode)node );
	}
	
	public static String  getNodeTypeToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.param.nospecificpage");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getNodeTypeToShowStatic();
	}
/*
	@Override
	public String getName() {		
		return name;
	}
	
	public void setName( String name ){
		this.name = name;
	}
*/
/*	
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
*/
	
	@Override
	public Element getXMLElement(Document document) {
		
		Element pageElement = super.getXMLElement(document);
		
		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
					
			Object object = this.getChildAt( i );
					
			if( !object.equals(this) && object instanceof ParamDataModelAdapter ){
						
				Element element = ((ParamDataModelAdapter)object).getXMLElement( document );
				pageElement.appendChild( element );		    		
				    	
			}
		}
				
		return pageElement;	
	}

/*	
	@Override
	public Object clone(){
		
		//Leklonozza a ParamPage-et
		ParamPageNoSpecificDataModel cloned = (ParamPageNoSpecificDataModel)super.clone();

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
*/
/*	
	@Override
	public Object cloneWithParent() {
		
		ParamPageNoSpecificDataModel cloned = (ParamPageNoSpecificDataModel) this.clone();
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
	}
*/	
}
