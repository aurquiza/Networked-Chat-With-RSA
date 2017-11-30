package Interfacepkg;

/* Menu class represents a Singleton design
 * since it's instance is the same for clients and only needs to be
 * created once.
 * Contains "info" and "about" boxes 
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Menu extends JMenuBar implements ActionListener {
	JMenu menu;
	JMenuItem menuItem;
	private static Menu m;
	//JMenu Help box section is divided in two sub menu items for easier use
	private Menu() {
		  menu = new JMenu("Menu");
		  	menuItem = new JMenuItem("Help Menu");
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(menu, 
							"Help:\n");
					}	  
			});
		  menu.add(menuItem);
			menuItem = new JMenuItem("About"); // battleship game rules
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(menu, 
							"mmelni4"
							+ "\naurqui7"
							+ "\neleon23");
				}
			});
		menu.add(menuItem);
		
		this.add(menu);
		
	//...for each JMenuItem instance:
	menuItem.addActionListener(this);
	
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static Menu getMenu()
	{
		if(m == null)	
			m = new Menu();
		return m;
	}
}
