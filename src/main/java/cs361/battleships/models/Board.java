package cs361.battleships.models;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.List;

public class Board {

	private List<Square> BoardoccupiedSquares;
	private List<Ship> shipList;
	private List<Result> attackResult;
	private int xDimension;
	private int yDimension;
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		BoardoccupiedSquares = new ArrayList<Square>();
		shipList = new ArrayList<Ship>();
		attackResult = new ArrayList<Result>();

		//In future these should be taken as args to the board constructor
		setYDimension(10);
		setXDimension(10);
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		int shipSize = ship.getShipSize();
		//List<Square> occupiedSquares = getBoardOccupiedSquares();

		if(!ship.getKind().equals("MINESWEEPER") && !ship.getKind().equals("DESTROYER") && !ship.getKind().equals("BATTLESHIP")){
			return false;
		}

		for(Ship item: shipList){
			if(item.getShipSize() == ship.getShipSize()){
				return false;
			}
		}

		Ship newShip = new Ship(ship.getKind());
		List<Square> squares = new ArrayList<Square>();

		if (checkSquareOccupied(x, y, isVertical, shipSize)) return false;

		if (!squareIsValid(new Square(x, y))) {
			return false;
		}

		//Sets new ship if it is able to be placed
		if (setNewShip(x, y, isVertical, shipSize, newShip, squares)) return true;

		return false;
	}

	private boolean setNewShip(int x, char y, boolean isVertical, int shipSize, Ship newShip, List<Square> squares) {
		if (isVertical) {
			if (x + (shipSize - 1) <= 10) {
				// successful
				for (int i = 0; i < shipSize; i++) {
					squares.add(new Square(x + i, y));
					BoardoccupiedSquares.add(new Square(x + i, y));
				}

				newShip.setOccupiedSquares(squares);
				shipList.add(newShip);
				return true;
			}
		} else {
			if (y + (shipSize - 1) <= 'J') {
				//successful
				for (int i = 0; i < shipSize; i++) {
					squares.add(new Square(x, (char)(y + i)));
					BoardoccupiedSquares.add(new Square(x, (char)(y + i)));
				}

				newShip.setOccupiedSquares(squares);
				shipList.add(newShip);
				return true;
			}
		}
		return false;
	}

	private boolean checkSquareOccupied(int x, char y, boolean isVertical, int shipSize) {
		if(!isVertical){
			for(int i = 0; i < shipSize; i++) {
				for (Square occupied : BoardoccupiedSquares) {
					if (occupied.getRow() == x && occupied.getColumn() == (char)(y + i)) {
						return true;
					}
				}
			}
		}
		else{
			for(int i = 0; i < shipSize; i++) {
				for (Square occupied : BoardoccupiedSquares) {
					if (occupied.getRow() == x + i && occupied.getColumn() == y) {
						return true;
					}
				}
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
		Result result = new Result();
		AtackStatus attackStatus;
		Square square = new Square(x, y);

		result.setLocation(square);

		//Check for INVALID
		if (!squareIsValid(square)) {
			attackStatus = AtackStatus.INVALID;
			result.setResult(attackStatus);
			attackResult.add(result);
			return result;
		}

		if (checkAttackRedundant(x, y, result)) return result;

		//Check for HIT, SUNK, SURRENDER
		if (checkKnownValidAttack(x, y, result)) return result;

		//If not any other attack result then it is a miss
		attackStatus = AtackStatus.MISS;
		result.setResult(attackStatus);
		attackResult.add(result);
		return result;
	}

	private boolean checkKnownValidAttack(int x, char y, Result result) {
		AtackStatus attackStatus;
		for(Ship occupiedShip : shipList){
			for(Square occupied : occupiedShip.getOccupiedSquares()){
				if(x == occupied.getRow() && y == occupied.getColumn()){
					result.setShip(occupiedShip);
					occupiedShip.getOccupiedSquares().remove(occupied);
					if(occupiedShip.getOccupiedSquares().isEmpty() == true){
						shipList.remove(occupiedShip);
						if(shipList.isEmpty() == true){
							attackStatus = AtackStatus.SURRENDER;
						}
						else{
							attackStatus = AtackStatus.SUNK;
						}
					}
					else{
						attackStatus = AtackStatus.HIT;
					}
					result.setResult(attackStatus);
					attackResult.add(result);
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkAttackRedundant(int x, char y, Result result) {
		AtackStatus attackStatus;
		for(Result validSpot: attackResult){
			if(validSpot.getLocation().getRow() == x && validSpot.getLocation().getColumn() == y){
				attackStatus = AtackStatus.INVALID;
				result.setResult(attackStatus);
				attackResult.add(result);
				return true;
			}
		}
		return false;
	}

	private boolean squareIsValid(Square square){
		int x = square.getRow();
		char y = square.getColumn();

		int minX = 1;
		char minY = 'A';

		if(x < minX || x >= minX + this.getXDimension()){
			return false;
		}

		if(y < minY || y >= minY + this.getYDimension()){
			return false;
		}
		return true;
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

	//Sets the number of board squares in the x direction
	public void setXDimension(int x){
		this.xDimension = x;
	}

	public int getXDimension(){
		return this.xDimension;
	}

	//Sets the number of board squares in the y direction
	public void setYDimension(int y){
		this.yDimension = y;
	}

	public int getYDimension(){
		return this.yDimension;
	}
}
