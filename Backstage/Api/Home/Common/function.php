<?php
//分拣方法
function sort_out_code($code){
	$model_code = substr($code,0,2);
	if(C($model_code) != null){
		$model = C($model_code);
		//判断传入代码方法是否存在
		if(C($code) != null){
			$call_method = C($code);
			eval("D('".$model."') -> ".$call_method."();"); 
		}else{
			json_output('002','Request api not found!');
		}
	}else{
		json_output('003','Request api model not found!');
	}
}
//json返回
function json_output($return,$data){
	$json_output = array('return' => $return, 'data' => $data);
	print_r(json_encode($json_output));
}

//计算页数
function cal_page($count,$each_page){
	if($each_page === 0 || !(is_int($count) && is_int($each_page))){
		return FALSE;
		exit;
	}
	$cal_page = ceil($count/$each_page);
	return intval($cal_page);
}

