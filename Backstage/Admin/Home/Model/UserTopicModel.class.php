<?php
namespace Home\Model;
use Think\Model;
class UserTopicModel extends Model {
	//获取累计话题数
	public function get_total_topic_count(){
		$total_topic_count = $this -> count();
		return intval($total_topic_count);
	}
	
	//获取当日新话题总数
	public function get_today_topic_count(){
		$today_topic_count = $this -> where("FROM_UNIXTIME(TOPIC_TIME,'%Y %D %M')=FROM_UNIXTIME(UNIX_TIMESTAMP(),'%Y %D %M')") -> count();
		return intval($today_topic_count);
	}
}
	