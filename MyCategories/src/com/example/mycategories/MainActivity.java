package com.example.mycategories;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.mycategoriesMetier.Categorie;
import com.example.mycategoriesMetier.ListCatModel;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
      ListCatModel listModel;
      List<Categorie> cats= new ArrayList<Categorie>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences preferences =
				PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		ipAdress=preferences.getString("ip_address", "10.0.2.2");
		port=preferences.getString("port", "80");
		url="http://"+ipAdress+":"+port;
			//Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
			listModel=new ListCatModel(this, R.layout.cat_item, cats);
			ListView listView=(ListView) findViewById(R.id.listView1);
			listView.setAdapter(listModel);
			new MyTask().execute(url+"/categories/categorie.php");
	}
	public StringBuffer getHttpResponse(String url) {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = client.execute(httpGet);
			if (response.getStatusLine().getStatusCode()==200) {
				HttpEntity httpEntity=response.getEntity();
				InputStream is=httpEntity.getContent();
				BufferedReader br= new 
						BufferedReader(new InputStreamReader(is));
				String str;
				while((str=br.readLine())!=null) {
					stringBuffer.append(str);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return stringBuffer;	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.options:
		Intent intent = new Intent(this, SettingActivity.class);
		startActivity(intent);
		return true;
		
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	class MyTask extends AsyncTask<String, Integer, StringBuffer>{

		@Override
		protected StringBuffer doInBackground(String... args) {
			String url=args[0];
			StringBuffer stringBuffer=getHttpResponse(url);
		
			return stringBuffer;
		}
		@Override
		protected void onPostExecute(StringBuffer result) {
			Log.e("", result.toString());
			Gson gson= new Gson();
			Categorie[] categories=gson.fromJson(result.toString(), Categorie[].class);
			for (Categorie c:categories) {
				Log.e("", c.getNomCategorie());
				cats.add(c);
			}
			listModel.notifyDataSetChanged();
			super.onPostExecute(result);
		}
	}

}
