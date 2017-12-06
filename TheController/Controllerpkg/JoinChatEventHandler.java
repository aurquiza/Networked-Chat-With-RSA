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
			MessageBox.addMessage("Conenction established :D");
			ref.allowAccess(c);
		}
		else
		{
			MessageBox.addMessage("Connection not established :c");
		}
	}

}
