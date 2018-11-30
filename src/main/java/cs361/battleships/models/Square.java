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

	public Square(int row, char column, String type, int maxHits, int timesHit){
		this.row = row;
		this.column = column;
		this.timesHit = timesHit;
		this.type = type;
		this.maxHits = maxHits;
	}

	public void makeCopy (Square sq) {
		this.timesHit = sq.getTimesHit();
		this.maxHits = sq.getMaxHits();
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

	public void changeTimesHit(int timesHit){this.timesHit = timesHit;}

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
