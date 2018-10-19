package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	// shipSize is the amount of squares that it takes up.
	private int shipSize;
	// kind is the type of ship: 'MINESWEEPER' for example
	private String kind;

	public Ship() {
		occupiedSquares = new ArrayList<>();
	}

	public Ship(String kind) {
		//TODO implement
		if (kind.equals("MINESWEEPER")) {
			this.shipSize = 2;
		} else if (kind.equals("DESTROYER")) {
			this.shipSize = 3;
		} else {
			this.shipSize = 4;
		}
		occupiedSquares = new ArrayList<Square>();
		this.kind = kind;
	}

	public int getShipSize() {
		return shipSize;
	}

	public String getKind() {
		return kind;
	}

	public void setOccupiedSquares(List<Square> squares) {
		for (Square item : squares) {
			occupiedSquares.add(item);
		}
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}
}
