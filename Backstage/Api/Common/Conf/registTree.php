<?php
return array(
	//'配置项'=>'配置值'
	'U0'   => 'User',	//定义User模块 U0
	'U000' => 'login',
	'U001' => 'logout',
	'U002' => 'regist',
	'U003' => 'get_user_info',
	'U004' => 'change_passwd',
	'U005' => 'check_username',
	'U006' => 'check_email_adr',
	'U008' => 'change_userinfo',
	'U009' => 'check_user_integral',
	'U010' => 'user_sign',
	
	'P0'   => 'Punch',//定义Punch模块 P0
	'P000' => 'get_now_matid',
	'P001' => 'get_user_punch',
	'P002' => 'user_do_punch',
	'P003' => 'get_punch_info_main',
	'P004' => 'get_user_now_punch',
	'P005' => 'get_mat_result',
	'P006' => 'get_server_timestamp',
	'P007' => 'get_mat_info',
	'P008' =>  'get_last_mat_info',
	
	'C0'   => 'Community',//定义社区模块 C0
	'C000' => 'deliver_topic',
	'C001' => 'reply_topic',
	'C002' => 'show_topic_list',
	'C003' => 'show_topic_detail',
	'C004' => 'show_my_reply',
	
	'A0'   => 'UserAppoint',//用户商品预约模块 A0
	'A000' => 'user_appoint' ,
	
	'C1'   => 'UserCollection',//用户搜藏商品 C1
	'C100' => 'user_collect',
	'C101' => 'user_cancel_collect',
	
	'I0'   => 'IntegralMall',//兑换模块 I0
	'I000' => 'get_integralmall_data',
	'I001' => 'get_myappoint_data',
	'I002' => 'mycollection',
	'I003' => 'get_by_avd_id',
	
	'I1'   => 'IntegralStategy',//积分攻略模块
	'I100' => 'get_integral_stategy'
	
);