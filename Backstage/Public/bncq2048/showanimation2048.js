function showNumberWithAnimation(i,j,randNumber){
	var numberCell = $("#number-cell-"+i+"-"+j);
	var theNumberLevel = $("#number-level-"+i+"-"+j);
	numberCell.css('background-color',getNumberBackgroundColor(randNumber));
	numberCell.css('color',getNumberColor(randNumber));
	numberCell.css('background-image',getNumberIron(board[i][j]));
	numberCell.css('background-repeat','no-repeat');
	numberCell.css('background-position','30%,70%');
	numberCell.css('background-size',0.6*cellSideLength,0.6*cellSideLength);
	theNumberLevel.css('width',0.3 * cellSideLength);
	theNumberLevel.css('height',0.3 * cellSideLength);
	theNumberLevel.css('top',0.02 * cellSideLength);
	theNumberLevel.css('left',cellSideLength-0.33 * cellSideLength);
	theNumberLevel.css('background-image',getNumberLevel(board[i][j]));
	theNumberLevel.css('background-repeat','no-repeat');
	theNumberLevel.css('background-position','50%,50%');
	numberCell.animate({
		width:cellSideLength,
		height:cellSideLength,
		top:getPosTop(i,j),
		left:getPosLeft(i,j)
	},70);
}

function showMoveAnimation(fromx,fromy,tox,toy){
	var numberCell = $('#number-cell-'+fromx+'-'+fromy);
	numberCell.animate({
		top:getPosTop(tox,toy),
		left:getPosLeft(tox,toy)
	},70);
}

function updateScore(score){
	$("#score").text(score);
}
