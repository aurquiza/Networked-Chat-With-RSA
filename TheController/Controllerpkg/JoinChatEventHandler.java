package Controllerpkg;
import Networkpkg.*;
import java.awt.event.*;
import Interfacepkg.*;

public class JoinChatEventHandler implements ActionListener
{
	private Chat ref;
	
	
	public JoinChatEventHandler(Chat ref)
	{
		this.ref = ref;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		
		Client c = new Client(ref.getIPInfo(), ref.getPortInfo(), ref);
		if(c.checkConnectionStatus())
		{
			System.out.println("Conenction established :D");
			ref.allowAccess(c);
		}
		else
		{
			System.out.println("Connection not established :c");
		}
	}

}
