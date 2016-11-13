package texturebuilder;

import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class TextureChannel extends JPanel implements Observer {
	private TexturePanel preview = new TexturePanel(null);
	private JScrollPane scrollPane = new JScrollPane(preview);
	private TextureModel model;
	private TextureModel.Channel channel;
	
	public TextureChannel(TextureModel model, TextureModel.Channel channel)
	{
		this.model = model;
		this.channel = channel;
		
		model.addObserver(this);
		
		setLayout(new GridLayout(1, 1));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
		
		configureDrop();
	}

	@Override
	public void update(Observable model, Object change) {
		switch((TextureModel.Change)change) {
		case CHANGE_CHANNEL:
			preview.setImage(this.model.getChannel(channel));
			
			preview.invalidate();
			scrollPane.updateUI();
			break;
		default:
			break;
		}
	}
	
	private void configureDrop()
	{
		preview.setDropTarget(new DropTarget() {
			@SuppressWarnings("unchecked")
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					
					if(droppedFiles.size() != 1)
					{
						JOptionPane.showMessageDialog(null, "Only one texture can be added to this channel");
						
						return;
					}
					
					File file = droppedFiles.get(0);
					
					if(file.exists()) {
						BufferedImage image = ImageIO.read(file);
						BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
						
						copy.getGraphics().drawImage(image, 0, 0, null);
						
						model.setChannel(channel, copy);
					}
				} catch (Exception error) {
					error.printStackTrace();
					
					JOptionPane.showMessageDialog(null, "This file is not a valid texture");
				}
			}
		});
	}
}
