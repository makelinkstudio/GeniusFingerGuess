<?php
namespace Home\Model;
use Think\Model;
class UserIntegralModel extends Model {
	//用户签到，更新插入积分
	public function sign_update($uid){
		if( ($this -> check_sign($uid)) === FALSE ){
			return FALSE;
		}else{
			$integral_config = D('IntegralConfig');
			$qdjf = $integral_config -> get_sign_integral_config();
			$set_time = time();
			$data['USER_ID'] = $uid;
			$data['INTEGRAL'] = $qdjf;
			$data['SET_TIME'] = $set_time;
			$data['USER_ITL_TYPE'] = 'U0_QD';
			$reply_topic = $this -> data($data) -> add();
		}
	}
	
	//查询用户当前是否已经签到
	public function check_sign($uid){
		$check_sign = intval($this -> where("USER_ID = ".$uid." AND USER_ITL_TYPE = 'U0_QD' AND FROM_UNIXTIME(SET_TIME,'%Y %D %M')=FROM_UNIXTIME(UNIX_TIMESTAMP(),'%Y %D %M')") -> count());
		if($check_sign === 1){
			return FALSE;
			exit;
		}
		return TRUE;
	}		
	
	//用户注册，更新插入积分
	public function regist_update($uid){
		if( ($this -> check_sign($uid)) === FALSE ){
			return FALSE;
		}else{
			$integral_config = D('IntegralConfig');
			$csjf = $integral_config -> get_init_resgster_integral_config();
			$set_time = time();
			$data['USER_ID'] = $uid;
			$data['INTEGRAL'] = $csjf;
			$data['SET_TIME'] = $set_time;
			$data['USER_ITL_TYPE'] = 'U0_CS';
			$reply_topic = $this -> data($data) -> add();
		}
	}
}
	