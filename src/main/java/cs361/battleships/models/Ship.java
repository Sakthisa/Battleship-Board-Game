package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty protected List<Square> occupiedSquares;
	// shipSize is the amount of squares that it takes up.
	protected int shipSize;
	protected int hits;
	protected int initrow;
	protected char initcol;
	protected Square captainsQuarter;
	// kind is the type of ship: 'MINESWEEPER' for example
	protected String kind;
	protected boolean sunk;
	protected boolean underwater;
	protected boolean isVertical;



	public Ship() {
		occupiedSquares = new ArrayList<>();
		this.hits = 0;
		this.sunk = false;
	}

	public boolean isVertical() {
		return isVertical;
	}

	public void setVertical(boolean vertical) {
		isVertical = vertical;
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

	public void setInitrow(int initrow) {
		this.initrow = initrow;
	}

	public void setInitcol(char initcol) {
		this.initcol = initcol;
	}

	public int getInitrow() {
		return initrow;
	}

	public char getInitcol() {
		return initcol;
	}




	// each move function will move the ship within a game board if it can be moved independently.
	public boolean moveUp(int row, char col){
		for(Square square : occupiedSquares){
			if(square.getRow() <= 0){
				return false;
			}
		}
		for(Square square : occupiedSquares) {
			square.setRow(square.getRow() - 1);
		}
		return true;
	}

	public boolean moveDown(int row, char col){
		for(Square square : occupiedSquares){
			if(square.getRow() >= 10){
				return false;
			}
		}
		for(Square square : occupiedSquares) {
			square.setRow(square.getRow() + 1);
		}
		return true;
	}

	public boolean moveLeft(int row, char col){
		for(Square square : occupiedSquares){
			if(square.getColumn() <= 'A'){
				return false;
			}
		}
		for(Square square : occupiedSquares) {
			square.setColumn((char)(square.getColumn() - 1));
		}
		return true;
	}

	public boolean moveRight(int row, char col){
		for(Square square : occupiedSquares){
			if(square.getColumn() >= 'F'){
				return false;
			}
		}
		for(Square square : occupiedSquares) {
			square.setColumn((char)(square.getColumn() + 1));
		}
		return true;
	}

	public boolean containsSquare(int row, char col){
		for(Square square : occupiedSquares){
			if(square.getRow() == row && square.getColumn() == col){
				return true;
			}
		}
		return false;
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

	public void setSunk(boolean sunk) { this.sunk = sunk; }

	public boolean getUnderwater(){
		return underwater;
	}

	public Square getCaptainsQuarter() {
		return captainsQuarter;
	}

	public boolean isSunk() {
		return sunk;
	}

	public boolean isUnderwater() {
		return underwater;
	}

	public void setUnderwater(){
		underwater = true;
	}
}
