package com.example.pokedex;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class TeamPokemonActivity extends Activity {

	static Team team = null;
	static TeamPokemon pokemon = null;
	int[] drawableIds = {R.drawable.pkm1, R.drawable.pkm2, R.drawable.pkm3, R.drawable.pkm4, R.drawable.pkm5, R.drawable.pkm6,
			R.drawable.pkm7, R.drawable.pkm8, R.drawable.pkm9, R.drawable.pkm10, R.drawable.pkm11, R.drawable.pkm12, R.drawable.pkm13,
			R.drawable.pkm14, R.drawable.pkm15, R.drawable.pkm16, R.drawable.pkm17, R.drawable.pkm18, R.drawable.pkm19, R.drawable.pkm20,
			R.drawable.pkm21, R.drawable.pkm22, R.drawable.pkm23, R.drawable.pkm24, R.drawable.pkm25};
	
	static TeamList list = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_pokemon);

		TextView header = (TextView) findViewById(R.id.nicknameHeader);
		if(pokemon.nickname.equals("") || pokemon.nickname == null)
		{
			header.setText(pokemon.chosenPokemon.name);
		}
		else
		{
			header.setText(pokemon.nickname);
		}
		
		TextView nameHeader = (TextView) findViewById(R.id.nameHeader);
		nameHeader.setText(pokemon.chosenPokemon.name + " " + String.format("%03d", pokemon.chosenPokemon.dex));
		ImageView img = (ImageView) findViewById(R.id.imageView1);
		img.setImageDrawable(this.getResources().getDrawable(drawableIds[pokemon.chosenPokemon.dex-1]));
		
		TextView hp = (TextView) findViewById(R.id.hpInfo);
		hp.setText(pokemon.chosenPokemon.stats[0]);
		
		TextView atk = (TextView) findViewById(R.id.atkInfo);
		atk.setText(pokemon.chosenPokemon.stats[1]);
		
		TextView def = (TextView) findViewById(R.id.defInfo);
		def.setText(pokemon.chosenPokemon.stats[2]);
		
		TextView spatk = (TextView) findViewById(R.id.spatkInfo);
		spatk.setText(pokemon.chosenPokemon.stats[3]);
		
		TextView spdef = (TextView) findViewById(R.id.spdefInfo);
		spdef.setText(pokemon.chosenPokemon.stats[4]);
		
		TextView spd = (TextView) findViewById(R.id.spdInfo);
		spd.setText(pokemon.chosenPokemon.stats[5]);
		
		TextView ability = (TextView) findViewById(R.id.abilityInfo);
		ability.setText(pokemon.chosenAbility);
		
		String[] moves = new String[pokemon.chosenMoves.length];
		int counter = 0;
		for(String s: pokemon.chosenMoves)
		{
			if(s != null)
			{
				moves[counter] = s;
				counter++;
			}
				
		}
		
		ListView movesList = (ListView) findViewById(R.id.listView1);
		
		if(moves.length!=0)
			movesList.setAdapter(new MovesListBaseAdapter2(this, moves));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.team_pokemon, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_team_pokemon,
					container, false);
			return rootView;
		}
	}
	
	public void openTeamView(View view)
	{
		list.write();
		Intent intent = new Intent(this, TeamActivity.class);
		startActivity(intent);
	}
	
	public void deletePokemon(View view)
	{
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final Context context = this;
		builder.setMessage("Are you sure you want to delete this team?")
			   .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					for(int i = 0; i < 6; i++)
					{
						if(team.pokemon[i] != null && team.pokemon[i].equals(pokemon))
						{
							team.pokemon[i] = null;
						}
					}
					list.write();
					TeamActivity.team = team;
					TeamActivity.list = list;
					Intent intent = new Intent(context, TeamActivity.class);
					startActivity(intent);
					
				}
			   })
			   .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
		builder.create();
		builder.show();
		
	}
	
	public void changeNickname(View view)
	{
		 AlertDialog.Builder build = new AlertDialog.Builder(this);
  	   	 LayoutInflater inflater = this.getLayoutInflater();
		 final View layout = inflater.inflate(R.layout.custom_nickname_view, null);
		 final EditText nick = (EditText) layout.findViewById(R.id.nickname);
		 nick.setText(pokemon.nickname);
		 
		 build.setView(layout)
		 	  .setTitle("Give Nickname")
  	   		  .setPositiveButton("Set Nickname", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						if(!nick.getText().toString().equals(""))
						{
							pokemon.nickname = nick.getText().toString();
						}
						else
						{
							pokemon.nickname = pokemon.chosenPokemon.name;
						}
						
					}
				});
		 build.create();
		 build.show();
	}

}
