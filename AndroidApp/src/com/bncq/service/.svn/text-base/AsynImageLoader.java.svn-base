package com.bncq.service;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bncq.common.APIModel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class AsynImageLoader {
	// 缓存下载过的图片的Map
	private Map<String,SoftReference<Bitmap>> caches;
	//任务队列  
	private List<Task> taskQueue;
	private boolean isRunning =false;
	
	public AsynImageLoader(){
		// 初始化变量 
		caches=new HashMap<String, SoftReference<Bitmap>>();
		taskQueue=new ArrayList<AsynImageLoader.Task>();
		// 启动图片下载线程 
		isRunning=true;
		new Thread(runnable).start();
	}
	/** 
     *  
     * @param imageView 需要延迟加载图片的对象 
     * @param url 图片的URL地址 
     * @param resId 图片加载过程中显示的图片资源 
     */
	public void showImageAsyn(ImageView imageView, String url,ProgressBar pbr_shop_probar){  
		imageView.setTag(url);
		Bitmap bitmap=loadImageAsyn(url, getImageCallback(imageView,pbr_shop_probar));
		if(bitmap==null){
			pbr_shop_probar.setVisibility(View.VISIBLE);
			imageView.setVisibility(View.GONE);
		}else{
			pbr_shop_probar.setVisibility(View.GONE);
			imageView.setVisibility(View.VISIBLE);
			imageView.setImageBitmap(bitmap);
		}
	}
	public Bitmap loadImageAsyn(String path, ImageCallback callback){ 
		// 判断缓存中是否已经存在该图片  
		if(caches.containsKey(path)){
			 // 取出软引用
			SoftReference<Bitmap> rf=caches.get(path);
			// 通过软引用，获取图片
			Bitmap bitmap=rf.get();
			// 如果该图片已经被释放，则将该path对应的键从Map中移除掉 
			if(bitmap==null){
				caches.remove(path);
			}else{
				// 如果图片未被释放，直接返回该图片  
				return bitmap;
			}
		}else{
			// 如果缓存中不常在该图片，则创建图片下载任务
			Task task=new Task();
			task.path=path;
			task.callback=callback;
			if(!taskQueue.contains(task)){
				taskQueue.add(task);
				// 唤醒任务下载队列 
				synchronized (runnable) {
					runnable.notify();
				}
			}
		}
		 // 缓存中没有图片则返回null  
		return null;
	}
	 /** 
     *  
     * @param imageView  
     * @param resId 图片加载完成前显示的图片资源ID 
     * @return 
     */ 
	private ImageCallback getImageCallback(final ImageView imageView,final ProgressBar pbr_shop_probar){ 
		return new ImageCallback(){
			@Override
			public void loadImage(String path, Bitmap bitmap) {
				// TODO Auto-generated method stub
				if(path.equals(imageView.getTag().toString())){
					pbr_shop_probar.setVisibility(View.GONE);
					imageView.setVisibility(View.VISIBLE);
					imageView.setImageBitmap(bitmap);
				}else{
					pbr_shop_probar.setVisibility(View.VISIBLE);
					imageView.setVisibility(View.GONE);
				}
			}
		};
	}
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			// 子线程中返回的下载完成的任务  
			Task task=(Task) msg.obj;
			// 调用callback对象的loadImage方法，并将图片路径和图片回传给adapter
			task.callback.loadImage(task.path, task.bitmap);
		};
	};
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			while (isRunning) {
				// 当队列中还有未处理的任务时，执行下载任务
				while (taskQueue.size()>0) {
					// 获取第一个任务，并将之从任务队列中删除 
					Task task=taskQueue.remove(0);
					 // 将下载的图片添加到缓存
					task.bitmap=getbitmap(task.path);
					caches.put(task.path, new SoftReference<Bitmap>(task.bitmap));
					if(handler!=null){
						// 创建消息对象，并将完成的任务添加到消息对象中  
						Message msg=handler.obtainMessage();
						msg.obj=task;
						// 发送消息回主线程  
						handler.sendMessage(msg);
					}
				}
			   //如果队列为空,则令线程等待  
				synchronized (this) {
					try {
						this.wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		}
	};
    //回调接口  
    public interface ImageCallback{  
        void loadImage(String path, Bitmap bitmap);  
    } 
	class Task{
		// 下载任务的下载路径 
		String path;
		// 下载的图片  
		Bitmap bitmap;
		ImageCallback callback;
		
		@Override
		public boolean equals(Object o) {
			// TODO Auto-generated method stub
			Task task=(Task) o;
			return task.path.equals(path);
		}
	}
	 /** 
     * 根据一个网络连接(String)获取bitmap图像 
     *  
     * @param imageUri 
     * @return 
     * @throws MalformedURLException 
     */
	public static Bitmap getbitmap(String imageUri) {
		// 显示网络上的图片
		Bitmap bitmap = null;  
		try {
			URL myFileUrl=new URL(APIModel.BASEURL+imageUri);
			HttpURLConnection conn=(HttpURLConnection) myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is=conn.getInputStream();
			bitmap=BitmapFactory.decodeStream(is);
			is.close();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return bitmap;
	}
}
