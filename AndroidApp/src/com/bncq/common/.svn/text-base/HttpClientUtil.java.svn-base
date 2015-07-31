package com.bncq.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class HttpClientUtil {
		private static DefaultHttpClient httpClient;
		private static SharedPreferences sp;
		private static Context context;
		private HttpClientUtil(){
		}
		/**
		 * 初始化httpClient
		 * @return
		 */
		public static synchronized DefaultHttpClient getHttpClient() {  
	        if(httpClient==null) {  
	        	HttpParams params=new BasicHttpParams();
	        	 //设置基本参数  
	            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);  
	            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);  
	            HttpProtocolParams.setUseExpectContinue(params, true);  
	            //超时设置  
	            /*从连接池中取连接的超时时间*/  
	            ConnManagerParams.setTimeout(params, 1000);  
	            /*连接超时*/  
	            HttpConnectionParams.setConnectionTimeout(params, 2000);  
	            /*请求超时*/  
	            HttpConnectionParams.setSoTimeout(params, 4000);  
	            //设置HttpClient支持HTTp和HTTPS两种模式  
	            SchemeRegistry schReg = new SchemeRegistry();  
	            schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));  
	            schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));  
	            //使用线程安全的连接管理来创建HttpClient  
	            ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);  
	            httpClient = new DefaultHttpClient(conMgr, params);
	            //初始化公共context
	            context=CommonUtil.getContext();
				sp=context.getSharedPreferences("cookieInfo",context.MODE_PRIVATE);
	        }  
	        return httpClient;  
	    } 
	    /**
	     * post方式访问接口
	     * @param nameValuePair post参数
	     * @return json结果集
	     */
		private static String  postHttpClient(List nameValuePair){
			String result=null;
			DefaultHttpClient httpClient=getHttpClient();
			boolean isSetCookie=false;//判断是否需要set cookie
			try {
				//设置post请求参数
				HttpPost httpPost=new HttpPost(APIModel.URL);
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,HTTP.UTF_8)); //设置编码格式
				Iterator it =nameValuePair.iterator();
				while (it.hasNext()) {
					BasicNameValuePair params=(BasicNameValuePair) it.next();
					String value=params.getValue();
					if(APIModel.USER_LOGIN.equals(value)||APIModel.USER_REGIST.equals(value)){
						isSetCookie=true;
						break;
					}
				}
				//设置cookie
				if(!isSetCookie){//登录 注册方法不能set cookie
					httpClient.getCookieStore().clear();
					httpClient.getCookieStore().addCookie(getCookie());
				}
			    int res = 0;  
			    HttpResponse httpResponse = httpClient.execute(httpPost); //发送请求
		        res = httpResponse.getStatusLine().getStatusCode();   //判断是否200成功
		        if (res == 200) {  
		              StringBuilder builder = new StringBuilder();  
		              BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));  
		              for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) {  
		                  builder.append(s);  
		              }  
		              result=builder.toString();//返回结果
		              //记录cookie
		              if(isSetCookie){//登陆 注册set cookie
		            	  CookieStore cookieStore=httpClient.getCookieStore();
			              addCookie(cookieStore);
		              }
		        }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        	//httpClient.getConnectionManager().shutdown(); //释放httpClient
	        }
	        return result;
	}
   /**
    * 连接接口，统一返回接口信息
    * @param params 输入参数
    * @return 返回参数
    */
	public static JSONObject  getRespond(List params) {
		String result = postHttpClient(params);
		JSONObject resultObj=null;
		try {
			JSONObject jsonObject = new JSONObject(result);
			resultObj=jsonObject;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * 存入cookie
	 * @param cookieStore
	 */
	private static void addCookie(CookieStore cookieStore){
		Editor editor=sp.edit();
		List<Cookie> list=cookieStore.getCookies();
        for(Cookie cookie:list){
        	String phpSessId=cookie.getName();
    		String phpSessValue=cookie.getValue();
    		editor.putString(phpSessId, phpSessValue);
    		editor.putInt("Version", cookie.getVersion());
    		editor.putString("Domain", cookie.getDomain());
    		editor.putString("Path", cookie.getPath());
        }
        editor.commit();
	}
	/**
	 * 获取cookie
	 * @return
	 */
	private static Cookie getCookie(){
		Map map=sp.getAll();
		Iterator it=map.entrySet().iterator();
		BasicClientCookie cookie = null;
		while(it.hasNext()){
			Entry entry=(Entry) it.next();
			String name=entry.getKey().toString();
			String value=entry.getValue().toString();
			if("Version".equals(name)){
				cookie.setVersion(Integer.valueOf(value));
			}else if("Domain".equals(name)){
				cookie.setDomain(value);
			}else if("Path".equals(name)){
				cookie.setPath(value);
			}else{
				cookie =new BasicClientCookie(name, value);
			}
		}
		return cookie;
	}
}
