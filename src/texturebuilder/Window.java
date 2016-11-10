package texturebuilder;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class Window extends JFrame {
	private static final int defaultWidth = 1024;
	private static final int defaultHeight = 768;
	private static final String defaultTitle = "Texture builder";
	
	private JTabbedPane contents = new JTabbedPane();
	
	public Window()
	{
		setJMenuBar(new MenuBar(this));
		setTitle(defaultTitle);
		setSize(defaultWidth, defaultHeight);
		setLocationRelativeTo(null);
		
		// Add contents
		contents.setFocusable(false);
		add(contents);
		
		// Set window behavior
		setWindowBehavior();
		
		setVisible(true);
	}
	
	public void addView(TextureModel model)
	{
		contents.add(model.getName(), new TextureView(model));
	}
	
	public void exit()
	{
		if(JOptionPane.showConfirmDialog(this, "Exit program?") == JOptionPane.OK_OPTION)
			System.exit(0);
	}
	
	public TextureView getCurrentView()
	{
		if(contents.getComponentCount() > 0) {
			return (TextureView) contents.getComponentAt(contents.getSelectedIndex());
		}
		else
			return null;
	}
	
	public void removeView(TextureView view)
	{
		contents.remove(view);
	}
	
	private void setWindowBehavior()
	{
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				
			}
		});
	}
}
