<?php
namespace Home\Model;
use Think\Model;
class UserReplyModel extends Model {
	//获取累计回复总数
	public function get_total_reply_count(){
		$total_reply_count = $this -> count();
		return intval($total_reply_count);
	}		
}
	