<?php
namespace Home\Model;
use Think\Model;
class UserSessionModel extends Model {
	//管理员登录，写入USER_SESSION表
	public function set_admin_session(){
		$result = $this -> where("USER_ID = " . $_SESSION['uid'] . " AND STATUS = 0") -> count();
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
			$data['LOGIN_TYPE'] = 2;
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
			$data['LOGIN_TYPE'] = 2;
			$data['STATUS'] = 0;
			$insertId = D('UserSession') -> data($data) -> add();
			if ($insertId) {
				return 1;
			}
		}
	}	

	//登出写入登出时间，并设置session status = 1
	public function set_logout(){
		$result = $this -> where("USER_ID = " . $_SESSION['uid'] . " AND STATUS = 0") -> count();
		$data['STATUS'] = 1;
		$data['LOGOUT_TIME'] = time();
		$affect_row = D('UserSession') -> where("USER_ID = " . $_SESSION['uid'] . " AND STATUS = 0") -> data($data) -> save();
		if ($affect_row) {
			return session_id();
		}
	}
	
	//获取活跃用户数量（根据是否登出判断）
	public function get_active_user_count(){
		$active_user_count = $this -> where("STATUS = 0 AND LOGIN_TYPE = 0") -> count();
		return intval($active_user_count);
	}
	
	//获取累计登录次数
	public function get_total_login_count(){
		$total_login_count = $this -> where("STATUS = 1 AND LOGIN_TYPE = 0") -> count();
		return intval($total_login_count);
	}
}
