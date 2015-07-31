<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0 user-scalable=no">
		<meta charset="utf-8">
		
		<title>BNCQ</title>
		<link rel="stylesheet" type="text/css" href="/tp_bncq/Public/bncq2048/2048.css"/>
		<script type="text/javascript">
			var path = '/tp_bncq/Public/bncq2048/';
		</script>
		<script src="/tp_bncq/Public/bncq2048/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
		<script src="/tp_bncq/Public/bncq2048/showanimation2048.js" type="text/javascript" charset="utf-8"></script>
		<script src="/tp_bncq/Public/bncq2048/support2048.js" type="text/javascript" charset="utf-8"></script>
		<script src="/tp_bncq/Public/bncq2048/main2048.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<header>
			<h1><span class="green">爆</span><span class="yello">脑</span><span class="pink">矩</span><span class="gray">阵</span></h1>
			<a href="javascript:newgame();" id="newgamebutton">新游戏</a>
			<a href="javascript:rerandomcell();" id="rerandomcell">随机重置 剩余<span id="reRandomcellCount">1</span>次</a>
		</header>
		<div id="grid-container">
			<div class="grid-cell" id="grid-cell-0-0"></div>
			<div class="grid-cell" id="grid-cell-0-1"></div>
			<div class="grid-cell" id="grid-cell-0-2"></div>
			<div class="grid-cell" id="grid-cell-0-3"></div>
			
			<div class="grid-cell" id="grid-cell-1-0"></div>
			<div class="grid-cell" id="grid-cell-1-1"></div>
			<div class="grid-cell" id="grid-cell-1-2"></div>
			<div class="grid-cell" id="grid-cell-1-3"></div>
			
			<div class="grid-cell" id="grid-cell-2-0"></div>
			<div class="grid-cell" id="grid-cell-2-1"></div>
			<div class="grid-cell" id="grid-cell-2-2"></div>
			<div class="grid-cell" id="grid-cell-2-3"></div>
			
			<div class="grid-cell" id="grid-cell-3-0"></div>
			<div class="grid-cell" id="grid-cell-3-1"></div>
			<div class="grid-cell" id="grid-cell-3-2"></div>
			<div class="grid-cell" id="grid-cell-3-3"></div>
			
			
		</div>
		<div class="mask" id="lost">
			<p id="losttips">You Lost!</p>
			<a href="javascript:newgame();" id="regamebutton">再来一次</a>
		</div>
		<div class="mask" id="win">
			<p id="wintips">You Win!</p>
			<a href="javascript:newgame();" id="renewgamebutton">再来一次</a>
		</div>
	</body>
</html>