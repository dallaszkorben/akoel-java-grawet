package hu.akoel.grawet.tree.nodes;

import hu.akoel.grawet.pages.PageBase;

import javax.swing.tree.DefaultMutableTreeNode;

public class PageBaseLeaf  extends DefaultMutableTreeNode{
	private PageBase pageBase;

	private static final long serialVersionUID = 8871077064641984017L;
	
	public PageBaseLeaf( PageBase pageBase ){
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
