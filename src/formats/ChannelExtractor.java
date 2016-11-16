package formats;

import java.awt.image.BufferedImage;

public class ChannelExtractor {
	public enum Channel {
		CHANNEL_R,
		CHANNEL_G,
		CHANNEL_B
	}
	
	public static BufferedImage extract(BufferedImage source, Channel channel)
	{
		BufferedImage filtered = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for(int y = 0; y < source.getHeight(); ++y)
			for(int x = 0; x < source.getWidth(); ++x)
			{
				int value = (source.getRGB(x, y) << ((1 + channel.ordinal()) << 3)) & 0xFF000000;
				int grayscale = 255 | (value >> 8) | (value >> 16) | (value >> 24);
				
				filtered.setRGB(x, y, grayscale);
			}
		
		return filtered;
	}
}
