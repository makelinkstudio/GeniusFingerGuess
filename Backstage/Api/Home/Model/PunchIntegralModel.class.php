<?php
namespace Home\Model;
use Think\Model;
class PunchIntegralModel extends Model {
	//插入猜拳积分流水表记录
	public function insert_punch_integral($mat_id){
		//获取场次英文代号
		$fun_matches = D('FunMatches');
		$mat_code_en = $fun_matches -> field("MAT_CODE_EN") -> where("MAT_ID = ".$mat_id) -> select();
		$mat_code_en = $mat_code_en[0]['MAT_CODE_EN'];
		//获取场次积分
		$integral_config = D('IntegralConfig');
		$integral = $integral_config -> get_punch_integral_config($mat_id);
		$data['USER_ID'] = $_SESSION['uid'];
		$data['INTEGRAL'] = -intval($integral);
		$data['SET_TIME'] = time();
		$data['PUN_ITL_TYPE'] = $mat_code_en;
		$data['OBJ_ID'] = $mat_id;
		$result = $this -> data($data) -> add();
		if($result){
			return TRUE;
		}else{
			return FALSE;
		}
	}		
	
	//获取用户积分变动
	public function get_user_punch_integral_change($mat_id){
		$data = $this -> field("INTEGRAL") -> where("OBJ_ID = ".$mat_id) -> order("PUN_ITL_ID desc") -> limit(1) -> select();
		return intval($data[0]['INTEGRAL']);
	}
	
}
	