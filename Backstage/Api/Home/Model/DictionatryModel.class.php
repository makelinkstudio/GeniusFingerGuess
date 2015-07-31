<?php
namespace Home\Model;
use Think\Model;
class DictionatryModel extends Model{
	protected $tableName = 'sys_dictionatry';
	//获取参与猜拳所需积分
	public function get_cqjf(){
		$cqjf = $this -> field("OBJ_VALUE") -> where("OBJ_NAME_EN = 'CQ_JF'") -> select();
		return intval($cqjf[0]['OBJ_VALUE']);
	}
	
	//获取发帖奖励积分
	public function get_ftjf(){
		$ftjf = $this -> field("OBJ_VALUE") -> where("OBJ_NAME_EN = 'FT_JF'") -> select();
		return intval($ftjf[0]['OBJ_VALUE']);
	}
	
	//获取回复话题奖励积分
	public function get_htjf(){
		$htjf = $this -> field("OBJ_VALUE") -> where("OBJ_NAME_EN = 'HF_JF'") -> select();
		return intval($htjf[0]['OBJ_VALUE']);
	}
	
	//获取签到奖励积分 <已期弃用 参见 IntegralConfigModel>
	public function get_qdjf(){
		$qdjf = $this -> field("OBJ_VALUE") -> where("OBJ_NAME_EN = 'QD_JF'") -> select();
		return intval($qdjf[0]['OBJ_VALUE']);
	}
}