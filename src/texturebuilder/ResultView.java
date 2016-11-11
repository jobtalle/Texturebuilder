package texturebuilder;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class ResultView extends JPanel implements Observer {
	private TexturePanel preview = new TexturePanel(null);
	private JScrollPane scrollPane = new JScrollPane(preview);
	
	private TextureModel model;
	
	public ResultView(TextureModel model)
	{
		this.model = model;
		
		model.addObserver(this);
		
		setLayout(new GridLayout(1, 1));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
	}

	@Override
	public void update(Observable model, Object change) {
		switch((TextureModel.Change)change) {
		case CHANGE_RESULT:
			preview.setImage(this.model.getResult());
			
			preview.invalidate();
			scrollPane.updateUI();
			break;
		default:
			break;
		}
	}
}
