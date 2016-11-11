package formats;

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
}
