package Controllerpkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Interfacepkg.Chat;
import Interfacepkg.MessageBox;

// event handler for leaveChat button
// uses Chat instance to call Client function
// close connection using instance of a client in chat

public class LeaveChatEventHandler  implements ActionListener {

private Chat ref;
	
	
	public LeaveChatEventHandler(Chat ref)
	{
		this.ref = ref;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		ref.callCloseConnection();
		MessageBox.addMessage("You are disconnected from chat now");
	}
}
