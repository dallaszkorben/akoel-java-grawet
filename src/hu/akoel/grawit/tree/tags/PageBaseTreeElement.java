package hu.akoel.grawit.tree.tags;

import hu.akoel.grawit.elements.ElementBase;

import javax.swing.tree.DefaultMutableTreeNode;

public class PageBaseTreeElement  extends DefaultMutableTreeNode{

	private static final long serialVersionUID = -8916078747948054716L;
	private ElementBase elementBase;

	public PageBaseTreeElement( ElementBase pageBase ){
		super();
		this.elementBase = pageBase;
	}
	
	public String toString(){
		return elementBase.getName();
	}
	
	public ElementBase getPageBase(){
		return elementBase;
	}
}
