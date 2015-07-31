<?php
namespace Home\Model;
use Think\Model;
class FunMatchesModel extends Model {
	protected $tableName = 'fun_matches';
	//获取当前场次信息(mat_id)
	public function get_now_matid(){
		$funmatches = D('FunMatches');
		$mat_id = $funmatches -> field("MAT_ID,MAT_STATUS")-> where("UNIX_TIMESTAMP(NOW()) >BEGIN_TIME AND UNIX_TIMESTAMP(NOW()) < END_TIME") -> limit(1) -> select();
		if($mat_id === null){
			return 0;
		}else{
			return intval($mat_id[0]['MAT_ID']);
			//return $mat_id;
		}
	}
	
	//获取当前场次名和下一场场次名
//	public function get_this_next_matcode($this_matid){
//		$funmatches = D('FunMatches');
//		$mat_id = $funmatches -> field("MAT_ID,MAT_CODE")-> where("MAT_STATUS = 0 and FROM_UNIXTIME(MAT_TIME,'%Y %D %M')=FROM_UNIXTIME(UNIX_TIMESTAMP(),'%Y %D %M') AND MAT_ID = ".$this_matid) -> select();
//		if($mat_id === null){
//			return 0;
//		}else{
//			$next_matid = $funmatches -> field("MAT_ID,MAT_CODE") -> where("MAT_ID > ".$this_matid." and FROM_UNIXTIME(MAT_TIME,'%Y %D %M')=FROM_UNIXTIME(UNIX_TIMESTAMP(),'%Y %D %M')") -> limit(1)->select();
//			if($next_matid === null){
//				return array('THIS_MAT_CODE' => intval($mat_id[0]['MAT_CODE']),'NEXT_MAT_CODE' => null);
//			}else{
//				return array('THIS_MAT_CODE' => intval($mat_id[0]['MAT_CODE']),'NEXT_MAT_CODE' => intval($next_matid[0]['MAT_CODE']));
//			}
//		}
//	}
	public function get_this_next_matcode($mat_id){
		//当前场次数组
		$this_mat_info = array();
		//下一场次数组
		$next_mat_info = array();
		//判断当前场次状态
		$mat_status = $this -> check_mat_status($mat_id);
		if($mat_status === '0'){
			//当前场次信息
			$data = null;
			$data = $this -> field("MAT_ID,MAT_CODE_EN,MAT_CODE_CH,BEGIN_TIME,END_TIME") -> where("MAT_ID = ".$mat_id) -> select();
			$this_mat_info = $data[0];
			$mat_type = substr($this_mat_info['MAT_CODE_EN'],3,2);
			$this_mat_info['MAT_TYPE'] = $mat_type;
			//下一场次信息
			$data = null;
			$data = $this -> field("MAT_ID,MAT_CODE_EN,MAT_CODE_CH,BEGIN_TIME,END_TIME") -> where("MAT_ID >".$mat_id." AND MAT_STATUS = 0")  -> limit(1) -> select();
			$next_mat_info = $data[0];
			$mat_type = substr($next_mat_info['MAT_CODE_EN'],3,2);
			$next_mat_info['MAT_TYPE'] = $mat_type;
			$this_mat_status = '0';
		}else if($mat_status === '10'){
			//当前进行完场次
			$data = null;
			$data = $this -> field("MAT_ID,MAT_CODE_EN,MAT_CODE_CH,BEGIN_TIME,END_TIME") -> where("MAT_ID <".$mat_id." AND MAT_STATUS = 0") -> order("MAT_ID desc")  -> limit(1) -> select();
			$this_mat_info = $data[0];
			$mat_type = substr($this_mat_info['MAT_CODE_EN'],3,2);
			$this_mat_info['MAT_TYPE'] = $mat_type;
			//下一场次
			$data = null;
			$data = $this -> field("MAT_ID,MAT_CODE_EN,MAT_CODE_CH,BEGIN_TIME,END_TIME") -> where("MAT_ID >".$mat_id." AND MAT_STATUS = 0")  -> limit(1) -> select();
			$next_mat_info = $data[0];
			$mat_type = substr($next_mat_info['MAT_CODE_EN'],3,2);
			$next_mat_info['MAT_TYPE'] = $mat_type;
			$this_mat_status = '10';
		}
//		for($i=0;$i<count($this_matid_array);$i++){
//			$data = null;
//			$data = $this -> field("MAT_ID,MAT_CODE_EN,MAT_CODE_CH,BEGIN_TIME,END_TIME") -> where("MAT_ID = ".$this_matid_array[$i]) -> select();
//			$this_mat_info[$i] = $data[0];
//			$mat_type = substr($this_mat_info[$i]['MAT_CODE_EN'],3,2);
//			$this_mat_info[$i]['MAT_TYPE'] = $mat_type;
//			//根据场次类型分类 获取下一场次信息数组
//			$data = null;
//			$data = $this -> field("MAT_ID,MAT_CODE_EN,MAT_CODE_CH,BEGIN_TIME,END_TIME") -> where("MAT_CODE_EN LIKE '%".$mat_type."%' AND UNIX_TIMESTAMP(NOW()) < BEGIN_TIME AND MAT_STATUS = 0") -> limit(1) -> select();
//			$next_mat_info[$i] = $data[0];
//			if($next_mat_info[$i] != null){
//				$mat_type = substr($next_mat_info[$i]['MAT_CODE_EN'],3,2);
//				$next_mat_info[$i]['MAT_TYPE'] = $mat_type;
//			}
//		}
		return array('THIS_MAT_INFO' => $this_mat_info,'NEXT_MAT_INFO' => $next_mat_info,'THIS_MAT_STATUS' => $this_mat_status);
	}

	//获取上一场次信息
	public function get_last_matcode($mat_id){
		$last_mat_info = array();
		$mat_status = $this -> check_mat_status($mat_id);
		if($mat_status === '10'){
			return FALSE;
		}else{
			$data = $this -> field("MAT_ID,MAT_CODE_EN,MAT_CODE_CH,BEGIN_TIME,END_TIME") -> where("MAT_ID <".$mat_id." AND MAT_STATUS = 0") -> order("MAT_ID desc")  -> limit(1) -> select();
			$last_mat_info = $data[0];
			$mat_type = substr($last_mat_info['MAT_CODE_EN'],3,2);
			$last_mat_info['MAT_TYPE'] = $mat_type;
			$last_mat_status = '0';
			return $last_mat_info;
		}
	}

	//获取当前场次名
	public function get_this_matcode(){
		if( ($this -> get_now_matid()) === 0 ){
			return 0;
		}else{
			$mat_code = $this -> field("MAT_CODE")-> where("MAT_STATUS = 0 and FROM_UNIXTIME(MAT_TIME,'%Y %D %M')=FROM_UNIXTIME(UNIX_TIMESTAMP(),'%Y %D %M')") -> select();
			return intval($mat_code[0]['MAT_CODE']);
		}
	}
	
	//根据mat_id获取场次名
	public function get_matcode_from_id($mat_id){
		$mat_code = $this -> field("MAT_CODE") -> where("MAT_ID = ".$mat_id) -> select();
		if($mat_code === null){
			return FALSE;
		}else{
			return $mat_code[0]['MAT_CODE'];
		}
	}
	
	//判断传入mat_id场次是否可用
	public function check_mat_available($mat_id){
		$available = $this -> where("UNIX_TIMESTAMP(NOW()) >BEGIN_TIME AND UNIX_TIMESTAMP(NOW()) < END_TIME and MAT_STATUS = 0 AND MAT_ID = ".$mat_id) -> select();
		if($available != null){
			return TRUE;
		}else{
			return FALSE;
		}
	}
	
	//根据mat_id获取中英文代号
	public function get_en_ch_code($mat_id){
		$data = $this -> where("MAT_ID = ".$mat_id) -> select();
		return array("MAT_CODE_EN" => $data[0]['MAT_CODE_EN'],"MAT_CODE_CH" => $data[0]['MAT_CODE_CH']);
	}
	
	//判断当前场次状态
	public function check_mat_status($mat_id){
		$data = $this -> field("MAT_STATUS") -> where("MAT_ID = ".$mat_id) -> limit(1) -> select();
		return $data[0]['MAT_STATUS'];
	}
	
}