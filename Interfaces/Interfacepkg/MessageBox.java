package Interfacepkg;
  
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class MessageBox extends JPanel
{

	JTextArea messageHistory;
	
	public MessageBox()
	{
		messageHistory = new JTextArea(20, 35);
		messageHistory.setEditable(false);
		this.add(new JScrollPane(messageHistory));
	}
	
	public void addMessage(String msg)
	{
		messageHistory.append(msg + "\n");
	}
	
}
