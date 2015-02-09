package hu.akoel.grawit.core.treenodedatamodel.param;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.tree.MutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.Player;
import hu.akoel.grawit.WorkingDirectory;
import hu.akoel.grawit.core.operations.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.ParamDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.variable.VariableRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.ElementCompareOperationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.LoopExceededMaxValueException;
import hu.akoel.grawit.exceptions.PageException;
import hu.akoel.grawit.exceptions.StoppedByUserException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;
import hu.akoel.grawit.gui.interfaces.progress.ElementProgressInterface;
import hu.akoel.grawit.gui.interfaces.progress.PageProgressInterface;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ParamLoopCollectorDataModel extends ParamCollectorDataModelAdapter {

	private static final long serialVersionUID = 5361088361756620748L;

	public static final Tag TAG = Tag.PARAMLOOPELEMENTCOLLECTOR;
	
	private static final String ATTR_COMPARE_BASE_ELEMENT_PATH = "compareelementabsolutepath";
	private static final String ATTR_OPERATION = "operation";
	private static final String ATTR_ONE_LOOP_LENGTH = "onelooplength";
	private static final String ATTR_MAX_LOOP_NUMBER = "maxloopnumber";
	private static final String ATTR_ON = "on";
			
	//Adatmodel ---
	private BaseElementDataModelAdapter lastBaseElement = null;
	private BaseElementDataModelAdapter compareBaseElement;	
	private ElementOperationAdapter elementOperation;
	private Integer oneLoopLength = null;
	private Integer maxLoopNumber = null;
	//----
	
	public ParamLoopCollectorDataModel( String name, String details, BaseElementDataModelAdapter compareBaseElement, Integer oneLoopLength, Integer maxLoopNumber, ElementOperationAdapter operation ){
		super(name, details);
		
		this.compareBaseElement = compareBaseElement;
		this.oneLoopLength = oneLoopLength;
		this.maxLoopNumber = maxLoopNumber;
		this.elementOperation = operation;
		
		//Engedelyezi a Node Ki/Be kapcsolasat
		this.setEnabledToTurnOnOff( true );

	}
	
	public ParamLoopCollectorDataModel( Element element, VariableRootDataModel variableRootDataModel, BaseRootDataModel baseRootDataModel ) throws XMLPharseException{
		
		super(element, baseRootDataModel, variableRootDataModel);
		
		BaseDataModelAdapter baseDataModel = baseRootDataModel;
		
		//Engedelyezi a Node Ki/Be kapcsolasat
		this.setEnabledToTurnOnOff( true );
				
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
		
		//========
		//
		// OneLoopLength
		//
		//========		
		if( !element.hasAttribute( ATTR_ONE_LOOP_LENGTH ) ){
			this.oneLoopLength = null;
		}else{
			String oneLoopLengthString = element.getAttribute( ATTR_ONE_LOOP_LENGTH);
			this.oneLoopLength = Integer.parseInt( oneLoopLengthString );
		}	
		
		//========
		//
		// MaxLoopNumber
		//
		//========		
		if( !element.hasAttribute( ATTR_MAX_LOOP_NUMBER ) ){
			this.maxLoopNumber = null;
		}else{
			String maxLoopNumberString = element.getAttribute( ATTR_MAX_LOOP_NUMBER );
			this.maxLoopNumber = Integer.parseInt( maxLoopNumberString );
		}				
		
		//=============
		//
		// BaseElement
		//
		//=============
		if( !element.hasAttribute( ATTR_COMPARE_BASE_ELEMENT_PATH ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_COMPARE_BASE_ELEMENT_PATH );			
		}	
		String baseElementPathString = element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH);				
		baseElementPathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + baseElementPathString;  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		DocumentBuilder builder;
		Document document = null;
		try {  
			builder = factory.newDocumentBuilder();  
			document = builder.parse( new InputSource( new StringReader( baseElementPathString ) ) );  
		} catch (Exception e) {  
	    
			//Nem sikerult az atalakitas
				throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_COMPARE_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH), e );
		} 
	    
		
		//Megkeresem a BASEROOT-ban az utvonalat a BASEELEMENT-hez
		Node actualNode = document;
		while( actualNode.hasChildNodes() ){
		
			actualNode = actualNode.getFirstChild();
			Element actualElement = (Element)actualNode;
			String tagName = actualElement.getTagName();
			String attrName = actualElement.getAttribute(BaseFolderDataModel.ATTR_NAME);	  
	    	
			//Ha BASENODE
			if( tagName.equals( BaseFolderDataModel.TAG.getName() ) ){
				baseDataModel = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModel, Tag.BASEFOLDER, attrName );

				if( null == baseDataModel ){

					throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_COMPARE_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH) );
				}
					
			//Ha BASEPAGE
			}else if( tagName.equals( BaseCollectorDataModel.TAG.getName() ) ){
				baseDataModel = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModel, Tag.BASECOLLECTOR, attrName );
				if( null == baseDataModel ){

					throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_COMPARE_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH) );
				}
					
			//Ha BASELEMENT
			}else if( tagName.equals( NormalBaseElementDataModel.TAG.getName() ) ){
  
				baseDataModel = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModel, Tag.BASEELEMENT, attrName );

				if( null == baseDataModel ){

					throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_COMPARE_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH) );	    		
				}
	    		
				try{
	    	    	
					this.compareBaseElement = (BaseElementDataModelAdapter) baseDataModel;
	    	    	
				}catch(ClassCastException e){

					throw new XMLBaseConversionPharseException( getRootTag(), TAG, ATTR_NAME, getName(), ATTR_COMPARE_BASE_ELEMENT_PATH, element.getAttribute(ATTR_COMPARE_BASE_ELEMENT_PATH), e );
				}
	    						    		
			}
	    	
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
				if( paramElement.getTagName().equals( Tag.PARAMELEMENT.getName() )){					
						
					this.add(new ParamElementDataModel(paramElement, baseRootDataModel, variableRootDataModel ));
						
				}
			}			
		}	
		
		elementOperation = CommonOperations.getElementOperation( element, compareBaseElement, (DataModelAdapter)this, elementOperation, getRootTag(), ATTR_OPERATION, variableRootDataModel );
		
	}
	
	@Override
	public BaseElementDataModelAdapter getLastBaseElement(){
		return lastBaseElement;
	}
	
	@Override
	public void setLastBaseElement( BaseElementDataModelAdapter lastBaseElement ){
		this.lastBaseElement = lastBaseElement;
	}
	
	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag() {
		return getTagStatic();
	}

	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.param.loopcollector");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}
	
	public ElementOperationAdapter getElementOperation() {
		return elementOperation;
	}

	public void setOperation(ElementOperationAdapter elementOperation) {
		this.elementOperation = elementOperation;
	}	

	public void setCompareBaseElement( BaseElementDataModelAdapter compareBaseElement ){
		this.compareBaseElement = compareBaseElement;
	}
	
	public BaseElementDataModelAdapter getCompareBaseElement(){
		return compareBaseElement;
	}
	
	public void setOneLoopLength( Integer oneLoopLength ){
		this.oneLoopLength = oneLoopLength;
	}
	
	public Integer getOneLoopLength(){
		return this.oneLoopLength;
	}
	
	public void setMaxLoopNumber( Integer maxLoopNumber ){
		this.maxLoopNumber = maxLoopNumber;
	}
	
	public Integer getMaxLoopNumber(){
		return this.maxLoopNumber;
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
		
		Element elementElement = super.getXMLElement(document);
		
		//========
		//
		//BaseElementAbsolutePath
		//
		//========
		attr = document.createAttribute( ATTR_COMPARE_BASE_ELEMENT_PATH );
		attr.setValue( compareBaseElement.getPathTag() );
		elementElement.setAttributeNode(attr);

		//========
		//
		//Operation
		//
		//========
		attr = document.createAttribute( ATTR_OPERATION );
		attr.setValue( getElementOperation().getName() );
		elementElement.setAttributeNode(attr);
		
		//========
		//
		// On
		//
		//========
		attr = document.createAttribute( ATTR_ON );
		attr.setValue( this.isOn().toString() );
		elementElement.setAttributeNode(attr);
		
		//========
		//
		// OneLoopLength
		//
		//========
		attr = document.createAttribute( ATTR_ONE_LOOP_LENGTH );
		attr.setValue( this.getOneLoopLength().toString() );
		elementElement.setAttributeNode(attr);
		
		//========
		//
		// MaxLoopNumber
		//
		//========
		attr = document.createAttribute( ATTR_MAX_LOOP_NUMBER );
		attr.setValue( this.getMaxLoopNumber().toString() );
		elementElement.setAttributeNode(attr);		
		
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
			elementElement.setAttributeNode(attr);		
		//}
			
		//Minden Operation a sajat attributumaiert felelos
		getElementOperation().setXMLAttribute( document, elementElement );
				
		return elementElement;	
	}	

	@Override
	public void doAction(WebDriver driver, Player player, PageProgressInterface pageProgres, ElementProgressInterface elementProgres) throws PageException,	CompilationException, StoppedByUserException {
		
		ParamElementDataModel parameterElement;
		
		Integer actualLoop = 0;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		
		Date startDate = Calendar.getInstance().getTime();			
		Date actualDate;
		
		//Annyiszor megy vegig a gyermekeken, amennyi a megengedett ciklusszam (es ha nem igaz a feltetel)
		while( actualLoop++ < maxLoopNumber ){		
			
			//Jelzi, hogy elindult az oldal feldolgozasa
			if( null != pageProgres ){
				pageProgres.pageStarted( getName(), getNodeTypeToShow() );
			}	
			
			try {
				
				//LOOP kiertekelese
				getElementOperation().doAction(driver, getCompareBaseElement(), elementProgres );
				
				//Ha igaz volt az osszehasonlitas, akkor vegig megy gyermekein
				//es vegrehajtja oket
				int childCount = this.getChildCount();
    		
				//testcaseProgress.testcaseStarted( actualTestcase.getName() );
		
				//A LOOP element-jeinek futtatasa
				for( int index = 0; index < childCount; index++ ){
				
					if( player.isStopped() ){
						throw new StoppedByUserException();
					}
					
					//Parameterezett elem
					parameterElement = (ParamElementDataModel)this.getChildAt( index );
					
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
							String frameName = ((NormalBaseElementDataModel)parameterElement.getBaseElement()).getFrame();

							if( null != frameName && frameName.trim().length() > 0 ){				
					
		elementProgres.outputCommand( "		//Switch to the '" + frameName + "' frame" );
		elementProgres.outputCommand( "		driver.switchTo().defaultContent();" );
		elementProgres.outputCommand( "		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( \"" + frameName + "\" ) );" );
		elementProgres.outputCommand( "		driver.switchTo().defaultContent();" );
		elementProgres.outputCommand( "		driver.switchTo().frame( \"" + frameName + "\" );" );
		elementProgres.outputCommand( "" );
								
								driver.switchTo().defaultContent();
								wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
								driver.switchTo().defaultContent();
								driver.switchTo().frame( frameName );		
							}				
						}
						
						try{
							
							//Elem muveletenek vegrehajtasa
							parameterElement.doAction( driver, elementProgres );
					
						//Ha nem futott le rendesen a teszteset
						}catch (ElementException f){
							throw new PageException( this.getName(), f.getElementName(), f.getElementSelector(), f);					
						}					
					}								
				}

			//Nem volt igaz a feltetel, igy a ujabb Loop veszi kezdetet
			}catch( ElementCompareOperationException e	){

				//A feltetel igaz volt, tehat vege a Loopnak
				break;
			
			//Ha egyebb problema volt a vegrehajtas soran, akkor azt tovabb kuldi
			}catch( ElementException g	){
				throw new PageException( this.getName(), g.getElementName(), g.getElementSelector(), g);
			}
			
			//Ha azert lett vege a Loop-nak, mert elerte a maximalis szamot, 
			if( actualLoop >= maxLoopNumber ){
				
				//Akkor egy uj hibat generalok
				throw new LoopExceededMaxValueException( this.getName(), compareBaseElement.getName(), new Exception() );
				
			}
			
			actualDate = Calendar.getInstance().getTime();
			long differenceTime = actualDate.getTime() - startDate.getTime();
			long neededToWait = oneLoopLength * 1000L * actualLoop - differenceTime;
		
			if( neededToWait > 0 ){
				
				try{
					Thread.sleep( neededToWait );
				} catch(InterruptedException ex) {}
			}
			
			//Jelzi, hogy befejezodott az oldal feldolgozasa
			if( null != pageProgres ){
				pageProgres.pageEnded( getName(), getNodeTypeToShow() );
			}			
		}


	}
	
	@Override
	public void add(ParamDataModelAdapter node) {
		super.add( (MutableTreeNode)node );
	}

	@Override
	public Object clone(){
		
		//Leklonozza a NODE-ot
		ParamLoopCollectorDataModel cloned = (ParamLoopCollectorDataModel)super.clone();
		
		cloned.oneLoopLength  = new Integer( oneLoopLength);
		cloned.maxLoopNumber = new Integer( maxLoopNumber );
		
		return cloned;
		
	}

}
