package texturebuilder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TexturePanel extends JPanel {
	private static final Color backgroundColor = Color.BLACK;
	
	private BufferedImage image;
	
	public TexturePanel(BufferedImage image)
	{
		update(image);
	}
	
	public void setImage(BufferedImage image)
	{
		update(image);
	}
	
	protected void paintComponent(Graphics g)
	{
		g.setColor(backgroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		
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
