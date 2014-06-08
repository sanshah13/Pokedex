package com.example.pokedex;

import java.io.InputStream;
import java.util.ArrayList; 
import java.util.Collections;

import android.app.Activity; 
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class PokemonListActivity extends Activity {

	static ArrayList<Pokemon> pokemon = new ArrayList<Pokemon>();
	ListView lv1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pokemon_list);
		
		lv1 = (ListView) findViewById(R.id.ListView01);
		
		if(pokemon.size() == 0)
			loadList(); //Initially sets up list based on number
		getByNumber();
		
	    lv1.setOnItemClickListener(new OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> a, View v, int position, long id) { 
		         Object o = lv1.getItemAtPosition(position);
		         Pokemon fullObject = (Pokemon)o;
		         //would open up another view that shows pokemon details
		         openPokemonActivity(fullObject);     
		         //Toast.makeText(PokemonListActivity.this,  fullObject.toString(), Toast.LENGTH_SHORT).show();
		         //Toast.makeText(PokemonListActivity.this, "You have chosen: " + " " + fullObject.name, Toast.LENGTH_SHORT).show();
		    }  
	    });
	}

	private void loadList()
	{
		//Load the pokemon, Reading all needed Dex Information from the dex files
		//Files located in res/raw
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.bulbasaur)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.ivysaur)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.venusaur)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.charmander)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.charmeleon)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.charizard)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.squritle)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.wartortle)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.blastoise)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.caterpie)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.metapod)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.butterfree)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.weedle)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.kakuna)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.beedrill)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.pidgey)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.pidgeotto)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.pidgeot)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.spearow)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.fearow)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.rattata)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.raticate)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.ekans)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.arbok)));
		pokemon.add(new Pokemon(getResources().openRawResource(R.raw.pikachu)));
	//25
		
		//pokemon.add(new Pokemon("001"));
		//InputStream is = getResources().openRawResource(R.raw.bulbasaur);
		//pokemon.add(new Pokemon(is);
		
		/* The fantastic stuff Sangini wrote
		String[] grass = {"GRASS"};
		String[] fire = {"FIRE"};
		String[] water = {"WATER"};
		String[] bug = {"BUG"};
		pokemon.add(new Pokemon("Bulbasaur", "1", grass));
		pokemon.add(new Pokemon("Ivysaur", "2", grass));
		pokemon.add(new Pokemon("Venusaur", "3", grass));
		pokemon.add(new Pokemon("Charmander", "4", fire));
		pokemon.add(new Pokemon("Charmeleon", "5", fire));
		pokemon.add(new Pokemon("Charizard", "6", fire));
		pokemon.add(new Pokemon("Squirtle", "7", water));
		pokemon.add(new Pokemon("Wartortle", "8", water));
		pokemon.add(new Pokemon("Blastoise", "9", water));
		pokemon.add(new Pokemon("Caterpie", "10", bug));
		*/
	}
	
	/**
	 * Loads the arraylist with all the pokemon in number order
	 * @return
	 */
	private void getByNumber() {
		Collections.sort(pokemon, new CompareByNumber());
		lv1.setAdapter(new PokemonListBaseAdapter(this, pokemon));
		Button b = (Button) findViewById(R.id.button1);
		b.setText("List By: Number");
	}
	
	/**
	 * Loads the arraylist with all the pokemon in alphabetical order
	 * @return
	 */
	private void getByName() {
		Collections.sort(pokemon, new CompareByName());
		lv1.setAdapter(new PokemonListBaseAdapter(this, pokemon));
		Button b = (Button) findViewById(R.id.button1);
		b.setText("List By: Name");
	
	}
	
	/**
	 * Loads the arraylist with all the pokemon in type order
	 * @return
	 */
	private void getByType() {
		Collections.sort(pokemon, new CompareByType());
		lv1.setAdapter(new PokemonListBaseAdapter(this, pokemon));
		Button b = (Button) findViewById(R.id.button1);
		b.setText("List By: Type");
	}
	
	
	public void openTeamView(View view)
	{
		Intent intent = new Intent(this, AllTeamsActivity.class);
		startActivity(intent);
	}
	
	public void openPokemonActivity(Pokemon p)
	{
		PokemonActivity.pokemon = p;
        Intent intent = new Intent(this, PokemonActivity.class);
        startActivity(intent);
	}
	
	public void openListByDialog(View view)
	{
		String[] listByArray = {"Number", "Name", "Type"};
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle("List By")
	           .setItems(listByArray, new DialogInterface.OnClickListener() {
	        	   public void onClick(DialogInterface dialog, int which) {
		               // The 'which' argument contains the index position
		               // of the selected item
	        		   
	        		   if(which == 0)
	        		   {
	        			   //Toast.makeText(PokemonListActivity.this, "You have chosen: Number", Toast.LENGTH_SHORT).show();
	        			   if(!(((Button) findViewById(R.id.button1)).getText().equals("List By: Number")))
	        					   getByNumber();
	        		   }
	        		   else if(which == 1)
	        		   {
	        			   //Toast.makeText(PokemonListActivity.this, "You have chosen: Name", Toast.LENGTH_SHORT).show();
	        			   if(!(((Button) findViewById(R.id.button1)).getText().equals("List By: Name")))
	        					   getByName();
	        		   }
	        		   else
	        		   {
	        			   //Toast.makeText(PokemonListActivity.this, "You have chosen: Type", Toast.LENGTH_SHORT).show();
	        			   if(!(((Button) findViewById(R.id.button1)).getText().equals("List By: Type")))
	        					  getByType();
	        		   }
	        	   }
	           });
	    AlertDialog thing = builder.create();
        thing.show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pokemon_list, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_pokemon_list,
					container, false);
			return rootView;
		}
	}

}
