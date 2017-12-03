package Controllerpkg;
import Networkpkg.*;
import java.awt.event.*;
import Interfacepkg.*;


public class SendMessageHandler implements ActionListener
{
	private Chat ref;
	private Client client;
	
	public SendMessageHandler(Chat ref)
	{
		this.ref = ref;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		client = ref.getClientSocket();
		ref.appendMessage(ref.getMessage());
		client.sendMessage(ref.getMessage());
	}

}
