/*
 * This is the JPanel in the gui for the messages the client will recieve and send.
 * This will also let the user know if a connection was made or if the user successfully disconnected
 * 
 */
package Interfacepkg;
  
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
