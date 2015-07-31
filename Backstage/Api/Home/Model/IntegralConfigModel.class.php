<?php
namespace Home\Model;
use Think\Model;
class IntegralConfigModel extends Model{
	//获取签到积分配置
	public function get_sign_integral_config(){
		$integral = $this -> field("CON_INTEGRAL") -> where("OBJ_NAME_EN = 'U0_QD'") -> select();
		return intval($integral[0]['CON_INTEGRAL']);
	}
	
	//获取发表话题积分配置
	public function get_deliver_topic_integral_config(){
		$integral = $this -> field("CON_INTEGRAL") -> where("OBJ_NAME_EN = 'C0_HT'") -> select();
		return intval($integral[0]['CON_INTEGRAL']);
	}
	
	//获取回复话题积分配置
	public function get_reply_topic_integral_config(){
		$integral = $this -> field("CON_INTEGRAL") -> where("OBJ_NAME_EN = 'C0_HF'") -> select();
		return intval($integral[0]['CON_INTEGRAL']);
	}
	
	//获取注册初始积分配置
	public function get_init_resgster_integral_config(){
		$integral = $this -> field("CON_INTEGRAL") -> where("OBJ_NAME_EN = 'U0_CS'") -> select();
		return intval($integral[0]['CON_INTEGRAL']);
	}
	
	//获取猜拳场次所需积分
	public function get_punch_integral_config($mat_id){
		$fun_matches = D('FunMatches');
		$mat_code_en = $fun_matches -> field("MAT_CODE_EN") -> where("MAT_ID = ".$mat_id) -> select();
		$mat_code_en = $mat_code_en[0]['MAT_CODE_EN'];
		$integral = $this -> field("CON_INTEGRAL") -> where("OBJ_NAME_EN = '".$mat_code_en."'") -> select();
		return intval($integral[0]['CON_INTEGRAL']);
	}
}
	