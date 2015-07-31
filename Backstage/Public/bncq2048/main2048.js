var board = new Array();
var score = 0;
var hasConficted = new Array();

var startx = 0;
var starty = 0;
var endx = 0;
var endy = 0;

//记录5级S P R
var sCount = 0;
var pCount = 0;
var rCount = 0;

//记录游戏状态
var gameStatus = true;

//可以随机重置次数
var reRamdomCount = 3;

$(document).ready(function(){
	perpareForMoblie();
	newgame();
});

function perpareForMoblie(){
	
	
	if(documentWidth > 1200){
		girdContainerWidth = 500;
		cellSpace = 20;
		cellSideLength = 100;
	}
	$("#grid-container").css('width',girdContainerWidth - (2 * cellSpace));
	$("#grid-container").css('height',girdContainerWidth - (2 * cellSpace));
	$("#grid-container").css('padding',cellSpace);
	$("#grid-container").css('border-radius',0.02*girdContainerWidth);
	
	$(".grid-cell").css('width',cellSideLength);
	$(".grid-cell").css('height',cellSideLength);
	$(".grid-cell").css('border-radius',0.02 * girdContainerWidth);
}

function newgame(){
	$("#lost").hide();
	$("#win").hide();
	//初始化棋盘格
	init();
	//随机两个格子生成数字
	generateOneNumber();
	generateOneNumber();
}

function init(){
	for(var i = 0;i<4;i++){
		for(var j = 0;j < 4;j++){
			//绘制棋盘格
			var gridCell = $("#grid-cell-"+i+"-"+j);
			gridCell.css('top',getPosTop(i,j));
			gridCell.css('left',getPosLeft(i,j));
		}
	}
	
	for(var i = 0;i < 4;i++){
		board[i] = new Array();
		hasConficted[i] = new Array();
		for(var j = 0;j < 4;j++){
			board[i][j] = 0;
			hasConficted[i][j] = false;
		}
	}
	updateBoardView();
	
	score = 0;
	gameStatus = true;
	reRamdomCount = 3;
	sCount = 0;
	pCount = 0;
	rCount = 0;
	$("#reRandomcellCount").text(reRamdomCount);
}

function updateBoardView(){
	$(".number-cell").remove();
	for(var i = 0;i < 4;i++){
		for(var j = 0;j < 4;j++){
			$("#grid-container").append('<div class="number-cell" id="number-cell-'+i+'-'+j+'"><div class="number-level" id="number-level-'+i+'-'+j+'"></div></div>');
			var theNumberCell = $("#number-cell-"+i+"-"+j);
			var theNumberLevel = $("#number-level-"+i+"-"+j);
			if(board[i][j] == 0){
				theNumberCell.css('width','0px');
				theNumberCell.css('height','0px');
				theNumberCell.css('top',getPosTop(i,j)+ (cellSideLength /2));
				theNumberCell.css('left',getPosLeft(i,j)+ (cellSideLength /2));
			}else{
				theNumberCell.css('width',cellSideLength);
				theNumberCell.css('height',cellSideLength);
				theNumberCell.css('top',getPosTop(i,j));
				theNumberCell.css('left',getPosLeft(i,j));
				//theNumberCell.css('background-color',getNumberBackgroundColor(board[i][j]));
				theNumberCell.css('background-color',getNumberBackgroundColor(board[i][j]));
				theNumberCell.css('color',getNumberColor(board[i][j]));
				//theNumberCell.text(board[i][j]);
				theNumberCell.css('background-image',getNumberIron(board[i][j]));
				theNumberCell.css('background-repeat','no-repeat');
				theNumberCell.css('background-position','30%,70%');
				theNumberCell.css('background-size',0.6*cellSideLength,0.6*cellSideLength);
				//set Level
				theNumberLevel.css('width',0.3 * cellSideLength);
				theNumberLevel.css('height',0.3 * cellSideLength);
				theNumberLevel.css('top',0.02 * cellSideLength);
				theNumberLevel.css('left',cellSideLength-0.33 * cellSideLength);
				theNumberLevel.css('background-image',getNumberLevel(board[i][j]));
				theNumberLevel.css('background-repeat','no-repeat');
				theNumberLevel.css('background-position','50%,50%');
			}
			hasConficted[i][j] = false;
			$(".number-cell").css('line-height',cellSideLength + 'px');
			$(".number-cell").css('font-size',0.6 * cellSideLength + 'px');
		}
	}
}

function generateOneNumber(){
	//判断是否能生成
	if(nospace(board)){
		return false;
	}
	//随机一个位置
	var randx = parseInt(Math.floor(Math.random() *4));
	var randy = parseInt(Math.floor(Math.random() *4));
	var times = 0;
	while(times < 50){
		if( board[randx][randy] == 0){
			break;
		}else{
			randx = parseInt(Math.floor(Math.random() *4));
			randy = parseInt(Math.floor(Math.random() *4));
			
			times ++;
		}
	}
	if(times == 50){
		for(var i = 0;i < 4;i++){
			for(var j = 0;j < 4;j++){
				if(board[i][j] == 0){
					randx = i;
					randy = j;
				}
			}
		}
	}
	//随机一个数字
	//var randNumber = Math.random() < 0.5?2:4;
	var randNumber = '';
	var randIndex = Math.random();
	if(randIndex < (1/3) && randIndex > 0){
		randNumber = 'S1';
	}else if(randIndex > (1/3) && randIndex < (2/3)){
		randNumber = 'P1';
	}else{
		randNumber = 'R1';
	}
	//在随机位置显示随机数字
	board[randx][randy] = randNumber;
	showNumberWithAnimation(randx,randy,randNumber);
	return true;
}

$(document).keydown(function(event){
	//event.preventDefault();
	switch(event.keyCode){
		case 37: //Left
			if(moveLeft()){
				setTimeout("generateOneNumber()",75);
				//判断游戏是否结束
				setTimeout("isgameover()",110);
			};
			break;
		case 38: //Up
			if(moveUp()){
				setTimeout("generateOneNumber()",75);
				//判断游戏是否结束
				setTimeout("isgameover()",110);
			};
			break;
		case 39:  //Right
			if(moveRight()){
				setTimeout("generateOneNumber()",75);
				//判断游戏是否结束
				setTimeout("isgameover()",110);
			};
			break;
		case 40:  //Down
			if(moveDown()){
				setTimeout("generateOneNumber()",75);
				//判断游戏是否结束
				setTimeout("isgameover()",110);
			};
			break;
		default:
			break;
	}
});

function moveLeft(){
	if(!canMoveLeft(board)){
		return false;
	}
	if(!isGameCanPlay()){
		return false;
	}
	//moveLeft
	for(var i = 0;i < 4;i++){
		for(var j = 1;j < 4;j++){
			if(board[i][j] != 0){
				for(var k =0;k<j;k++){
					if(board[i][k] == 0 && noBlockHorizontal(i,k,j,board)){
						//move
						showMoveAnimation(i,j,i,k);
						board[i][k] = board[i][j];
						board[i][j] = 0;
						continue;
					}else if(noBlockHorizontal(i,k,j,board) && !hasConficted[i][k] && canUpgrade(board[i][j],board[i][k])){
						//move
						showMoveAnimation(i,j,i,k);
						//add
						board[i][k] = upGrade(board[i][j],board[i][k]);
						board[i][j] = 0;
						//add score
						//score += board[i][k];
						//updateScore(score);
						hasConficted[i][k] = true;
						continue;
					}
				}
			}
		}
	}
	setTimeout("updateBoardView()",100);
	return true;
}

function moveUp(){
	if(!canMoveUp(board)){
		return false;
	}
	if(!isGameCanPlay()){
		return false;
	}
	//moveUp
	for(var i = 1;i < 4;i++){
		for(var j = 0;j < 4;j++){
			if(board[i][j] != 0){
				for(var k = 0;k < i;k++){
					if(board[k][j]  == 0 && noVertical(j,k,i,board)){
						//move
						showMoveAnimation(i,j,k,j);
						board[k][j] = board[i][j];
						board[i][j] = 0;
						continue;
					}else if(noVertical(j,k,i,board) && !hasConficted[k][j] && canUpgrade(board[i][j],board[k][j])){
						//move
						showMoveAnimation(i,j,k,j);
						//add
						board[k][j] = upGrade(board[i][j],board[k][j]);
						board[i][j] = 0;
						//score += board[k][i];
						//updateScore(score);
						hasConficted[k][j] = true;
						continue;
					}
				}
			}
		}
	}
	setTimeout("updateBoardView()",100);
	return true;
}

function moveRight(){
	if(!canMoveRight(board)){
		return false;
	}
	if(!isGameCanPlay()){
		return false;
	}
	//moveRight
	for(var i = 0;i < 4;i++){
		for(var j = 2;j > -1;j--){
			if(board[i][j] !=0){
				for(var k = 3;k > j;k--){
					if(board[i][k] == 0 && noBlockHorizontal(i,j,k,board)){
						showMoveAnimation(i,j,i,k);
						board[i][k] = board[i][j];
						board[i][j] = 0;
						continue;
					}else if(noBlockHorizontal(i,j,k,board) && !hasConficted[i][k] && canUpgrade(board[i][j],board[i][k])){
						showMoveAnimation(i,j,i,k);
						board[i][k] = upGrade(board[i][j],board[i][k]);
						board[i][j] = 0;
						//score += board[i][k];
						//updateScore(score);
						hasConficted[i][k] = true;
						continue;
					}
				}
			}
		}
	}
	setTimeout("updateBoardView()",100);
	return true;
}

function moveDown(){
	if(!canMoveDown(board)){
		return false;
	}
	if(!isGameCanPlay()){
		return false;
	}
	//moveDown
	for(var i = 2;i > -1;i--){
		for(var j = 0;j < 4;j++){
			if(board[i][j] != 0){
				for(var k = 3;k > i;k--){
					if(board[k][j] == 0 && noVertical(j,i,k,board)){
						showMoveAnimation(i,j,k,j);
						board[k][j] = board[i][j];
						board[i][j] = 0;
						continue;
					}else if(noVertical(j,i,k,board) && !hasConficted[k][j] && canUpgrade(board[i][j],board[k][j])){
						showMoveAnimation(i,j,k,j);
						board[k][j] = upGrade(board[i][j],board[k][j]);
						board[i][j] = 0;
						//score += board[k][j];
						//updateScore(score);
						hasConficted[k][j];
						continue;
					}
				}
			}
		}
	}
	setTimeout("updateBoardView()",100);
	return true;
}
document.addEventListener('touchstart',function(event){
	startx = event.touches[0].pageX;
	starty = event.touches[0].pageY;
});
document.addEventListener('touchend',function(event){
	endx = event.changedTouches[0].pageX;
	endy = event.changedTouches[0].pageY;
	
	var deltax = endx -startx;
	var deltay = endy - starty;
	
	if(Math.abs(deltax) < 0.2*documentWidth && Math.abs(deltay) < 0.2*documentWidth){
		return;
	}
	//x
	if(Math.abs(deltax) >= Math.abs(deltay)){
		if(deltax > 0){
			//move right
			if(moveRight()){
				setTimeout("generateOneNumber()",75);
				//判断游戏是否结束
				setTimeout("isgameover()",110);
			};
		}else{
			//move left
			if(moveLeft()){
				setTimeout("generateOneNumber()",75);
				//判断游戏是否结束
				setTimeout("isgameover()",110);
			};
		}
	}else{
	//y
		if(deltay > 0){
			//move down
			if(moveDown()){
				setTimeout("generateOneNumber()",75);
				//判断游戏是否结束
				setTimeout("isgameover()",110);
			};
		}else{
			//move up
			if(moveUp()){
				setTimeout("generateOneNumber()",75);
				//判断游戏是否结束
				setTimeout("isgameover()",110);
			};
		}
	}
});
document.addEventListener('touchmove', function(event) { 
	event.preventDefault(); 
}, false);
function isgameover(){
	if(sCount != 0 && pCount != 0 && rCount != 0){
		gameStatus = false;
		gamewin();
	}
	if(nospace(board) && nomove(board)){
		gameStatus = false;
		gameover();
	}
}
//判断游戏是否可以继续
function isGameCanPlay(){
	if(gameStatus == true){
		return true;
	}else{
		return false;
	}
}

function gameover(){
	$("#lost").show();
}

function gamewin(){
	$("#win").show();
}

//随机替换格子
function rerandomcell(){
	if(reRamdomCount == 0 || gameStatus == false){
		return ;
	}
	for(var i = 0 ;i < 4; i ++){
		for(var j = 0; j < 4;j++){
			if(board[i][j] != 0){
				board[i][j] = changeCell(board[i][j]);
			}
		}
	}
	reRamdomCount = reRamdomCount - 1;
	$("#reRandomcellCount").text(reRamdomCount);
	setTimeout("updateBoardView()",100);
	//updateBoardView();
}

