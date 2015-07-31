<?php
namespace Home\Model;
use Think\Model;
class UserAppointModel extends Model {
	//获取用户累计兑换总数
	public function get_total_luck_count(){
		$total_luck_count = $this -> where("APP_RESULT = 1") -> count();
		return intval($total_luck_count);
	}		
}
	