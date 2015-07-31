<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html>

	<head>
		 <meta charset="utf-8">
   		 <meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title></title>
		<link href="/speculate_cat.tp_bncq/Public/css/bootstrap.min.css" rel="stylesheet">
	</head>

	<body>
		<div class="container">
			<div class="row">
				<div class="col-md-8 col-lg-offset-2">
					<form action="/speculate_cat.tp_bncq/index.php/Home/Index/check_cross" method="post" style="margin-top: 30px;">
						<div class="form-group">
							<input class="form-control" type="password" name="passwd" />
						</div>
						<div class="form-group">
							<input class="btn btn-info" type="submit" value="Cross" />
						</div>
					</form>
				</div>
			</div>
		</div>

		<script src="/speculate_cat.tp_bncq/Public/js/jquery.min.js"></script>
		<script src="/speculate_cat.tp_bncq/Public/js/bootstrap.min.js"></script>
	</body>

</html>