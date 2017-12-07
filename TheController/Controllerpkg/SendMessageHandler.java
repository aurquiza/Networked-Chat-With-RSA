package Controllerpkg;
import Networkpkg.*;
import java.awt.event.*;
import java.util.List;
import java.util.Vector;

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
		
		Vector<DataChunk> recievers = ref.getClientsToSendMsg(); 
		
		if (recievers == null)
		{
			return;
		}
		
		for(DataChunk rec : recievers)
		{
			client.sendMessage(rec);
		}
		
	}

}
