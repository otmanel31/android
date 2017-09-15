package com.example.mycategoriesMetier;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.example.mycategories.MainActivity;
import com.example.mycategories.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListCatModel extends ArrayAdapter<Categorie>{
	private List<Categorie> categories;

	public ListCatModel(Context context, int textViewResourceId, List<Categorie> cats) {
		super(context, textViewResourceId, cats);
		this.categories=cats;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater=(LayoutInflater)
				getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.cat_item, parent, false);
		TextView textView=(TextView) rowView.findViewById(R.id.textViewCat);
		final ImageView imageView=(ImageView) rowView.findViewById(R.id.imageViewCat);
		textView.setText(categories.get(position).getNomCategorie());
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
	                String nomImage=categories.get(position).getPhoto();
					InputStream is=new URL(MainActivity.url+"/categories/img/"+ nomImage).openStream();
					Bitmap bitmap=BitmapFactory.decodeStream(is);
					imageView.setImageBitmap(bitmap);
				} 
				 catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start(); 
	
		return rowView;
		
		
	}
	
	
	
	

}
