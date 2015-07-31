<?php
namespace Home\Model;
use Think\Model;
class InfUserModel extends Model {
	//获取系统总注册用户量
	public function get_total_user_count(){
		$total_user_count = $this -> count();
		return intval($total_user_count);
	}		
	
	//获取当前系统总积分
	public function get_sys_total_integral(){
		$total_intagral = $this -> sum("INTEGRAL");
		return intval($total_intagral);
	}
}
	