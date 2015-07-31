<?php
namespace Home\Model;
use Think\Model;
class CommunityIntegralModel extends Model {
	//用户发表话题，更新插入积分表
	public function deliver_update(){
		$uid = $_SESSION['uid'];
		//获取发帖获得积分
		$integral_config = D('IntegralConfig');
		$ftjf = $integral_config -> get_deliver_topic_integral_config();
		$set_time = time();
		$data['USER_ID'] = $uid;
		$data['INTEGRAL'] = $ftjf;
		$data['SET_TIME'] = $set_time;
		$data['CMNTY_TYPE'] = 1;
		$deliver_update = $this -> data($data) -> add();
	}
	
	//用户回复话题，更新插入积分表
	public function reply_topic(){
		$uid = $_SESSION['uid'];
		//获取回复获得积分
		$integral_config = D('IntegralConfig');
		$htjf = $integral_config -> get_reply_topic_integral_config();
		$set_time = time();
		$data['USER_ID'] = $uid;
		$data['INTEGRAL'] = $htjf;
		$data['SET_TIME'] = $set_time;
		$data['CMNTY_TYPE'] = 2;
		$reply_topic = $this -> data($data) -> add();
	}
	
	//用户签到，更新插入积分 (弃用 参考UserIntehgral相应方法)
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
			$data['CMNTY_TYPE'] = 0;
			$reply_topic = $this -> data($data) -> add();
		}
	}
	
	//查询用户当前是否已经签到 (弃用 参考UserIntehgral相应方法)
	public function check_sign($uid){
		$check_sign = intval($this -> where("USER_ID = ".$uid." AND CMNTY_TYPE = 0 AND FROM_UNIXTIME(SET_TIME,'%Y %D %M')=FROM_UNIXTIME(UNIX_TIMESTAMP(),'%Y %D %M')") -> count());
		if($check_sign === 1){
			return FALSE;
			exit;
		}
		return TRUE;
	}
}
