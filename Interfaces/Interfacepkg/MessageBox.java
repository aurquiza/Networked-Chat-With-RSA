package Interfacepkg;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class MessageBox extends JPanel {

	public MessageBox() {
		JTextArea messageHistory = new JTextArea(20, 35);
		messageHistory.setEditable(false);
		this.add(new JScrollPane(messageHistory));
	}
	
}
