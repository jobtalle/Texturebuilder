package formats;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class ChannelExtractor {
	public enum Channel {
		CHANNEL_R,
		CHANNEL_G,
		CHANNEL_B
	}
	
	public static BufferedImage extract(BufferedImage source, Channel channel)
	{
		BufferedImage filtered = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		int[] src = ((DataBufferInt)source.getRaster().getDataBuffer()).getData();
		int[] dest = ((DataBufferInt)filtered.getRaster().getDataBuffer()).getData();
		
		for(int pixel = 0; pixel < source.getWidth() * source.getHeight(); ++pixel)
		{
			int value = (src[pixel] >> ((Channel.values().length - 1 - channel.ordinal()) << 3)) & 0xFF;
			dest[pixel] = value | (value << 8) | (value << 16) | (0xFF << 24);
		}
		
		return filtered;
	}
}
