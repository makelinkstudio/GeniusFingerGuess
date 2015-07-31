<?php
	function curl_post_method() {
		$url = "http://localhost/speculate_cat.tp_bncq/automatiquement.php/Home/Index/c2c99601e9b4dda217fa6bc44adac8682d921d";
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL, $url);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($ch, CURLOPT_POST, 1);
		curl_setopt($ch, CURLOPT_POSTFIELDS,null);
		$output = curl_exec($ch);
		curl_close($ch);
		return $output;
	}
	curl_post_method();
	echo 'success';