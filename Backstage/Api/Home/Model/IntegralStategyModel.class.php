<?php
namespace Home\Model;
use Think\Model;
class IntegralStategyModel extends Model{
	//根据avd_id获取当前获取攻略 I100
	public function get_integral_stategy(){
		if(!isset($_POST['data']['avd_id'])){
			json_output('072','No incoming parameters correctly!');
			exit;
		}
		$avd_id = $_POST['data']['avd_id'];
		//获取用户当前积分
		$inf_user = D('User');
		$user_integral = $inf_user -> get_user_integral();
		//获取当前兑换商品所需积分
		$busin_avd = D('BusinAdv');
		$avd_integral = $busin_avd -> get_avd_integral($avd_id);
		if($avd_integral === FALSE){
			json_output('071','This goods is not exists!');
			exit;
		}
		$progress = round(($user_integral / $avd_integral),4) * 100;//获取进度 百分比 2位
		//获取当前场次可玩状态
		
	}
}
