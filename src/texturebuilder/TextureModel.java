package texturebuilder;

import java.awt.image.BufferedImage;

import java.util.Observable;

import javax.swing.JOptionPane;

import formats.Formats;
import formats.Formatter;

public class TextureModel extends Observable {
	private static final Formats.Format defaultFormat = Formats.Format.FORMAT_PBR_8CHANNEL;
	private static final String[] channelName = {
			"Diffuse",
			"Roughness",
			"Metallic",
			"Normals",
			"Emissive"
	};
	
	private boolean initialized = false;
	
	private String name = "New texture";
	private Formats.Format format = defaultFormat;
	private boolean mirrorY = true; // Fetch from config
	private int width;
	private int height;
	
	public enum Channel {
		CHANNEL_DIFFUSE,
		CHANNEL_ROUGHNESS,
		CHANNEL_METALLIC,
		CHANNEL_NORMALS,
		CHANNEL_EMISSIVE
	}
	
	public enum Change {
		CHANGE_NAME,
		CHANGE_CHANNEL,
		CHANGE_SIZE,
		CHANGE_RESULT,
		CHANGE_FORMAT,
		CHANGE_MIRROR_Y
	}
	
	private BufferedImage[] channels = new BufferedImage[Channel.values().length];
	private BufferedImage result;
	private Formatter formatter;
	
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
	
	public BufferedImage getResult()
	{
		return result;
	}
	
	public static String getChannelName(Channel channel)
	{
		return channelName[channel.ordinal()];
	}
	
	public Formats.Format getFormat()
	{
		return format;
	}
	
	public boolean getMirrorY()
	{
		return mirrorY;
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
	
	public void setFormat(Formats.Format format)
	{
		this.format = format;
		
		change(Change.CHANGE_FORMAT);
		
		renderResult();
	}
	
	public void setMirrorY(boolean mirror)
	{
		mirrorY = mirror;
		
		change(Change.CHANGE_MIRROR_Y);
		
		renderResult();
	}
	
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		// Create compiled image
		result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
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
		else {
			initialized = true;
			
			// Create result
			formatter = Formats.getFormatter(format, this);
		}
			
		// Set size
		setSize(image.getWidth(), image.getHeight());
		
		// Store image
		channels[channel.ordinal()] = image;
		
		renderResult();
		
		change(Change.CHANGE_CHANNEL);
	}
	
	private void change(Change change)
	{
		setChanged();
		notifyObservers(change);
	}
	
	private void renderResult()
	{
		if(result == null)
			return;
		
		result = formatter.format();
		
		change(Change.CHANGE_RESULT);
	}
}
