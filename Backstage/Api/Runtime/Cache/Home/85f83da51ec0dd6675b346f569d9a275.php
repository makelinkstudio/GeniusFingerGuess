<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html>
	<head>
		<title></title>
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" charset="utf-8" />
		<script src="/speculate_cat.tp_bncq/Public/js/chart.min.js"></script>
		<script src="/speculate_cat.tp_bncq/Public/js/jquery.min.js"></script>
		<script type="text/javascript">
			 //构建信息图方法
			$(document).ready(function() {
				var dataL = {
					labels : ["9:00","9:15","9:30","9:45","10:00","10:15","10:30","10:45","11:00","11:15"],
					datasets : [
						{
							fillColor : "rgba(0,0,0,0.1)",
							strokeColor : "rgba(15,35,64,0.6)",
							pointColor : "rgba(15,35,64,0.2)",
							pointStrokeColor : "#fff",
							data : [65,59,90,81,56,55,40]
						},
						{
							fillColor : "rgba(0,0,0,0.1)",
							strokeColor : "rgba(60,156,123,0.6)",
							pointColor : "rgba(60,156,123,0.8)",
							pointStrokeColor : "#fff",
							data : [28,48,40,19,96,27,100]
						},
						{
							fillColor : "rgba(0,0,0,0.1)",
							strokeColor : "rgba(124,240,72,0.6)",
							pointColor : "rgba(124,240,72,0.8)",
							pointStrokeColor : "#fff",
							data : [40,52,3,70,80,15,60]
						}
					]
				};
				var dataB = {
					labels : ["9:00","9:15","9:30","9:45","10:00","10:15","10:30","10:45","11:00","11:15"],
					datasets : [
						{
							fillColor : "rgba(241,196,15,0.6)",
							strokeColor : "rgba(241,196,15,0.6)",
							data : [65,59,90,81,56,55,40]
						},
						{
							fillColor : "rgba(232,72,61,0.6)",
							strokeColor : "rgba(232,72,61,0.6)",
							data : [28,48,40,19,96,27,100]
						}
					]
				};
				var width = document.body.clientWidth; //保持两次执行宽度数据一致
				draw("punRet",width,dataL,0);
				draw("campRet",width,dataB,1);
			});
			function draw(parDivId, width, data,type) {
				var options={
					scaleShowGridLines :false, //消背景网格
					pointDot : false, //消点
					datasetFill:false, //消背景填充
					animation:false //消动画
				}
				//生成canvas对象
				var canvas = document.createElement("canvas");
				canvas.setAttribute("width", width*0.95);
				canvas.setAttribute("height", width*0.70);
				//画图
				var ctx = canvas.getContext("2d");
				if(type==0){
					new Chart(ctx).Line(data,options);
				}else if(type==1){
					new Chart(ctx).Bar(data,options);
				}
				var parDiv = document.getElementById(parDivId);
				parDiv.appendChild(canvas);
			}
			
		</script>
		<style>
			*{
				font-family: "Microsoft YaHei Light";
			}
			body{
				margin:0;
				padding:0;
			}
			table {   
					border-collapse:collapse;
					padding:0;
					maring:0;
				} 
				.topTitle{
					font-size:15px;
					color:#666666;
					text-align:center;
					font-weight:bold;
					padding:10px 0 10px 0;
				}
				.hold_legend{
					width:auto;
					height:60px;
				}
				.r_legend{
					width:20px;
					height:20px;
					display:inline-block;
				}
				.legend_font{
					display:inline-block;
					margin-right:5px;
					font-size:15px;
					font-weight:bold;
					color:#666666;
				}
				.A_color{
					background-color:#F1C40F;
				}
				.B_color{
					background-color:#E8483D;
				}
				.J_color{
					background-color:#0F2340;
				}
				.S_color{
					background-color:#3C9C7B;
				}
				.BU_color{
					background-color:#7CF048;	
				}
		</style>
	</head>
	<body style="background-color:#F3F3F3">
		<table >
			<tr>
				<td rowspan="3" width="5%"></td>
				<td>
					<div class="topTitle">出拳人数趋势图</div>
				</td>
				<td rowspan="3" width="5%"></td> 
			</tr>
			<tr>
				<td colspan="3">
					<div id="punRet"></div>
				</td>
			</tr>
			<tr colspan="3">
				<td align="center" >
					<div class="hold_legend">
						<div class="r_legend J_color">&nbsp;</div>
						<div class="legend_font">剪刀</div>
						<div class="r_legend S_color">&nbsp;</div>
						<div class="legend_font">石头</div>
						<div class="r_legend BU_color">&nbsp;</div>
						<div class="legend_font">布</div>
					</div>
				</td>
			</tr>
		</table>
		<table >
			<tr>
				<td rowspan="3" width="5%"></td>
				<td>
					<div class="topTitle">阵营人数趋势图</div>
				</td>
				<td rowspan="3" width="5%"></td> 
			</tr>
			<tr>
				<td colspan="3">
					<div id="campRet"></div>
				</td>
			</tr>
			<tr colspan="3">
				<td align="center" >
					<div class="hold_legend">
						<div class="r_legend A_color">&nbsp;</div>
						
						<div class="legend_font">A</div>
						<div class="r_legend B_color">&nbsp;</div>
						<div class="legend_font">B</div>
					</div>
				</td>
			</tr>
		</table>
		<table>
			<tr>
				<td rowspan="3" width="5%"></td>
				<td>
					<div class="topTitle">阵营人数趋势图</div>
				</td>
				<td rowspan="3" width="5%"></td> 
			</tr>
			<tr>
				<td colspan="3">
					<div id="campRet"></div>
				</td>
			</tr>
			<tr colspan="3">
				<td align="center" >
					<div class="hold_legend">
						<div class="r_legend A_color">&nbsp;</div>
						
						<div class="legend_font">A</div>
						<div class="r_legend B_color">&nbsp;</div>
						<div class="legend_font">B</div>
					</div>
				</td>
			</tr>
		</table>
	</body>

</html>