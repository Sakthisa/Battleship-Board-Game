

var isSetup = true;
var placedShips = 0;
var game;
var shipType;
var shipSize = 1;
var submerged = false;
var count = 0;
var vertical = false;
var isRadar = false;
var isFleet = false;
var fleetType = "";
var numSunk = 0;
var numSunkOpp = 0;
var radarsUsed = 0;
var fleetsUsed = 0;

function containsCQ(board, row, col) {

    if (board != null) {
        for (sq of board.boardOccupiedSquares) {
            if (sq.type[0] === 'C' && row === sq.row && col === sq.column.charCodeAt(0) - 'A'.charCodeAt(0)) {
                return true;
            }
        }
    }
    return false;
}

function makeGrid(table, isPlayer) {
    var thC = "<tr><th></th>";

    for (let i = 0; i < 10; i++) {
        thC += "<th scope='col'>" + String.fromCharCode(65 + i) + "</th>";
    }
    thC += "</tr>";
    table.insertAdjacentHTML("beforeend", thC);

    for (let i = 0; i < 10; i++) {
        let row = document.createElement('tr');
        let th = "<th scope='row'>" + (i + 1) + "</th>";
        row.insertAdjacentHTML("afterbegin", th);
        for (j = 0; j < 10; j++) {

            let column = document.createElement('td');
            if (count >= 4 && isPlayer == true) {

            }
            else if (count < 4 && isPlayer == false) {

            }
            else {
                column.addEventListener("click", cellClick);
            }
            row.appendChild(column);
        }
        table.appendChild(row);
    }
    //Create table headers
    if (isPlayer == true) {
        var caption = document.createElement('caption');
        caption.innerHTML = "PLAYER";
        table.appendChild(caption);
    }
    else {
        var caption = document.createElement('caption');
        caption.innerHTML = "OPPONENT";
        table.appendChild(caption);
    }
}

function markHits(board, elementId, surrenderText) {
    board.attacks.forEach((attack) => {
        // Remove the radar class to start so that if a user attacked a spot within the radar, it would appear over the grey square
        document.getElementById(elementId).rows[attack.location.row - 1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("radar-square");
        document.getElementById(elementId).rows[attack.location.row - 1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("found");
        let result;
        let i = 0;
        for (result of attack.results) {
            if (result === "MISS") {
                document.getElementById(elementId).rows[attack.location.row - 1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("miss");
                document.getElementById(elementId).rows[attack.location.row - 1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("hit");

            }
            else if (result === "HIT" && !document.getElementById(elementId).rows[attack.location.row - 1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.contains("sink")) {
                document.getElementById(elementId).rows[attack.location.row - 1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("hit");
                document.getElementById(elementId).rows[attack.location.row - 1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("miss");

            }
            else if (result === "SUNK") {
                // We need to mark the ship as sunk with the appropriate images. Let CSS handle that after we give add a class to it
                let ship = attack.ships[i];
                let square;
                for (square of ship.occupiedSquares) {
                    if (square.type == "CQ") {
                        document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("miss");
                        document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("cq_place");
                        document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("cq_sink");

                    }
                    else {
                        document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("sink");
                        document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("found");
                        document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("hit");
                        document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("miss");

                    }
                }
            }
            //When a surrender occurs, a modal will popup and display the win message
            else if (result === "SURRENDER") {
                let ship = attack.ships[i];
                let square;
                for (square of ship.occupiedSquares) {
                    if (square.type == "CQ") {
                        document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("miss");
                        document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("hit");
                        document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("cq_place");
                        document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("cq_sink");

                    }
                    else {
                        document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("sink");
                        document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("found");
                        document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("hit");
                        document.getElementById(elementId).rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("miss");

                    }
                }
                if (surrenderText == false) {
                    document.getElementsByClassName("win-message")[0].innerHTML = "Opponent won the game! You can now view your results or exit the modal to restart and play a new one.";
                }
                displayVictoryDialogue();
            }
            // If there is a radar used there, then make it show.
            else if (result === "RADAR") {
                let table = document.getElementById(elementId);
                let row = attack.location.row - 1;
                let col = attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0);
                let tableRow;
                let cell;
                let i;
                for (i = 0; i < 5; i++) {
                    if (table.rows[row - 2 + i] == undefined || table.rows[row - 2 + i].rowIndex === 0) {
                        continue;
                    }
                    // start from 2 rows down and make your way up to the top
                    tableRow = table.rows[row - 2 + i];
                    if (tableRow != undefined) {
                        cell = tableRow.cells[col];
                        // Only mark with the placed class if there doesn't already exist an attack there
                        if (!(cell.classList.contains("hit") || (cell.classList.contains("miss") && !cell.classList.contains("opp_cq_place")) || cell.classList.contains("sink") || cell.classList.contains("cq_sink"))) {
                            if (cell.classList.contains("opp-occupied") || cell.classList.contains("occupied")) {
                                cell.classList.add("found");
                            }
                            else {
                                cell.classList.add("radar-square");
                            }
                        }
                    }

                    // Now place the 4 squares diagonal to middle
                    if (i === 1 || i === 3) {
                        if ((tableRow.cells[col - 1] != undefined && col - 1 != 0) && !(tableRow.cells[col - 1].classList.contains("hit") || (tableRow.cells[col - 1].classList.contains("miss") && !tableRow.cells[col - 1].classList.contains("opp_cq_place")) || tableRow.cells[col - 1].classList.contains("sink") || tableRow.cells[col - 1].classList.contains("cq_sink"))) {
                            if (tableRow.cells[col - 1].classList.contains("opp-occupied") || tableRow.cells[col - 1].classList.contains("occupied")) {
                                tableRow.cells[col - 1].classList.add("found");
                            } else {
                                tableRow.cells[col - 1].classList.add("radar-square");
                            }

                        }
                        if (tableRow.cells[col + 1] != undefined && !(tableRow.cells[col + 1].classList.contains("hit") || (tableRow.cells[col + 1].classList.contains("miss") && !tableRow.cells[col + 1].classList.contains("opp_cq_place")) || tableRow.cells[col + 1].classList.contains("sink") || tableRow.cells[col + 1].classList.contains("cq_sink"))) {
                            if (tableRow.cells[col + 1].classList.contains("opp-occupied") || tableRow.cells[col + 1].classList.contains("occupied")) {
                                tableRow.cells[col + 1].classList.add("found");
                            } else {
                                tableRow.cells[col + 1].classList.add("radar-square");
                            }
                        }
                    }
                }
                tableRow = table.rows[row];
                for (i = 0; i < 5; i++) {
                    if (tableRow.cells[col - 2 + i] == undefined || tableRow.cells[col - 2 + i].cellIndex === 0) {
                        continue;
                    }
                    // Start 2 cells left of center, and work your way right
                    cell = tableRow.cells[col - 2 + i];
                    if (cell != undefined) {
                        // If no other attack exists in this location, then we can mark it with a radar class
                        if (!(cell.classList.contains("hit") || (cell.classList.contains("miss") && !cell.classList.contains("opp_cq_place")) || cell.classList.contains("sink") || cell.classList.contains("cq_sink"))) {
                            if (cell.classList.contains("opp-occupied") || cell.classList.contains("occupied")) {
                                cell.classList.add("found");
                            } else {
                                cell.classList.add("radar-square");
                            }
                        }
                    }

                }
            }
            i++;
        }


    });

    displayResults(board, elementId);
}

function displayResults(board, elementId) {
    // DISPLAY THE ATTACK RESULT IN THE RESULTS CONTAINER.
    // COLOR-CODE DIFFERENT ATTACK RESULTS
    if (board.attacks.length > 0) {
        let result = board.attacks[board.attacks.length - 1];
        let row = result.location.row - 1;
        let col = String.fromCharCode(result.location.column.charCodeAt(0) - 1);
        let newLaser = false;
        let newLaserOpp = false;

        for (let i = 0; i < result.results.length; i++) {
            let resultHTML = "<span class='attack-detail'><span class='";
            let html = "<div class='result'><span";

            if (result.results[i] === "HIT")
                resultHTML += "hitResult'>" + result.results[i] + "</span>" + " " + row + col + "</span></div>";
            else if (result.results[i] === "MISS")
                resultHTML += "missResult'>" + result.results[i] + "</span>" + " " + row + col + "</span></div>";
            else if (result.results[i] === "SUNK") {
                if (elementId === "opponent") {
                    numSunk++;
                } else {
                    numSunkOpp++;
                }
                resultHTML += "sunkResult'>" + result.results[i] + "</span>" + " " + result.ships[i].kind + " CQ" + "</span></div>";
            }
            else if (result.results[i] === "RADAR") {
                resultHTML += "radarResult'>" + result.results[i] + "</span> PLACED</span></div>";
            }

            // If elementID is opponent then that means we are displaying the attacks that the player did on the opponent's board. Same the other way around
            if (elementId === "opponent") {
                html += " class='player-name'>PLAYER: </span>" + resultHTML;
                document.getElementById("player-results").insertAdjacentHTML("afterbegin", html);
                // here we display the radar button
                if (numSunk === 1) {
                    document.getElementById("radar-btn").style.visibility = "visible";
                }
                else if(numSunk === 2) {
                    console.log("two sunks");
                    document.getElementById("move-fleet").style.visibility = "visible";
                }

            } else if (elementId === "player") {
                html += " class='opponent-name'>AI: </span>" + resultHTML;
                document.getElementById("opponent-results").insertAdjacentHTML("afterbegin", html);
            }
        }



    }

}

//Resets the website
function clearBoard() {
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
        if(square.type === "CQ"){
            document.getElementById("player").rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("cq_place");
        }
        document.getElementById("player").rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
        if (ship.underwater == true) {
            document.getElementById("player").rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("submerged");
        }
    }));

    game.opponentsBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {
        // for testing
        if(square.type === "CQ"){
            document.getElementById("opponent").rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("opp-cq_place");
        }
        document.getElementById("opponent").rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
        if (ship.underwater == true) {
            document.getElementById("opponent").rows[square.row - 1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("submerged");
        }
    }));

    let numSunkBefore = numSunk;
    let numSunkOppBefore = numSunkOpp;

    markHits(game.opponentsBoard, "opponent", true);
    markHits(game.playersBoard, "player", false);

    let resultHTML = "<span class='attack-detail'><span class='laser-detail'>SPACE LASER ENGAGED</span></span></div>";
    let html = "<div class='result'><span";
    console.log("in here");
    if (numSunkBefore === 0 && numSunk === 1 && numSunkOppBefore === numSunkOpp) {
        html += " class='player-name'>PLAYER: </span>" + resultHTML;
        document.getElementById("player-results").insertAdjacentHTML("afterbegin", html);
        html = "<div class='result'><span class='opponent-name'>AI:</span><span>. . .</span></div>";
        document.getElementById("opponent-results").insertAdjacentHTML("afterbegin", html);
    } else if (numSunkBefore === numSunk && numSunkOppBefore === 0 && numSunkOpp === 1) {
        html += " class='opponent-name'>AI: </span>" + resultHTML;
        document.getElementById("opponent-results").insertAdjacentHTML("afterbegin", html);
        html = "<div class='result'><span class='player-name'>PLAYER:</span><span>. . .</span></div>";
        document.getElementById("player-results").insertAdjacentHTML("afterbegin", html);
    }
}

var oldListener;

function registerCellListener(f, board) {
    if (board != "none") {
        let el = document.getElementById(board);
        for (i = 1; i < 11; i++) {
            for (j = 1; j < 11; j++) {
                let cell = el.rows[i].cells[j];
                cell.removeEventListener("mouseover", oldListener);
                cell.removeEventListener("mouseout", oldListener);
                cell.addEventListener("mouseover", f);
                cell.addEventListener("mouseout", f);
            }
        }
        oldListener = f;
    }
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
btn.onclick = function () {
    modal = document.getElementById('myModal');
    modal.style.display = "block";
}


function displayVictoryDialogue() {
    endGame = 1;
    modal = document.getElementById('endModal');
    modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function () {
    if (endGame === 1) {
        location.reload();
    }
    modal.style.display = "none";
}

span2.onclick = function () {
    if (endGame === 1) {
        location.reload();
    }
    modal.style.display = "none";
}


// When the user clicks anywhere outside of the modal, close it
window.onclick = function (event) {

    if (event.target === modal) {
        modal.style.display = "none";
        if (endGame === 1) {
            location.reload();
        }
    }
}


function cellClick() {
    let row = this.parentNode.rowIndex + 1;
    let col = String.fromCharCode(this.cellIndex + 65);
    if (isSetup) {
            sendXhr("POST", "/place", {
                game: game,
                shipType: shipType,
                x: row,
                y: col,
                isSubmerged: submerged,
                isVertical: vertical
            }, function (data) {
                game = data;
                redrawGrid();

                let tempShipType = shipType.toLowerCase();
                document.getElementById("place_" + tempShipType).style.display = "none";
                document.getElementById("is_submerged").style.display = "none";


                placedShips++;
                if (placedShips === 4) {
                    isSetup = false;
                    // registerCellListener((e) => {});

                    //Resets the results box and also gets rid of the ship placement buttons and includes a restart button
                    document.getElementsByClassName("buttonHolder")[0].children.item(4).setAttribute("id", "is_vertical")
                    document.getElementsByClassName("buttonHolder")[0].children.item(4).innerHTML = "Vertical";
                    document.getElementsByClassName("buttonHolder")[0].children.item(5).setAttribute("id", "is_submerged")
                    document.getElementsByClassName("buttonHolder")[0].children.item(5).innerHTML = "Submerged";

                    //Remove place ship buttons and adding a restart button
                    document.getElementById("place_battleship").style.display = "none";
                    document.getElementById("is_vertical").style.display = "none";
                    document.getElementById("is_submerged").style.display = "none";
                    document.getElementById("place_destroyer").style.display = "none";
                    document.getElementById("place_submarine").style.display = "none";
                    document.getElementById("place_minesweeper").style.display = "none";
                    document.getElementById("restart").style.visibility = "visible";
                    document.getElementById("move-fleet").style.display = "inline-block"
                    document.getElementById("restart").addEventListener("click", function (e) {
                        location.reload();
                    });

                    //Clearing out the results box and preparing it for the attack phase results
                    document.getElementsByClassName("container-header")[0].innerHTML = "ATTACK RESULTS";
                    resultscontain = document.getElementById("results-container");
                    resultscontain.innerHTML = "";

                    playerResults = document.createElement('div');
                    playerResults.setAttribute("id", "player-results");
                    playerResults.setAttribute("class", "individual-results");

                    opponentResults = document.createElement('div');
                    opponentResults.setAttribute("id", "opponent-results");
                    opponentResults.setAttribute("class", "individual-results");

                    resultscontain.appendChild(playerResults);
                    resultscontain.appendChild(opponentResults);
                }
            });

    } else {
        sendXhr("POST", "/attack", {game: game, x: row, y: col, fleet: "", radar: isRadar}, function (data) {
            game = data;
            if (isRadar) {
                radarsUsed++;
                isRadar = false;
                document.getElementById("radar-btn").classList.toggle("btn-toggle");
            }
            if (radarsUsed === 2) {
                document.getElementById("radar-btn").style.display = "none";
            }
            if (fleetsUsed === 2) {
                document.getElementById("move-fleet").style.display = "none";
            }
            redrawGrid();
        })
    }
}

function sendXhr(method, url, data, handler) {
    var req = new XMLHttpRequest();
    req.addEventListener("load", function (event) {
        if (req.status != 200) {
            if (url === "/attack") {
                var row = data.x;
                var col = data.y.charCodeAt(0) - 64;

                //Displays an invalid attack if there is an error
                var html = "<div class='result'><span";
                html += " class='player-name'>PLAYER: </span>" + "<span class='error'>INVALID ATTACK</span></div>";
                document.getElementById("player-results").insertAdjacentHTML("afterbegin", html);

                html = "<div class='result'><span";
                html += " class='opponent-name'>AI: </span>" + "<span class='error'>WAITING...</span></div>";

                document.getElementById("opponent-results").insertAdjacentHTML("afterbegin", html);
                document.body.insertAdjacentHTML("afterend", req.response);
                console.log(req.response);
            }
            else if(url === "/move"){
                console.log(req.response);
            }
            //Displays an invalid placement if there is an error
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
    console.log(url);
    console.log(data);
    req.send(JSON.stringify(data));
}

function place(size, submarine) {
    return function () {
        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;
        let table = document.getElementById("player");

        for (let i = 0; i < size; i++) {
            let cell;
            if (row === 0 || col === 0) {
                break;
            }
            if (vertical) {
                // check for submarine going over edge
                if (col === 10 && submarine == true) {
                    for (let j = 0; j < size; j++) {
                        if (table.rows[row + j] != undefined) {
                            cell = table.rows[row + j].cells[col];
                            cell.classList.toggle("error-place");
                        }
                    }
                    break;
                }
                let tableRow = table.rows[row + i];

                if (tableRow === 0) {
                    break;
                }

                if (tableRow === undefined) {
                    // ship is over the edge, mark the visible squares with red X's
                    for (let j = (row + i - 1); j >= row; j--) {
                        cell = table.rows[j].cells[col];
                        cell.classList.toggle("error-place");
                        if (submarine == true) {
                            if (j === 10 && i === 3 && table.rows[j].cells[col + 1] != undefined) {
                                table.rows[j].cells[col + 1].classList.toggle("error-place");
                            }
                        }
                    }

                    break;
                }
                if(submarine == true && i === 2 && tableRow.cells[col + 1] != undefined){
                    cell = tableRow.cells[col+1];
                    cell.classList.toggle("placed");
                }
                if (submarine == true && submerged != true && i === 2 && tableRow.cells[col + 1].classList.contains("occupied")) {
                    for (let j = (row + i + 1); j >= row; j--) {
                        cell = table.rows[j].cells[col];
                        cell.classList.toggle("error-place");
                    }
                    break;
                }
                cell = tableRow.cells[col];
                // If cell is occupied, then we can't place there either, so mark visible square with red X's
                if (cell.classList.contains('occupied') && !(submerged == true || cell.classList.contains('submerged'))) {
                    for (let j = (row + i - 1); j >= row; j--) {
                        cell = table.rows[j].cells[col];
                        cell.classList.toggle("error-place");
                        if (submarine == true && j - row === 2) {
                            cell = table.rows[j].cells[col + 1];
                            cell.classList.toggle("error-place");
                        }
                    }
                    break;
                }
            } else {

                if (row === 1 && submarine == true) {
                    for (let j = 0; j < size; j++) {
                        cell = table.rows[row].cells[j + col];
                        if (cell != undefined) {
                            cell.classList.toggle("error-place");
                        }
                    }
                    break;
                }
                if(submarine == true && i === 2 && table.rows[row - 1].cells[col + i] != undefined) {
                    cell = table.rows[row-1].cells[col+i];
                    cell.classList.toggle("placed");
                }
                cell = table.rows[row].cells[col + i];
            }

            if (cell === undefined) {
                // ship is over the edge, so mark visible squares with red X's
                for (let j = (col + i - 1); j >= col; j--) {
                    cell = table.rows[row].cells[j];
                    cell.classList.toggle("error-place");
                    if (submarine == true) {
                        if (j === 10 && i === 3 && table.rows[row - 1].cells[j] != undefined) {
                            table.rows[row - 1].cells[j].classList.toggle("error-place");
                        }
                    }
                }
                break;
            } else if (submarine == true && submerged != true && i === 2 && table.rows[row - 1].cells[col + i].classList.contains("occupied")) {
                for (let j = (col + i + 1); j >= col; j--) {
                    cell = table.rows[row].cells[j];
                    cell.classList.toggle("error-place");
                }
                break;
            } else if (cell.classList.contains('occupied') && !(submerged == true || cell.classList.contains("submerged"))) {
                // If cell is occupied, then we can't place there either, so mark visible square with red X's
                for (let j = (col + i - 1); j >= col; j--) {
                    cell = table.rows[row].cells[j];
                    cell.classList.toggle("error-place");
                    if (submarine == true && j - col === 2) {
                        cell = table.rows[row - 1].cells[j];
                        cell.classList.toggle("error-place");
                    }
                }
                break;
            }
            cell.classList.toggle("placed");
        }
    }
}

/***********************************************
 * attack()
 * For showing the radar on the opponent's board when they are HOVERING.
 * This function is called by registerCellListener()
 * @returns {Function}
 */
function attack() {
    return function () {
        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;
        let table = document.getElementById("opponent");


        if (isRadar) {
            let tableRow;
            let cell;
            let i;
            for (i = 0; i < 5; i++) {
                if (table.rows[row - 2 + i] === undefined || table.rows[row - 2 + i].rowIndex === 0) {
                    continue;
                }
                tableRow = table.rows[row - 2 + i];
                if (tableRow != undefined) {
                    cell = tableRow.cells[col];
                    cell.classList.toggle("placed");
                }
                if (i === 1 || i === 3) {
                    if (tableRow.cells[col - 1] != undefined && col - 1 != 0) {
                        tableRow.cells[col - 1].classList.toggle("placed");
                    }
                    if (tableRow.cells[col + 1] != undefined) {
                        tableRow.cells[col + 1].classList.toggle("placed");
                    }
                }
            }
            tableRow = table.rows[row];
            for (i = 0; i < 5; i++) {
                if (tableRow.cells[col - 2 + i] === undefined || tableRow.cells[col - 2 + i].cellIndex === 0) {
                    continue;
                }
                cell = tableRow.cells[col - 2 + i];
                if (cell != undefined) {
                    cell.classList.toggle("placed");
                }

            }

        }
    }
}

function initGame() {
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    document.getElementById("is_submerged").style.display = "none";
    document.getElementById("move-fleet").style.display = "none";
    document.getElementById("north").style.display = "none";
    document.getElementById("east").style.display = "none";
    document.getElementById("south").style.display = "none";
    document.getElementById("west").style.display = "none";

    document.getElementById("place_minesweeper").addEventListener("click", function (e) {
        shipType = "MINESWEEPER";
        shipSize = 2;
        registerCellListener(place(2, false), "player");
        document.getElementsByClassName("buttonHolder")[0].children.item(5).setAttribute("id", "is_submerged")
        document.getElementsByClassName("buttonHolder")[0].children.item(5).innerHTML = "Submerged";
        document.getElementById("is_submerged").style.display = "none";
        submerged = false;
    });
    document.getElementById("place_destroyer").addEventListener("click", function (e) {
        shipType = "DESTROYER";
        shipSize = 3;
        registerCellListener(place(3, false), "player");
        document.getElementsByClassName("buttonHolder")[0].children.item(5).setAttribute("id", "is_submerged")
        document.getElementsByClassName("buttonHolder")[0].children.item(5).innerHTML = "Submerged";
        document.getElementById("is_submerged").style.display = "none";
        submerged = false;
    });
    document.getElementById("place_battleship").addEventListener("click", function (e) {
        shipType = "BATTLESHIP";
        shipSize = 4;
        registerCellListener(place(4, false), "player");
        document.getElementsByClassName("buttonHolder")[0].children.item(5).setAttribute("id", "is_submerged")
        document.getElementsByClassName("buttonHolder")[0].children.item(5).innerHTML = "Submerged";
        document.getElementById("is_submerged").style.display = "none";
        submerged = false;

    });
    document.getElementById("place_submarine").addEventListener("click", function (e) {
        shipType = "SUBMARINE";
        shipSize = 5;
        registerCellListener(place(4, true), "player");
        document.getElementsByClassName("buttonHolder")[0].children.item(5).setAttribute("id", "is_submerged")
        document.getElementsByClassName("buttonHolder")[0].children.item(5).innerHTML = "Submerged";
        document.getElementById("is_submerged").style.display = "block";
    });

    // Event listener for the radar button
    document.getElementById("radar-btn").addEventListener("click", (e) => {
        // rad is true if adding btn-toggle class to the radar button, meaning we want to use radar, else false
        let rad = e.target.classList.toggle("btn-toggle");
        if (rad) {
            isRadar = true;
            registerCellListener(attack(), "opponent");
        } else {
            isRadar = false;
            registerCellListener((e) => {
            }, "none");
        }
    });

    document.getElementById("move-fleet").addEventListener("click", (e) => {
            isFleet = true;
            document.getElementById("move-fleet").style.display = "none";
            document.getElementById("north").style.display = "inline-block";
            document.getElementById("east").style.display = "inline-block";
            document.getElementById("south").style.display = "inline-block";
            document.getElementById("west").style.display = "inline-block";
    });

    document.getElementById("north").addEventListener("click", (e) => {
            // implement north direction logic here
            fleetsUsed++;
            fleetType = "north";
            document.getElementById("move-fleet").style.display = "block";
            document.getElementById("north").style.display = "none";
            document.getElementById("east").style.display = "none";
            document.getElementById("south").style.display = "none";
            document.getElementById("west").style.display = "none";
            if (fleetsUsed === 2) {
                document.getElementById("move-fleet").style.display = "none";
            }
            sendXhr("POST", "/move", {game: game, fleet: fleetType}, function (data) {
                game = data;
                redrawGrid();
            })
            isFleet = false;
    });

    document.getElementById("east").addEventListener("click", (e) => {
        // implement east direction logic here
        fleetType = "east";
        fleetsUsed++;
        document.getElementById("move-fleet").style.display = "block";
        document.getElementById("north").style.display = "none";
        document.getElementById("east").style.display = "none";
        document.getElementById("south").style.display = "none";
        document.getElementById("west").style.display = "none";
        if (fleetsUsed === 2) {
            document.getElementById("move-fleet").style.display = "none";
        }
        sendXhr("POST", "/move", {game: game, fleet: fleetType}, function (data) {
            game = data;
            redrawGrid();
        })
        isFleet = false;
    });

    document.getElementById("south").addEventListener("click", (e) => {
        // implement south direction logic here
        fleetsUsed++;
        fleetType = "south";
        document.getElementById("move-fleet").style.display = "block";
        document.getElementById("north").style.display = "none";
        document.getElementById("east").style.display = "none";
        document.getElementById("south").style.display = "none";
        document.getElementById("west").style.display = "none";
        if (fleetsUsed === 2) {
            document.getElementById("move-fleet").style.display = "none";
        }
        sendXhr("POST", "/move", {game: game, fleet: fleetType}, function (data) {
            game = data;
            redrawGrid();
        })
        isFleet = false;
    });

    document.getElementById("west").addEventListener("click", (e) => {

        // implement west direction logic
        fleetType = "west";
        fleetsUsed++;
        document.getElementById("move-fleet").style.display = "block";
        document.getElementById("north").style.display = "none";
        document.getElementById("east").style.display = "none";
        document.getElementById("south").style.display = "none";
        document.getElementById("west").style.display = "none";
        if (fleetsUsed === 2) {
            document.getElementById("move-fleet").style.display = "none";
        }

        sendXhr("POST", "/move", {game: game, y: "B", x: 2, radar: false, fleet: fleetType}, function (data) {
            game = data;
            redrawGrid();
        })
        isFleet = false;
        if (fleetsUsed === 2) {
            document.getElementById("move-fleet").style.display = "none";
        }

    });


    //Makes the vertical button have the toggle effect allowing users to switch between horizontal and vertical
    document.getElementById("is_vertical").addEventListener("click", function (e) {
        //document.getElementById("is_vertical").innerHTML = "Horizontal";
        if (vertical == true) {
            vertical = false;
            document.getElementById("is_horizontal").innerHTML = "Vertical";
            document.getElementById("is_horizontal").setAttribute("id", "is_vertical");
        }
        else {
            vertical = true;
            document.getElementById("is_vertical").innerHTML = "Horizontal";
            document.getElementById("is_vertical").setAttribute("id", "is_horizontal");

        }
    });

     document.getElementById("is_submerged").addEventListener("click", function (e) {
            if (submerged == true) {
                submerged = false;
                document.getElementById("is_submerged").innerHTML = "Submerged";
            }
            else {
                submerged = true;
                document.getElementById("is_submerged").innerHTML = "Surface";
            }
      });

    sendXhr("GET", "/game", {}, function (data) {
        game = data;
    });


};