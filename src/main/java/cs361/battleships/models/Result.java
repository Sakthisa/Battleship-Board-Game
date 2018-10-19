package cs361.battleships.models;

public class Result {
    private AtackStatus a;
    private Ship s;
    private Square sq;

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
        	s = ship;
	}

	public Square getLocation() {
		return sq;
	}

	public void setLocation(Square square) {
		sq =  square;
	}
}
