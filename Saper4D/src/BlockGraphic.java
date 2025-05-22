import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

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
	public static int iconSize;
	static {
		unknown = new ImageIcon("./images/unknown.png");
		clicked = new ImageIcon("./images/clicked.png");
		hover = new ImageIcon("./images/hover.png");
		flag = new ImageIcon("./images/flag.png");
		iconSize = unknown.getIconWidth();
	}
	public BlockGraphic(Block block) {
		super();
		blockRef = block;
		setIcon(unknown);
		System.out.println(blockRef.neighboursWithBombs);
		
	}
	public void SetBlock(Block block) {
		blockRef = block;
		Update();
	}
	public void Update() {
		if(blockRef.ifClicked()) {
			if(blockRef.ifBomb()) curIcon = bomb;
			else {
				curIcon = clicked;
				if(blockRef.neighboursWithBombs > 0) {
					setText(Integer.toString(blockRef.neighboursWithBombs));
					setVerticalTextPosition(SwingConstants.CENTER);
					setHorizontalTextPosition(SwingConstants.CENTER);
				}
				//setVerticalAlignment(SwingConstants.CENTER);
				//setHorizontalAlignment(SwingConstants.CENTER);
			}
		}
		else if(blockRef.isFlagged) curIcon = flag;
		else {
			curIcon = unknown;
			setText("");
		}
		setIcon(curIcon);
	}

}
