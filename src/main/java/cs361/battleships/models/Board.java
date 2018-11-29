package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private List<Square> BoardoccupiedSquares;
	private List<Ship> shipList;
	private List<Result> attackResult;
	private int xDimension;
	private int yDimension;
	private	int shipsSunk;
	private int nRadars;
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		this.BoardoccupiedSquares = new ArrayList<Square>();
		this.shipList = new ArrayList<Ship>();
		this.attackResult = new ArrayList<Result>();
		this.shipsSunk = 0;
		this.nRadars = 0;
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

		//System.out.println(x);
		//System.out.println(y);
		//System.out.println("\n");
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

		if (checkSquareOccupied(x, y, isVertical, shipSize, newShip)) return false;

		//Sets new ship if it is able to be placed
		return setNewShip(x, y, isVertical, shipSize, newShip, squares, false);

	}

	public boolean placeSubShip(Ship ship, int x, char y, boolean isVertical, boolean isSubmerged) {
		int shipSize = ship.getShipSize();
		//List<Square> occupiedSquares = getBoardOccupiedSquares();

		// Check for the user trying to place multiple of the same ship type
		for(Ship item : shipList){
			if(item.getShipSize() == ship.getShipSize()){
				return false;
			}
		}

		Ship newShip;
		newShip = CreateShip(ship);
		List<Square> squares = new ArrayList<Square>();

		if (isSubmerged)
			newShip.setUnderwater();

		if(!squareIsValid(new Square(x, y))){
			return false;
		}

		//Sets new ship if it is able to be placed
		return setNewShip(x, y, isVertical, shipSize, newShip, squares, isSubmerged);

	}

	private Ship CreateShip(Ship ship) {
		Ship newShip;
		if (ship.getKind().equals("MINESWEEPER")) {
			newShip = new Minesweeper();
		} else if (ship.getKind().equals("DESTROYER")) {
			newShip = new Destroyer();
		} else if(ship.getKind().equals("BATTLESHIP")){
			newShip = new Battleship();
		}
		else{
			newShip = new Submarine();
		}
		return newShip;
	}

	private boolean setNewShip(int x, char y, boolean isVertical, int shipSize, Ship newShip, List<Square> squares, boolean isSubmerged) {
		if(newShip.getKind().equals("SUBMARINE")){
			shipSize = 4;
			if (SubOutBounds(x, y, isVertical)) return false;
		}
		if (isVertical) {
			// If it is within the row bounds, then it is a successful placement
			if ((x + (shipSize - 1) <= 11 && x + (shipSize - 1) >= 1)) {
				placeShipVertical(x, y, isVertical, shipSize, newShip, squares, isSubmerged);
				newShip.setOccupiedSquares(squares);
				newShip.setInitcol(y);
				newShip.setInitrow(x);
				newShip.setVertical(isVertical);
                shipList.add(newShip);
				return true;
			}
		} else {
			// If it is within the column bounds, then it is a successful placement
			if ((y + (shipSize - 1) <= 'K') && (y + (shipSize - 1) >= 'B')) {
				//successful
				placeShipHorizontal(x, y, shipSize, newShip, squares, isSubmerged);
				newShip.setOccupiedSquares(squares);
                newShip.setInitcol(y);
                newShip.setInitrow(x);
                newShip.setVertical(isVertical);
                shipList.add(newShip);
				return true;
			}
		}
		return false;
	}

	private void placeShipVertical(int x, char y, boolean isVertical, int shipSize, Ship newShip, List<Square> squares, boolean isSubmerged) {
		for (int i = 0; i < shipSize; i++) {
			// place the CQ in a single square, here choose second square placed
			if((newShip.getShipSize() - i == 2 && !newShip.getKind().equals("SUBMARINE")) || (newShip.getKind().equals("SUBMARINE") && i == 3)) {
				squares.add(new CaptainQuarter(x + i, y));
				if(isSubmerged == false) {
					BoardoccupiedSquares.add(new CaptainQuarter(x + i, y));
				}
			}
			else{
				if(newShip.getKind().equals("SUBMARINE") && i == 2){
					squares.add(new Square(x+i, (char)(y + 1)));
					squares.add(new Square(x + i, (char)(y)));
                    if(isSubmerged == false){
                        BoardoccupiedSquares.add(new Square(x + i, (char)(y + 1)));
                    }
				}
				if(newShip.getKind().equals("SUBMARINE") && (i == 4)){
				}
				else{
					squares.add(new Square(x + i, y));
				}

				if(isSubmerged == false){
					BoardoccupiedSquares.add(new Square(x + i, y));
				}
			}
		}
	}

	private void placeShipHorizontal(int x, char y, int shipSize, Ship newShip, List<Square> squares, boolean isSubmerged) {
		for (int i = 0; i < shipSize; i++) {
			// place the CQ in a single square, here choose second square placed
			if((newShip.getShipSize() - i == 2 && !newShip.getKind().equals("SUBMARINE")) || (i == 3 && newShip.getKind().equals("SUBMARINE"))) { // think about using rand to place the ship
				squares.add(new CaptainQuarter(x, (char) (y + i)));
				if(isSubmerged == false){
					BoardoccupiedSquares.add(new CaptainQuarter(x, (char) (y + i))); // consider not using new a second time
				}
			}
			else{ // normal square
				if(newShip.getKind().equals("SUBMARINE") && i == 2){
					squares.add(new Square(x-1, (char)(y + i)));
					squares.add(new Square(x, (char)(y + i)));
                    if(isSubmerged == false){
                        BoardoccupiedSquares.add(new Square(x-1 , (char)(y + i)));
                    }
				}
				else if(newShip.getKind().equals("SUBMARINE") && (i == 4)){ //TODO: What is this?
				}
				else{
					squares.add(new Square(x, (char)(y + i)));
				}
				if(isSubmerged == false){
					BoardoccupiedSquares.add(new Square(x , (char)(y + i)));
				}
			}
		}
	}

	private boolean checkSquareOccupied(int x, char y, boolean isVertical, int shipSize, Ship newShip) {
		if(newShip.getKind().equals("SUBMARINE")){
			shipSize = 4;
			if (SubOutBounds(x, y, isVertical)) return true;
		}
		if(!isVertical){
			for(int i = 0; i < shipSize; i++) {
				for (Square occupied : BoardoccupiedSquares) {
					if(newShip.getKind().equals("SUBMARINE") && i == 2 && occupied.getRow() == x-1 && occupied.getColumn() == (char)(y + i)){
						return true;
					}
					if (occupied.getRow() == x && occupied.getColumn() == (char)(y + i)) {
						return true;
					}
				}
			}
		}
		else{
			for(int i = 0; i < shipSize; i++) {
				for (Square occupied : BoardoccupiedSquares) {
					if(newShip.getKind().equals("SUBMARINE") && i == 2 && occupied.getRow() == x+i && occupied.getColumn() == (char)(y + 1)){
						return true;
					}
					if (occupied.getRow() == x + i && occupied.getColumn() == y) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean SubOutBounds(int x, char y, boolean isVertical) {
		//System.out.println(x);
		//System.out.println(y);
		if(isVertical){
			if((char)(y+1) == 'L'){
				return true;
			}
		}
		else{
			if(x - 1 <= 1) {
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
		setnRadars();
		return result;
	}

	private boolean shipHasBeenSunk(Result result) {
		if (getShipsSunk() > 0) return true;

		result.setResult(AtackStatus.INVALID);
		return false;
	}
	
	private boolean checkTooManyRadars(Result result) {
		if (getnRadars() >= 2) {
			result.setResult(AtackStatus.INVALID);
			return true;
		}
		return false;
	}

	// This method is a behemoth because of all of the checks, these could be factored into class methods from other classes
	private boolean checkKnownValidAttack(int x, char y, Result result) {
		boolean success = false;
		boolean sunk;
		//For every ship check its occupied squares, if there is a occupied square delete it and check if the occupied square list is empty. If it is empty remove the ship and check if the ship
		//list is empty. If the ship list is empty send an attack status of SURRENDER, if the ship list is not empty send an attack status of SUNK. Else it is just a normal hit.
		for(Ship occupiedShip : shipList) {
			if (occupiedShip.getUnderwater() && getShipsSunk() == 0) {
				continue;
			}
			// only execute if ship is not sunk

			if (!occupiedShip.isSunk()) {
				for(Square occupied : occupiedShip.getOccupiedSquares()) {
					if (x == occupied.getRow() && y == occupied.getColumn()) {
						sunk = AttackOccupied(result, occupiedShip, occupied);
						if (getShipsSunk() == 0 || (getShipsSunk() == 1 && sunk)) {
							attackResult.add(result);
							return true;
						}
						success = true;
						break;
					}
				}
			} else {
				for(Square occupied : occupiedShip.getOccupiedSquares()) {
					if (SunkResult(x, y, result, occupiedShip, occupied)) {
						success = true;
						break;
					}
				}
			}
		}
		if (success) {
			attackResult.add(result);
		}
		return success;
	}

	private boolean SunkResult(int x, char y, Result result, Ship occupiedShip, Square occupied) {
		if (x == occupied.getRow() && y == occupied.getColumn()) {
			result.setShip(occupiedShip);
			result.setResult(AtackStatus.SUNK);
			return true;
		}
		return false;
	}

	private boolean AttackOccupied(Result result, Ship occupiedShip, Square occupied) {
		AtackStatus attackStatus;
		boolean sunk;
		ResultShipSet(result, occupiedShip, occupied);

		occupied.setTimesHit();
		attackStatus = AtackStatus.HIT;
		sunk = isCqSink(occupied);
		if(sunk) {
			attackStatus = CqOccupiedAttack(occupiedShip);
		}
		else{
			if(occupied.getType().equals("CQ") && occupied.getTimesHit() == 1){
				attackStatus = AtackStatus.MISS;
			}
		}
		result.setResult(attackStatus);
		return sunk;
	}

	private AtackStatus CqOccupiedAttack(Ship occupiedShip) {
		AtackStatus attackStatus;
		occupiedShip.setSunk(true);
		setShipsSunk();
		attackStatus = AtackStatus.SUNK;
		if(getShipsSunk() == 4){
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
	private void setnRadars() {this.nRadars++; }
	public int getnRadars() { return nRadars; }
	public int getShipsSunk() {
		return shipsSunk;
	}

	private boolean checkAttackRedundant(int x, char y, Result result) {
		//Checks if a square has already been attacked
		AtackStatus attackStatus;
		for (Result validSpot : attackResult) {
			if (validSpot.getLocation().getRow() == x && validSpot.getLocation().getColumn() == y) {
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


		int minX = 2;

		char minY = 'B';

		//System.out.println("X: " + this.getXDimension());
		//System.out.println("Y: " + this.getYDimension());
		if(x < minX || x > minX + this.getXDimension() - 1){
			return false;
		}

		if(y < minY || y > minY + this.getYDimension() - 1){
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

//	public void moveShipsWestold(){
//		boolean[][] squares = new boolean[xDimension][yDimension];
//		int [] xdir = new int[xDimension];
//		int [] ydir = new int[yDimension];
//		int j = 0;
//		int k = 0;
//		// iterate through the
//		for(int x : xdir){
//			for(int y : ydir){
//				if(squares[j][k]){continue;}
//				if(j == 0){break;} // leave the inner for loop
//				if(squareExists((char) (j+'A'), k) && !squares[j][k]){
//					if(squareExists((char) (j - 1 + 'A'), k) && !squares[j][k]){continue;}
//					Square translate = getOccupiedSquare((char) (j+'A'), k);
//					translate.setColumn((char)(translate.getColumn() - 1));
//					squares[j][k] = true;
//				}
//
//				k++;
//			}
//			k = 0;
//			j++;
//		}
//		j = 0;
//		k = 0;
//		for(boolean [] row : squares){
//			for(boolean idx : row){
//				if(!idx){
//					k++;
//					continue;
//				}
//				boolean removed = false;
//				for(Result result : attackResult){
//					if(result.getLocation().getColumn() == (char)(k+'A') && result.getLocation().getRow() == j){
//						attackResult.remove(result);
//						removed = true;
//					}
//					if(removed){
//						attack(j, (char)(k - 1));
//					}
//				}
//				k++;
//			}
//			k = 0;
//			j++;
//		}
//		j = 0;
//		k = 0;
//
//		for(boolean [] row : squares){
//			for(boolean idx : row){
//				if(!idx){
//					k++;
//					continue;
//				}
//				for(Ship ship : shipList){
//					if(ship.containsSquare(j, (char) (k+'A'))){
//						for(Square square : ship.getOccupiedSquares()){
//							squares[square.getRow()][square.getColumn() - 'A'] = false;
//						}
//						ship.moveLeft( xDimension, (char)('A'+yDimension));
//					}
//				}
//				k++;
//			}
//			k = 0;
//			j++;
//		}
//	}

    private Ship westernMostShip(List <Ship> newShipList){
	    Ship leftmost = null;
	    char max = (char) ('A' + xDimension);
	    for(Ship ship : shipList){
	    	boolean isAdded = false;
	    	for(Ship added : newShipList){
	    		if(ship == added){isAdded = true;}
			}
			if(isAdded){continue;}
	        for(Square square : ship.getOccupiedSquares()){
	            if(square.getColumn() <= max){
	                leftmost = ship;
	                max = square.getColumn();
                }
            }
        }
		System.out.println(leftmost);
        return leftmost;
    }

	private Ship easternMostShip(List <Ship> newShipList){
		Ship rightmost = null;
		char min = 'B';
		for(Ship ship : shipList){
			boolean isAdded = false;
			for(Ship added : newShipList){
				if(ship == added){isAdded = true;}
			}
			if(isAdded){continue;}
			for(Square square : ship.getOccupiedSquares()){
				if(square.getColumn() >= min){
					rightmost = ship;
					min = square.getColumn();
				}
			}
		}
		System.out.println(rightmost);
		return rightmost;
	}

	private Ship northernMostShip(List <Ship> newShipList){
		Ship northernmost = null;
		int min = 11;
		for(Ship ship : shipList){
			boolean isAdded = false;
			for(Ship added : newShipList){
				if(ship == added){isAdded = true;}
			}
			if(isAdded){continue;}
			for(Square square : ship.getOccupiedSquares()){
				if(square.getRow() <= min){
					northernmost = ship;
					min = square.getRow();
				}
			}
		}
		return northernmost;
	}

	private Ship southernMostShip(List <Ship> newShipList){
		Ship southernmost = null;
		int max = 2;
		for(Ship ship : shipList){
			boolean isAdded = false;
			for(Ship added : newShipList){

				if(ship == added){isAdded = true;}
			}
			if(isAdded){continue;}
			for(Square square : ship.getOccupiedSquares()){
				if(square.getRow() >= max){
					southernmost = ship;
					max = square.getRow();
				}
			}
		}
		return southernmost;
	}


	public boolean moveShips(String direction){
		List <Ship> newShipList = new ArrayList<>();
		Ship tempShip;
		boolean underWater = false;

		for(Ship ship : shipList){
			switch(direction) {
				case("east"):
					tempShip = easternMostShip(newShipList);
					if(tempShip.getKind().equals("SUBMARINE") && tempShip.getUnderwater()){
						underWater = true;
					}
					break;
				case("west"):
					tempShip = westernMostShip(newShipList);
					if(tempShip.getKind().equals("SUBMARINE") && tempShip.getUnderwater()){
						underWater = true;
					}
					break;
				case("north"):
					tempShip = northernMostShip(newShipList);
					if(tempShip.getKind().equals("SUBMARINE") && tempShip.getUnderwater()){
						System.out.println("SHIPS: " + tempShip.getKind());
						underWater = true;
					}
					break;
				case("south"):
					tempShip = southernMostShip(newShipList);
					if(tempShip.getKind().equals("SUBMARINE") && tempShip.getUnderwater()){
						underWater = true;
					}
					break;
				default:
					return false;
			}
			newShipList.add(tempShip);
		}
		this.shipList = new ArrayList<Ship>();
		BoardoccupiedSquares = new ArrayList<Square>();
		for(Ship ship : newShipList){
			if (ship.getKind().equals("MINESWEEPER")) {
				tempShip = new Minesweeper();
			} else if (ship.getKind().equals("DESTROYER")) {
				tempShip = new Destroyer();
			} else if (ship.getKind().equals("BATTLESHIP")){
				tempShip = new Battleship();
			}
			else{
				tempShip = new Submarine();
			}
			switch(direction) {
				case("east"):
					if(tempShip.getKind().equals("SUBMARINE") && underWater){
						System.out.println("UNDERWATER");
						if(placeSubShip(tempShip, ship.getInitrow(), (char)(ship.getInitcol() + 1), ship.isVertical(), true)){

						}
						else{
							placeSubShip(tempShip, ship.getInitrow(), (ship.getInitcol()), ship.isVertical(), true);
						}
					}
					else{
						if(placeShip(tempShip, ship.getInitrow(), (char)(ship.getInitcol() + 1), ship.isVertical())){

						}
						else{
							placeShip(tempShip, ship.getInitrow(), (ship.getInitcol()), ship.isVertical());
						}
					}
					break;
				case("west"):
					if(tempShip.getKind().equals("SUBMARINE") && underWater){
						if (placeSubShip(tempShip, ship.getInitrow(), (char) (ship.getInitcol() - 1), ship.isVertical(),true)) {

						}
						else {
							placeSubShip(tempShip, ship.getInitrow(), (ship.getInitcol()), ship.isVertical(), true);
					}
					}
					else{
						if (placeShip(tempShip, ship.getInitrow(), (char) (ship.getInitcol() - 1), ship.isVertical())) {

						}
						else {
							placeShip(tempShip, ship.getInitrow(), (ship.getInitcol()), ship.isVertical());
						}
					}
					break;
				case("north"):
					if(tempShip.getKind().equals("SUBMARINE") && underWater){
						if(placeSubShip(tempShip, ship.getInitrow()-1, (ship.getInitcol()), ship.isVertical(), true)){

						}
						else{
							placeSubShip(tempShip, ship.getInitrow(), (ship.getInitcol()), ship.isVertical(), true);
						}
					}
					else{
						if(placeShip(tempShip, ship.getInitrow()-1, (ship.getInitcol()), ship.isVertical())){

						}
						else{
							placeShip(tempShip, ship.getInitrow(), (ship.getInitcol()), ship.isVertical());
						}
					}
					break;
				case("south"):
					if(tempShip.getKind().equals("SUBMARINE") && underWater){
						if(placeSubShip(tempShip, ship.getInitrow()+1, (ship.getInitcol()), ship.isVertical(), true)){

						}
						else{
							placeSubShip(tempShip, ship.getInitrow(), (ship.getInitcol()), ship.isVertical(), true);
						}
					}
					else{
						if(placeShip(tempShip, ship.getInitrow()+1, (ship.getInitcol()), ship.isVertical())){

						}
						else{
							placeShip(tempShip, ship.getInitrow(), (ship.getInitcol()), ship.isVertical());
						}
					}
					break;
				default:
					return false;
			}
		}
		return true;
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
