package com.example.pokedex;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MovesListBaseAdapter extends BaseAdapter {
	private static String[] movesList;
	private LayoutInflater mInflater;
	
	public MovesListBaseAdapter(Context context, String[] results){
		movesList = results;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return movesList.length;
	}

	@Override
	public Object getItem(int position) {
		return movesList[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null)
		{
			convertView = mInflater.inflate(R.layout.custom_move_row_view, null);
			holder = new ViewHolder();
			holder.moveName = (TextView) convertView.findViewById(R.id.moveName);
			
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.moveName.setText(movesList[position]);
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView moveName;
	}

}
