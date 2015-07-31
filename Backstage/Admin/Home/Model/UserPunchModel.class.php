<?php
namespace Home\Model;
use Think\Model;
class UserPunchModel extends Model {
	//获取用户累计参与猜拳次数
	public function get_user_punch_count(){
		$user_punch_count = $this -> count();
		return intval($user_punch_count);
	}
	
	//获取人均参与猜拳次数
	public function get_avg_punch_count(){
		$inf_user = D('InfUser');
		$total_user_count = $inf_user -> get_total_user_count();
		$user_punch_count = $this -> get_user_punch_count();
		$avg_punch_count = floatval($user_punch_count / $total_user_count);
		return $avg_punch_count;
	}
}
	