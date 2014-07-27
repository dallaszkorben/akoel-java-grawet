package hu.akoel.grawet.testcase;

import hu.akoel.grawet.page.ParameterizedPage;

import java.util.ArrayList;

public class TestCase {
	private String name;

	private ArrayList<ParameterizedPage> pageList = new ArrayList<>();
	
	public TestCase( String name ){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void addPage( ParameterizedPage page ){
		pageList.add( page );
	}
}
