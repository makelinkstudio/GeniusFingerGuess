package com.bncq.common;

public final class APIModel {

	//����Ҫ��
	public static final String BASEURL="http://192.168.1.151/myServer";//��̨����·��
	public static final String URL=BASEURL+"/api.php";//�ӿڷ��ʵ�ַ
	public static final String SHOP_URL=BASEURL+"/api.php/Home/Index/integralmall";//�һ�����
	public static final String SHOP_DETAIL_URL=BASEURL+"/api.php/Home/Index/gooddetail";//��Ʒ��ϸ
	public static final String SHOP_MYAPPT_URL=BASEURL+"/api.php/Home/Index/myappoint";//�ҵ�ԤԼ
	public static final String SHOP_COLLECTION_URL=BASEURL+"/api.php/Home/Index/myappoint";//�ҵ�ԤԼ
	
	public static final String INFMAP_URL=BASEURL+"/api.php/Home/Index/infoLineResult";//��ȭ��Ϸͼ
	public static final String INFCQRULE_URL=BASEURL+"/api.php/Home/Index/inforesult";//��ȭ����ͼ
	public static final String INFCQRESULT_URL=BASEURL+"/api.php/Home/Index/infofiveresult";//��ȭ�峡���ͼ
	public static final String SERVER_SESSIONID="PHPSESSID";//������sessionid��
	
	public static final String CODE="code";		 //����ӿڱ���key
	public static final String DATA="data";		//��������ӿ�����key
	public static final String STATUS="status";		//���״̬key
	public static final String RETCODE="return";		//�������key
	public static final String MSG="msg";		//�����Ϣkey
	
	public static final String SUCCEE="1";	//�ɹ�����
	public static final String ERROR="error";	//ʧ�ܴ���

    //�û�ģ��
	public static final String USER_LOGIN="U000";	//��¼
	public static final String USER_LOGOUT="U001";	//�˳���¼
	public static final String USER_REGIST="U002";	//ע��
	public static final String USER_INFO="U003";	//�û���Ϣ
	public static final String UPD_USER_INFO="U008";	//�޸��û���Ϣ
	//��ȭ
	public static final String CQ_INF_ALL="P000";//���յ�ǰ��ȭ��Ϣ
	public static final String CQ_UP_INFO="P001";//��ǰ�����û���ȭ��Ϣ
	public static final String CQ_SET_UP="P002";//�û�ѡ���ȭ
	public static final String CQ_GETUSERINF="P003";//��ȡ��ȭ�û���Ϣ
	public static final String CQ_GETINFO="P004";//��ȡ��ȭ��Ϣ
	public static final String CQ_GETRESULT="P005";//��ȡ��ȭ�����Ϣ
	public static final String CQ_GETSYSTIME="P006";//��ȡ��ǰϵͳʱ��
	public static final String CQ_GETMATINFO="P007";//��ȡ������Ϣ
	public static final String CQ_GET_LASTMAT_INFO="P008";//��ȡ������Ϣ
	//��ҳ
	public static final String AVD_APPOINT="A000";//�û�ԤԼ
	public static final String AVD_COLLECT="C100";//�û��ղ�
	public static final String AVD_CANCEL_COLLECT="C101";//�û�ȡ���ղ�
	//����
	public static final String TOPIC_RELEASE="C000";//��������
	public static final String TOPIC_REPLY="C001";//����ظ�
	public static final String TOPIC_TOPIC_LIST="C002";//���������б�
	public static final String TOPIC_DET_LIST="C003";//��������ظ��б�
	public static final String FOUND_SIGN="C004";//ǩ��
	public static final String MY_REPLY_TOPIC="C004";//�һظ��Ļ����б�
	//�һ�����
	public static final String SHOP_LIST="I000";//��ȡ�һ���������
	public static final String SHOP_MYAPPT_LIST="I001";//��ȡ�ҵ�ԤԼ����
	public static final String SHOP_COLLECTION_LIST="I002";//��ȡ�ҵ��ղ�����
	public static final String SHOP_ITEM_DETAIL="I003";//���� ��Ʒid��ȡ��Ʒ��Ϣ
	//�㲥
	public static final String MESSAGE_PUSH="B000";//����
	
}
