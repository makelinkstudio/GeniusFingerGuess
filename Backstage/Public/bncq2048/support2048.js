var documentWidth = document.documentElement.clientWidth;
var  girdContainerWidth = 0.92 * documentWidth;
var cellSideLength = 0.18 * documentWidth;
var cellSpace = 0.04 * documentWidth;

function getPosTop(i,j){
	return cellSpace + i*(cellSpace + cellSideLength);
}

function getPosLeft(i,j){
	return cellSpace + j*(cellSpace + cellSideLength);
}

function getNumberBackgroundColor(number){
	var number_punch = number.substr(1,1);
	switch(number_punch){
		case '1':return '#4ab5de';break;
		case '2':return '#00c58b';break;
		case '3':return '#99e900';break;
		case '4':return '#ff6060';break;
		case '5':return '#821e1e';break;
//		case '6':return '#edcf72';break;
//		case 128:return '#adcc61';break;
//		case 256:return '#9c0';break;
//		case 512:return '#33b5e5';break;
//		case 1024:return '#09c';break;
//		case 2048:return '#a6c';break;
//		case 4096:return '#93c';break;
//		case 8192:return '#f67c5f';break;
	}
	
	return "black";
}

function getNumberLevel(number){
	var number_punch = number.substr(1,1);
	switch(number_punch){
		case '1':return 'url('+path+'img/one.png)';break;
		case '2':return 'url('+path+'img/two.png)';break;
		case '3':return 'url('+path+'img/three.png)';break;
		case '4':return 'url('+path+'img/four.png)';break;
		case '5':return 'url('+path+'img/five.png)';break;
	}
	return false;
}

function getNumberIron(number){
	var number_punch_select = number.substr(0,1);
	switch(number_punch_select){
		case 'S':return 'url('+path+'img/jian.png)';break;
		case 'P':return 'url('+path+'img/bao.png)';break;
		case 'R':return 'url('+path+'img/shi.png)';break;
	}
}
function getNumberColor(number){
	if(number <= 4){
		return '#776e65';
	}
	
	return 'white';
}

function nospace(boader){
	for(var i = 0;i < 4;i++){
		for(var j = 0; j < 4;j ++){
			if(boader[i][j] == 0){
				return false;
			}
		}
	}
	return true;
}
//判断是否能向左移动
function canMoveLeft(board){
	for(var i = 0;i < 4;i++){
		for(var j = 1;j < 4;j++){
			if(board[i][j] != 0){
				if(board[i][j-1] == 0 || canUpgrade(board[i][j-1],board[i][j])){
					return true;
				}
			}
		}
	}
	return false;
}
//判断是否能向上移动
function canMoveUp(board){
	for(var i = 1;i < 4;i++){
		for(var j = 0;j < 4;j++){
			if(board[i][j] != 0){
				if(board[i-1][j] == 0 || canUpgrade(board[i-1][j],board[i][j])){
					return true
				}
			}
		}
	}
	return false;
}
//判断是否能向右移动
function canMoveRight(board){
	for(var i = 3;i > -1 ;i--){
		for(var j = 2;j > -1;j--){
			if(board[i][j] != 0){
				if(board[i][j+1] == 0 || canUpgrade(board[i][j+1],board[i][j])){
					return true;
				}
			}
		}
	}
	return false;
}
//判断是否能向下移动
function canMoveDown(board){
	for(var i = 2;i > -1;i--){
		for(var j = 0; j < 4;j++){
			if(board[i][j] !=0){
				if(board[i+1][j] == 0 ||canUpgrade(board[i+1][j],board[i][j])){
					return true;
				}
			}
		}
	}
	return false;
}
function noBlockHorizontal(row,col1,col2,board){
	for(var i = col1 + 1;i < col2;i++){
		if(board[row][i] != 0){
			return false;
		}
	}
	return true;
}

function noVertical(col,row1,row2,board){
	for(var i = row1 + 1;i < row2;i++){
		if(board[i][col] !=0){
			return false;
		}
	}
	return true;
}

function nomove(board){
	if( canMoveLeft(board) ||
	    canMoveRight(board) ||
		canMoveUp(board) ||
		canMoveDown(board)){
		return false;
	}
		return true;
}

function canUpgrade(fromBoard,toBoard){
	var fromBoardGrade = fromBoard.substr(1,1);
	var fromBoardSelect = fromBoard.substr(0,1);
	var toBoardGrade = toBoard.substr(1,1);
	var toBoardSelect = toBoard.substr(0,1);
	if(fromBoardGrade != toBoardGrade){
		return false;
	}
	if(fromBoardGrade == '5' || toBoardGrade == '5'){
		return false;
	}
	switch(fromBoardSelect){
		case 'S':if(toBoardSelect != 'S'){
			return true;
		}break;
		case 'P':if(toBoardSelect != 'P'){
			return true;
		};break;
		case 'R':if(toBoardSelect != 'R'){
			return true;
		};break;
	}
	return false;
}

function upGrade(fromBoard,toBoard){
	var fromBoardGrade = fromBoard.substr(1,1);
	var fromBoardSelect = fromBoard.substr(0,1);
	var toBoardGrade = toBoard.substr(1,1);
	var toBoardSelect = toBoard.substr(0,1);
	var newGrade = '';
	var newSelect = '';
	switch(fromBoardGrade){
		case '1' : newGrade = '2';
		break;
		case '2' : newGrade = '3';
		break;
		case '3' : newGrade = '4';
		break;
		case '4' : newGrade = '5';
		break;
	}
	switch(fromBoardSelect){
		case 'S' :if(toBoardSelect == 'R'){
			newSelect = 'R';
		}else{
			newSelect = 'S';	
		}
		break;
		case 'P' : if(toBoardSelect == 'S'){
			newSelect = 'S';
		}else{
			newSelect = 'P';
		}
		break;
		case 'R' : if(toBoardSelect == 'P'){
			newSelect = 'P';
		}else{
			newSelect = 'R';
		}
		break;
	}
	//判断是否5级
	if(newGrade == '5'){
		if(newSelect == 'S'){
			sCount = sCount + 1;
		}else if(newSelect == 'P'){
			pCount = pCount +1;
		}else if(newSelect == 'R'){
			rCount = rCount + 1;
		}
	}
	return newSelect + newGrade;
}

//随机替换cell出拳
function changeCell(board){
	var boardGrade = board.substr(1,1);
	var boardSelect = board.substr(0,1);
	var newBoardSelect = null;
	var random1 = Math.random();
	var random2 = Math.random();
	if(boardGrade == '5'){
		return board;
	}
	switch(boardSelect){
		case 'S' : if(Math.random() > 0.5 && random1 > random2){
			newBoardSelect = 'P';
		}else if(Math.random() > 0.5 && random1 < random2){
			newBoardSelect = 'R';
		}else{
			newBoardSelect = 'S';
		}break;
		case 'P' : if(Math.random() > 0.5  && random1 > random2){
			newBoardSelect = 'R';
		}else if(Math.random() > 0.5 && random1 < random2){
			newBoardSelect = 'S';
		}else{
			newBoardSelect = 'P';
		}break;
		case 'R' : if(Math.random() > 0.5  && random1 > random2){
			newBoardSelect = 'P';
		}else if(Math.random() > 0.5 && random1 < random2){
			newBoardSelect = 'S';
		}else{
			newBoardSelect = 'R';
		}break;
	}
	return newBoardSelect + boardGrade;
}

