var isSetup = true;
var placedShips = 0;
var game;
var shipType;
var shipSize = 1;
var vertical;
var count = 0;
var vertical = false;

function makeGrid(table, isPlayer) {
    var thC = "<tr><td></td>";
    var thR;
    for (i = 0; i < 10; i++) {
        thC += "<th scope='col'>" + String.fromCharCode(65 + i) + "</th>";
        console.log(String.fromCharCode(65 + i));
    }
    thC += "</tr>";
    table.insertAdjacentHTML("beforeend", thC);

    for (i=0; i<10; i++) {
        let row = document.createElement('tr');
        let th = "<th scope='row'>" + i + "</th>";
        row.insertAdjacentHTML("afterbegin", th);
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
    if(isPlayer == true){
            var caption = document.createElement('caption');
            caption.innerHTML = "PLAYER";
            table.appendChild(caption);
    }
    else{
            var caption = document.createElement('caption');
            caption.innerHTML = "OPPONENT";
            table.appendChild(caption);
    }
}

function markHits(board, elementId, surrenderText) {
    var oldListener;

    board.attacks.forEach((attack) => {
        let className;
        if (attack.result === "MISS")
            if(elementId === "opponent"){
                className = "miss";
            }
            else{
                className = "missPlayer"
            }

        else if (attack.result === "HIT" && ! document.getElementById(elementId).rows[attack.location.row - 1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.contains("sink"))
            if(elementId === "opponent"){
                className = "hit";
            }
            else{
                className = "hitPlayer"
            }
        else if (attack.result === "SUNK") {

            var square;
            if(elementId === "opponent"){
                for (square of attack.ship.occupiedSquares) {
                    document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("sink");
                    document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("hit")
                }
            }
            else{
                for (square of attack.ship.occupiedSquares) {
                    document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("sinkPlayer");
                    document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("hitPlayer")
                }
            }

            return;

        }
        else if (attack.result === "SURRENDER"){
            //alert(surrenderText);
            //clearBoard();
            if(surrenderText == false){
                console.log(document.getElementsByClassName("win-message"));
                document.getElementsByClassName("win-message")[0].innerHTML = "Opponent won the game! You can now view your results or exit the modal to restart and play a new one.";
            }
            displayVictoryDialogue();

            return;
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
        setTimeout(function(){
            var opponentTable = document.getElementById("opponent");
            //console.log(opponentTable);
             for(var i = 1; i  < 11; i++){
                      for(var j = 1; j < 11; j++){


                          if(opponentTable.rows[i].cells[j].className === "miss"){
                               opponentTable.rows[i].cells[j].setAttribute("class", "missError");
                           }
                           else if(opponentTable.rows[i].cells[j].className === "hit"){
                               opponentTable.rows[i].cells[j].setAttribute("class", "hitError");
                           }
                           else if(opponentTable.rows[i].cells[j].className === "sink"){
                                opponentTable.rows[i].cells[j].setAttribute("class", "sinkError");
                           }


                      }
             }



        }, 215);
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
    markHits(game.opponentsBoard, "opponent", true);
    markHits(game.playersBoard, "player", false);
}

var oldListener;
function registerCellListener(f) {
    let el = document.getElementById("player");
    for (i=1; i<11; i++) {
        for (j=1; j<11; j++) {
            let cell = el.rows[i].cells[j];
            cell.removeEventListener("mouseover", oldListener);
            cell.removeEventListener("mouseout", oldListener);
            cell.addEventListener("mouseover", f);
            cell.addEventListener("mouseout", f);
        }
    }
    oldListener = f;
}

// function errorPlace(var event) {
//
// }


// Modal handlers from W3 Schools https://www.w3schools.com/howto/howto_css_modals.asp
// Get the modal
var endGame = 0;
var modal = document.getElementById('myModal');

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

var span2 = document.getElementsByClassName("close")[1];


// When the user clicks the button, open the modal
btn.onclick = function() {
    modal = document.getElementById('myModal');
    modal.style.display = "block";
}


function displayVictoryDialogue(){
    endGame = 1;
    modal = document.getElementById('endModal');
    modal.style.display = "block";
    console.log(document.getElementsByClassName("win-message"));
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    if(endGame == 1){
        location.reload();
    }
    modal.style.display = "none";
}

span2.onclick = function() {
     if(endGame == 1){
            location.reload();
     }
    modal.style.display = "none";
}


// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {

    if (event.target == modal) {
        modal.style.display = "none";
        if(endGame == 1){
            location.reload();
        }
    }
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
                document.getElementsByClassName("buttonHolder")[0].children.item(3).setAttribute("id", "is_vertical")
                document.getElementsByClassName("buttonHolder")[0].children.item(3).innerHTML = "Vertical";

                document.getElementById("place_battleship").style.display = "none";
                document.getElementById("is_vertical").style.display = "none";
                document.getElementById("place_destroyer").style.display = "none";
                document.getElementById("place_minesweeper").style.display = "none";
                document.getElementById("restart").style.visibility = "visible";
                document.getElementById("restart").addEventListener("click", function(e){
                        location.reload();
                });

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
            if (row === 0 || col === 0) {
                break;
            }
            if(vertical) {
                let tableRow = table.rows[row+i];

                if (tableRow === 0) {
                    break;
                }
                if (tableRow === undefined) {
                    // ship is over the edge; let the back end deal with it
                    for (let j = (row + i - 1); j >= row; j--) {
                        cell = table.rows[j].cells[col];
                        cell.classList.toggle("error-place");
                    }
                    break;
                }
                cell = tableRow.cells[col];
                if (cell.classList.contains('occupied')) {
                    for (let j = (row + i - 1); j >= row; j--) {
                        cell = table.rows[j].cells[col];
                        cell.classList.toggle("error-place");
                    }
                    break;
                }
            } else {
                cell = table.rows[row].cells[col+i];
            }

            if (cell === undefined) {
                // ship is over the edge; let the back end deal with it
                for (let j = (col + i - 1); j >= col; j--) {
                    cell = table.rows[row].cells[j];
                    cell.classList.toggle("error-place");
                }
                break;
            } else if (cell.classList.contains('occupied')) {
                for (let j = (col + i - 1); j >= col; j--) {
                    cell = table.rows[row].cells[j];
                    cell.classList.toggle("error-place");
                }
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
        shipSize = 2;
       registerCellListener(place(2));
    });
    document.getElementById("place_destroyer").addEventListener("click", function(e) {
        shipType = "DESTROYER";
        shipSize = 3;
       registerCellListener(place(3));
    });
    document.getElementById("place_battleship").addEventListener("click", function(e) {
        shipType = "BATTLESHIP";
        shipSize = 4;
       registerCellListener(place(4));
    });
    document.getElementById("is_vertical").addEventListener("click", function(e){
       //document.getElementById("is_vertical").innerHTML = "Horizontal";
       if(vertical == true){
          vertical = false;
          document.getElementById("is_horizontal").innerHTML = "Vertical";
          document.getElementById("is_horizontal").setAttribute("id", "is_vertical");
       }
       else{
          vertical = true;
          document.getElementById("is_vertical").innerHTML = "Horizontal";
          document.getElementById("is_vertical").setAttribute("id", "is_horizontal");

       }
    });

    sendXhr("GET", "/game", {}, function(data) {
        game = data;
    });


};