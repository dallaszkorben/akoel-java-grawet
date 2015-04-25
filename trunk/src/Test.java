import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class Test extends JFrame {

    private boolean wrapped;
    private JButton toggleButton = null;
    private JTextPane textPane = null;
    private JPanel noWrapPanel = null;
    private JScrollPane scrollPane = null;

    public Test() {
        super();
        init();
    }

    public void init() {
        this.setSize(300, 200);
        this.setLayout(new BorderLayout());

        wrapped = false;

        textPane = new JTextPane();
        noWrapPanel = new JPanel( new BorderLayout() );
        noWrapPanel.add( textPane );

        scrollPane = new JScrollPane( noWrapPanel );

        toggleButton = new JButton("wrap");
        toggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (wrapped == true){
                    scrollPane.setViewportView(noWrapPanel);
                    noWrapPanel.add(textPane);
                    toggleButton.setText("wrap");
                    wrapped = false;
                }else {
                    scrollPane.setViewportView(textPane);
                    toggleButton.setText("unWrap");
                    wrapped = true;
                }
            }
        });

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(toggleButton, BorderLayout.NORTH);
    }
}