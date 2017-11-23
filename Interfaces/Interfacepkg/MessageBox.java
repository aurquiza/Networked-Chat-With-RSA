package Interfacepkg;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MessageBox extends JPanel {

	public MessageBox() {
		JTextField messageHistory = new JTextField();
		//messageHistory.setBounds(0, 0, 20,30);
		messageHistory.setPreferredSize( new Dimension(370, 330));
		this.add(messageHistory);
	}
	
}
