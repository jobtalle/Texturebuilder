package texturebuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {
	private JMenu file = new JMenu("File");
	private JMenuItem fileNew = new JMenuItem("New texture");
	private JMenuItem fileClose = new JMenuItem("Close texture");
	private JMenuItem fileExport = new JMenuItem("Export");
	private JMenuItem fileExit = new JMenuItem("Exit");
	
	private Window window;
	
	public MenuBar(Window window)
	{
		this.window = window;
		
		// Configure menu's
		configureFile();
		
		// Add menu's
		file.add(fileNew);
		file.add(fileClose);
		file.addSeparator();
		file.add(fileExport);
		file.addSeparator();
		file.add(fileExit);
		add(file);
	}
	
	private void configureFile()
	{
		fileNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Add empty texture
				window.addView(new TextureModel());
			}
		});
		
		fileClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.removeView(window.getCurrentView());
			}
		});
		
		fileExport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage result = window.getCurrentView().getModel().getResult();
				
				if(result != null)
				{
					JFileChooser chooser = new JFileChooser();
					
					if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
					{
						try {
							ImageIO.write(result, "png", chooser.getSelectedFile());
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "Failed saving result to file");
						}
					}
				}
			}
		});
		
		fileExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.exit();
			}
		});
	}
}
