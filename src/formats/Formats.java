package formats;

import texturebuilder.TextureModel;

public class Formats {
	public enum Format {
		FORMAT_PBR_8CHANNEL
	}
	
	private static final String[] formatName = {
			"8-Channel PBR"
	};
	
	public static String getFormatName(Format format)
	{
		return formatName[format.ordinal()];
	}
	
	public static String[] getFormats()
	{
		return formatName;
	}
	
	public static Formatter getFormatter(Format format, TextureModel model)
	{
		switch(format)
		{
		case FORMAT_PBR_8CHANNEL:
			return new FormatterPBR8(model);
		default:
			return null;
		}
	}
}
