<?php
namespace Home\Model;
use Think\Model;
class InfBusinessModel extends Model {
	//获取合作商家数
	public function get_total_adv_count(){
		$total_adv_count = $this -> count();
		return intval($total_adv_count);
	}		
}
	