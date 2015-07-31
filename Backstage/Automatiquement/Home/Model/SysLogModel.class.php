<?php
namespace Home\Model;
use Think\Model;
class SysLogModel extends Model {
	function auto_start(){
		$data['LOG_TYPE'] = 0;
		$data['PRO_NAME'] = "Auto corntab";
		$data['LOG_CON']  = "自动计划任务开始执行";
		$data['LOG_TIME'] = date("Y-m-d H:i:s",time());
		$this -> data($data) -> add();
	}
	
	function auto_end(){
		$data['LOG_TYPE'] = 0;
		$data['PRO_NAME'] = "Auto corntab";
		$data['LOG_CON']  = "自动计划任务执行完毕";
		$data['LOG_TIME'] = date("Y-m-d H:i:s",time());
		$this -> data($data) -> add();
	}
}
	