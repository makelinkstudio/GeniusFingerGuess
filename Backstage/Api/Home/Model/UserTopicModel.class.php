<?php
namespace Home\Model;
use Think\Model;
class UserTopicModel extends Model {
	//发表新话题 插入记录
	public function deliver_topic($topic_con) {
		$uid = $_SESSION['uid'];
		$topic_time = time();
		$topic_status = 0;
		$data['USER_ID'] = $uid;
		$data['TOPIC_CON'] = $topic_con;
		$data['TOPIC_TIME'] = $topic_time;
		$data['TOPIC_STATUS'] = $topic_status;
		$deliver_topic = $this -> data($data) -> add();
		if ($deliver_topic) {
			$com_integral = D('CommunityIntegral') -> deliver_update();
			$integral_config = D('IntegralConfig');
			$ftjf = $integral_config -> get_deliver_topic_integral_config();
			$user = D('User') -> update_integral($ftjf);
			if ($user === 0) {
				json_output('032', 'Can not update integral!');
				exit ;
			}
		}else{
			json_output('033','deliver topic fail!');
			exit;
		}
		return 1;
	}
	
	//查询话题id是否存在
	public function check_topic_id($u_top_id){
		$result = $this -> where("U_TOPIC_ID = ".$u_top_id) -> count();
		return intval($result);//若无 返回 0 
	}
	
	//查询返回话题列表
	public function get_topic_list($page,$uid = null,$topic_con = null){
		//判断是否设置$uid条件
		if($uid === null){
			$where_uid = "";
		}else{
			$where_uid = "USER_ID = ".$uid;
		}
		//判断是否设置$topic_con条件
		if($topic_con === null){
			$where_topic_con = "";
		}else{
			$where_topic_con = "TOPIC_CON LIKE '%".$topic_con."%'";
		}
		//判断$where_uid是否为空
		if($where_uid === '' && $where_topic_con === ''){
			$where = '';
		}else if($where_uid === '' && $where_topic_con != ''){
			$where = $where_topic_con;
		}else if($where_topic_con === '' && $where_uid != ''){
			$where = $where_uid;
		}else{
			$where = $where_uid.' AND '.$where_topic_con;
		}
		$topic_count = intval($this -> field("TOPIC_CON") -> where($where) -> count());
		if($topic_count === 0){
			//若返回结果为0 则输出0条记录结果 跳出程序
			return array('TOPIC_COUNT' => $topic_count,'PAGE' => $page,'TOTAL_PAGE' => $total_page,'TOPIC_LIST' => null);
			exit;
		}
		$total_page = cal_page($topic_count,10);//获取总页数
		if($total_page === FALSE){
			json_output('038','Incomming page is wrong!');
			exit;
		}
		if($page > $total_page){
			json_output('039','Incoming page is greater than max page! The Last page is '.$total_page);
			exit;
		}
		$topic_list = $this -> field("U_TOPIC_ID,TOPIC_CON,USER_ID,TOPIC_TIME") -> where($where) -> order("U_TOPIC_ID DESC") -> page($page,10) -> select();
		$topic_list_output = array();
		for($i=0;$i<count($topic_list);$i++){
			$u_topic_id = $topic_list[$i]['U_TOPIC_ID'];
			$uid = $topic_list[$i]['USER_ID'];
			$username =  D('User') -> get_username_from_id($uid);
			if($username === FALSE){
				$username = 'Undefined';
			}
			$reply_count = D('UserReply') -> reply_count($u_topic_id);
				$topic_list_output[$i] = array('U_TOPIC_ID' => intval($u_topic_id),
											   'TOPIC_CON' => $topic_list[$i]['TOPIC_CON'],
											   'USER_NAME' => $username,
											   'TOPIC_TIME' => $topic_list[$i]['TOPIC_TIME'],
											   'REPLY_COUNT' => $reply_count
											   );
		}
		return array('TOPIC_COUNT' => $topic_count,'PAGE' => $page,'TOTAL_PAGE' => $total_page,'TOPIC_LIST' => $topic_list_output);
	}

	//根据U_TOPIC_ID查找话题内容
	public function show_topic_from_id($u_topic_id){
		if(($this -> check_topic_id($u_topic_id)) === 0){
			return FALSE;
			exit;
		}
		$topic_con = $this -> field("U_TOPIC_ID,TOPIC_CON,USER_ID,TOPIC_TIME") -> where("U_TOPIC_ID = ".$u_topic_id) -> select();
		$username = D('User') -> get_username_from_id($topic_con[0]['USER_ID']);
		$reply_count = D('UserReply') -> reply_count($u_topic_id);
		$topic_con_output = array('U_TOPIC_ID' => $topic_con[0]['U_TOPIC_ID'],
								  'TOPIC_CON' => $topic_con[0]['TOPIC_CON'],
								  'USER_NAME' => $username,
								  'TOPIC_TIME' => $topic_con[0]['TOPIC_TIME'],
								  'REPLY_COUNT' => $reply_count
								  );
		return $topic_con_output;
	}
}
