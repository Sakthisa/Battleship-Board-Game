package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private List<Square> BoardoccupiedSquares;
	private List<Ship> shipList;
	private List<Result> attackResult;
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {

		BoardoccupiedSquares = new ArrayList<Square>();
		shipList = new ArrayList<Ship>();
		attackResult = new ArrayList<Result>();
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		int shipSize = ship.getShipSize();
		//List<Square> occupiedSquares = getBoardOccupiedSquares();
		List<Square> squares = new ArrayList<Square>();
		if(!isVertical){
			for(int i = 0; i < shipSize; i++) {
				for (Square occupied : BoardoccupiedSquares) {
					if (occupied.getRow() == x && occupied.getColumn() == (char)(y + i)) {
						return false;
					}
				}
			}
		}
		else{
			for(int i = 0; i < shipSize; i++) {
				for (Square occupied : BoardoccupiedSquares) {
					if (occupied.getRow() == x + i && occupied.getColumn() == y) {
						return false;
					}
				}
			}
		}
		if (x > 10 || x < 1 || y > 'J' || y < 'A') {
			return false;
		}

		if (isVertical) {
			if (x + (shipSize - 1) <= 10) {
				// successful
				for (int i = 0; i < shipSize; i++) {
					squares.add(new Square(x + i, y));
					BoardoccupiedSquares.add(new Square(x + i, y));
				}

				ship.setOccupiedSquares(squares);
				return true;
			}
		} else {
			if (y + (shipSize - 1) <= 'J') {
				//successful
				for (int i = 0; i < shipSize; i++) {
					squares.add(new Square(x, (char)(y + i)));
					BoardoccupiedSquares.add(new Square(x, (char)(y + i)));
				}

				ship.setOccupiedSquares(squares);
				return true;
			}
		}

		return false;
	}

	public List<Square> getBoardOccupiedSquares() {
		return BoardoccupiedSquares;
	}



	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		//List<Square> occupiedSquares = getBoardOccupiedSquares();
		for(Square occupied : BoardoccupiedSquares){
			if(x == occupied.getRow() && y == occupied.getColumn()){
				//HIT
			}
		}
		//MISS
		return null;
	}

	public List<Ship> getShips() {
		return shipList;
	}

	public void setShips(List<Ship> ships) {
		for (Ship item : ships) {
			shipList.add(item);
		}
	}

	public List<Result> getAttacks() {
		return attackResult;
	}

	public void setAttacks(List<Result> attacks) {
		for (Result item : attacks) {
			attackResult.add(item);
		}
	}
}
