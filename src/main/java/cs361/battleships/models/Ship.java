package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	private int shipSize;

	public Ship(String kind) {
		//TODO implement
		if (kind == "Minesweeper") {
			this.shipSize = 2;
		} else if (kind == "Destroyer") {
			this.shipSize = 3;
		} else {
			this.shipSize = 4;
		}
	}

	public int getShipSize() {
		return shipSize;
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
