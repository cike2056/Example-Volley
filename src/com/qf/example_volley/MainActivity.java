package com.qf.example_volley;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity implements Listener<JSONObject>{
	private RequestQueue mQueue;
	private TextView tx;
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
		mQueue = Volley.newRequestQueue(getApplicationContext());
		getJSOnObject();
	}
	private void getJSOnObject() {
		// TODO Auto-generated method stub
		Request<JSONObject> requestObj = mQueue.add(new JsonObjectRequest(Method.GET,url, null, this, null));
	}
	/**
	 * 重写onResponse方法中的JSONObject是要获得的JSONObject.
	 */
	@Override
	public void onResponse(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		tx.setText(jsonObject.toString());
	}
}
