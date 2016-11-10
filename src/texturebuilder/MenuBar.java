package texturebuilder;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {
	private JMenu file = new JMenu("File");
	
	public MenuBar()
	{
		add(file);
	}
}
