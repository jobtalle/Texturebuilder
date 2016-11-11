package texturebuilder;

import java.awt.image.BufferedImage;

import java.util.Observable;

import javax.swing.JOptionPane;

public class TextureModel extends Observable {
	private static final String[] channelName = {
			"Diffuse",
			"Roughness",
			"Metallic",
			"Normals"
	};
	
	private boolean initialized = false;
	
	private int width;
	private int height;
	private String name = "New texture";
	
	public enum Channel {
		CHANNEL_DIFFUSE,
		CHANNEL_ROUGHNESS,
		CHANNEL_METALLIC,
		CHANNEL_NORMALS
	}
	
	public enum Change {
		CHANGE_NAME,
		CHANGE_CHANNEL,
		CHANGE_SIZE
	}
	
	private BufferedImage[] channels = new BufferedImage[Channel.values().length];
	private BufferedImage compiled;
	
	public TextureModel()
	{
		
	}
	
	public void load()
	{
		
	}
	
	public String getName()
	{
		return name;
	}
	
	public BufferedImage getChannel(Channel channel)
	{
		return channels[channel.ordinal()];
	}
	
	public static String getChannelName(Channel channel)
	{
		return channelName[channel.ordinal()];
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setName(String name)
	{
		this.name = name;
		
		change(Change.CHANGE_NAME);
	}
	
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		change(Change.CHANGE_SIZE);
	}
	
	public void setChannel(Channel channel, BufferedImage image)
	{
		if(initialized) {
			// Validate texture size
			if(image.getWidth() != getWidth() || image.getHeight() != getHeight())
			{
				JOptionPane.showMessageDialog(null, "This image does not match this texture's dimensions");
				
				return;
			}
		}
		else
			initialized = true;
			
		// Set size
		setSize(image.getWidth(), image.getHeight());
		
		// Store image
		channels[channel.ordinal()] = image;
		
		change(Change.CHANGE_CHANNEL);
	}
	
	private void change(Change change)
	{
		setChanged();
		notifyObservers(change);
	}
}
