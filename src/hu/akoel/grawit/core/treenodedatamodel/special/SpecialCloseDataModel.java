package hu.akoel.grawit.core.treenodedatamodel.special;

import javax.swing.tree.MutableTreeNode;

import org.openqa.selenium.WebDriver;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ElementProgressInterface;
import hu.akoel.grawit.ExecutablePageInterface;
import hu.akoel.grawit.PageProgressInterface;
import hu.akoel.grawit.core.treenodedatamodel.SpecialDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.testcase.TestcaseParamPageDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

public class SpecialCloseDataModel extends SpecialPageModelInterface{

	private static final long serialVersionUID = 8332341626236756322L;

	public static Tag TAG = Tag.SPECIALCLOSE;
	
	private String name;

//	private PageProgressInterface pageProgressInterface = null;
	
	public SpecialCloseDataModel(String name ){
		common( name );	
	}

//	public SpecialCloseDataModel( SpecialCloseDataModel element ){
//		this.name = element.getName();
//	}

	/**
	 * XML alapjan gyartja le a SPECIALCLOSE-t
	 * 
	 * @param element
	 * @throws XMLPharseException 
	 */
	public SpecialCloseDataModel( Element element ) throws XMLPharseException{
		
		//name
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( getRootTag(), TAG, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );		
		this.name = nameString;
		
	}
	
	private void common( String name ){		
		this.name = name;
	}

	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag() {
		return getTagStatic();
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void add(SpecialDataModelInterface node) {
	}
	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.special.close");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}
/*	
	public void setPageProgressInterface( PageProgressInterface pageProgressInterface ){
		this.pageProgressInterface = pageProgressInterface;
	}
	
	@Override
	public PageProgressInterface getPageProgressInterface() {
		return this.pageProgressInterface;
	}
*/	
	@Override
	public void doAction(WebDriver driver, PageProgressInterface pageProgress, ElementProgressInterface elementProgress ) {

		//Jelzi, hogy elindult az oldal feldolgozasa
		if( null != pageProgress ){
			pageProgress.pageStarted( getName(), getNodeTypeToShow() );
		}	
		
		driver.close();
		
		//Az osszes nyitott ablakot bezarja
		driver.quit();
		
		//Csak az aktualis ablakot zarja be
		//driver.close();
		
		driver = null;

		//Jelzi, hogy befejezodott az oldal feldolgozasa
		if( null != pageProgress ){
			pageProgress.pageEnded( getName(), getNodeTypeToShow() );
		}
	}
	
	@Override
	public Element getXMLElement(Document document) {
		Attr attr;

		//Node element
		Element elementElement = document.createElement( SpecialCloseDataModel.this.getTag().getName() );
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		elementElement.setAttributeNode(attr);	

		return elementElement;	
	}

	@Override
	public Object clone(){
		
		SpecialCloseDataModel cloned = (SpecialCloseDataModel)super.clone();
	
		return cloned;
		
	}

	@Override
	public Object cloneWithParent() {
		
		SpecialCloseDataModel cloned = (SpecialCloseDataModel) this.clone();
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
	}
}
