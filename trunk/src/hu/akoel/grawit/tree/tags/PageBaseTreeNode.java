package hu.akoel.grawit.tree.tags;

import javax.swing.tree.DefaultMutableTreeNode;

public class PageBaseTreeNode extends DefaultMutableTreeNode{

	private static final long serialVersionUID = -5125611897338677880L;
	private String name;
	private String details;
	
	public PageBaseTreeNode( String name, String details ){
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
