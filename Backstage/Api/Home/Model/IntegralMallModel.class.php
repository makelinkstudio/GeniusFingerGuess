<?php
namespace Home\Model;
use Think\Model;
class IntegralMallModel extends Model {
	//获取兑换大厅数据 I000
	public function get_integralmall_data(){
		if(!isset($_POST['data']['page'])){
			$page = 1;
		}else{
			$page = $_POST['data']['page'];
		}
		$adv_list = D('BusinAdv') -> get_adv_list($page);
		//dump($adv_list);
		json_output('1',$adv_list);
	}
	
	//获取我的预约数据 I001
	public function get_myappoint_data(){
		if(!isset($_POST['data']['page'])){
			$page = 1;
		}else{
			$page = $_POST['data']['page'];
		}
		$user_appoint = D('UserAppoint');
		$myappoint_list = $user_appoint -> get_myappoint_list($page);
		json_output('1',$myappoint_list);
		//dump($myappoint_list);
	}
	
	//获取用户我的收藏数据 I002
	public function mycollection(){
		if(!isset($_POST['data']['page'])){
			$page = 1;
		}else{
			$page = $_POST['data']['page'];
		}
		$mycollection = D('UserCollection') -> get_user_collection_list($page);
		json_output('1',$mycollection);
		//dump($mycollection);
	}
	
	//根据avd_id获取商品数据 I003
	public function get_by_avd_id(){
		if(!isset($_POST['data']['avd_id'])){
			json_output('083', 'No incoming parameters correctly!');
			exit;
		}
		$avd_id = $_POST['data']['avd_id'];
		$avd_list = D('BusinAdv') -> get_avd_by_avdid($avd_id);
		json_output('1', $avd_list);
	}
}
	