<?php
namespace Home\Controller;
use Think\Controller;
class IndexController extends Controller {
	//调用开场存储过程
    public function c2c99601e9b4dda217fa6bc44adac8682d921d(){
    	$sys_log = D('SysLog');
		$sys_log -> auto_start();
    	$model = M();
		$model -> execute("call PROC_CREATE_MATCHES(@rst)");
		$sys_log -> auto_end();
    }
	
	//调用统计场次结果及更新积分存储过程
	public function b354578aa4b4121d59ab62ee1ffc2e1d56ba6(){
		$sys_log = D('SysLog');
		$sys_log -> auto_start();
		$fun_matches = D('FunMatches');
		$mat_result = $fun_matches -> deal_mat_available();
		if($mat_result['return'] === 0){
			$sys_log -> auto_end();
			exit;
		}else{
			$mat_id = $mat_result['mat_id'];
			$model = M();
			$sql = "call PROC_COUNT_MATCHES($mat_id,@rst)";
			$model -> execute($sql);
			$rst = $model -> query("SELECT @rst");
			if($rst[0]['@rst'] === '1'){
				$sql_update = "call PROC_UPDATE_INTEGRAL($mat_id,@rst)";
				$model -> execute($sql_update);
				$rst = $model -> query("SELECT @rst");
				if($rst[0]['@rst'] === '1'){
					$fun_matches -> succee_mat_deal($mat_id);
				}else{
					$fun_matches -> error_mat_deal($mat_id);
				}
			}else{
				$fun_matches -> error_mat_deal($mat_id);
			}
		}
		$sys_log -> auto_end();
	}
}