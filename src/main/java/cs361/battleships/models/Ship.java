package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty protected List<Square> occupiedSquares;
	// shipSize is the amount of squares that it takes up.
	protected int shipSize;
	protected int hits;
	protected Square captainsQuarter;
	// kind is the type of ship: 'MINESWEEPER' for example
	protected String kind;
	protected boolean sunk;
	protected boolean underwater;

	public Ship() {
		occupiedSquares = new ArrayList<>();
		this.hits = 0;
		this.sunk = false;
	}

	public int getShipSize() {
		return shipSize;
	}

	public String getKind() {
		return kind;
	}

	public void setHit() {hits += 1;}

	public int getHits() { return hits;}

	public void setOccupiedSquares(List<Square> squares) {
		for (Square item : squares) {
			occupiedSquares.add(item);
		}
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

	public void setSunk(boolean sunk) { this.sunk = sunk; }
	public boolean getSunk() { return this.sunk; }

	public boolean getUnderwater(){
		return underwater;
	}

	public void setUnderwater(){
		underwater = true;
	}
}
