<?php
namespace Home\Model;
use Think\Model;
class FunMatchesModel extends Model {
	/* 获取当前是否有场次需要判断
	 * 若有 返回 array('return' => 1,'mat_id' => mat_id)
	 * 若无 返回 array('return' => 0)
	 */
	public function deal_mat_available(){
		$data = $this -> field("MAT_ID") -> where(" UNIX_TIMESTAMP(NOW()) > END_TIME AND MAT_STATUS = 0") -> order("MAT_ID desc") -> limit(1) -> select();
		//判断是否有场次需要处理
		if($data === null){
			return array('return' => 0);
		}else{
			return array('return' => 1,'mat_id' => intval($data[0]['MAT_ID']));
		}
	}
	
	/*
	 * 将当前mat_id设置为1 表示处理成功
	 */		
	 public function succee_mat_deal($mat_id){
	 	$data['MAT_STATUS'] = 1;
		$this -> data($data) -> where("MAT_ID = ".$mat_id) -> save();
	 }
	 
	 /*
	  * 将当前mat_id设置为2 表示处理失败
	  */
	  public function error_mat_deal($mat_id){
	  	$data['MAT_STATUS'] = 2;
		$this -> data($data) -> where("MAT_ID = ".$mat_id) -> save();
	  }
}
	