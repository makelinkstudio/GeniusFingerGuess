<?php
namespace Home\Controller;
use Think\Controller;
class IndexController extends Controller {
	public function index() {
		//判断是否有登录并且没有传入参数
		if (isset($_SESSION['uid']) && !isset($_POST['code'])) {
			json_output('001', 'No incoming api code!');
			exit ;
		}
		//判断是否已经登陆
		if (!isset($_SESSION['uid'])) {
			//判断是否有传入code参数
			if (isset($_POST['code'])) {
				$code = $_POST['code'];
				//判断传入非登陆code是否合法
				if ($code === 'U000' || $code === 'U002' || $code === 'U005' || $code === 'U006') {
					sort_out_code($code);
				} else {
					json_output('000', 'The illegal call of need of landing api!');
				}
			} else {
				json_output('001', 'No incoming api code!');
			}
		} else {
			//判断用户登陆有效期
			if (time() > $_SESSION['yh_yxq']) {
				session_unset();
				session_destroy();
				json_output('011', 'Login time out!');
				exit ;
			}
			//判断是否同以终端登录
			$check_same_client = D('UserSession') -> check_same_client();
			if ($check_same_client === 0) {
				session_unset();
				session_destroy();
				json_output('020', 'Login in other client!');
				exit ;
			}
			//根据传入code参数调用方法！
			$code = $_POST['code'];
			sort_out_code($code);
		}
	}

	//出拳阵型信息参考图
	public function inforesult() {
		if(!isset($_SESSION['uid'])){
			json_output('030', 'You are not allow to open this page!');
			exit;
		}
		$info_data = D('MatInfoMap') -> get_info_data();
		if ($info_data === 0) {
			$this -> assign('A_RATE', 0);
			$this -> assign('B_RATE', 0);
			$this -> assign('JIAN_RATE', 0);
			$this -> assign('SHI_RATE', 0);
			$this -> assign('BU_RATE', 0);
			$this -> display();
		} else {
			$this -> assign('A_RATE', $info_data['A_RATE']);
			$this -> assign('B_RATE', $info_data['B_RATE']);
			$this -> assign('JIAN_RATE', $info_data['JIAN_RATE']);
			$this -> assign('SHI_RATE', $info_data['SHI_RATE']);
			$this -> assign('BU_RATE', $info_data['BU_RATE']);
			$this -> display();
		}
	}

	//最近5场赛果信息参考图
	public function infofiveresult(){
		if(!isset($_SESSION['uid'])){
			json_output('046', 'You are not allow to open this page!');
			exit;
		}
		$inforfiveresult = D('MatResult') -> get_info_five_result();
		for($i=0;$i<count($inforfiveresult);$i++){
			$this -> assign('MAT_TIME_'.$i,$inforfiveresult[$i]['MAT_TIME']);
			$this -> assign('MAT_CODE_'.$i,$inforfiveresult[$i]['MAT_CODE']);
			$this -> assign('WIN_PUNCH_'.$i,$inforfiveresult[$i]['WIN_PUNCH']);
			$this -> assign('WIN_CAMP_'.$i,$inforfiveresult[$i]['WIN_CAMP']);
			$this -> assign('A_RATE_'.$i,$inforfiveresult[$i]['A_RATE']);
			$this -> assign('B_RATE_'.$i,$inforfiveresult[$i]['B_RATE']);
			$this -> assign('JIAN_RATE_'.$i,$inforfiveresult[$i]['JIAN_RATE']);
			$this -> assign('SHI_RATE_'.$i,$inforfiveresult[$i]['SHI_RATE']);
			$this -> assign('BU_RATE_'.$i,$inforfiveresult[$i]['BU_RATE']);
		}
		$this -> display();
	}

	//积分商场首页
	private function integralmall(){
		if(!isset($_SESSION['uid'])){
			json_output('049', 'You are not allow to open this page!');
			exit;
		}
		if(isset($_GET['page'])){
			$page = intval($_GET['page']);
		}else{
			$page = 1;
		}
		$adv_list = D('BusinAdv') -> get_adv_list($page);
		$this -> assign('adv_list',$adv_list['AVD_LIST']);
		//dump($adv_list);
		$this -> display();
	}
	
	//用户我的预约页面
	private function myappoint(){
		if(!isset($_SESSION['uid'])){
			json_output('057', 'You are not allow to open this page!');
			exit;
		}
		if(isset($_GET['page'])){
			$page = intval($_GET['page']);
		}else{
			$page = 1;
		}
		$user_appoint = D('UserAppoint');
		$myappoint_list = $user_appoint -> get_myappoint_list($page);
		$this -> assign('adv_list',$myappoint_list['AVD_LIST']);
		$this -> display();
		//dump($myappoint_list);
	}
	
	//用户我的收藏页面
	private function mycollection(){
		if(!isset($_SESSION['uid'])){
			json_output('070', 'You are not allow to open this page!');
			exit;
		}
		if(isset($_GET['page'])){
			$page = intval($_GET['page']);
		}else{
			$page = 1;
		}
		$mycollection = D('UserCollection') -> get_user_collection_list($page);
		//dump($mycollection);
		$this -> assign('adv_list',$mycollection['AVD_LIST']);
		$this -> display();
	}

	//商品细节介绍页面
	public function gooddetail($avd_id){
		//$this -> display();
		print_r($avd_id);
	}
}
