<?php
namespace Home\Model;
use Think\Model;
class UserSessionModel extends Model {
	protected $tableName = 'user_session';
	//登录后设置UserSession记录
	public function set_user_session() {
		$session_info = D('UserSession');
		$result = $session_info -> where("USER_ID = " . $_SESSION['uid'] . " AND STATUS = 0") -> count();
		if (intval($result) === 0) {
			$sessionid = session_id();
			$agent = new \Org\Util\Agent();
			$client_ip = $agent::GetIp();
			//获取ip
			$client_equ = $agent::GetAgent();
			//获取用户设备
			$data['USER_ID'] = $_SESSION['uid'];
			$data['SESSION_ID'] = $sessionid;
			$data['LOGIN_TIME'] = time();
			$data['IP'] = $client_ip;
			$data['EQUIPMENT'] = $client_equ;
			$data['LOGIN_TYPE'] = 0;
			$data['STATUS'] = 0;
			$insertId = D('UserSession') -> data($data) -> add();
			if ($insertId) {
				return 1;
			}
		} else {
			$data['STATUS'] = 1;
			$data['LOGOUT_TIME'] = time();
			$session_info = D('UserSession');
			$affect_row = $session_info -> where("USER_ID = " . $_SESSION['uid'] . " AND STATUS = 0") -> data($data) -> save();
			$data = null;
			$sessionid = session_id();
			$agent = new \Org\Util\Agent();
			$client_ip = $agent::GetIp();
			//获取ip
			$client_equ = $agent::GetAgent();
			//获取用户设备
			$data['USER_ID'] = $_SESSION['uid'];
			$data['SESSION_ID'] = $sessionid;
			$data['LOGIN_TIME'] = time();
			$data['IP'] = $client_ip;
			$data['EQUIPMENT'] = $client_equ;
			$data['LOGIN_TYPE'] = 0;
			$data['STATUS'] = 0;
			$insertId = D('UserSession') -> data($data) -> add();
			if ($insertId) {
				return 1;
			}
		}
	}

	//登出写入登出时间，并设置session status = 1
	public function set_logout() {
		$session_info = D('UserSession');
		$result = $session_info -> where("USER_ID = " . $_SESSION['uid'] . " AND STATUS = 0") -> count();
		$data['STATUS'] = 1;
		$data['LOGOUT_TIME'] = time();
		$affect_row = D('UserSession') -> where("USER_ID = " . $_SESSION['uid'] . " AND STATUS = 0") -> data($data) -> save();
		if ($affect_row) {
			return session_id();
		}
	}
	
	//验证是否同以终端登录，是返回1 否返回0
	public function check_same_client(){
		$session_info = D('UserSession');
		$session_id = $session_info -> field("SESSION_ID") -> where("USER_ID = " . $_SESSION['uid'] . " AND STATUS = 0") -> select();
		if($session_id[0]['SESSION_ID'] === session_id()){
			return 1;
		}else{
			return 0;
		}
	}

}
