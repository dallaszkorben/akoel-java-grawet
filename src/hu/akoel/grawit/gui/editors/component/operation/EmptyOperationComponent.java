package hu.akoel.grawit.gui.editors.component.operation;

import hu.akoel.grawit.gui.editors.component.EditorComponentInterface;
import java.awt.Component;
import javax.swing.JPanel;

public class EmptyOperationComponent extends JPanel implements EditorComponentInterface{

	private static final long serialVersionUID = -7736373552416187606L;
	
	public EmptyOperationComponent(){
		super();

	}	

	@Override
	public Component getComponent() {
		return this;
	}


	@Override
	public void setEnableModify(boolean enable) {
	}

}
