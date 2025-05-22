import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.BoxLayout;
import javax.swing.JSlider;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private SaperMap saperMap;
	private Block[][] currentPanel;
	public int z;
	public int w;
	
	public static int size = 4;
	public static int bombs = size * 2;
	
	JPanel mapPanel, centerPanel;
	JPanel control, Wpanel, Zpanel;
	JLabel loserText;
	
	
	
	private List<BlockGraphic> blocks;
	private JPanel labelPanel;
	private JLabel wLabel;
	private JLabel zLabel;
	private JPanel buttonsPanel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		saperMap = new SaperMap(size, 10);
		z = 0;
		w = 0;
		currentPanel = saperMap.getPanel(z, w);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 945, 544);
		contentPane = new JPanel(new BorderLayout());
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		

		mapPanel = new JPanel(new GridLayout(size, size, 0, 0));
		mapPanel.setPreferredSize(new Dimension(BlockGraphic.iconSize * size, BlockGraphic.iconSize * size));
		
		centerPanel = new JPanel(new GridBagLayout());
		centerPanel.add(mapPanel);
		contentPane.add(centerPanel, BorderLayout.CENTER);

		
		control = new JPanel();
		control.setLayout(new BoxLayout(control, BoxLayout.X_AXIS));
		contentPane.add(control, BorderLayout.EAST);
		
		labelPanel = new JPanel();
		control.add(labelPanel);
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		
		wLabel = new JLabel("W: 0");
		labelPanel.add(wLabel);
		
		zLabel = new JLabel("Z: 0");
		labelPanel.add(zLabel);
		
		buttonsPanel = new JPanel();
		control.add(buttonsPanel);
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

		
		
		Wpanel = new JPanel();
		buttonsPanel.add(Wpanel);
		Wpanel.setLayout(new BoxLayout(Wpanel, BoxLayout.X_AXIS));
		
		JButton wDownButton = new JButton("-");
		Wpanel.add(wDownButton);
		wDownButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(w == 0) return;
				w--;
				currentPanel = saperMap.getPanel(z,w);
				UpdateMap();
			}
		});
		
		JButton wUpButton = new JButton("+");
		Wpanel.add(wUpButton);
		wUpButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(w == size - 1) return;
				w++;
				currentPanel = saperMap.getPanel(z,w);
				UpdateMap();
			}
		});
		
		Zpanel = new JPanel();
		buttonsPanel.add(Zpanel);
		Zpanel.setLayout(new BoxLayout(Zpanel, BoxLayout.X_AXIS));
		
		JButton zDownButton = new JButton("-");
		Zpanel.add(zDownButton);
		zDownButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(z == 0) return;
				z--;
				currentPanel = saperMap.getPanel(z,w);
				UpdateMap();
			}
		});
		
		JButton zUpButton = new JButton("+");
		zUpButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(z == size - 1) return;
				z++;
				currentPanel = saperMap.getPanel(z,w);
				UpdateMap();
			}
		});
		Zpanel.add(zUpButton);
		
		// my code
		SpawnPanel(currentPanel);
		
		// Final window settings
		pack(); 
		setLocationRelativeTo(null); 
		//setVisible(true);
	}
	public void SpawnPanel(Block[][] panel) {
		blocks = new ArrayList<BlockGraphic>();
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				//JLabel text = new JLabel(Integer.toString(panel[i][j].neighboursWithBombs));
				BlockGraphic temp = new BlockGraphic(panel[i][j]);
				//temp.add(text);
				temp.setLayout(new BorderLayout());
		        //text.setHorizontalAlignment(SwingConstants.CENTER);
		        //text.setVerticalAlignment(SwingConstants.CENTER);
		        //temp.add(text, BorderLayout.CENTER);
				blocks.add(temp);
				mapPanel.add(temp);
				temp.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent me) {
						Boolean bombFree = true;
						if(SwingUtilities.isRightMouseButton(me)) temp.blockRef.flag();
						else bombFree = saperMap.click(temp.blockRef.pos);
						UpdateMap();
						if(!bombFree) Lose();
					}
				});
				
			}
		}
		UpdateMap();
	}
	public void UpdateMap() {
		for(BlockGraphic block : blocks) {
			Vector4D toCpy = block.blockRef.pos;
			Vector4D posCpy = new Vector4D(toCpy.x(), toCpy.y(), z, w);
			Block newBlock = saperMap.vecToBlock(posCpy);
			block.SetBlock(newBlock);
		}
		zLabel.setText("Z: " + Integer.toString(z));
		wLabel.setText("W: " + Integer.toString(w));
		
	}
	public void Lose() {
		for(BlockGraphic block : blocks) {
			block.setEnabled(false);
			block.setVisible(false);
		}
		control.setVisible(false);
		mapPanel.setVisible(false);
		loserText = new JLabel("You lose");
		contentPane.add(loserText);
	}

}
