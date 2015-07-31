<?php
namespace Home\Model;
use Think\Model;
class PunchModel extends Model {
	protected $tableName = 'user_punch';
	//获取当前场次信息 P000
	public function get_now_matid() {
		$funmatches = D('FunMatches');
		$mat_id = $funmatches -> get_now_matid();
		if ($mat_id === 0) {
			json_output('021', 'There is not matches is available!');
		} else {
			$mat_code = $funmatches -> get_this_next_matcode($mat_id);
			json_output('1', $mat_code);
		}
	}

	//获取用户当前出拳状态 P001
	public function get_user_punch() {
		if(!isset($_POST['data']['mat_id'])){
			json_output('073', 'No incoming parameters correctly!');
			exit;
		}
		$mat_id = $_POST['data']['mat_id'];
		$uid = $_SESSION['uid'];
		$username = $_SESSION['username'];
		$user_punch = D('UserPunch');
		//获取用户出拳信息
		$user_punch_info = $user_punch -> where("USER_ID = ".$uid." AND MAT_ID = ".$mat_id) -> select();
		if ($user_punch_info === null) {
				json_output('022', 'User has not been punched!');
		} else {
				$user_punch_info_output = array('USER_ID' => $uid, 'USER_NAME' => $username, 'U_CAMP' => $user_punch_info[0]['U_CAMP'], 'U_PUNCH' => $user_punch_info[0]['U_PUNCH']);
				json_output('1', $user_punch_info);
		}
//		$funmatches = D('FunMatches');
//		$mat_id = $funmatches -> get_now_matid();
//		if ($mat_id === 0) {
//			json_output('022', 'There is not matches is available!');
//		} else {
//			$uid = $_SESSION['uid'];
//			$username = $_SESSION['username'];
//			$punch = D('Punch');
//			$user_punch = $punch -> field("U_CAMP,U_PUNCH") -> where("USER_ID = " . $uid . " AND MAT_ID = " . $mat_id) -> select();
//			if ($user_punch === null) {
//				json_output('022', 'User has not been punched!');
//			} else {
//				$user_punch_info = array('USER_ID' => $uid, 'USER_NAME' => $username, 'U_CAMP' => $user_punch[0]['U_CAMP'], 'U_PUNCH' => $user_punch[0]['U_PUNCH']);
//				json_output('1', $user_punch_info);
//			}
//		}
	}

	//用户选择出拳 P002
	public function user_do_punch() {
		//判断传入
		if (!(isset($_POST['data']['u_camp']) && isset($_POST['data']['u_punch']) && isset($_POST['data']['mat_id']))) {
			json_output('024', 'No incoming parameters correctly!');
			exit ;
		}
		$mat_id = intval($_POST['data']['mat_id']);
		$u_camp = $_POST['data']['u_camp'];
		if (!($u_camp === 'A' || $u_camp === 'B')) {
			json_output('025', 'User camp select only A or B!');
			exit ;
		}
		$u_punch = intval($_POST['data']['u_punch']);
		if (!($u_punch === 0 || $u_punch === 1 || $u_punch === 2)) {
			json_output('026', 'User punch select only 0 or 1 or 2 !');
			exit ;
		}
		//验证提交场次是否允许出拳
		$fun_matches = D('FunMatches');
		$available = $fun_matches -> check_mat_available($mat_id);
		if($available === FALSE){
			json_output('027', 'This mat is not availavle!');
			exit ;
		}
		//判断是否曾经出拳
		$has_punched = $this -> judge_now_punch($mat_id);
		//获取当前场次所需要积分
		$integral_config = D('IntegralConfig');
		$integral = $integral_config -> get_punch_integral_config($mat_id);
		//获取用户积分
		$inf_user = D('User');
		$user_integral = $inf_user -> get_user_integral();
		if($user_integral < $integral){
			json_output('027', 'User integral is not enough for punch!');
			exit ;
		}
		if($has_punched === 0){
			//写入user_punch表
			$data['USER_ID'] = $_SESSION['uid'];
			$data['MAT_ID'] = $mat_id;
			$data['U_CAMP'] = $u_camp;
			$data['U_PUNCH'] = $u_punch;
			$data['CHOOSE_TIME'] = time();
			$result = $this -> data($data) -> add();
			if($result){
				$punch_integral = D('PunchIntegral');
				$result_punch_integral = $punch_integral -> insert_punch_integral($mat_id);
				if($result_punch_integral){
					$result_user_integral = $inf_user -> update_integral(-($integral));
					if($result_user_integral === 1){
						json_output('1', 'User punch succee!');
					}else{
						json_output('074', 'Update user integral error!');
						exit;
					}
				}else{
					json_output('075', 'Insert punch integral error!');
					exit;
				}
			}else{
				json_output('076', 'Insert punch error!');
				exit;
			}
		}else{
			//更新user_punch表
			$data['USER_ID'] = $_SESSION['uid'];
			$data['MAT_ID'] = $mat_id;
			$data['U_CAMP'] = $u_camp;
			$data['U_PUNCH'] = $u_punch;
			$data['CHOOSE_TIME'] = time();
			$result = $this -> data($data) -> where("MAT_ID = ".$mat_id." AND USER_ID = ".$_SESSION['uid']) -> save();
			if($result){
				json_output('1', 'User punch succee!');
			}
		}
	}

	//获取猜拳主要信息 P003
	public function get_punch_info_main() {
		$u_mat_result = D('UMatResult');
		$record = $u_mat_result -> get_user_record();
		$user = D('User');
		$integral = $user -> get_user_integral();
		//获取用户是否已经签到
		$user_sign = D('UserIntegral') -> check_sign($_SESSION['uid']);
		if($user_sign === TRUE){
			$sign_status = TRUE;
		}else{
			$sign_status = FALSE;
		}
		$data = array('USER_NAME' => $_SESSION['username'],'INTEGRAL' => $integral,'SIGN_STATUS' => $sign_status,'RECORD' => $record);
		json_output('1',$data);
	}
	
	//获取当前场次信息及用户出拳状态 P004
	public function get_user_now_punch(){
		//判断输入参数
		if(!isset($_POST['data']['mat_id'])){
			json_output('076','No incoming parameters correctly!');
			exit;
		}
		$mat_id = $_POST['data']['mat_id'];
		$result = $this -> judge_now_punch($mat_id);
		if($result === 1){
			$data = $this -> where("MAT_ID = ".$mat_id." AND USER_ID = ".$_SESSION['uid']) -> select();
			$fun_matches = D('FunMatches');
			$mat_code = $fun_matches -> get_en_ch_code($mat_id);
			$result_output = array('MAT_CODE_EN' => $mat_code['MAT_CODE_EN'],
								   'MAT_CODE_CH' => $mat_code['MAT_CODE_CH'],
								   'U_PUNCH'     => $data[0]['U_PUNCH'],
								   'U_CAMP'      => $data[0]['U_CAMP']
			);
			json_output('1',$result_output);
		}else{
			json_output('077','User has been not punched!');
		}
	}
	
	//获取猜拳结果信息 P005
	public function get_mat_result(){
		if( !isset($_POST['data']['mat_id']) ){
			json_output('078','No incoming parameters correctly!');
			exit;
		}
		$mat_id = intval($_POST['data']['mat_id']);
		//判断用户是否参与场次
		if(($this -> judge_now_punch($mat_id)) === 0){
			json_output('079','User has not been punched this mat!');
			exit;
		}
		//判断用户胜负
		$u_mat_result = D('UMatResult');
		$user_mat_result = $u_mat_result -> get_user_mat_result($mat_id);
		//获取场次结果信息
		$mat_result = D("MatResult");
		$this_mat_result = $mat_result -> get_this_mat_result($mat_id);
		//获取用户积分变动
		$punch_integral = D("PunchIntegral");
		$user_punch_integral_change = $punch_integral -> get_user_punch_integral_change($mat_id);
		$mat_result_out_put = array('USER_MAT_RESULT' => $user_mat_result,'THIS_MAT_RESULT' => $this_mat_result,'USER_PUNCH_INTEFRAL_CHANGE' => $user_punch_integral_change);
		json_output('1', $mat_result_out_put);
		//dump($mat_result_out_put);
	}
	
	//获取系统当前时间戳 P006
	public function get_server_timestamp(){
		json_output('1', time());
	}

	//获取当前场次信息 P007
	public function get_mat_info() {
		$funmatches = D('FunMatches');
		if(!isset($_POST['data']['mat_id'])){
			json_output('080','No incoming parameters correctly!');
			exit;
		}
		$mat_id = $_POST['data']['mat_id'];
		if ($mat_id === 0) {
			json_output('021', 'There is not matches is available!');
		} else {
			$mat_code = $funmatches -> get_this_next_matcode($mat_id);
			json_output('1', $mat_code);
		}
	}
	
	//获取当前场次的上一个场次 P008
	public function get_last_mat_info(){
		$funmatches = D('FunMatches');
		if(!isset($_POST['data']['mat_id'])){
			json_output('081','No incoming parameters correctly!');
			exit;
		}
		$mat_id = $_POST['data']['mat_id'];
		$last_mat_info = $funmatches -> get_last_matcode($mat_id);
		if($last_mat_info === FALSE){
			json_output('82', 'Interface timing call error ');
			exit;
		}else{
			json_output('1',$last_mat_info);
		}
	}

	//判断用户当前场次是否有出拳
	public function judge_now_punch($mat_id) {
		$uid = $_SESSION['uid'];
		$user_punch = $this -> field("U_CAMP,U_PUNCH") -> where("USER_ID = " . $uid . " AND MAT_ID = " . $mat_id) -> select();
		if ($user_punch === null) {
			return 0;
		} else {
			return 1;
		}
	}
	
	//获取用户当前出拳状态
	public function get_now_punch(){
		if(($this -> judge_now_punch()) === 0){
			return 0;
		}else{
			$funmatches = D('FunMatches');
			$mat_id = $funmatches -> get_now_matid();
			$uid = $_SESSION['uid'];
			$user_punch = $this -> field("U_CAMP,U_PUNCH") -> where("USER_ID = " . $uid . " AND MAT_ID = " . $mat_id) -> select();
			return $user_punch[0];
		}
	}
}
