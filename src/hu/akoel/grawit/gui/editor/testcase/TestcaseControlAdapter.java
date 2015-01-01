package hu.akoel.grawit.gui.editor.testcase;

import hu.akoel.grawit.gui.editor.DataEditor;

public abstract class TestcaseControlAdapter extends DataEditor{

	private static final long serialVersionUID = 6473830548306238316L;

	public TestcaseControlAdapter(String element) {
		super(element);		
	}
	
	public TestcaseControlAdapter(EditMode mode, String element) {
		super(mode, element);		
	}

}
