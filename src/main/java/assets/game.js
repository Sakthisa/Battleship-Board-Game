var isSetup = true;
var placedShips = 0;
var game;
var shipType;
var vertical;
var count = 0;
var vertical = false;

function makeGrid(table, isPlayer) {
    for (i=0; i<10; i++) {
        let row = document.createElement('tr');
        for (j=0; j<10; j++) {
            let column = document.createElement('td');
            if(count >= 3 && isPlayer == true){

            }
            else if(count < 3 && isPlayer == false){

            }
            else{
                column.addEventListener("click", cellClick);
            }
            row.appendChild(column);
        }
        table.appendChild(row);
    }
}

function markHits(board, elementId, surrenderText) {
    var oldListener;

    board.attacks.forEach((attack) => {
        let className;
        if (attack.result === "MISS")
            className = "miss";
        else if (attack.result === "HIT" && ! document.getElementById(elementId).rows[attack.location.row - 1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.contains("sink"))
            className = "hit";
        else if (attack.result === "SUNK") {

            var square;
            for (square of attack.ship.occupiedSquares) {
                document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("sink");
                document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("hit")
            }
            return;

        }
        else if (attack.result === "SURRENDER"){
            alert(surrenderText);
            clearBoard();
         }
        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);
    });
    
    if (board.attacks.length > 0) {
        var result = board.attacks[board.attacks.length - 1];
        var html = "<div class='result'><span";
        var resultHTML = "<span class='attack-detail'><span class='";

        if (result.result === "HIT")
            resultHTML += "hitResult'>" + result.result + "</span>" + " " + result.location.row + result.location.column + "</span></div>";
        else if (result.result === "MISS")
            resultHTML += "missResult'>" + result.result + "</span>" + " " + result.location.row + result.location.column + "</span></div>";
        else if (result.result === "SUNK")
            resultHTML += "sunkResult'>" + result.result + "</span>" + " " + result.ship.kind + "</span></div>";
        if (elementId === "opponent") {
            html += " class='player-name'>PLAYER: </span>" + resultHTML;
            document.getElementById("player-results").insertAdjacentHTML("afterbegin", html);

        } else if (elementId === "player") {
            html += " class='opponent-name'>AI: </span>" + resultHTML;
            document.getElementById("opponent-results").insertAdjacentHTML("afterbegin", html);
        }
    }
}

function clearBoard(){
        location.reload();
}

function redrawGrid() {
    count++;
    Array.from(document.getElementById("opponent").childNodes).forEach((row) => row.remove());
    Array.from(document.getElementById("player").childNodes).forEach((row) => row.remove());
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    if (game === undefined) {
        return;
    }

    game.playersBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {
        document.getElementById("player").rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
    }));
    markHits(game.opponentsBoard, "opponent", "You won the game");
    markHits(game.playersBoard, "player", "You lost the game");
}

var oldListener;
function registerCellListener(f) {
    let el = document.getElementById("player");
    for (i=0; i<10; i++) {
        for (j=0; j<10; j++) {
            let cell = el.rows[i].cells[j];
            cell.removeEventListener("mouseover", oldListener);
            cell.removeEventListener("mouseout", oldListener);
            cell.addEventListener("mouseover", f);
            cell.addEventListener("mouseout", f);
        }
    }
    oldListener = f;
}

function cellClick() {
    let row = this.parentNode.rowIndex+1;
    let col = String.fromCharCode(this.cellIndex + 65);
    if (isSetup) {
        sendXhr("POST", "/place", {game: game, shipType: shipType, x: row, y: col, isVertical: vertical}, function(data) {
            game = data;


            redrawGrid();
            placedShips++;
            if (placedShips == 3) {
                isSetup = false;
                registerCellListener((e) => {});
                document.getElementById("place_battleship").style.display = "none";
                document.getElementById("is_vertical").style.display = "none";
                document.getElementById("place_destroyer").style.display = "none";
                document.getElementById("place_minesweeper").style.display = "none";
                document.getElementById("restart").style.visibility = "visible";
                document.getElementById("restart").addEventListener("click", function(e){
                        location.reload();
                });
                console.log(document.getElementsByClassName("container-header"));
                document.getElementsByClassName("container-header")[0].innerHTML = "ATTACK RESULTS";
                resultscontain = document.getElementById("results-container");
                resultscontain.innerHTML = "";
                playerResults = document.createElement('div');
                playerResults.setAttribute("id", "player-results");
                playerResults.setAttribute("class", "individual-results");
                opponentResults = document.createElement('div');
                opponentResults.setAttribute("id", "opponent-results" );
                opponentResults.setAttribute("class", "individual-results");
                resultscontain.appendChild(playerResults);
                resultscontain.appendChild(opponentResults);


            }
        });
    } else {
        sendXhr("POST", "/attack", {game: game, x: row, y: col}, function(data) {
            game = data;
            redrawGrid();
        })
    }
}

function sendXhr(method, url, data, handler) {
    var req = new XMLHttpRequest();
    req.addEventListener("load", function(event) {
        if (req.status != 200) {
            if (url === "/attack") {
                var row = data.x;
                var col = data.y.charCodeAt(0)-64;
                var opponentTable = document.getElementById("opponent");
                for(var i = 0; i  < 10; i++){
                    for(var j = 0; j < 10; j++){
                        if(i == row && j == col){
                            console.log(opponentTable.rows[i-1].cells[j-1].className);
                            if(opponentTable.rows[i-1].cells[j-1].className == "miss"){
                                opponentTable.rows[i-1].cells[j-1].setAttribute("class", "missError");
                            }
                            else if(opponentTable.rows[i-1].cells[j-1].className == "hit"){
                                opponentTable.rows[i-1].cells[j-1].setAttribute("class", "hitError");
                            }
                            else{
                                opponentTable.rows[i-1].cells[j-1].setAttribute("class", "sinkError");
                            }

                        }
                    }
                }

                console.log(row);
                console.log(col);
                var html = "<div class='result'><span";
                html += " class='player-name'>PLAYER: </span>" + "<span class='error'>INVALID ATTACK</span></div>";
                document.getElementById("player-results").insertAdjacentHTML("afterbegin", html);

                html ="<div class='result'><span"
                html += " class='opponent-name'>AI: </span>" + "<span class='error'>WAITING...</span></div>";;
                document.getElementById("opponent-results").insertAdjacentHTML("afterbegin", html);
            }
            else {
                var html = "<div class='result'><span";
                html += " class='player-name'>PLAYER: </span>" + "<span class='error'>INVALID SHIP PLACEMENT</span></div>";
                document.getElementById("player-results").insertAdjacentHTML("afterbegin", html);
            }
            return;
        }
        handler(JSON.parse(req.responseText));
    });
    req.open(method, url);
    req.setRequestHeader("Content-Type", "application/json");
    req.send(JSON.stringify(data));
}

function place(size) {
    return function() {
        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;
        let table = document.getElementById("player");
        for (let i=0; i<size; i++) {
            let cell;
            if(vertical) {
                let tableRow = table.rows[row+i];
                if (tableRow === undefined) {
                    // ship is over the edge; let the back end deal with it
                    break;
                }
                cell = tableRow.cells[col];
            } else {
                cell = table.rows[row].cells[col+i];
            }
            if (cell === undefined) {
                // ship is over the edge; let the back end deal with it
                break;
            }
            cell.classList.toggle("placed");
        }
    }
}

function initGame() {
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    document.getElementById("place_minesweeper").addEventListener("click", function(e) {
        shipType = "MINESWEEPER";
       registerCellListener(place(2));
    });
    document.getElementById("place_destroyer").addEventListener("click", function(e) {
        shipType = "DESTROYER";
       registerCellListener(place(3));
    });
    document.getElementById("place_battleship").addEventListener("click", function(e) {
        shipType = "BATTLESHIP";
       registerCellListener(place(4));
    });
    document.getElementById("is_vertical").addEventListener("click", function(e){
       document.getElementById("is_vertical").innerHTML = "Horizontal";
       if(vertical == true){
          vertical = false;
          document.getElementById("is_vertical").innerHTML = "Vertical";
       }
       else{
          vertical = true;
          document.getElementById("is_vertical").innerHTML = "Horizontal";
       }
    });
    sendXhr("GET", "/game", {}, function(data) {
        game = data;
    });

};