package texturebuilder;

import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class Window extends JFrame implements Observer {
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
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
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
		
		model.addObserver(this);
	}
	
	public void exit()
	{
		if(JOptionPane.showConfirmDialog(this, "Exit program?") == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	
	public TextureView getCurrentView()
	{
		if(contents.getComponentCount() > 0)
			return (TextureView) contents.getComponentAt(contents.getSelectedIndex());
		else
			return null;
	}
	
	public void removeView(TextureView view)
	{
		view.getModel().deleteObserver(this);
		
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

	@Override
	public void update(Observable model, Object change) {
		for(Component component : contents.getComponents()) {
			TextureView view = (TextureView)component;
			
			if(view.getModel() == model) {
				break;
			}
		}
		
		switch((TextureModel.Change)change) {
		case CHANGE_NAME:
			for(Component component : contents.getComponents()) {
				TextureView view = (TextureView)component;
				
				if(view.getModel() == model) {
					contents.setTitleAt(contents.indexOfComponent(view), view.getModel().getName());
					break;
				}
			}
			break;
		default:
			break;
		}
	}
}
