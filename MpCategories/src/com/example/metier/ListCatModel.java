package com.example.metier;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import com.example.mpcategories.MainActivity;
import com.example.mpcategories.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListCatModel extends ArrayAdapter<Categorie> {

	private List<Categorie> categories;
	
	public ListCatModel(Context context, int textViewResourceId, List<Categorie> objects) {
		super(context, textViewResourceId, objects);
		this.categories = objects;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// ici fait la relation entre objet vue et objet metiers
		
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.cat_item, parent, false);	
		TextView textView = (TextView) rowView.findViewById(R.id.textView1);
		final ImageView image = (ImageView) rowView.findViewById(R.id.imageView1);

		textView.setText(this.categories.get(position).getNom());
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					String nomImg = categories.get(position).getPhoto();
					InputStream url = new URL(MainActivity.url+"/categories/img/"+nomImg).openStream();
					Bitmap bitmap = BitmapFactory.decodeStream(url);
					image.setImageBitmap(bitmap);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}).start();

		
		//return getView(position, convertView, parent);
		return rowView;
	}
}
