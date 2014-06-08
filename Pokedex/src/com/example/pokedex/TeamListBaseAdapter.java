package com.example.pokedex;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TeamListBaseAdapter extends BaseAdapter {

	private static ArrayList<Team> teamsArrayList;
	private LayoutInflater mInflater;
	
	public TeamListBaseAdapter(Context context, ArrayList<Team> results){
		teamsArrayList = results;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return teamsArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return teamsArrayList.get(position);
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
			convertView = mInflater.inflate(R.layout.custom_team_row_view, null);
			holder = new ViewHolder();
			holder.teamName = (TextView) convertView.findViewById(R.id.teamName);
			holder.description = (TextView) convertView.findViewById(R.id.description);
			
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.teamName.setText(teamsArrayList.get(position).name);
		holder.description.setText(teamsArrayList.get(position).description);
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView teamName;
		TextView description;
	}

}
