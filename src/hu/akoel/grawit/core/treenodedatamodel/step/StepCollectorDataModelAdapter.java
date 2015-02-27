package hu.akoel.grawit.core.treenodedatamodel.step;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ExecutablePageInterface;
import hu.akoel.grawit.core.treenodedatamodel.BaseDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseCollectorDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseFolderDataModel;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLBaseConversionPharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public abstract class StepCollectorDataModelAdapter extends StepNodeDataModelAdapter implements ExecutablePageInterface{ //extends ParamDataModelAdapter implements ExecutablePageInterface{

	public static final String ATTR_LAST_BASE_ELEMENT_PATH = "lastbaseelementpath";
	
	public StepCollectorDataModelAdapter(Element element, BaseRootDataModel baseRootDataModel, ConstantRootDataModel variableRootDataModel) throws XMLPharseException {
		super(element, baseRootDataModel, variableRootDataModel);

	}

	public StepCollectorDataModelAdapter(String name, String details) {
		super(name, details);
	}

	private static final long serialVersionUID = 6053404210638476702L;

	abstract public BaseElementDataModelAdapter getLastBaseElement();
	
	abstract public void setLastBaseElement( BaseElementDataModelAdapter lastBaseElement );
	
	/**
	 * Az attributum-ban megadott eleresi utvonalbol keszit BaseDataModel objektumot, ha lehet
	 * 
	 * @param element
	 * @param attribute
	 * @return
	 * @throws XMLBaseConversionPharseException 
	 */
	public static BaseDataModelAdapter getBaseDataModelFromPath(Element element, BaseRootDataModel baseRootDataModel, Tag tag, String name ) throws XMLBaseConversionPharseException{
		
		String attribute = ATTR_LAST_BASE_ELEMENT_PATH;
		BaseDataModelAdapter baseDataModel = baseRootDataModel;
		
		//Nincs megadva eleresi utvonal egyaltalan
		if( !element.hasAttribute( attribute ) ){
			
			return null;
			
		//Van utvonal
		}else{
		
			String paramPagePathString = element.getAttribute(attribute);
			
			if( paramPagePathString.trim().isEmpty() ){
				
				//Else [Fatal Error] :-1:-1: Premature end of file.  
				return null;
			}
			
			paramPagePathString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + paramPagePathString;  
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
			DocumentBuilder builder; 
			Document document = null;
			try {
				
				//attributum-kent tarolt utvonal atalakitasa Documentum-ma
				builder = factory.newDocumentBuilder();  
				
				
				StringReader sr = new StringReader( paramPagePathString );
				InputSource is = new InputSource( sr );
				
				document = builder.parse( new InputSource( new StringReader( paramPagePathString ) ) ); 
     
			} catch (Exception e) {	    	
				
				//Nem sikerult az atalakitas
				throw new XMLBaseConversionPharseException( getRootTag(), tag, ATTR_NAME, name, attribute, element.getAttribute(attribute), e );
	    	
			} 
			
			//Megkeresem a BASEROOT-ben a BASEPAGE-hez vezeto utat
			Node actualNode = document;
			while( actualNode.hasChildNodes() ){
	
				actualNode = actualNode.getFirstChild();
				Element actualElement = (Element)actualNode;
				String tagName = actualElement.getTagName();
				String attrName = null;
	    	
				//Ha BASEFOLDER
				if( tagName.equals( BaseFolderDataModel.TAG.getName() ) ){
					attrName = actualElement.getAttribute(BaseFolderDataModel.ATTR_NAME);	    		
					baseDataModel = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModel, Tag.BASEFOLDER, attrName );

					if( null == baseDataModel ){

						throw new XMLBaseConversionPharseException( getRootTag(), tag, ATTR_NAME, name, attribute, element.getAttribute(attribute) );
					}
	    		
				//Ha BASECOLLECTOR
				}else if( tagName.equals( BaseCollectorDataModel.TAG.getName() ) ){
					attrName = actualElement.getAttribute(BaseCollectorDataModel.ATTR_NAME);
					baseDataModel = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModel, Tag.BASECOLLECTOR, attrName );
					if( null == baseDataModel ){

						throw new XMLBaseConversionPharseException( getRootTag(), tag, ATTR_NAME, name, attribute, element.getAttribute(attribute) );
					}
	    
				//Ha BASEELEMENT
				}else if( tagName.equals( BaseElementDataModelAdapter.TAG.getName() ) ){
					attrName = actualElement.getAttribute(BaseElementDataModelAdapter.ATTR_NAME);						    		
	    	
					baseDataModel = (BaseDataModelAdapter) CommonOperations.getDataModelByNameInLevel( baseDataModel, Tag.BASEELEMENT, attrName );
					if( null == baseDataModel ){

						throw new XMLBaseConversionPharseException( getRootTag(), tag, ATTR_NAME, name, attribute, element.getAttribute(attribute) );
					}
					
				}else{
	    		
					throw new XMLBaseConversionPharseException( getRootTag(), tag, ATTR_NAME, name, attribute, element.getAttribute(attribute) );	    		
				}	    	
	    	
			}	    
			try{				
				
				//return (BaseElementDataModelAdapter)baseDataModel;
				return baseDataModel;
	    	
			}catch(ClassCastException e){

				//Nem sikerult az utvonalat megtalalni
				throw new XMLBaseConversionPharseException( getRootTag(), tag, ATTR_NAME, name, attribute, element.getAttribute(attribute), e );
			}
				
		}
		
	}
	
}
