package texturebuilder;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import texturebuilder.TextureModel.Change;

@SuppressWarnings("serial")
public class TextureView extends JPanel implements Observer {
	private TextureModel model;
	
	private JTabbedPane channels = new JTabbedPane();
	
	public TextureView(TextureModel model)
	{
		this.model = model;
		model.addObserver(this);
		model.getName();
		
		// Create preview tabs
		for(TextureModel.Channel channel : TextureModel.Channel.values())
		{
			channels.add(TextureModel.getChannelName(channel), new TextureChannel(model, channel));
		}
		
		// Create result tab
		channels.add("Result", null);
		
		// Create layout
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.weightx = constraints.weighty = 1;
		
		// Add interface elements
		add(channels, constraints);
	}
	
	public TextureModel getModel()
	{
		return model;
	}
	
	@Override
	public void update(Observable model, Object change)
	{
		effectChange((Change) change);
	}
	
	private void effectChange(Change change)
	{
		switch(change) {
		case CHANGE_NAME:
			
			break;
		default:
			break;
		}
	}
}
