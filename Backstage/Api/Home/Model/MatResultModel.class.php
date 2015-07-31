<?php
namespace Home\Model;
use Think\Model;
class MatResultModel extends Model {
	//获取最近5场结果信息
	public function get_info_five_result(){
		$get_info_five_result = $this -> field("MAT_ID,WIN_CAMP,WIN_PUNCH,CHOOSE_A_RATE,CHOOSE_B_RATE,JIAN_RATE,SHI_RATE,BU_RATE,FROM_UNIXTIME(RESULT_TIME,'%Y %m %d') MAT_TIME") -> limit(5) -> order("M_RES_ID desc") -> select();
		$info_five_result = array();
		for($i=0;$i<count($get_info_five_result);$i++){
			$mat_code = D('FunMatches') -> get_matcode_from_id(intval($get_info_five_result[$i]['MAT_ID']));
			$info_five_result[$i] = array('MAT_CODE' => $mat_code.":00场",
										  'WIN_PUNCH' => $this -> change_punch($get_info_five_result[$i]['WIN_PUNCH'],$get_info_five_result[$i]['WIN_CAMP']),
										  'WIN_CAMP' => $this -> change_camp($get_info_five_result[$i]['WIN_CAMP']),
										  'A_RATE' => $get_info_five_result[$i]['CHOOSE_A_RATE'],
										  'B_RATE' => $get_info_five_result[$i]['CHOOSE_B_RATE'],
										  'JIAN_RATE' => $get_info_five_result[$i]['JIAN_RATE'],
										  'SHI_RATE' => $get_info_five_result[$i]['SHI_RATE'],
										  'BU_RATE' => $get_info_five_result[$i]['BU_RATE'],
										  'MAT_TIME' => $get_info_five_result[$i]['MAT_TIME'],
			);
		}
		return $info_five_result;
	}
	
	//自动转换出拳
	public function change_punch($punch,$camp){
		if($camp === 'H'){
			return '和局';
			exit;
		}
		if($punch === '0'){
			return '剪刀';
		}else if($punch === '1'){
			return '石头';
		}else if($punch === '2'){
			return '布';
		}
	}
	
	//自动转换阵型
	public function change_camp($camp){
		if($camp === 'A'){
			return 'A阵型';
		}else if($camp === 'B'){
			return 'B阵型';
		}else if($camp === 'H'){
			return '和局';
		}
	}
	
	//获取场次结果信息
	public function get_this_mat_result($mat_id){
		$data = $this -> field("WIN_CAMP,WIN_INTEGRAL,CHOOSE_A_RATE,CHOOSE_B_RATE") -> where("MAT_ID =".$mat_id ) -> select();
		$win_camp = $data[0]['WIN_CAMP'];
		$integral = intval($data[0]['WIN_INTEGRAL']);
		$choose_a_rate = intval($data[0]['CHOOSE_A_RATE']);
		$choose_b_rate = intval($data[0]['CHOOSE_B_RATE']);
		if($win_camp == 'A'){
			$win_integral = $integral * ($choose_a_rate / ($choose_a_rate + $choose_b_rate));
			$lost_integral = $integral * ($choose_b_rate / ($choose_a_rate +$choose_b_rate));
		}else{
			$win_integral = $integral * ($choose_b_rate / ($choose_a_rate + $choose_b_rate));
			$lost_integral = $integral * ($choose_a_rate / ($choose_a_rate + $choose_b_rate));	
		}
		$data = null;
		$data = $this -> field("WIN_CAMP,WIN_PUNCH,LOST_CAMP,LOST_PUNCH") -> where("MAT_ID = ".$mat_id) -> select();
		$data[0]['WIN_INTEGRAL'] = intval($win_integral);
		$data[0]['LOST_INTEGRAL'] = intval($lost_integral);
		return $data[0];
	}
}
