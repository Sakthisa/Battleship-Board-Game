package cs361.battleships.models;

@SuppressWarnings("unused")
public class Square {

	protected int row;
	protected char column;
	protected String type;
	protected int maxHits;
	protected int timesHit;

	public Square() {
	}

	public Square(int row, char column) {
		this.row = row;
		this.column = column;
		this.timesHit = 0;
		this.type = "N";
		this.maxHits = 1;
	}

	public int getMaxHits(){
		return maxHits;
	}
	public void setMaxHits(int maxHits){
		this.maxHits = maxHits;
	}

	public void setTimesHit(){
		this.timesHit++;
	}
	public int getTimesHit(){
		return timesHit;
	}

	public char getColumn() {
		return column;
	}

	public void setColumn(char column) {
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getType(){
		return type;
	}
}
