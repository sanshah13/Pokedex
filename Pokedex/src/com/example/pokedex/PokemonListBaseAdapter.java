package com.example.pokedex;

import java.util.ArrayList; 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PokemonListBaseAdapter extends BaseAdapter {
	 private static ArrayList<Pokemon> pokemonArrayList;
	 private Integer[] imgid = { 
			    R.drawable.pkm1s, R.drawable.pkm2s, R.drawable.pkm3s, R.drawable.pkm4s, R.drawable.pkm5s, R.drawable.pkm6s,
			    R.drawable.pkm7s, R.drawable.pkm8s, R.drawable.pkm9s, R.drawable.pkm10s, R.drawable.pkm11s, R.drawable.pkm12s,
			    R.drawable.pkm13s, R.drawable.pkm14s, R.drawable.pkm15s, R.drawable.pkm16s, R.drawable.pkm17s, R.drawable.pkm18s,
			    R.drawable.pkm19s, R.drawable.pkm20s, R.drawable.pkm21s, R.drawable.pkm22s, R.drawable.pkm23s, R.drawable.pkm24s,
			    R.drawable.pkm25s 
			};
	 private LayoutInflater mInflater;

	 public PokemonListBaseAdapter(Context context, ArrayList<Pokemon> results) {
		  pokemonArrayList = results;
		  mInflater = LayoutInflater.from(context);
	 }

	 public int getCount() {
		 return pokemonArrayList.size();
	 }

	 public Object getItem(int position) {
		 return pokemonArrayList.get(position);
	 }

	 public long getItemId(int position) {
		 return position;
	 }

	 public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHolder holder;
		 if (convertView == null) {
			 convertView = mInflater.inflate(R.layout.custom_row_view, null);
			 holder = new ViewHolder();
			 holder.txtName = (TextView) convertView.findViewById(R.id.name);
			 holder.imgPhoto = (ImageView) convertView.findViewById(R.id.photo);
			 
			 convertView.setTag(holder);
		 } 
		 else {
			  holder = (ViewHolder) convertView.getTag();
		 }
		  
		 holder.txtName.setText(String.format("%03d", pokemonArrayList.get(position).dex) + " " + pokemonArrayList.get(position).name);
		 holder.imgPhoto.setImageResource(imgid[pokemonArrayList.get(position).dex - 1]);
		 
		 return convertView;
	}
	
	static class ViewHolder {
		TextView txtName;
		ImageView imgPhoto;
	}
}
