package formats;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import texturebuilder.TextureModel;

public abstract class Formatter {
	private TextureModel model;
	
	public Formatter(TextureModel model) {
		this.model = model;
	}
	
	public BufferedImage format()
	{
		BufferedImage result = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		int[] dest = ((DataBufferInt)result.getRaster().getDataBuffer()).getData();
		
		for(int channel = 0; channel < TextureModel.Channel.values().length; ++channel)
		{
			BufferedImage channelImage = model.getChannel(TextureModel.Channel.values()[channel]);
			
			if(channelImage != null)
			{
				int[] src = ((DataBufferInt)channelImage.getRaster().getDataBuffer()).getData();
				
				for(int pixel = 0; pixel < getSourceWidth() * getSourceHeight(); ++pixel)
					write(TextureModel.Channel.values()[channel], pixel, src, dest);
			}
		}
		
		return result;
	}
	
	protected int getSourceWidth() {
		return model.getWidth();
	}
	
	protected int getSourceHeight() {
		return model.getHeight();
	}
	
	protected abstract int getWidth();
	protected abstract int getHeight();
	protected abstract void write(TextureModel.Channel channel, int pixel, int[] src, int[] dest);
}
