<?php
namespace Home\Model;
use Think\Model;
class InfAdminModel extends Model {
	//用户登录
	public function login($admin,$passwd){
		$user = strval($user);
		$passwd = strtoupper(md5($passwd));
		if($this -> check_admin($admin)){
			//判断管理员帐户是否存在 如果存在
			if($this -> check_passwd($admin, $passwd)){
				//判断密码是否正确 如果正确
				$admin_info = $this -> get_admin_info($admin);
				if(intval($admin_info['AN_STATUS']) === 0){
					//判断管理员状态 如果正常
					$_SESSION['admin_id'] = $admin_info['ADMIN_ID'];
					$_SESSION['admin_name'] = $admin_info['AN_NAME'];
					$_SESSION['uid'] = $admin_info['ADMIN_ID'];
					$set_admin_session = D('UserSession') -> set_admin_session();
					if($set_admin_session === 1){
						return array('return' => '1');
					}else{
						return array('return' => '003');
					}
				}else{
					return array('return' => '002');//管理员帐户非正常状态
				}
			}else{
				return array('return' => '001');//密码错误
			}
		}else{
			return array('return' => '000');//管理员帐户不存在
		}
	}
	
	//用户登出
	public function logout(){
		$session_info = D('UserSession');
		$session_id_to_destroy = $session_info -> set_logout();
		session_id($session_id_to_destroy);
		session_start();
		if (ini_get("session.use_cookies")) {
			$params = session_get_cookie_params();
			setcookie(session_name(), '', time() - 42000, $params["path"], $params["domain"], $params["secure"], $params["httponly"]);
		}
		session_destroy();
		session_commit();
		$_SESSION = array();
		if (!isset($_SESSION['uid'])) {
			return TRUE;//登出成功
		} else {
			return FALSE;//登出失败
		}
	}
	
	//查询管理员帐户是否存在
	public function check_admin($admin){
		$res = $this -> where("AN_NAME = '".$admin."'") -> select();
		if($res === null){
			return FALSE;//管理员帐户不存在
		}else{
			return TRUE;//密码错误
		}
	}
	
	//验证管理员密码是否正确
	public function check_passwd($admin,$passwd){
		$res = $this -> field("AN_PASSWD") -> where("AN_NAME = '".$admin."'") -> select();
		$check_passwd = $res[0]['AN_PASSWD'];
		if($check_passwd === $passwd){
			return TRUE;//密码正确
		}else{
			return FALSE;//密码不正确	
		}
	}
	
	//获取管理员信息
	public function get_admin_info($admin){
		$admin_info = $this -> field("AN_PASSWD,AN_TIME",TRUE) -> where("AN_NAME = '".$admin."'") -> select();
		return $admin_info[0];
	}
	
}