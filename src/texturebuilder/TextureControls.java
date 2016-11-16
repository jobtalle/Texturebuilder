package texturebuilder;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import formats.ChannelExtractor;
import formats.Formats;
import texturebuilder.TextureModel.Channel;
import utils.SpringUtilities;

@SuppressWarnings("serial")
public class TextureControls extends JPanel implements Observer {
	private static final int preferredWidth = 200;
	
	private TextureModel model;
	
	private JPanel wrapper = new JPanel();
	private JPanel info = new JPanel();
	private JPanel controls = new JPanel();
	
	private JTextField name = new JTextField();
	private JComboBox<String> format = new JComboBox<String>(Formats.getFormats());
	private JCheckBox mirrory = new JCheckBox();
	private JLabel width = new JLabel();
	private JLabel height = new JLabel();
	private JCheckBox[] textures = new JCheckBox[TextureModel.Channel.values().length];
	
	public TextureControls(TextureModel model)
	{
		this.model = model;
		model.addObserver(this);
		
		setPreferredSize(new Dimension(preferredWidth, 0));
		setMinimumSize(new Dimension(preferredWidth, 0));
		
		configureDrop();
		configureElements();
		addElements();
	}
	
	public void addFiles(List<File> droppedFiles)
	{
		int accepted = 0;
		int rejected = 0;
		
		String rejectedFiles = "";
		
		for(File file : droppedFiles)
		{
			if(file.exists())
			{
				Vector<TextureModel.Channel> channels = new Vector<TextureModel.Channel>();
				
				for(TextureModel.Channel c : TextureModel.Channel.values())
				{
					if(file.getName().contains(TextureModel.getChannelName(c)))
						channels.add(c);
				}
				
				if(channels.size() != 0)
				{
					try
					{
						BufferedImage image = ImageIO.read(file);
						BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
						copy.getGraphics().drawImage(image, 0, 0, null);
						
						if(channels.size() == 1)
						{
							model.setChannel(channels.get(0), copy);
						}
						else
						{
							// Set all found channels
							for(Channel channel : channels)
							{
								BufferedImage filtered = ChannelExtractor.extract(copy, ChannelExtractor.Channel.values()[channels.indexOf(channel)]);
								
								model.setChannel(channel, filtered);
							}
						}
						
						++accepted;
					}
					catch (Exception error) {
						rejectedFiles += file.getName() + " - Invalid file\n";
						
						++rejected;
					}
				}
				else
				{
					rejectedFiles += file.getName() + " - No known channel\n";
					
					++rejected;
				}
			}
			else
			{
				rejectedFiles += file.getName() + " - Does not exist\n";
				
				++rejected;
			}
		}
		
		if(rejected != 0)
		{
			String rejectMsg = (accepted + rejected) + " files were given. " + rejected + " files could not be added:\n";
			
			JOptionPane.showMessageDialog(null, rejectMsg + rejectedFiles);
		}
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
		
		mirrory.setSelected(model.getMirrorY());
		mirrory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.setMirrorY(mirrory.isSelected());
			}
		});
	}
	
	private void addElements()
	{
		controls.setLayout(new SpringLayout());
		controls.setBorder(BorderFactory.createTitledBorder("Properties"));
		
		controls.add(new JLabel("Name", JLabel.TRAILING));
		controls.add(name);
		
		controls.add(new JLabel("Format", JLabel.TRAILING));
		controls.add(format);
		
		controls.add(new JLabel("Mirror Y", JLabel.TRAILING));
		controls.add(mirrory);
		
		SpringUtilities.makeCompactGrid(controls, 3, 2, 3, 3, 3, 3);
		
		info.setLayout(new SpringLayout());
		info.setBorder(BorderFactory.createTitledBorder("Info"));
		
		info.add(new JLabel("Width", JLabel.TRAILING));
		info.add(width);
		
		info.add(new JLabel("Height", JLabel.TRAILING));
		info.add(height);
		
		for(int i = 0; i < textures.length; ++i)
		{
			info.add(new JLabel(TextureModel.getChannelName(TextureModel.Channel.values()[i]), JLabel.TRAILING));
			info.add(textures[i]);
		}
		
		SpringUtilities.makeCompactGrid(info, 2 + textures.length, 2, 3, 3, 3, 3);
		
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.NORTH;
		
		wrapper.setLayout(new GridBagLayout());
		wrapper.add(controls, constraints);
		constraints.gridy = 1;
		wrapper.add(info, constraints);
		add(wrapper, constraints);
	}
	
	private void configureDrop()
	{
		setDropTarget(new DropTarget() {
			@SuppressWarnings("unchecked")
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					addFiles((List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
				} catch (Exception error) {
					error.printStackTrace();
					
					JOptionPane.showMessageDialog(null, "Couldn't accept these files");
				}
			}
		});
	}
}
