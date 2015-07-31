package com.bncq.common;

public final class APIModel {

	//基础要数
	public static final String BASEURL="http://192.168.1.151/myServer";//后台部署路径
	public static final String URL=BASEURL+"/api.php";//接口访问地址
	public static final String SHOP_URL=BASEURL+"/api.php/Home/Index/integralmall";//兑换大厅
	public static final String SHOP_DETAIL_URL=BASEURL+"/api.php/Home/Index/gooddetail";//商品明细
	public static final String SHOP_MYAPPT_URL=BASEURL+"/api.php/Home/Index/myappoint";//我的预约
	public static final String SHOP_COLLECTION_URL=BASEURL+"/api.php/Home/Index/myappoint";//我的预约
	
	public static final String INFMAP_URL=BASEURL+"/api.php/Home/Index/infoLineResult";//猜拳游戏图
	public static final String INFCQRULE_URL=BASEURL+"/api.php/Home/Index/inforesult";//猜拳规则图
	public static final String INFCQRESULT_URL=BASEURL+"/api.php/Home/Index/infofiveresult";//猜拳五场结果图
	public static final String SERVER_SESSIONID="PHPSESSID";//服务器sessionid名
	
	public static final String CODE="code";		 //输入接口编码key
	public static final String DATA="data";		//输入输出接口数据key
	public static final String STATUS="status";		//输出状态key
	public static final String RETCODE="return";		//输出返回key
	public static final String MSG="msg";		//输出信息key
	
	public static final String SUCCEE="1";	//成功代号
	public static final String ERROR="error";	//失败代号

    //用户模块
	public static final String USER_LOGIN="U000";	//登录
	public static final String USER_LOGOUT="U001";	//退出登录
	public static final String USER_REGIST="U002";	//注册
	public static final String USER_INFO="U003";	//用户信息
	public static final String UPD_USER_INFO="U008";	//修改用户信息
	//猜拳
	public static final String CQ_INF_ALL="P000";//当日当前猜拳信息
	public static final String CQ_UP_INFO="P001";//当前场次用户出拳信息
	public static final String CQ_SET_UP="P002";//用户选择出拳
	public static final String CQ_GETUSERINF="P003";//获取猜拳用户信息
	public static final String CQ_GETINFO="P004";//获取猜拳信息
	public static final String CQ_GETRESULT="P005";//获取猜拳结果信息
	public static final String CQ_GETSYSTIME="P006";//获取当前系统时间
	public static final String CQ_GETMATINFO="P007";//获取场次信息
	public static final String CQ_GET_LASTMAT_INFO="P008";//获取场次信息
	//首页
	public static final String AVD_APPOINT="A000";//用户预约
	public static final String AVD_COLLECT="C100";//用户收藏
	public static final String AVD_CANCEL_COLLECT="C101";//用户取消收藏
	//社区
	public static final String TOPIC_RELEASE="C000";//发布话题
	public static final String TOPIC_REPLY="C001";//话题回复
	public static final String TOPIC_TOPIC_LIST="C002";//社区话题列表
	public static final String TOPIC_DET_LIST="C003";//社区话题回复列表
	public static final String FOUND_SIGN="C004";//签到
	public static final String MY_REPLY_TOPIC="C004";//我回复的话题列表
	//兑换大厅
	public static final String SHOP_LIST="I000";//获取兑换大厅数据
	public static final String SHOP_MYAPPT_LIST="I001";//获取我的预约数据
	public static final String SHOP_COLLECTION_LIST="I002";//获取我的收藏数据
	public static final String SHOP_ITEM_DETAIL="I003";//根据 商品id获取商品信息
	//广播
	public static final String MESSAGE_PUSH="B000";//推送
	
}
