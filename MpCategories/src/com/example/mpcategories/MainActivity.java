package com.example.mpcategories;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import com.example.metier.Categorie;
import com.example.metier.ListCatModel;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncStatusObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static String ipAdress;
	public static String port;
	public static String url;
	ListCatModel listCatModel;
	List<Categorie> categories = new ArrayList<Categorie>();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		ipAdress = preferences.getString("ip_adress", "10.0.2.2");
		port = preferences.getString("port", "80");
		url = "http://" + ipAdress + ":" + port;
		
		//Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
		listCatModel = new ListCatModel(this, R.layout.cat_item, categories);
		ListView listView = (ListView) findViewById(R.id.list1);
		listView.setAdapter(listCatModel);
		
		new MyTask().execute(url+"/categories/categorie.php");
	}

	public StringBuffer getHttpResponse(String url) {
		StringBuffer strBuffer = new StringBuffer();
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResp = client.execute(httpGet);
			if (httpResp.getStatusLine().getStatusCode() == 200){
				HttpEntity httpEntity = httpResp.getEntity();
				InputStream is = httpEntity.getContent();
				BufferedReader bf = new BufferedReader(new InputStreamReader(is));
				String str;
				while ((str = bf.readLine()) != null) {
					strBuffer.append(str);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return strBuffer;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent intent = new Intent(this, SettingActivity.class);
		switch(item.getItemId()) {
			case R.id.options:
				startActivity(intent);
				return true;
			default:
				break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	class MyTask extends AsyncTask<String, Integer, StringBuffer> {

		@Override
		protected StringBuffer doInBackground(String... params) {
			String url = params[0];
			StringBuffer strBuff = getHttpResponse(url);
			//Log.e("STRing buff",strBuff.toString());
			return strBuff;
		}
		@Override
		protected void onPostExecute(StringBuffer result) {
			//Log.e("STRing buff",result.toString());
			Gson gson = new Gson();
			Categorie[] catz= gson.fromJson(result.toString(), Categorie[].class);
			for (Categorie c : catz) {
				categories.add(c);
			}
			listCatModel.notifyDataSetChanged();
			super.onPostExecute(result);
		}
		
	}
}
