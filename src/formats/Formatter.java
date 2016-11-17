package formats;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import texturebuilder.TextureModel;

public abstract class Formatter {
	protected TextureModel model;
	
	public Formatter(TextureModel model)
	{
		this.model = model;
	}
	
	public final BufferedImage format()
	{
		BufferedImage result = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = result.getGraphics();
		
		g.setColor(new Color(0, 0, 0, 255));
		g.fillRect(0, 0, result.getWidth(), result.getHeight());
		
		int[] dest = ((DataBufferInt)result.getRaster().getDataBuffer()).getData();
		
		for(int channel = 0; channel < TextureModel.Channel.values().length; ++channel)
		{
			BufferedImage channelImage = model.getChannel(TextureModel.Channel.values()[channel]);
			
			if(channelImage != null)
			{
				int[] src = ((DataBufferInt)channelImage.getRaster().getDataBuffer()).getData();
				
				for(int pixel = 0; pixel < getSourceSize(); ++pixel)
				{
					int source;
					
					if(model.getMirrorY())
						source = src[(getSourceHeight() - 1 - (pixel / getSourceWidth())) * getSourceWidth() + (pixel % getSourceWidth())];
					else
						source = src[pixel];
					
					write(TextureModel.Channel.values()[channel], pixel, source, dest);
				}
			}
		}
		
		return result;
	}
	
	protected final int getSourceWidth()
	{
		return model.getWidth();
	}
	
	protected final int getSourceHeight()
	{
		return model.getHeight();
	}
	
	protected final int getSourceSize()
	{
		return getSourceWidth() * getSourceHeight();
	}
	
	protected final int getSize()
	{
		return getWidth() * getHeight();
	}
	
	protected abstract int getWidth();
	protected abstract int getHeight();
	protected abstract void write(TextureModel.Channel channel, int pixel, int src, int[] dest);
}
