package texturebuilder;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TextureView extends JPanel {
	private TextureModel model;
	
	public TextureView(TextureModel model)
	{
		this.model = model;
	}
	
	public TextureModel getModel()
	{
		return model;
	}
}
