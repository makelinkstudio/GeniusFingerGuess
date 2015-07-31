<?php
namespace Home\Model;
use Think\Model;
/*
 * CommunityModel用于处理社区相关功能处理
 * 本类并没有数据表，功能依靠通过实例化 CommunityIntegral、UserTopic、UserReply处理相关数据表内容实现
 */
class CommunityModel {
	//发表话题 C000
	public function deliver_topic() {
		if (!isset($_POST['data']['topic_con'])) {
			json_output('031', 'No incoming parameters correctly!');
			exit ;
		}
		$topic_con = $_POST['data']['topic_con'];
		$deliver_topic = D('UserTopic') -> deliver_topic($topic_con);
		if ($deliver_topic === 1) {
			json_output('1', 'deliver topic succee!');
		}
	}

	//回复话题 C001
	public function reply_topic() {
		if (!(isset($_POST['data']['u_topic_id']) && isset($_POST['data']['reply_con']))) {
			json_output('034', 'No incoming parameters correctly!');
			exit ;
		}
		$u_top_id = $_POST['data']['u_topic_id'];
		$user_topic = D('UserTopic');
		if ($user_topic -> check_topic_id($u_top_id) === 0) {
			json_output('035', 'reply topic is not exists!');
			exit ;
		}
		$reply_con = $_POST['data']['reply_con'];
		$user_reply = D('UserReply');
		$reply_topic = $user_reply -> reply_topic($u_top_id, $reply_con);
		if ($reply_topic === 1) {
			json_output('1', 'reply topic succee!');
		}
	}

	/*
	 * 获取话题列表 C002
	 * 1- 不传入参数返回第一页话题列表数据
	 * 2- 传入POST[data][uid] 查询该用户话题
	 * 3- 传入POST[data][topic_con] 搜索话题内容
	 */
	public function show_topic_list() {
		if (isset($_POST['data']['uid']) && !(isset($_POST['data']['page']) && isset($_POST['data']['topic_con']))) {
			//只传入用户ID 输出该用户数据
			$uid = intval($_POST['data']['uid']);
			$topic_list = D('UserTopic') -> get_topic_list(1, $uid);
		} else if (isset($_POST['data']['topic_con']) && !(isset($_POST['data']['uid']) && isset($_POST['data']['page']))) {
			//只传入搜索关键词 输出该关键词数据
			$topic_con = $_POST['data']['topic_con'];
			$topic_list = D('UserTopic') -> get_topic_list(1, null, 'fm');
		} else if (isset($_POST['data']['page']) && !(isset($_POST['data']['uid']) && isset($_POST['data']['topic_con']))) {
			//只传入页码 输出该页码数据
			$page = intval($_POST['data']['page']);
			$topic_list = D('UserTopic') -> get_topic_list($page);
		} else if (!(isset($_POST['data']['page']) && isset($_POST['data']['uid']) && isset($_POST['data']['topic_con']))) {
			//传入参数为空 默认输出第一页数据
			$topic_list = D('UserTopic') -> get_topic_list(1);
		}
		json_output('1', $topic_list);
		//dump($topic_list);
	}

	// 获取话题详细内容 C003
	public function show_topic_detail() {
		if (!isset($_POST['data']['u_topic_id'])) {
			json_output('040', 'No incoming parameters correctly!');
			exit ;
		}
		if (!isset($_POST['data']['page'])) {
			$page = 1;
		} else {
			$page = intval($_POST['data']['page']);
		}
		$u_topic_id = intval($_POST['data']['u_topic_id']);
		$user_topic = D('UserTopic');
		$check_u_topic_id = $user_topic -> check_topic_id($u_topic_id);
		if ($check_u_topic_id === 0) {
			json_output('041', 'Can not find this topic!');
			exit ;
		}
		$u_topic_con = $user_topic -> show_topic_from_id($u_topic_id);
		$user_reply = D('UserReply');
		$reply_con = $user_reply -> show_replay_con($u_topic_id, $page);
		//整合输出数据
		$topic_detail = array('U_TOPIC_ID' => $u_topic_con['U_TOPIC_ID'], 'TOPIC_CON' => $u_topic_con['TOPIC_CON'], 'USER_NAME' => $u_topic_con['USER_NAME'], 'TOPIC_TIME' => $u_topic_con['TOPIC_TIME'], 'REPLY_COUNT' => $u_topic_con['REPLY_COUNT'], 'REPLY_PAGE' => $reply_con['PAGE'], 'TOTAL_PAGE' => $reply_con['TOTAL_PAGE'], 'REPLY' => $reply_con['REPLY_LIST']);
		json_output('1', $topic_detail);
	}
	
	// 获取用户回复主题（我的回帖） C004
	public function show_my_reply(){
		if(!isset($_POST['data']['page'])){
			$page = 1;
		}else{
			$page = $_POST['data']['page'];
		}
		$reply_topic_id = D('UserReply') -> get_reply_topic_id($page,$_SESSION['uid']);
		if($reply_topic_id === FALSE){
			json_output('047','User never replay any topic!');
			exit;
		}
		//获取话题内容
		$topic_list = D('UserTopic');
		$topic_list_raw = array();
		for($i=0;$i<count($reply_topic_id);$i++){
			$u_topic_id = $reply_topic_id['TOPIC_ID'][$i]['U_TOPIC_ID'];
			$topic_list_raw[$i] = $topic_list -> show_topic_from_id($u_topic_id);
		}
		$topic_list_output = array('TOTAL_PAGE' => $reply_topic_id['TOTAL_PAGE'],'PAGE' => $reply_topic_id['PAGE'],'TOPIC_COUNT' => $reply_topic_id['TOPIC_COUNT'],'TOPIC_LIST' => $topic_list_raw);
		json_output('1',$topic_list_output);
	}

}
