package texturebuilder;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import formats.Formats;
import texturebuilder.TextureModel.Channel;
import utils.SpringUtilities;

@SuppressWarnings("serial")
public class TextureControls extends JPanel implements Observer {
	private static final int preferredWidth = 200;
	
	private TextureModel model;
	
	private JPanel wrapper = new JPanel();
	private JTextField name = new JTextField();
	private JComboBox<String> format = new JComboBox<String>(Formats.getFormats());
	private JLabel width = new JLabel();
	private JLabel height = new JLabel();
	private JCheckBox[] textures = new JCheckBox[TextureModel.Channel.values().length];
	
	public TextureControls(TextureModel model)
	{
		this.model = model;
		model.addObserver(this);
		
		setPreferredSize(new Dimension(preferredWidth, 0));
		setMinimumSize(new Dimension(preferredWidth, 0));
		
		configureElements();
		addElements();
	}
	
	@Override
	public void update(Observable model, Object change) {
		switch((TextureModel.Change)change) {
		case CHANGE_SIZE:
			width.setText(Integer.toString(this.model.getWidth()));
			height.setText(Integer.toString(this.model.getHeight()));
			break;
		case CHANGE_CHANNEL:
			for(int i = 0; i < textures.length; ++i)
				textures[i].setSelected(this.model.getChannel(Channel.values()[i]) != null);
			break;
		default:
			break;
		}
	}
	
	private void configureElements()
	{
		name.setText(model.getName());
		name.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.setName(name.getText());
			}
		});
		
		format.setSelectedIndex(model.getFormat().ordinal());
		format.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.setFormat(Formats.Format.values()[format.getSelectedIndex()]);
			}
		});
		
		for(int i = 0; i < textures.length; ++i)
		{
			textures[i] = new JCheckBox();
			textures[i].setEnabled(false);
		}
	}
	
	private void addElements()
	{
		wrapper.setLayout(new SpringLayout());
		
		wrapper.add(new JLabel("Name", JLabel.TRAILING));
		wrapper.add(name);
		
		wrapper.add(new JLabel("Format", JLabel.TRAILING));
		wrapper.add(format);
		
		wrapper.add(new JLabel("Width", JLabel.TRAILING));
		wrapper.add(width);
		
		wrapper.add(new JLabel("Height", JLabel.TRAILING));
		wrapper.add(height);
		
		for(int i = 0; i < textures.length; ++i)
		{
			wrapper.add(new JLabel(TextureModel.getChannelName(TextureModel.Channel.values()[i]), JLabel.TRAILING));
			wrapper.add(textures[i]);
		}
		
		SpringUtilities.makeCompactGrid(wrapper, 4 + textures.length, 2, 3, 3, 3, 3);
		
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.NORTH;
		
		add(wrapper, constraints);
	}
}
