package texturebuilder;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TexturePanel extends JPanel {
	private BufferedImage image;
	
	public TexturePanel(BufferedImage image)
	{
		this.setImage(image);
		update(image);
	}
	
	public void setImage(BufferedImage image)
	{
		update(image);
	}
	
	protected void paintComponent(Graphics g)
	{
		if(image != null)
			g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
	}
	
	private void update(BufferedImage image)
	{
		this.image = image;
		
		if(image == null)
			return;
		
		setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
	}
}
