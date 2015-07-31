<?php
namespace Home\Model;
use Think\Model;
class UserModel extends Model {
	protected $tableName = 'inf_user';
	//登陆方法 U000
	public function login() {
		//验证传入参数！
		if (!(isset($_POST['data']['user']) && isset($_POST['data']['passwd']))) {
			json_output('004', 'No incoming parameters correctly!');
			exit ;
		}
		$user = $_POST['data']['user'];
		$passwd = $_POST['data']['passwd'];
		//判断用户是否存在
		if (self::check_user($user) === 0) {
			json_output('005', 'User not exist!');
			exit ;
		}
		//判断用户密码是否正确
		if (self::check_passwd($user, $passwd) === 1) {
			$user_info = D('User');
			$userinfo = $user_info -> field('USER_ID,USER_NAME,LOGIN_COUNT') -> where("USER_NAME ='" . $user . "' OR USER_EMAIL = '" . $user . "'") -> select();
			$_SESSION['uid'] = $userinfo[0]['USER_ID'];
			$_SESSION['username'] = $userinfo[0]['USER_NAME'];
			//登录次数更新
			if($userinfo[0]['LOGIN_COUNT'] === null){
				$data['LOGIN_COUNT'] =1;
				$user_info -> where("USER_ID = ".$_SESSION['uid']) -> data($data) -> save();
			}else{
				$data['LOGIN_COUNT'] = $userinfo[0]['LOGIN_COUNT'] + 1;
				$user_info -> where("USER_ID = ".$_SESSION['uid']) -> data($data) -> save();
			}
			$userinfo = $user_info -> field('PASSWORD',TRUE) -> where("USER_NAME ='" . $user . "' OR USER_EMAIL = '" . $user . "'") -> select();
			$yh_yxq = self::get_yh_yxq();
			$_SESSION['yh_yxq'] = $yh_yxq + time();
			//查询写入user_session表
			$user_session = D('UserSession');
			$result = $user_session -> set_user_session();
			if ($result === 1) {
				json_output('1', $userinfo[0]);
			}
		} else {
			json_output('006', 'User password wrong!');
		}
	}

	//用户退出登陆 U001
	public function logout() {
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
			json_output('1', 'Logout succee!');
		} else {
			json_output('007', 'Logout faile!');
		}
	}

	//用户注册 U002
	public function regist() {
		if (!(isset($_POST['data']['username']) && isset($_POST['data']['email']) && isset($_POST['data']['passwd']))) {
			json_output('008', 'No incoming parameters correctly!');
		}
		$username = $_POST['data']['username'];
		$email = $_POST['data']['email'];
		$passwd = $_POST['data']['passwd'];
		if (self::check_user($username) === 0) {
			if (self::check_email($email) === 0) {
				$passwd = strtoupper(md5($passwd));
				$data['USER_NAME'] = $username;
				$data['USER_EMAIL'] = $email;
				$data['PASSWORD'] = $passwd;
				$data['SEX'] = 0;
				$data['REG_TIME'] = time();
				$integral_config = D('IntegralConfig');
				$csjf = $integral_config -> get_init_resgster_integral_config();
				$data['INTEGRAL'] = $csjf;
				$data['FLAG'] = 0;
				$regist = D('User');
				$insertId = $regist -> data($data) -> add();
				if ($insertId != null) {
					$_SESSION['uid'] = $insertId;
					$_SESSION['username'] = $username;
					$yh_yxq = self::get_yh_yxq();
					$_SESSION['yh_yxq'] = $yh_yxq + time();
					$userinfo = $regist -> field("PASSWORD", TRUE) -> where("USER_ID = " . $insertId) -> select();
					$userinfo = $userinfo[0];
					//查询写入user_session表
					$user_session = D('UserSession');
					$result = $user_session -> set_user_session();
					//调用写入user_integral表方法
					$user_integral = D('UserIntegral');
					$insert_user_integral = $user_integral -> regist_update($_SESSION['uid']);
					if ($result === 1) {
						json_output('1', $userinfo);
					}
					//json_output('1', $userinfo);
				}
			} else {
				json_output('009', 'Email is exists!');
			}
		} else {
			json_output('010', 'Username is exists!');
		}

	}

	//获取用户信息 U003
	public function get_user_info() {
		$uid = $_SESSION['uid'];
		$get_user_info = D('User');
		$userinfo = $get_user_info -> field('PASSWORD', TRUE) -> where("USER_ID = " . $uid) -> select();
		$shop_integral = D('ShopIntegral');
		$user_freeze_integral = $shop_integral -> get_user_freeze_integral();
		if ($userinfo != null) {
			$userinfo[0]['USER_FREEZE_INTEGRAL'] = $user_freeze_integral;
			if($userinfo[0]['USER_FREEZE_INTEGRAL'] === null){
				$userinfo[0]['USER_FREEZE_INTEGRAL'] = 0;
			}
			json_output('1', $userinfo[0]);
		} else {
			json_output('029', 'Get user info fail!');
		}
	}

	//修改密码 U004
	public function change_passwd() {
		$uid = $_SESSION['uid'];
		$passwd = $_POST['data']['passwd'];
		$new_passwd = $_POST['data']['new_passwd'];
		if (self::check_passwd($uid, $passwd) === 1) {
			$new_passwd = strtoupper(md5($new_passwd));
			$data['PASSWORD'] = $new_passwd;
			$change_passwd = D('User');
			$affect_row = $change_passwd -> where("USER_ID = " . $uid) -> data($data) -> save();
			if ($affect_row === 1) {
				json_output('1', 'Change password succee!');
			} else {
				json_output('013', 'Change password fail!');
			}
		} else {
			json_output('012', 'Password is wrong!');
		}
	}

	//验证用户名是否存在 U005
	public function check_username() {
		$username = $_POST['data']['username'];
		if (self::check_user($username) === 0) {
			json_output('1', 'The user name is available!');
		} else {
			json_output('014', 'The user name is unavailable!');
		}
	}

	//验证用户邮箱是否存在 U006
	public function check_email_adr() {
		$email = $_POST['data']['email'];
		if (self::check_user($email) === 0) {
			json_output('1', 'The email name is available!');
		} else {
			json_output('015', 'The email name is unavailable!');
		}
	}

	//原修改用户名（已删除） U007
	//修改用户信息 U008
	public function change_userinfo() {
		if (!(isset($_POST['data']['sex']) && isset($_POST['data']['age']) && isset($_POST['data']['qq']))) {
			json_output('016', 'No incoming parameters correctly!');
			exit ;
		}
		$uid = $_SESSION['uid'];
		$sex = intval($_POST['data']['sex']);
		if ($sex != 0 && $sex != 1) {
			json_output('017', 'Sex incomming unavailavle!');
			exit ;
		}
		$age = intval($_POST['data']['age']);
		if ($age === 0) {
			json_output('018', 'Age can not be 0 or other unavailavle incomming!');
			exit ;
		}
		$qq = intval($_POST['data']['qq']);
		if ($qq === 0) {
			json_output('019', 'QQ incomming is unavailavle!');
			exit ;
		}
		$data['SEX'] = $sex;
		$data['AGE'] = $age;
		$data['QQ'] = $qq;
		$change_userinfo = D('User');
		$affect_row = $change_userinfo -> where("USER_ID = " . $uid) -> data($data) -> save();
		if ($affect_row === 1) {
			json_output('1', 'Change user info succee!');
		} else {
			json_output('020', 'Change user info fail!');
		}
	}
	
	//查询用户积分 U009
	public function check_user_integral(){
		$user = D('User');
		$integral = $user -> get_user_integral();
		json_output('1',array('INTEGRAL' => $integral));
	}
	
	//用户签到 U010
	public function user_sign(){
		$uid = $_SESSION['uid'];
		$user_integral = D('UserIntegral');
		$user_sign = $user_integral -> sign_update($uid);
		if($user_sign === FALSE){
			json_output('044','User signed before!');
		}else{
			$integral_config = D('IntegralConfig');
			$qdjf = $integral_config -> get_sign_integral_config();
			if( ($this -> update_integral($qdjf)) === 1){
				json_output('1','Sign succee!');
			}else{
				json_output('045','Update integral fail!');
			}
		}
		//dump($user_sign);
	}

	//查询用户是否存在
	public static function check_user($user) {
		$check_user = D('User');
		$data = $check_user -> where("USER_NAME ='" . $user . "' OR USER_EMAIL = '" . $user . "'") -> count();
		if (intval($data) != 0) {
			return 1;
			//用户存在！
		} else {
			return 0;
			//用户不存在！
		}
	}

	//查询用户是否存在
	public static function check_email($email) {
		$check_email = D('User');
		$data = $check_email -> where("USER_EMAIL = '" . $email . "'") -> count();
		if (intval($data) != 0) {
			return 1;
			//用户存在！
		} else {
			return 0;
			//用户不存在！
		}
	}

	//查询用户密码是否正确
	public static function check_passwd($user, $passwd) {
		$check_passwd = D('User');
		//可选传入 USER_NAME,EMAIL,USER_ID
		$ck_passwd = $check_passwd -> field('PASSWORD') -> where("USER_NAME ='" . $user . "' OR USER_EMAIL = '" . $user . "' OR USER_ID = '" . $user . "'") -> select();
		$passwd = strtoupper(md5($passwd));
		if ($passwd === $ck_passwd[0]['PASSWORD']) {
			return 1;
			//密码正确 返回1
		} else {
			return 0;
			//密码错误 返回0
		}
	}

	//获取系统登陆有效期
	public static function get_yh_yxq() {
		$dic = D('Dictionatry');
		$yh_yxq = $dic -> field('OBJ_VALUE') -> where("OBJ_NAME_EN = 'YH_YXQ'") -> select();
		return $yh_yxq = intval($yh_yxq[0]['OBJ_VALUE']);
	}

	//获取注册登录初始积分
	public function get_csjf() {
		$dic = D('Dictionatry');
		$csjf = $dic -> field('OBJ_VALUE') -> where("OBJ_NAME_EN = 'CS_JF'") -> select();
		return $csjf = $yh_yxq = intval($csjf[0]['OBJ_VALUE']);
	}
	
	//获取用户积分
	public function get_user_integral(){
		$user = D('User');
		$integral = $user -> field("INTEGRAL") -> where("USER_ID = ".$_SESSION['uid']) -> select();
		return intval($integral[0]['INTEGRAL']);
	}
	
	//更新用户积分(参数：变动值)
	public function update_integral($integral){
		$user = D('User');
		$old_integral = $user -> get_user_integral();
		if(($old_integral + $integral) < 0){
			return 0;
		}else{
			$new_integral = $old_integral + $integral;
			$data['INTEGRAL'] = $new_integral;
			$affect_row = $user -> where("USER_ID = ".$_SESSION['uid']) -> data($data) -> save();
			if($affect_row){
				return 1;
			}else{
				return 0;
			}
		}
	}
	
	//根据用户id获取用户名（参数：用户ID） 没有找到返货false
	public function get_username_from_id($uid){
		$username = $this -> field("USER_NAME") -> where("USER_ID = ".$uid) -> select();
		if($username === null){
			return FALSE;
			exit;
		}
		return $username[0]['USER_NAME'];
	}
}
