import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BlockGraphic extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Block blockRef;
	public static ImageIcon unknown;
	public static ImageIcon clicked;
	public static ImageIcon hover;
	public static ImageIcon bomb;
	public static ImageIcon flag;
	private ImageIcon curIcon;
	private JLabel bombsText;
	static {
		unknown = new ImageIcon("./images/unknown.png");
		clicked = new ImageIcon("./images/clicked.png");
		hover = new ImageIcon("./images/hover.png");
	}
	public BlockGraphic(Block block) {
		super();
		blockRef = block;
		bombsText = new JLabel(Integer.toString(blockRef.neighboursWithBombs));
		add(bombsText);
		bombsText.setEnabled(false);
		setIcon(unknown);
		System.out.println(blockRef.neighboursWithBombs);
	}
	public void SetBlock(Block block) {
		blockRef = block;
		Update();
	}
	public void Update() {
		bombsText.setText(Integer.toString(blockRef.neighboursWithBombs));
		if(blockRef.ifClicked()) {
			if(blockRef.ifBomb()) curIcon = bomb;
			else {
				curIcon = clicked;
				bombsText.setEnabled(blockRef.neighboursWithBombs > 0);
			}
		}
		else if(blockRef.isFlagged) curIcon = flag;
		else curIcon = unknown;
		setIcon(curIcon);
	}

}
