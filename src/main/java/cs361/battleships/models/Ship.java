package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	private int shipSize;
	private String kind;

	public Ship(String kind) {
		//TODO implement
		if (kind == "MINESWEEPER") {
			this.shipSize = 2;
		} else if (kind == "DESTROYER") {
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
