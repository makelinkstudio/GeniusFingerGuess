<?php
namespace Home\Model;
use Think\Model;
class UMatResultModel extends Model{
	protected $tableName = 'u_mat_result';
	//获取用户战绩
	public function get_user_record(){
		$uid = $_SESSION['uid'];
		$u_mat_result = D('UMatResult');
		//获胜场数
		$win_count = intval($u_mat_result -> where("USER_ID = ".$uid." AND RESULT_STATUS = 0") -> count());
		//获败场数
		$draw_count = intval($u_mat_result -> where("USER_ID = ".$uid." AND RESULT_STATUS = 1") -> count());
		//和局场数
		$funmatches = D('FunMatches');
		if(($funmatches -> get_now_matid()) === 0){
			//当前没有场次情况
			$lost_count = intval($u_mat_result -> where("USER_ID = ".$uid." AND RESULT_STATUS = 2") -> count());
		}else{
			//当前存在场次情况
			$punch = D('Punch');
			if( ($judge_now_punch = $punch -> judge_now_punch()) ===  0){
				//用户当前场次没有出拳
				$lost_count = intval($u_mat_result -> where("USER_ID = ".$uid." AND RESULT_STATUS = 2") -> count());
			}else{
				//用户当前场次已经出拳
				$lost_count = intval($u_mat_result -> where("USER_ID = ".$uid." AND RESULT_STATUS = 2") -> count());
				if($lost_count === 0){
					$lost_count = $lost_count;
				}else{
					$lost_count = $lost_count - 1;
				}
			}
		}
		return array('WIN_COUNT' => $win_count,'DRAW_COUNT' => $draw_count,'LOST_COUNT' => $lost_count);
	}

	//判断用户胜负信息
	public function get_user_mat_result($mat_id){
		$data = $this -> field("RESULT_STATUS") -> where("MAT_ID = ".$mat_id." AND USER_ID = ".$_SESSION['uid']) -> select();
		return $data[0]['RESULT_STATUS'];
	}
}
	