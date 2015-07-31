<?php
namespace Home\Controller;
use Think\Controller;
class IndexController extends Controller {
    //首页显示
    public function index(){
    	$this -> check_login();
		//$this -> display();
		$index = D('Index');
		//获取用户概要信息
		$user_sum = $index -> return_user_sum();
		//获取猜拳概要信息
		$punch_sum = $index -> return_punch_sum();
		//获取话题概要信息
		$topic_sum = $index -> return_topic_sum();
		//获取合作商家概要信息
		$adv_sum = $index -> return_adv_sum();
		$this -> assign('user_sum',$user_sum);
		$this -> assign('punch_sum',$punch_sum);
		$this -> assign('topic_sum',$topic_sum);
		$this -> assign('adv_sum',$adv_sum);
		$this -> display();
    }
	
	//登录页面显示
	public function login(){
		$this -> display();
	}
	
	//登录方法
	public function dologin(){
		if(!(isset($_POST['admin']) && isset($_POST['passwd']))){
			$this -> redirect("Index/login");
			exit;
		}
		$admin = $_POST['admin'];
		$passwd = $_POST['passwd'];
		$login = D('InfAdmin') -> login($admin,$passwd);
		if($login['return'] === '1'){
			$this -> redirect("Index/index");
		}else{
			$this -> redirect("Index/login");
		}
	}
	
	//登出方法
	public function dologout(){
		$inf_admin = D('InfAdmin');
		$res = $inf_admin -> logout();
		if($res){
			$this -> redirect("Index/login");
		}
	}
	
	//判断是否登录 若无登录转到登录页面
	public function check_login(){
		if(!isset($_SESSION['admin_id'])){
    		$this -> redirect("Index/login");
			exit;
    	}
	}
}