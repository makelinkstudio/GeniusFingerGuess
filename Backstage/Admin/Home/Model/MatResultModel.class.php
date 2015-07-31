<?php
namespace Home\Model;
use Think\Model;
class MatResultModel extends Model {
	//获取累计投入总积分
	public function get_total_input_intergal(){
		$total_input_integral = $this -> sum("WIN_INTEGRAL");
		return intval($total_input_integral);
	}		
}
	