package texturebuilder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TexturePanel extends JPanel {
	private static final Color backgroundColor = Color.BLACK;
	private static final float zoomMin = 0.125f;
	private static final float zoomMax = 4;
	private static final float zoomDelta = 2;
	
	private BufferedImage image;
	private float zoom = 1;
	
	public TexturePanel(BufferedImage image)
	{
		update(image);
		
		configureScroll();
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
			g.drawImage(image, 0, 0, (int)(image.getWidth() * zoom), (int)(image.getHeight() * zoom), null);
	}
	
	private void update(BufferedImage image)
	{
		this.image = image;
		
		if(image == null)
			return;
		
		updateSize();
	}
	
	private void updateSize()
	{
		setPreferredSize(new Dimension((int)(image.getWidth() * zoom), (int)(image.getHeight() * zoom)));
	}
	
	private void configureScroll()
	{
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				if(arg0.getWheelRotation() < 0)
				{
					zoom *= zoomDelta;
					if(zoom > zoomMax)
						zoom = zoomMax;
				}
				else {
					zoom /= zoomDelta;
					if(zoom < zoomMin)
						zoom = zoomMin;
				}
				
				updateSize();
				updateUI();
			}
		});
	}
}
