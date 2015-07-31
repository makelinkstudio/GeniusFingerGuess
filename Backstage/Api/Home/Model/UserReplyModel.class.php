<?php
namespace Home\Model;
use Think\Model;
class UserReplyModel extends Model {
	//回复话题 插入记录
	public function reply_topic($u_top_id, $reply_con) {
		$uid = $_SESSION['uid'];
		$reply_time = time();
		$data['USER_ID'] = $uid;
		$data['U_TOPIC_ID'] = $u_top_id;
		$data['REPLY_CON'] = $reply_con;
		$data['REPLY_TIME'] = $reply_time;
		$data['REPLY_STATUS'] = 0;
		$reply_topic = $this -> data($data) -> add();
		if ($reply_con) {
			$com_integral = D('CommunityIntegral') -> reply_topic();
			$integral_config = D('IntegralConfig');
			$htjf = $integral_config -> get_reply_topic_integral_config();
			$user = D('User') -> update_integral($htjf);
			if ($user === 0) {
				json_output('036', 'Can not update integral!');
				exit ;
			}
		} else {
			json_output('037', 'reply topic fail!');
			exit ;
		}
		return 1;
	}

	//查询回复指定话题回复总数 （参数：$u_topic_id）
	public function reply_count($u_topic_id) {
		$reply_count = $this -> where("U_TOPIC_ID = " . $u_topic_id) -> count();
		return intval($reply_count);
	}

	//查询指定话题回复数据 （参数 ：$u_topic_id,$page）
	public function show_replay_con($u_topic_id, $page) {
		$check_topic_id = D('UserTopic') -> check_topic_id($u_topic_id);
		if ($check_topic_id === 0) {
			return 0;
			exit ;
		}
		//获取回复总页数
		$reply_count = intval($this -> field("TOPIC_CON") -> where("U_TOPIC_ID = " . $u_topic_id) -> count());
		if ($reply_count === 0) {
			//若返回结果为0 则输出0条记录结果 跳出程序
			return array('REPLY_COUNT' => $reply_count, 'PAGE' => 1, 'TOTAL_PAGE' => 1, 'REPLY_LIST' => null);
			exit ;
		}
		$total_page = cal_page($reply_count, 10);
		//获取总页数
		if ($total_page === FALSE) {
			json_output('051', 'Incomming page is wrong!');
			exit ;
		}
		if ($page > $total_page) {
			json_output('043', 'Incoming page is greater than max page! The Last page is ' . $total_page);
			exit ;
		}
		//获取话题回复数据
		$reply_con_raw = $this -> field("USER_ID,REPLY_CON,REPLY_TIME") -> where("U_TOPIC_ID = " . $u_topic_id) -> page($page, 10) -> select();
		$reply_con = array();
		for ($i = 0; $i < count($reply_con_raw); $i++) {
			$uid = intval($reply_con_raw[$i]['USER_ID']);
			$username = D('User') -> get_username_from_id($uid);
			if ($username === FALSE) {
				$username = 'Undefined';
			}
			$reply_con[$i] = array('USER_NAME' => $username, 'REPLY_CON' => $reply_con_raw[$i]['REPLY_CON'], 'REPLY_TIME' => $reply_con_raw[$i]['REPLY_TIME']);
		}
		$reply_con_output = array('REPLY_COUNT' => $reply_count, 'PAGE' => $page, 'TOTAL_PAGE' => $total_page, 'REPLY_LIST' => $reply_con);
		return $reply_con_output;
	}

	// 根据用户名获取用户回复过的话题的u_topic_id
	public function get_reply_topic_id($page,$uid){
		$reply_topic_count = M() -> query("SELECT COUNT(1) FROM (SELECT U_TOPIC_ID FROM USER_REPLY WHERE USER_ID = ".$uid." GROUP BY U_TOPIC_ID) RST");
		$reply_topic_count = intval($reply_topic_count[0]['COUNT(1)']);
		if($reply_topic_count === 0){
			return FALSE;
		}else{
			$total_page = cal_page($reply_topic_count,10);//获取总页数
			if($page > $total_page){
				json_output('048','Incoming page is greater than max page! The Last page is '.$total_page);
				exit;
			}
			$reply_topic_id = $this -> field("U_TOPIC_ID") -> where("USER_ID = ".$uid) -> group("U_TOPIC_ID") -> page($page,10) -> select();
			return array('TOTAL_PAGE' => $total_page,'PAGE' => $page,'TOPIC_COUNT' => $reply_topic_count,'TOPIC_ID' => $reply_topic_id);
		}
	}
}
