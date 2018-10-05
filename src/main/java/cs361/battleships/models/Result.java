package cs361.battleships.models;

public class Result {

	public AtackStatus getResult() {
		return a;
	}

	public void setResult(AtackStatus result) {
		a =  result;
	}

	public Ship getShip() {
		return s;
	}

	public void setShip(Ship ship) {
		//TODO implement
	}

	public Square getLocation() {
		return sq;
	}

	public void setLocation(Square square) {
		sq =  square;
	}
}
