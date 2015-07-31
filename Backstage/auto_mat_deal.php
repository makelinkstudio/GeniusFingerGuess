<?php
	function curl_post_method() {
		$url = "http://localhost/speculate_cat.tp_bncq/automatiquement.php/Home/Index/b354578aa4b4121d59ab62ee1ffc2e1d56ba6";
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