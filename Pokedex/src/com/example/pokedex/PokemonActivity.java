package com.example.pokedex;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

//Please View readme for further information

public class PokemonActivity extends Activity {

	static Pokemon pokemon = null;
	int[] drawableIds = {R.drawable.pkm1, R.drawable.pkm2, R.drawable.pkm3, R.drawable.pkm4, R.drawable.pkm5, R.drawable.pkm6,
			R.drawable.pkm7, R.drawable.pkm8, R.drawable.pkm9, R.drawable.pkm10, R.drawable.pkm11, R.drawable.pkm12, R.drawable.pkm13,
			R.drawable.pkm14, R.drawable.pkm15, R.drawable.pkm16, R.drawable.pkm17, R.drawable.pkm18, R.drawable.pkm19, R.drawable.pkm20,
			R.drawable.pkm21, R.drawable.pkm22, R.drawable.pkm23, R.drawable.pkm24, R.drawable.pkm25};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pokemon);

		TextView header = (TextView) findViewById(R.id.header);
		header.setText(pokemon.getName() + " " + String.format("%03d", pokemon.getDex()));
		
		ImageView img = (ImageView) findViewById(R.id.pokemonPhoto);
		img.setImageDrawable(this.getResources().getDrawable(drawableIds[pokemon.dex-1]));
		
		TextView general = (TextView) findViewById(R.id.general);
		general.setText(pokemon.getFlavorText());
		
		TextView types = (TextView) findViewById(R.id.type);
		String type = "";
		for(int i = 0; i < pokemon.type.length; i++)
		{
			if(i != pokemon.type.length - 1)
			{
				type += pokemon.type[i] + ", ";
			}
			else
			{
				type += pokemon.type[i];
			}
		}
		types.setText(type);
		
		TextView evolution = (TextView) findViewById(R.id.evolution);
		String evo = "";
		for(int i = 0; i < pokemon.evo.length; i++)
		{
			if(i != pokemon.evo.length - 1)
			{
				evo += pokemon.evo[i] + " -> \n";
			}
			else
			{
				evo += pokemon.evo[i];
			}
		}
		//Toast.makeText(this, "evo: " + evo, Toast.LENGTH_LONG).show();
		evolution.setText(evo);
		
		TextView hp = (TextView) findViewById(R.id.hp);
		hp.setText(pokemon.stats[0]);
		
		TextView atk = (TextView) findViewById(R.id.atk);
		atk.setText(pokemon.stats[1]);
		
		TextView def = (TextView) findViewById(R.id.def);
		def.setText(pokemon.stats[2]);
		
		TextView spatk = (TextView) findViewById(R.id.spatk);
		spatk.setText(pokemon.stats[3]);
		
		TextView spdef = (TextView) findViewById(R.id.spdef);
		spdef.setText(pokemon.stats[4]);
		
		TextView spd = (TextView) findViewById(R.id.spd);
		spd.setText(pokemon.stats[5]);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pokemon, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_pokemon,
					container, false);
			return rootView;
		}
	}
	
	public void openPokedexView(View view)
	{
		Intent intent = new Intent(this, PokemonListActivity.class);
		startActivity(intent);
	}
	 public void openMovesView(View view)
	 {
		 MovesListActivity.pokemon = pokemon;
		 Intent intent = new Intent(this, MovesListActivity.class);
		 startActivity(intent);
	 }

}
