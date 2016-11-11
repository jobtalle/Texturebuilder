package formats;

import texturebuilder.TextureModel;
import texturebuilder.TextureModel.Channel;
import utils.Vector2;
import utils.Vector3;

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
		case CHANNEL_NORMALS:
			Vector3 n = new Vector3(src[pixel]);
			Vector2 spheremap = new Vector2(n.x, n.y);
			
			float divisor = (float) Math.sqrt(8 * n.z + 8);
			
			spheremap.x /= divisor;
			spheremap.y /= divisor;
			
			spheremap.x += 0.5;
			spheremap.y += 0.5;
			
			dest[pixel + getSourceWidth() * getSourceHeight()] |= spheremap.toInt();
			break;
		case CHANNEL_ROUGHNESS:
			dest[pixel + getSourceWidth() * getSourceHeight()] |= src[pixel] & 0x000000FF;
			break;
		default:
			break;
		}
	}
}
