<?php
namespace Home\Model;
use Think\Model;
class InfBusinessModel extends Model {
	//根据busin_id获取商家名称
	public function get_businname_from_id($busin_id){
		$busin_name_count = intval($this -> where("BUSIN_ID = ".$busin_id) -> count());
		if($busin_name_count === 0){
			return FALSE;
		}else{
			$busin_name = $this -> field("BUSIN_NAME") -> where("BUSIN_ID = ".$busin_id) -> select();
			return $busin_name[0]['BUSIN_NAME'];
		}
	}
}
