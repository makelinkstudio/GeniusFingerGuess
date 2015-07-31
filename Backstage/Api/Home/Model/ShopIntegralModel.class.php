<?php
namespace Home\Model;
use Think\Model;
class ShopIntegralModel extends Model {
	//插入商品预约记录
	public function insert_shop_integral($avd_id){
		$uid = $_SESSION['uid'];
		$data['USER_ID'] = $uid;
		//获取商品所需兑换积分
		$busin_avd = D('BusinAdv');
		$integral = $busin_avd -> get_avd_integral($avd_id);
		$data['INTEGRAL'] = $integral;
		$data['SET_TIME'] = time();
		$data['SHOP_ITL_TYPE'] = 'A0_YY';
		$data['OBJ_ID'] = $avd_id;
		$insert_shop_integral = $this -> data($data) -> add(); 
	}
	
	//获取当前用户冻结积分
	public function get_user_freeze_integral(){
		$uid = $_SESSION['uid'];
		$user_freeze_integral = $this -> where("USER_ID = ".$uid) -> sum("INTEGRAL");
		return $user_freeze_integral;
	}
}
	