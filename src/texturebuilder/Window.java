package texturebuilder;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {
	private static final int defaultWidth = 1024;
	private static final int defaultHeight = 768;
	private static final String defaultTitle = "Texture builder";
	
	public Window()
	{
		setJMenuBar(new MenuBar());
		setTitle(defaultTitle);
		setSize(defaultWidth, defaultHeight);
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
}
