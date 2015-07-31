<?php
namespace Home\Model;
use Think\Model;
class IndexModel {
	//返回系统用户信息概要（总注册用户量、当前活跃用户、系统总积分、累计登录）
	public function return_user_sum(){
		$inf_user = D('InfUser');
		$user_session = D('UserSession');
		$total_user_count = $inf_user -> get_total_user_count();
		$active_user_count = $user_session -> get_active_user_count();
		$total_integral = $inf_user -> get_sys_total_integral();
		$total_login_count = $user_session -> get_total_login_count();
		return array('TOTAL_USER_COUNT'  => $total_user_count,
					 'ACTIVE_USER_COUNT' => $active_user_count,
					 'TOTAL_INTEGRAL'    => $total_integral,
					 'TOTAL_LOGIN_COUNT' => $total_login_count 
		);
	}
	
	//返回猜拳信息概要（累计参与人数、累计投入总积分、人均参与次数）
	public function return_punch_sum(){
		$user_punch = D('UserPunch');
		$mat_result = D('MatResult');
		$user_punch_count = $user_punch -> get_user_punch_count();//累计参与人数
		$total_input_integral = $mat_result -> get_total_input_intergal();//累计投入总积分
		$avg_punch_count = $user_punch -> get_avg_punch_count();//人均参与猜拳次数
		return array('USER_PUNCH_COUNT'      => $user_punch_count,
					 'TOTAL_INPUT_INTEGRAL'  => $total_input_integral,
					 'AVD_PUNCH_COUNT'       => $avg_punch_count
		);
	}
	
	//返回社区话题信息概要（累计话题总数、累计回复、当日话题总数）
	public function return_topic_sum(){
		$user_topic = D('UserTopic');
		$user_reply = D('UserReply');
		$total_topic_count = $user_topic -> get_total_topic_count();//累计话题总数
		$total_reply_count = $user_reply -> get_total_reply_count();//累计回复总数
		$today_topic_count = $user_topic -> get_today_topic_count();//当日话题总数
		return array('TOTAL_TOPIC_COUNT' => $total_topic_count,
					 'TOTAL_REPLY_COUNT' => $total_reply_count,
					 'TODAY_TOPIC_COUNT' => $today_topic_count
		);
	}
	
	//返回合作商家信息概要（合作商家数、累计兑换总数）
	public function return_adv_sum(){
		$inf_business = D('InfBusiness');
		$user_appoint = D('UserAppoint');
		$total_adv_count = $inf_business -> get_total_adv_count();//合作商家总数
		$total_luck_count = $user_appoint -> get_total_luck_count();//获取累计兑换总数
		return array('TOTAL_ADV_COUNT'   => $total_adv_count,
				 	 'TOTAL_LUCK_COUNT'  => $total_luck_count
		);
	}
	
}