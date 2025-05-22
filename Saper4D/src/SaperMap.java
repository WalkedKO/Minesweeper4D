import java.util.ArrayList;
import java.util.List;

// to do: repair setBombs, at bombs at all..., and add flags
public class SaperMap {
	private int state;
	public int size;
	public int bombs;
	Block[][][][] saperMap;
	List<Block> allBlocks;
	List<Vector4D> clickedBlocks;
	List<Vector4D> bombsList;
	
	
	public SaperMap(int newSize, int newBombs){
		size = newSize;
		bombs = newBombs;
		saperMap = new Block[size][size][size][size];
		clickedBlocks = new ArrayList<Vector4D>();
		allBlocks = new ArrayList<Block>();
		
		for(int w = 0; w < size; w++){
			for(int z = 0; z < size; z++) {
				for(int y = 0; y < size; y++) {
					for(int x = 0; x < size; x++) {
						saperMap[w][z][y][x] = new Block(x, y, z, w);
						allBlocks.add(saperMap[w][z][y][x]);
					}
				}
			}
		}
		
		bombsList = setBombs();
		
		for(Block block : allBlocks) {
			if(!block.ifBomb()) block.neighboursWithBombs = countNeighboursBombs(block);
		}
	}
	
	public boolean click(Vector4D vec) {
		System.out.println(vec.x() + " " + vec.y() + " " + vec.z() + " " + vec.w());
		Block blockRef = vecToBlock(vec);
		if(!blockRef.isClicked) {
			clickedBlocks.add(vec);
			blockRef.click();
		}
		else fill(vec);
		return !(blockRef.ifBomb());
	}
	
	public void flag(Vector4D vec) {
		vecToBlock(vec).flag();
	}
	
	public int getClicked() {
		return clickedBlocks.size();
	}
	
	public int getBombs() {
		return bombsList.size();
	}
	
	public Block[][] getPanel(int z, int w){
		return saperMap[w][z];
	}
	
	public void fill(Vector4D vec) {
		Block blockRef = vecToBlock(vec);
		List<Block> neighbours = getNeighbours(blockRef);
		for(Block element : neighbours) {
			if(!(element.ifBomb()) && !(element.ifClicked())) {
				click(element.pos);
				if(element.neighboursWithBombs == 0) {
					fill(element.pos);
				}
			}
		}
	}
	
	public Block vecToBlock(Vector4D vec){
		return saperMap[vec.w()][vec.z()][vec.y()][vec.x()];
	}
	
	private List<Block> getNeighbours(Block block){
		List<Block> neighbours = new ArrayList<Block>();
		Vector4D pos = block.pos;
		for(int w = -1; w <= 1; w++) {
			for(int z = -1; z <= 1; z++) {
				for(int y = -1; y <= 1; y++) {
					for(int x = -1; x <= 1; x++) {
						Vector4D tempPos = new Vector4D(pos.x() + x, pos.y() + y, pos.z() + z, pos.w() + w);
						if(tempPos.w() < 0 || tempPos.z() < 0 || tempPos.y() < 0 || tempPos.x() < 0) break;
						if(tempPos.w() >= size || tempPos.z() >= size || tempPos.y() >= size || tempPos.x() >= size) break;
						if(w == 0 && z == 0 && y == 0 && x == 0) break;
						neighbours.add(vecToBlock(tempPos));
					}
				}
			}
		}
		return neighbours;
	}
	
	private List<Vector4D> setBombs()
	{
		List<Vector4D> bombsLoc = new ArrayList<Vector4D>();
		int similarity;
		externalFor:
		for(int i = 0; i < bombs; i++)
		{
			int[] temp = new int[4];
			for(int j = 0; j < 4; j++) temp[j] = (int)(Math.random() * size);
			for(Vector4D element : bombsLoc){
				similarity = 0;
				if(element.x() == temp[0]) similarity++;
				if(element.y() == temp[1]) similarity++;
				if(element.z() == temp[2]) similarity++;
				if(element.w() == temp[3]) similarity++;
				if(similarity == 4) {
					i--;
					continue externalFor;
				}
			}
			Vector4D tempVec = new Vector4D(temp[0], temp[1], temp[2], temp[3]);
			vecToBlock(tempVec).isBomb = true;
			bombsLoc.add(tempVec);
		}
		return bombsLoc;
	}
	
	private int countNeighboursBombs(Block block) {
		int counter = 0;
		List<Block> neighbours = getNeighbours(block);
		for(Block element : neighbours) if(element.ifBomb()) counter++;
		return counter;
	}
}
