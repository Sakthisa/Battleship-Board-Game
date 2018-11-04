package cs361.battleships.models;

@SuppressWarnings("unused")
public class Square {

	protected int row;
	protected char column;
	protected String type;
	protected int hitCount;
	protected int timesHit;

	public Square(int row, char column) {
		this.row = row;
		this.column = column;
		this.timesHit = 0;
		this.type = "N";
		this.hitCount = 1;
	}

	public int getHitCount(){
		return hitCount;
	}

	public void setTimesHit(int timesHit){
		this.timesHit = timesHit;
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
