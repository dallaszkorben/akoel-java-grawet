package hu.akoel.grawet.tree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

public class PageBaseNode extends DefaultMutableTreeNode{

	private static final long serialVersionUID = -5125611897338677880L;
	private String name;
	private String details;
	
	public PageBaseNode( String name, String details ){
		super( );
		this.name = name;
		this.details = details;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDetails(){
		return details;
	}
	
	public String toString(){
		return name;
	}
}
