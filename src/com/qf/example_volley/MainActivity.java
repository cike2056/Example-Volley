package com.qf.example_volley;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.qf.example_volley.cache.BitmapCache;
import com.qf.example_volley.entity.News;
import com.qf.example_volley.entity.Story;
import com.qf.example_volley.entity.TopStory;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements Listener<JSONObject>{
	private RequestQueue mQueue;
	private NetworkImageView netImg;//Volley�е�װ������ͼƬ�Ŀռ䰴.
	private TextView tx;
	private ListView lv;
	private News news ;
	private List<Story> stories;
	private List<TopStory> topStories;
	String url = "http://news-at.zhihu.com/api/3/stories/latest";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
	private void init() {
		// TODO Auto-generated method stub
		tx=(TextView) findViewById(R.id.tx);
		lv = (ListView) findViewById(R.id.lv);
		mQueue = Volley.newRequestQueue(getApplicationContext());
		getJSOnObject();

	}
	private void getJSOnObject() {
		// TODO Auto-generated method stub
		mQueue.add(new JsonObjectRequest(Method.GET,url, null, this, null));
		
	}
	/**
	 * ��дonResponse�����е�JSONObject��Ҫ��õ�JSONObject.
	 */
	@Override
	public void onResponse(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		//tx.setText(jsonObject.toString());
		if(jsonObject!=null)
		getContent(jsonObject);
	}
	
	/**
	 * ���JSON����.
	 * @param json
	 */
	private void getContent(JSONObject json) {
		// TODO Auto-generated method stub
		try {
			String date = json.getString("date");
			JSONArray arrayStories = json.getJSONArray("stories");
			news = new News();
			if(arrayStories!=null&&arrayStories.length()>0){
				stories = new ArrayList<Story>();
				Story story=null;
				for(int i=0;i<arrayStories.length();i++){
					JSONObject jsonStory = arrayStories.getJSONObject(i);
					story = new Story();
					story.setShare_url(jsonStory.getString("share_url"));
					story.setTitle(jsonStory.getString("title"));
					story.setGa_prefixtype(jsonStory.getString("ga_prefix"));
					story.setId(jsonStory.getString("id"));
					story.setType(jsonStory.getString("type"));
					//ͼƬ
					JSONArray arrayImages = jsonStory.getJSONArray("images");
					if(arrayImages!=null&&arrayImages.length()>0){
						String imagesArr []= new String [arrayImages.length()];
						for(int j=0;j<arrayImages.length();j++){
							imagesArr[j]= arrayImages.getString(j);
						}
					}
					stories.add(story);
				}
				Log.i("stories"," "+stories.get(2).getTitle());
			}
			JSONArray arrayTopStories = json.getJSONArray("top_stories");
			if(arrayTopStories!=null&&arrayTopStories.length()>0){
				topStories = new ArrayList<TopStory> ();
				TopStory topStory=null;
				for(int i=0;i<arrayTopStories.length();i++){
					JSONObject jsonStory2 = arrayTopStories.getJSONObject(i);
					topStory = new TopStory();
					topStory.setShare_url(jsonStory2.getString("share_url"));
					topStory.setTitle(jsonStory2.getString("title"));
					topStory.setGa_prefixtype(jsonStory2.getString("ga_prefix"));
					topStory.setId(jsonStory2.getString("id"));
					topStory.setType(jsonStory2.getString("type"));
					topStory.setImage(jsonStory2.getString("image"));
					topStories.add(topStory);
				}
				
			}
			
			news.setDate(date);
			news.setStroies(stories);
			news.setTopStories(topStories);
			
			MyAdapter adapter = new MyAdapter(stories,MainActivity.this);
			lv.setAdapter(adapter);
			if(news!=null){
				Log.i("MainActivity","news��Ϊ��");
			}else{
				Log.i("MainActivity","newsΪ��");
			}
			if(stories!=null){
				Log.i("MainActivity","stories��Ϊ��");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * �ڲ���:MyAdapter,BaseAdapter
	 */
	class MyAdapter extends BaseAdapter{
		List<Story> stories_adapter;
		Context context;
		public MyAdapter(List<Story> stories,Context context ){
			stories_adapter = stories;
			context = context;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			Log.i("stories_adapter.size()"," "+stories_adapter.size());
			return stories_adapter==null? 0:stories_adapter.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return stories_adapter.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			MyHolder myHolder;
			if(convertView==null){
				myHolder = new MyHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.item_lv, parent, false);
				myHolder.imgv = (NetworkImageView) convertView.findViewById(R.id.netImgv);
				convertView.setTag(myHolder);
			}else{
				myHolder = (MyHolder) convertView.getTag();
			}
			//getImages ֻ��һ��ͼƬ.
			Story story = stories_adapter.get(position);
			if(story!=null&&story.getImages().length>0){
				myHolder.imgv.setImageUrl(story.getImages()[0], new ImageLoader(mQueue, new BitmapCache()));
			}
			return convertView;
		}
	}
	class MyHolder{
		public NetworkImageView imgv;
	}
}
