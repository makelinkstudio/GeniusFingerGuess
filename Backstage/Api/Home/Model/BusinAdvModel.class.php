<?php
namespace Home\Model;
use Think\Model;
class BusinAdvModel extends Model {
	//获取商品列表
	public function get_adv_list($page){
		//获取商品记录总页数
		$avd_count = intval($this -> where("AVD_STATUS = 0") -> count());
		if($avd_count === 0){
			return array('TOTAL_PAGE' => 1,
						 'PAGE' => 1,
						 'AVD_COUNT' => 0,
						 'AVD_LIST' => null
			);
			exit;
		}
		$total_page = cal_page($avd_count,10);//获取总页数
		if($total_page === FALSE){
			json_output('050','Incomming page is wrong!');
			exit;
		}
		if($page > $total_page){
			json_output('051','Incoming page is greater than max page! The Last page is '.$total_page);
			exit;
		}
		$adv_list_raw = $this -> field("AVD_ID,BUSIN_ID,AVD_TITLE,AVD_INTEGRAL,AVD_IMG_URL,AVD_ETIME") -> where("AVD_STATUS = 0") -> select();
		$adv_list = array();
		$user_appoint = D('UserAppoint');
		for($i=0;$i<count($adv_list_raw);$i++){
			$busin_id = intval($adv_list_raw[$i]['BUSIN_ID']);
			$inf_busin = D('InfBusiness') -> get_businname_from_id($busin_id);
			if($inf_busin === FALSE){
				$busin_name = 'Undefine';
			}else{
				$busin_name = $inf_busin;
			}
			$check_appoint = $user_appoint -> check_user_appoint($_SESSION['uid'],$adv_list_raw[$i]['AVD_ID']);
			if($check_appoint){
				$appoint = TRUE;
			}else{
				$appoint = FALSE;
			}
			//获取收藏状态
			$user_collection = D('UserCollection') -> get_avd_status($adv_list_raw[$i]['AVD_ID']);
			if($user_collection === TRUE){
				$collection = TRUE;//可收藏
			}else{
				$collection = FALSE;//不可收藏
			}	
			$appoint_count = $user_appoint -> get_appoint_count_from_avdid($adv_list_raw[$i]['AVD_ID']);
			$adv_list[$i] = array('AVD_ID' => $adv_list_raw[$i]['AVD_ID'],
								  'BUSIN_NAME' => $busin_name,
								  'AVD_TITLE' => $adv_list_raw[$i]['AVD_TITLE'],
								  'AVD_INTEGRAL' => $adv_list_raw[$i]['AVD_INTEGRAL'],
								  'AVD_IMG_URL' => "/Public/img/adv_good_img/".$adv_list_raw[$i]['AVD_IMG_URL'],
								  'AVD_ETIME' => $adv_list_raw[$i]['AVD_ETIME'],
								  'APPOINT_STATUS' => $appoint,
								  'COLLECT_STATUS' => $collection,
								  'APPOINT_COUNT' => $appoint_count
			);
		}
		return array('TOTAL_PAGE' => $total_page,
					 'PAGE' => $page,
					 'AVD_COUNT' => $avd_count,
					 'AVD_LIST' => $adv_list
		);
		//return $user_appoint = D('UserAppoint') -> get_appoint_count_from_avdid(1);
	}

	//查询商品是否可预约 
	public function check_appoint_available($avd_id,$uid){
		$check_appoint = $this ->  where("AVD_ID = ".$avd_id) -> select();
		if($check_appoint === null){
			json_output('059','Appoint goods is not exists!');
			exit;
		}
		$avd_integral = $this -> get_avd_integral($avd_id);//兑换所需要
		$avd_num = $this -> get_avd_num($avd_id);//可兑换数量
		$avd_status = $this -> get_avd_status($avd_id);//商品状态
		if($avd_status != 0){
			json_output('060','This goods is not allow to appoint!');
			exit;
		}
//		if($avd_num === 0){
//			json_output('061','Not enough goods for appoint!');
//			exit;
//		}
		//获取用户积分
		$user_integral = D('User') -> get_user_integral();
		if($user_integral < $avd_integral){
			json_output('062','User integral is not enough!');
			exit;
		}
		return TRUE;//满足预约条件 返回 TRUE
	}
	
	//获取当前商品所需积分
	public function get_avd_integral($avd_id){
		$avd_integral = $this -> field("AVD_INTEGRAL") -> where("AVD_ID = ".$avd_id) -> select();
		if($avd_integral === null){
			return FALSE;
			exit;
		}else{
			return $avd_integral = intval($avd_integral[0]['AVD_INTEGRAL']);
		}
	}
	
	//获取商品可兑换数量
	public function get_avd_num($avd_id){
		$avd_num = $this -> field("AVD_NUM") -> where("AVD_ID = ".$avd_id) -> select();
		if($avd_num === null){
			return FALSE;
			exit;
		}else{
			return intval($avd_num[0]['AVD_NUM']);
		}
	}
	
	//获取商品当前预约状态
	public function get_avd_status($avd_id){
		$avd_status = $this -> field("AVD_STATUS") -> where("AVD_ID = ".$avd_id) -> select();
		if($avd_status === null){
			return FALSE;
			exit;
		}else{
			return intval($avd_status[0]['AVD_STATUS']);
		}
	}
	
	//根据avd_id获取商品列表
	public function get_avd_by_avdid($avd_id){
		$avd_info = $this -> field("AVD_ID,BUSIN_ID,AVD_TITLE,AVD_INTEGRAL,AVD_NUM,AVD_IMG_URL,AVD_NUM,AVD_ETIME,AVD_JUMP_URL") -> where("AVD_ID = ".$avd_id) -> limit(1)  -> select();
		if($avd_info === null){
			return FALSE;
			exit;
		}
		//获取商家名字
		$busin_name = D('InfBusiness') -> get_businname_from_id($avd_info[0]['BUSIN_ID']);
		//获取预约状态
		$user_appoint = D('UserAppoint');
		$check_appoint = $user_appoint -> check_user_appoint($_SESSION['uid'],$avd_id);
		if($check_appoint){
			$appoint = TRUE;
		}else{
			$appoint = FALSE;
		}
		//获取收藏状态
		$user_collection = D('UserCollection') -> get_avd_status($avd_id);
		if($user_collection === TRUE){
			$collection = TRUE;//可收藏
		}else{
			$collection = FALSE;//不可收藏
		}
		return array('AVD_ID' => intval($avd_info[0]['AVD_ID']),
					 'BUSIN_NAME' => $busin_name,
					 'AVD_TITLE' => $avd_info[0]['AVD_TITLE'],
					 'AVD_INTEGRAL' => intval($avd_info[0]['AVD_INTEGRAL']),
					 'AVD_IMG_URL' => "/Public/img/adv_good_img/".$avd_info[0]['AVD_IMG_URL'],
					 'AVD_ETIME' => intval($avd_info[0]['AVD_ETIME']),
					 'APPOINT_STATUS' => $appoint,
					 'COLLECT_STATUS' => $collection,
					 'APPOINT_COUNT' => intval($avd_info[0]['AVD_NUM']),
					 'AVD_JUMP_URL' => $avd_info[0]['AVD_JUMP_URL']
		);
	}
}
