package Controllerpkg;
import Networkpkg.*;
import java.awt.event.*;
import java.util.List;

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
		
		// not fully implemented. yet.
		List<String> recievers = ref.getClientsToSendMsg();
		//RSA 
		
		
		ref.appendMessage(ref.getMessage());
		client.sendMessage(ref.getMessage());
	}

}
