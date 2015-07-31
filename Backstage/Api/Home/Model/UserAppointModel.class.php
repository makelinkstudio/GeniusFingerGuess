<?php
namespace Home\Model;
use Think\Model;
class UserAppointModel extends Model {
	//根据avd_id获取商品预约人数
	public function get_appoint_count_from_avdid($avd_id){
		$appoint_count = intval($this -> where("AVD_ID = ".$avd_id) -> count());
		return $appoint_count;
	}
	
	//根据用户uid adv_id判断用户当前商品是否已经预约
	public function check_user_appoint($uid,$adv_id){
		$result = intval($this -> where("USER_ID = ".$uid." AND AVD_ID = ".$adv_id) -> count());
		if($result === 0){
			return FALSE;
		}else{
			return TRUE;
		}
	}
	
	//用户预约兑奖 A000
	public function user_appoint(){
		//判断传入参数
		if(!isset($_POST['data']['avd_id'])){
			json_output('052','No incoming parameters correctly!');
			exit;
		}
		$avd_id = $_POST['data']['avd_id'];
		$check_appoint = $this -> check_appoint($avd_id);
		if($check_appoint === FALSE){
			json_output('056','User has been appointed before!');
			exit;
		}
		$uid = $_SESSION['uid'];
		//查询用户是否满足预约条件 
		$busin_adv = D('BusinAdv');
		$busin_availavle = $busin_adv ->  check_appoint_available($avd_id,$uid);
		if($busin_availavle){
			//获取当前兑换商品积分
			$avd_integral = $busin_adv -> get_avd_integral($avd_id);
			//扣除用户积分
			D('User') -> update_integral(-($avd_integral));
			//新增用户预约表记录
			$data['AVD_ID'] = $avd_id;
			$data['USER_ID'] = $_SESSION['uid'];
			$data['APP_RESULT'] = 0;
			$data['APP_TIME'] = time();
			$insertId = $this -> data($data) -> add();
			//添加预约流水记录 更新插入shop_integral表
			$shop_integral = D('ShopIntegral');
			$shop_integral -> insert_shop_integral($avd_id);
			if($insertId){
				//减少商品可兑换数量
//				$avd_num = $busin_adv -> get_avd_num($avd_id);
//				$update_num = $avd_num - 1;
//				$data = null;
//				$data['AVD_NUM'] = $update_num;
//				$update = $busin_adv -> where("AVD_ID = ".$avd_id) -> data($data) -> save();
//				if($update){
//					json_output('1','Appoint succee!');
//				}else{
//					json_output('055','Update avd num fail!');
//				}
				json_output('1','Appoint succee!');
			}else{
				json_output('054','Insert data fail!');
			}
		}else{
			json_output('053','Appoint fail!');
			exit;
		}
	}

	//查询用户是否已经预约指定商品
	public function check_appoint($avd_id){
		$check_appoint = $this -> where("AVD_ID = ".$avd_id." AND USER_ID = ".$_SESSION['uid']) -> select();
		if($check_appoint === null){
			return TRUE;
			exit;
		}else{
			return FALSE;
			exit;
		}
	}
	
	//获取用户已预约产品$avd_id
	public function get_user_appoint_avdid($page){
		$uid = $_SESSION['uid'];
		$appoint_avd_id = $this -> field("AVD_ID") -> where("USER_ID = ".$uid) -> order("AVD_ID DESC") -> page($page,10) -> select();
		if($appoint_avd_id === null){
			return FALSE;
			exit;
		} 
		return $appoint_avd_id;
	}
	
	//获取用户预约产品数量
	public function get_user_appoint_count(){
		$uid = $_SESSION['uid'];
		$user_appoint_count = intval($this -> where("USER_ID = ".$uid) -> count());
		return $user_appoint_count;
	}
	
	//获取用户我的预约商品列表
	public function get_myappoint_list($page){
		//查询用户预约产品数量
		$appoint_count = $this -> get_user_appoint_count();
		//若用户没有预约任何产品
		if($appoint_count === 0){
			return array('TOTAL_PAGE' => 1,
						 'PAGE' => 1,
						 'AVD_COUNT' => 0,
						 'AVD_LIST' => null
			);
			exit;
		}
		$total_page = cal_page($appoint_count,10);//获取总页数
		if($total_page === FALSE){
			json_output('057','Incomming page is wrong!');
			exit;
		}
		if($page > $total_page){
			json_output('058','Incoming page is greater than max page! The Last page is '.$total_page);
			exit;
		}
		//获取用户预约产品avd_id
		$appoint_avdid = $this -> get_user_appoint_avdid($page);
		//循环遍历 构造商品列表数组
		$busin_adv = D('BusinAdv');
		$appoint_list = array();
		for($i=0;$i<count($appoint_avdid);$i++){
			$avd_id = intval($appoint_avdid[$i]['AVD_ID']);
			$appoint_list[$i] = $busin_adv -> get_avd_by_avdid($avd_id);
			$appoint_list[$i]['AVD_COUNTDOWN'] = intval($appoint_list[$i]['AVD_ETIME'] - time());
			if($appoint_list[$i]['AVD_COUNTDOWN'] < 0){
				$appoint_list[$i]['AVD_COUNTDOWN'] = 0;
			} 
		}
		return array('TOTAL_PAGE' => $total_page,
					 'PAGE' => $page,
					 'AVD_COUNT' => $appoint_count,
					 'AVD_LIST' => $appoint_list
		);
	}
}
	