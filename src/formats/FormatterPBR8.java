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
	protected void write(Channel channel, int pixel, int src, int[] dest) {
		switch(channel)
		{
		case CHANNEL_DIFFUSE:
			dest[pixel] = src;
			break;
		case CHANNEL_NORMALS:
			Vector3 n = new Vector3(src);
			Vector2 spheremap = new Vector2(n.x, n.y);
			
			float divisor = (float) Math.sqrt(8 * n.z + 8);
			
			spheremap.x /= divisor;
			spheremap.y /= divisor;
			
			spheremap.x += 0.5;
			spheremap.y += 0.5;
			
			dest[pixel + getSourceSize()] |= spheremap.toInt();
			break;
		case CHANNEL_ROUGHNESS:
			dest[pixel + getSourceSize()] |= src & 0x000000FF;
			break;
		case CHANNEL_OCCLUSION:
			int r = (dest[pixel] << 8) & 0xFF000000;
			int g = (dest[pixel] << 16) & 0xFF000000;
			int b = (dest[pixel] << 24) & 0xFF000000;
			float factor = (src & 0xFF000000) / 255.0f;
			
			r *= factor;
			g *= factor;
			b *= factor;
			
			dest[pixel] = (dest[pixel] & 0xFF000000) | (r >> 8) | (g >> 16) | (b >> 24);			
			break;
		default:
			break;
		}
	}
}
