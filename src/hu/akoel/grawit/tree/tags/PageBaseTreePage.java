package hu.akoel.grawit.tree.tags;

import hu.akoel.grawit.pages.PageBase;

import javax.swing.tree.DefaultMutableTreeNode;

public class PageBaseTreePage  extends DefaultMutableTreeNode{
	private PageBase pageBase;

	private static final long serialVersionUID = 8871077064641984017L;
	
	public PageBaseTreePage( PageBase pageBase ){
		super();
		this.pageBase = pageBase;
	}
	
	public String toString(){
		return pageBase.getName();
	}
	
	public PageBase getPageBase(){
		return pageBase;
	}
}
