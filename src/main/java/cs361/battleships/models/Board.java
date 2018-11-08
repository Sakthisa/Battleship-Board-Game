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
	private	int shipsSunk;
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		this.BoardoccupiedSquares = new ArrayList<Square>();
		this.shipList = new ArrayList<Ship>();
		this.attackResult = new ArrayList<Result>();
		this.shipsSunk = 0;
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

		// Check for anomaly where user can edit ship type somehow. Make sure that ship type is either minesweeper, destroyer, or battleship
//		if(!ship.getKind().equals("MINESWEEPER") && !ship.getKind().equals("DESTROYER") && !ship.getKind().equals("BATTLESHIP")){
//			return false;
//		}

		// Check for the user trying to place multiple of the same ship type
		for(Ship item : shipList){
			if(item.getShipSize() == ship.getShipSize()){
				return false;
			}
		}

		Ship newShip;
		newShip = CreateShip(ship);
		List<Square> squares = new ArrayList<Square>();

		if(!squareIsValid(new Square(x, y))){
			return false;
		}

		if (checkSquareOccupied(x, y, isVertical, shipSize)) return false;

		//Sets new ship if it is able to be placed
		return setNewShip(x, y, isVertical, shipSize, newShip, squares);

	}

	private Ship CreateShip(Ship ship) {
		Ship newShip;
		if (ship.getKind().equals("MINESWEEPER")) {
			newShip = new Minesweeper();
		} else if (ship.getKind().equals("DESTROYER")) {
			newShip = new Destroyer();
		} else {
			newShip = new Battleship();
		}
		return newShip;
	}

	private boolean setNewShip(int x, char y, boolean isVertical, int shipSize, Ship newShip, List<Square> squares) {

		if (isVertical) {
			// If it is within the row bounds, then it is a successful placement
			if ((x + (shipSize - 1) <= 11)) {
				placeShipVertical(x, y, isVertical, shipSize, newShip, squares);
				newShip.setOccupiedSquares(squares);
				shipList.add(newShip);
				return true;
			}
		} else {
			// If it is within the column bounds, then it is a successful placement
			if ((y + (shipSize - 1) <= 'K')) {
				//successful
				placeShipHorizontal(x, y, shipSize, newShip, squares);

				newShip.setOccupiedSquares(squares);
				shipList.add(newShip);
				return true;
			}
		}
		return false;
	}

	private void placeShipVertical(int x, char y, boolean isVertical, int shipSize, Ship newShip, List<Square> squares) {
		for (int i = 0; i < shipSize; i++) {
			// place the CQ in a single square, here choose second square placed
			if(newShip.getShipSize() - i == 2) {
				squares.add(new CaptainQuarter(x + i, y));
				BoardoccupiedSquares.add(new CaptainQuarter(x + i, y));
			}
			else{
				squares.add(new Square(x + i, y));
				BoardoccupiedSquares.add(new Square(x + i, y));
			}
		}
	}

	private void placeShipHorizontal(int x, char y, int shipSize, Ship newShip, List<Square> squares) {
		for (int i = 0; i < shipSize; i++) {
			// place the CQ in a single square, here choose second square placed
			if(newShip.getShipSize() - i == 2) { // think about using rand to place the ship
				squares.add(new CaptainQuarter(x, (char) (y + i)));
				BoardoccupiedSquares.add(new CaptainQuarter(x, (char) (y + i))); // consider not using new a second time
			}
			else{ // normal square
				squares.add(new Square(x, (char)(y + i)));
				BoardoccupiedSquares.add(new Square(x, (char)(y + i)));
			}
		}
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

		//Check for INVALID x and y coordinates
		if (!squareIsValid(square)) {
			attackStatus = AtackStatus.INVALID;
			result.setResult(attackStatus);
			attackResult.add(result);
			return result;
		}

		//Check for HIT, SUNK, SURRENDER
		if (checkKnownValidAttack(x, y, result)) return result;

		//If not any other attack result then it is a miss
		attackStatus = AtackStatus.MISS;
		result.setResult(attackStatus);
		attackResult.add(result);
		return result;
	}

	public Result radarAttack(int x, char y) {
		Result result = new Result();
		AtackStatus attackStatus;
		Square square = new Square(x, y);

		result.setLocation(square);

		//Check for INVALID x and y coordinates
		if (!squareIsValid(square)) {
			attackStatus = AtackStatus.INVALID;
			result.setResult(attackStatus);
			attackResult.add(result);
			return result;
		}

		// If attacking with radar...
		// First check to see if a ship has been sunk
		if (!shipHasBeenSunk(result)) return result;
		// Check to see if two radars have already been placed
		if (checkTooManyRadars(result)) return result;

		attackStatus = AtackStatus.RADAR;
		result.setResult(attackStatus);
		attackResult.add(result);
		return result;
	}

	private boolean shipHasBeenSunk(Result result) {
		for (Result res : attackResult) {
			if (res.getResult() == AtackStatus.SUNK) {
				return true;
			}
		}
		result.setResult(AtackStatus.INVALID);
		return false;
	}
	
	private boolean checkTooManyRadars(Result result) {
		AtackStatus attackStatus;
		int nRadars = 0;

		for (Result res : attackResult) {
			if (res.getResult() == AtackStatus.RADAR) nRadars++;
		}

		if (nRadars >= 2) {
			result.setResult(AtackStatus.INVALID);
			return true;
		}
		return false;
	}

	// This method is a behemoth because of all of the checks, these could be factored into class methods from other classes
	private boolean checkKnownValidAttack(int x, char y, Result result) {
		AtackStatus attackStatus;
		//For every ship check its occupied squares, if there is a occupied square delete it and check if the occupied square list is empty. If it is empty remove the ship and check if the ship
		//list is empty. If the ship list is empty send an attack status of SURRENDER, if the ship list is not empty send an attack status of SUNK. Else it is just a normal hit.
		for(Ship occupiedShip : shipList){
			// only execute if ship is not sunk
			if (!occupiedShip.getSunk()) {
				for(Square occupied : occupiedShip.getOccupiedSquares()) {
					if (x == occupied.getRow() && y == occupied.getColumn()) {
						return AttackOccupied(result, occupiedShip, occupied);

					}
				}
			} else {
				for(Square occupied : occupiedShip.getOccupiedSquares()) {
					if (SunkResult(x, y, result, occupiedShip, occupied)) return true;
				}

			}
		}
		return false;
	}

	private boolean SunkResult(int x, char y, Result result, Ship occupiedShip, Square occupied) {
		if (x == occupied.getRow() && y == occupied.getColumn()) {
			result.setShip(occupiedShip);
			result.setResult(AtackStatus.SUNK);
			attackResult.add(result);
			return true;
		}
		return false;
	}

	private boolean AttackOccupied(Result result, Ship occupiedShip, Square occupied) {
		AtackStatus attackStatus;
		ResultShipSet(result, occupiedShip, occupied);

		occupied.setTimesHit();
		attackStatus = AtackStatus.HIT;
		if(isCqSink(occupied)){
			attackStatus = CqOccupiedAttack(occupiedShip);
		}
		 else{
			if(occupied.getType().equals("CQ") && occupied.getTimesHit() == 1){
				attackStatus = AtackStatus.MISS;
			}
		}
		result.setResult(attackStatus);
		attackResult.add(result);
		return true;
	}

	private AtackStatus CqOccupiedAttack(Ship occupiedShip) {
		AtackStatus attackStatus;
		occupiedShip.setSunk(true);
		setShipsSunk();
		attackStatus = AtackStatus.SUNK;
		if(this.shipsSunk == 3){
			attackStatus = AtackStatus.SURRENDER;
		}
		return attackStatus;
	}

	private void ResultShipSet(Result result, Ship occupiedShip, Square occupied) {
		result.setShip(occupiedShip);
		// If square has already been hit max times, then don't add it to ship hit count
		if (occupied.getTimesHit() < occupied.getMaxHits()) {
			occupiedShip.setHit();
		}
		if(occupiedShip.getKind().equals("MINESWEEPER")){
			occupied.setMaxHits(1);
		}
	}

	private boolean isCqSink(Square occupied) {
		return occupied.getType().equals("CQ") && occupied.getTimesHit() == occupied.getMaxHits();
	}

	private void setShipsSunk() {
		this.shipsSunk++;
	}
	public int getShipsSunk() {
		return shipsSunk;
	}

	private boolean checkAttackRedundant(int x, char y, Result result) {
		//Checks if a square has already been attacked
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
		//Checks for a valid square in the 10x10 board rows denoted (1-10) cols (A-J)
		int x = square.getRow();
		char y = square.getColumn();

		int minX = 1;
		char minY = 'A';

		if(x < minX || x > minX + this.getXDimension()){
			return false;
		}

		if(y < minY || y > minY + this.getYDimension()){
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
