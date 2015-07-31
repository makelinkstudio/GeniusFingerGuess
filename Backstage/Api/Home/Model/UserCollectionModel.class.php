<?php
namespace Home\Model;
use Think\Model;
class UserCollectionModel extends Model {
	//用户收藏 C100
	public function user_collect(){
		//过滤输入参数
		if(!isset($_POST['data']['avd_id'])){
			json_output('058','No incoming parameters correctly!');
			exit;
		}else{
			$avd_id = $_POST['data']['avd_id'];
		}
		//检查用户是否已经收藏
		$check_collect = $this -> check_collect($avd_id);
		if($check_collect === TRUE){
			//如果没有收藏过 插入预约记录
			$data['AVD_ID'] = $avd_id;
			$data['USER_ID'] = $_SESSION['uid'];
			$data['UC_STATUS'] = 0;
			$data['UC_TIME'] = time();
			$insertId = $this -> data($data) -> add();
			if($insertId){
				json_output('1','User collect succee!');
			}else{
				json_output('063','User collect fail!');
			}
		}else{
			//曾经搜藏过 更新预约记录
			$data['AVD_ID'] = $avd_id;
			$data['USER_ID'] = $_SESSION['uid'];
			$data['UC_STATUS'] = 0;
			$data['UC_TIME'] = time();
			$update = $this -> where("USER_ID = ".$_SESSION['uid']." AND AVD_ID = ".$avd_id) -> data($data)  ->save();
			if($update){
				json_output('1','User collect succee!');
			}else{
				json_output('063','User collect fail!');
			}
		}
	}
	
	//用户取消收藏 C101
	public function user_cancel_collect(){
		//过滤输入参数
		if(!isset($_POST['data']['avd_id'])){
			json_output('064','No incoming parameters correctly!');
			exit;
		}else{
			$avd_id = $_POST['data']['avd_id'];
		}
		//检查用户是否已经收藏
		$check_collect = $this -> check_collect($avd_id);
		if($check_collect === TRUE){
			//如果用户没有收藏 跳出
			json_output('065','User has been not collect this goods!');
			exit;
		}else{
			$check_collect_status = $this -> check_collect_status($avd_id);
			if($check_collect_status === FALSE){
				//如果用户已经取消收藏 跳出
				json_output('066','User has been canceled before!');
				exit;
			}else{
				$data['AVD_ID'] = $avd_id;
				$data['USER_ID'] = $_SESSION['uid'];
				$data['UC_STATUS'] = 1;
				$data['UC_TIME'] = time();
				$update = $this -> where("USER_ID = ".$_SESSION['uid']." AND AVD_ID = ".$avd_id) -> data($data)  ->save();
				if($update){
					json_output('1','User cancel collect succee!');
				}else{
					json_output('067','User cancel collect fail!');
				}
			}
		}
	}
	
	//获取用户收藏列表
	public function get_user_collection_list($page){
		$collection_count = $this -> get_user_collection_count();
		if($collection_count === 0){
			return array('TOTAL_PAGE' => 1,
						 'PAGE' => 1,
						 'AVD_COUNT' => 0,
						 'AVD_LIST' => null
			);
			exit;
		}
		$total_page = cal_page($collection_count,10);//获取总页数
		if($total_page === FALSE){
			json_output('068','Incomming page is wrong!');
			exit;
		}
		if($page > $total_page){
			json_output('069','Incoming page is greater than max page! The Last page is '.$total_page);
			exit;
		}
		//获取用户当前积分
		$inf_user = D('User');
		$user_integral = $inf_user -> get_user_integral();
		//获取用户收藏avd_id
		$collection_avdid = $this -> get_user_collection_avdid($page);
		//循环遍历 构造商品列表数组
		$busin_adv = D('BusinAdv');
		$collection_list = array();
		for($i=0;$i<count($collection_avdid);$i++){
			$avd_id = intval($collection_avdid[$i]['AVD_ID']);
			$collection_list[$i] = $busin_adv -> get_avd_by_avdid($avd_id);
			$collection_list[$i]['AVD_INTEGRAL_DIFF'] = intval($collection_list[$i]['AVD_INTEGRAL']) - $user_integral;
			if($collection_list[$i]['AVD_INTEGRAL_DIFF'] < 0){
				$collection_list[$i]['AVD_INTEGRAL_DIFF'] = 0;
			}
		}
		return array('TOTAL_PAGE' => $total_page,
					 'PAGE' => $page,
					 'AVD_COUNT' => $collection_count,
					 'AVD_LIST' => $collection_list
		);
	}
	
	//查询用户是否曾经收藏
	public function check_collect($avd_id){
		$check_collect = $this -> where("AVD_ID = ".$avd_id." AND USER_ID = ".$_SESSION['uid']) -> select();
		if($check_collect === null){
			return TRUE; //没有预约过
			exit;
		}else{
			return FALSE;//预约过
			exit;
		}
	}
	
	//查询用户特定商品当前搜藏状态  
	public function check_collect_status($avd_id){
		$check_collect_status = $this -> field("UC_STATUS") -> where("AVD_ID = ".$avd_id." AND USER_ID = ".$_SESSION['uid']) -> select();
		$check_collect_status = intval($check_collect_status[0]['UC_STATUS']);
		if($check_collect_status === 0){
			return TRUE; //不可收藏
			exit;
		}else{
			return FALSE;//可以搜藏
			exit;
		}
	}
	
	//获取用户指定商品收藏状态
	public function get_avd_status($avd_id){
		$avd_status_check_collect = $this -> check_collect($avd_id);
		if($avd_status_check_collect === FALSE){
			$avd_check_status = $this -> check_collect_status($avd_id);
			if($avd_check_status === FALSE){
				return TRUE;//可以收藏
				exit;
			}else{
				return FALSE;//已经收藏
				exit;
			}
		}else{
			return TRUE;
			exit;
		}
	}
	
	//获取用户预约数量
	public function get_user_collection_count(){
		$uid = $_SESSION['uid'];
		$collection_count = intval($this -> where("USER_ID = ".$uid) -> count());
		return $collection_count;
	}
	
	//获取用户收藏avd_id列表
	public function get_user_collection_avdid($page){
		$uid = $_SESSION['uid'];
		$collection_avdid = $this -> field("AVD_ID") -> where("USER_ID = ".$uid." AND UC_STATUS != 1") -> page($page,10) -> select();
		return $collection_avdid;
	}
}
	