package Interfacepkg;
  
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
//Eric Leon, Alexis Urquiza, Mariia Melnikova 
//This is for the message box class
@SuppressWarnings("serial")
public class MessageBox extends JPanel
{

	static JTextArea messageHistory;
	
	public MessageBox()
	{
		messageHistory = new JTextArea(20, 30);
		messageHistory.setEditable(false);
		this.setBackground(new Color(204, 255, 245));
		this.add(new JScrollPane(messageHistory));
	}
	
	public static void addMessage(String msg)
	{
		messageHistory.append(msg + "\n");
	}
	
}
