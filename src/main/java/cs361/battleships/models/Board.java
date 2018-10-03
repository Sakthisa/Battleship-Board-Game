package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		// TODO Implement
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		// TODO Implement
		int shipSize = ship.getShipSize();
		List<Square> squares = new ArrayList<Square>();
		if (x > 10 || x < 1 || y > 'J' || y < 'A') {
			return false;
		}
		if (isVertical) {
			if (x + (shipSize - 1) <= 10) {
				// successful
				for (int i = 0; i < shipSize; i++) {
					squares.add(new Square(x + i, y));
				}

				ship.setOccupiedSquares(squares);
				return true;
			}
		} else {
			if (y + (shipSize - 1) <= 'J') {
				//successful
				for (int i = 0; i < shipSize; i++) {
					squares.add(new Square(x, (char)(y + i)));
				}

				ship.setOccupiedSquares(squares);
				return true;
			}
		}

		return false;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		//TODO Implement
		return null;
	}

	public List<Ship> getShips() {
		//TODO implement
		return null;
	}

	public void setShips(List<Ship> ships) {
		//TODO implement
	}

	public List<Result> getAttacks() {
		//TODO implement
		return null;
	}

	public void setAttacks(List<Result> attacks) {
		//TODO implement
	}
}
