package hu.akoel.grawit.core.treenodedatamodel.step;

import java.io.StringReader;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.swing.tree.MutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.Player;
import hu.akoel.grawit.core.operation.interfaces.CompareOperationInterface;
import hu.akoel.grawit.core.operation.interfaces.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.NormalBaseElementDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.CompilationException;
import hu.akoel.grawit.exceptions.ElementCompareOperationException;
import hu.akoel.grawit.exceptions.ElementException;
import hu.akoel.grawit.exceptions.LoopExceededMaxValueException;
import hu.akoel.grawit.exceptions.StepException;
import hu.akoel.grawit.exceptions.StoppedByUserException;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

import org.openqa.selenium.WebDriver;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class StepLoopCollectorDataModel extends StepCollectorDataModelAdapter {

	private static final long serialVersionUID = 5361088361756620748L;

	public static final Tag TAG = Tag.STEPLOOPELEMENTCOLLECTOR;
	
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
	
	public StepLoopCollectorDataModel( String name, String details, BaseElementDataModelAdapter compareBaseElement, Integer oneLoopLength, Integer maxLoopNumber, ElementOperationAdapter operation ){
		super(name, details);
	
		this.compareBaseElement = compareBaseElement;
		this.oneLoopLength = oneLoopLength;
		this.maxLoopNumber = maxLoopNumber;
		this.elementOperation = operation;
		
		if( this.elementOperation instanceof CompareOperationInterface ){
			((CompareOperationInterface) this.elementOperation).setIsInLoop( true );
		}
		
		//Engedelyezi a Node Ki/Be kapcsolasat
		this.setEnabledToTurnOnOff( true );
	}
	
	public StepLoopCollectorDataModel( Element element, ConstantRootDataModel constantRootDataModel, BaseRootDataModel baseRootDataModel ) throws XMLPharseException{
		
		super(element, baseRootDataModel, constantRootDataModel);
		
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
				if( paramElement.getTagName().equals( Tag.STEPELEMENT.getName() )){					
						
					this.add(new StepElementDataModel(paramElement, baseRootDataModel, constantRootDataModel ));
						
				}
			}			
		}	
		
		elementOperation = CommonOperations.getElementOperation( element, compareBaseElement, (DataModelAdapter)this, elementOperation, getRootTag(), ATTR_OPERATION, constantRootDataModel );
		
		//Jelzem, hogy az adott Operation egy Loop-ban szerepel es igy a forraskod bizonyos elemei kulonbozoek lehetnek
		if( this.elementOperation instanceof CompareOperationInterface ){
			((CompareOperationInterface) this.elementOperation).setIsInLoop( true );
		}
		
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
		return CommonOperations.getTranslation( "tree.nodetype.step.loopcollector");
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
	public void doAction(WebDriver driver, Player player, ProgressIndicatorInterface progressIndicator, String tab, Set<String> definedElementSet ) throws StepException, CompilationException, StoppedByUserException {
		
		StepElementDataModel parameterElement;
		
		Integer actualLoop = 0;
		//SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		
		Date startDate = Calendar.getInstance().getTime();			
		Date actualDate;
		
		progressIndicator.printSourceLn( tab + "//Cycle starts" );
		progressIndicator.printSourceLn( tab + "startDate = Calendar.getInstance().getTime();" );	
		progressIndicator.printSourceLn( tab + "actualLoop = 0;" );
		progressIndicator.printSourceLn( tab + "oneLoopLength = " + getOneLoopLength() + ";" );
		progressIndicator.printSourceLn( tab + "maxLoopNumber = " + maxLoopNumber + ";" );
		progressIndicator.printSourceLn( tab + "while( actualLoop++ < maxLoopNumber ){");
		progressIndicator.printSourceLn( "" );	
		
		//Annyiszor megy vegig a gyermekeken, amennyi a megengedett ciklusszam (es ha nem igaz a feltetel)
		while( actualLoop++ < maxLoopNumber ){		
			
			try {
				if( actualLoop == 1 ){				
					progressIndicator.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + "//");				
					progressIndicator.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + "//Evaluation");
					progressIndicator.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + "//");
				}
				
				//LOOP kiertekelese - true parameter jelzi, hogy hiaba lesz Comparation Exception attol meg le kell zarni az uzenetet
getElementOperation().doAction(driver, getCompareBaseElement(), progressIndicator, tab + CommonOperations.TAB_BY_SPACE, definedElementSet, (actualLoop == 1? true: false) );

				if( actualLoop == 1 ){
					progressIndicator.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + "//");				
					progressIndicator.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + "//Execution");
					progressIndicator.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + "//");
				}

				//Ha igaz volt az osszehasonlitas, akkor vegig megy gyermekein
				//es vegrehajtja oket
				int childCount = this.getChildCount();
		
				//A LOOP element-jeinek futtatasa
				for( int index = 0; index < childCount; index++ ){
				
					//A felhasznalo Player gombokon keresztuli kereseire reagal
					checkAndExecuteRequestsFromUser( player, progressIndicator, tab );
					
					//Parameterezett elem
					parameterElement = (StepElementDataModel)this.getChildAt( index );
					
					//Ha a parameterezett elem be van kapcsolva
					if( parameterElement.isOn() ){
					
						try{
							
							//Elem muveletenek vegrehajtasa
parameterElement.doAction( driver, progressIndicator, tab + CommonOperations.TAB_BY_SPACE, definedElementSet, ( actualLoop == 1? true: false ) );
					
						//Ha nem futott le rendesen a teszteset
						}catch (ElementException f){
							
							//A While loopot le kell azert zarni
							printSourceCloseAtStop(progressIndicator, tab);
							
							throw new StepException( this, parameterElement, f);					
						}					
					}								
				}

			//NEM IGAZ A FELTETEL, tehat megszakitja a LOOP-ot, hogy folytathassa a kovetkezo STEP-et
			}catch( ElementCompareOperationException e	){

				//Vege a Loopnak
				break;
			
			//Ha egyebb problema volt a vegrehajtas soran, akkor azt tovabb kuldi
			}catch( ElementException g	){
								
				//A While loopot le kell azert zarni
				printSourceCloseAtStop(progressIndicator, tab);
				
				throw new StepException( this, null, g );
			}
			
			if( actualLoop == 1 ){
				progressIndicator.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + "if( actualLoop >= maxLoopNumber ){" );
				progressIndicator.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + CommonOperations.TAB_BY_SPACE + "fail( \"Stopped because the loop exceeded the max value but the LOOP condition is still TRUE for the '" + compareBaseElement.getName() + "' element.\" );" );			
				progressIndicator.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + "}" );
				progressIndicator.printSourceLn( "" );
			}
			
			//Ha azert lett vege a Loop-nak, mert elerte a maximalis szamot, vagyis a feltetel meg mindig igaz (ez nem jo) 
			if( actualLoop >= maxLoopNumber ){
				
				//A While loopot le kell azert zarni
				printSourceCloseAtStop(progressIndicator, tab);
				
				//Akkor egy uj hibat generalok
				throw new LoopExceededMaxValueException( this, (NormalBaseElementDataModel)compareBaseElement, new Exception() );
				
			}
			
			//Waiting before the next cycle
			actualDate = Calendar.getInstance().getTime();
			long differenceTime = actualDate.getTime() - startDate.getTime();
			long neededToWait = oneLoopLength * 1000L * actualLoop - differenceTime;
		
			if( neededToWait > 0 ){
				
				if( actualLoop == 1 ){
					progressIndicator.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + "//Waiting before the next cycle" );
					progressIndicator.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + "actualDate = Calendar.getInstance().getTime();" );
					progressIndicator.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + "long differenceTime = actualDate.getTime() - startDate.getTime();" );
					progressIndicator.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + "long neededToWait = oneLoopLength * 1000L * actualLoop - differenceTime;" );
					progressIndicator.printSourceLn( tab + CommonOperations.TAB_BY_SPACE + "try{ Thread.sleep( neededToWait ); } catch(InterruptedException ex) {}");
					progressIndicator.printSourceLn( "" );
				}
				
				try{
					Thread.sleep( neededToWait );
				} catch(InterruptedException ex) {}
			}			
		}

		printSourceCloseAtStop(progressIndicator, tab);
		
	}
	
	@Override
	public void printSourceCloseAtStop( ProgressIndicatorInterface progressIndicator, String tab) {
		progressIndicator.printSourceLn( tab + "} //while()");		
	}
	
	@Override
	public void add(StepDataModelAdapter node) {
		super.add( (MutableTreeNode)node );
	}

	@Override
	public Object clone(){
		
		//Leklonozza a NODE-ot
		StepLoopCollectorDataModel cloned = (StepLoopCollectorDataModel)super.clone();
		
		cloned.oneLoopLength  = new Integer( oneLoopLength);
		cloned.maxLoopNumber = new Integer( maxLoopNumber );
		
		return cloned;
		
	}

}
