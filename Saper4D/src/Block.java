

public class Block {
	Boolean isFlagged;
	Boolean isBomb;
	public Vector4D pos;
	public int neighboursWithBombs;
	public Boolean isClicked;
	
	public Block(int x, int y, int z, int w)
	{
		pos = new Vector4D(x, y, z, w);
		isFlagged = false;
		isBomb = false;
		isClicked = false;
	}
	public Boolean ifBomb()
	{
		return isBomb;
	}
	public Boolean ifClicked()
	{
		return isClicked;
	}
	public void flag() 
	{
		isFlagged = !isFlagged;
	}
	public String toString()
	{
		if (isBomb) return "*";
		else if (neighboursWithBombs > 0) return Integer.toString(neighboursWithBombs);
		else return " ";
	}
}
