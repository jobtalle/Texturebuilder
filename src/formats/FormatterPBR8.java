package formats;

import texturebuilder.TextureModel;
import texturebuilder.TextureModel.Channel;

public class FormatterPBR8 extends Formatter {
	public FormatterPBR8(TextureModel model) {
		super(model);
	}

	@Override
	protected int getWidth() {
		return getSourceWidth();
	}

	@Override
	protected int getHeight() {
		return getSourceHeight() * 2;
	}

	@Override
	protected void write(Channel channel, int pixel, int[] src, int[] dest) {
		switch(channel)
		{
		case CHANNEL_DIFFUSE:
			dest[pixel] = src[pixel];
			break;
		default:
			break;
		}
	}
}
