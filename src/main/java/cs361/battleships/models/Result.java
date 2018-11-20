package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private List<AtackStatus> a;
    private List<Ship> s;
    private Square sq;

    public Result () {
		this.a = new ArrayList<AtackStatus>();
		this.s = new ArrayList<Ship>();
	}

	public List<AtackStatus> getResult() {
		return a;
	}

	public void setResult(AtackStatus result) {
		this.a.add(result);
	}

	public List<Ship> getShip() {
		return s;
	}

	public void setShip(Ship ship) {
    	this.s.add(ship);
	}

	public Square getLocation() {
		return sq;
	}

	public void setLocation(Square square) {
		this.sq = square;
	}
}
