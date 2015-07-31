<?php
namespace Home\Model;
use Think\Model;
class MatInfoMapModel extends Model {
	protected $tableName = 'mat_info_map';
	//获取猜拳数据图数据
	public function get_info_data(){
		//判断当前是否存在场次
		$funmatches = D('FunMatches');
		$mat_id = $funmatches -> get_now_matid();
		if($mat_id === 0){
			return 0;
		}else{
			//判断当前是否有信息图数据可读
			$info_data = $this -> field("A_RATE,B_RATE,JIAN_RATE,SHI_RATE,BU_RATE") -> where("MAT_ID = ".$mat_id) -> order("MAT_IM_ID desc") -> limit(1) -> select();
			if($info_data === null){
				return 0;
			}else{
				return array('A_RATE' => intval($info_data[0]['A_RATE']),
							 'B_RATE' => intval($info_data[0]['B_RATE']),
							 'JIAN_RATE' => intval($info_data[0]['JIAN_RATE']),
							 'SHI_RATE' => intval($info_data[0]['SHI_RATE']),
							 'BU_RATE' => intval($info_data[0]['BU_RATE']),
				);
			}
		}
	}	
}