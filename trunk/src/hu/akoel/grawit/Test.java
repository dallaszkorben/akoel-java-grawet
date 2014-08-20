package hu.akoel.grawit;

import java.awt.*;
import java.io.StringReader;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class Test {

	Test() {
		JPanel gui = new JPanel(new BorderLayout());
		JToolBar toolBar = new JToolBar();
		for (int ii = 1; ii < 6; ii++) {
			toolBar.add(new JButton("Event " + ii));
			if (ii % 2 == 0) {
				toolBar.addSeparator();
			}
		}
		gui.add(toolBar, BorderLayout.NORTH);
		gui.add(new JScrollPane(new JTextArea(5, 30)), BorderLayout.CENTER);

		JOptionPane.showMessageDialog(null, gui);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Test();
			}
		});


	}

}