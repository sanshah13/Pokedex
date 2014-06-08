package com.example.pokedex;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class AllTeamsActivity extends Activity {

	ArrayList<Team> teams = new ArrayList<Team>();
	TeamList list;
	ListView teamsList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_teams);
		
		teamsList = (ListView) findViewById(R.id.teamsList);
		teamsList.setOnItemClickListener(new OnItemClickListener() {
		       @Override
		       public void onItemClick(AdapterView<?> a, View v, int position, long id) { 
			        Object o = teamsList.getItemAtPosition(position);
			        Team fullObject = (Team)o;
			        //Toast.makeText(AllTeamsActivity.this, "You have chosen: " + " " + fullObject.name, Toast.LENGTH_SHORT).show();
			        openTeamView(fullObject, list);
		       }  
		});
		
		loadList();
		
		if(teams.size() != 0)
		{
			teamsList.setAdapter(new TeamListBaseAdapter(this, teams));
		}
		else
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// Add the buttons'
			builder.setTitle("No Teams!");
			builder.setMessage("Create new team?");
			builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   addNewTeam();
			           }
			       });
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			               //nothing happens
			           }
			       });

			// Create the AlertDialog
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.all_teams, menu);
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

	private void loadList()
	{
		list = (TeamList) Utils.read();
		if(list != null)
			teams = list.teams; //teams = (ArrayList<Team>) list.teams.values();
		else
			list = new TeamList();
		
		/*Team test = new Team("My Creation", "It's Alive!!");
		test.addPokemon(new TeamPokemon(new Pokemon("Bulbasaur", "1", null, null, null, null, null, null), "Bulby", null, null, null, null, null));
		test.addPokemon(new TeamPokemon(new Pokemon("Charmander", "4", null, null, null, null, null, null), "Charry", null, null, null, null, null));
		list.teams.add(test);*/
		
		/*Team test1 = new Team("Monsters", "");
		test1.addPokemon(new TeamPokemon(PokemonListActivity.pokemon.get(3), "Nirali", null, null, null, null, null));
		list.teams.add(test1);*/
	}
	
	private void addNewTeam()
	{
		//Toast.makeText(AllTeamsActivity.this, "In addNewTeam()", Toast.LENGTH_SHORT).show();
		
		//dialog that prompts user to input team name and description and on 
		//selecting create opens up a team activity
		 final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 // Get the layout inflater
		 LayoutInflater inflater = this.getLayoutInflater();
		 final View layout = inflater.inflate(R.layout.custom_add_team_view, null);
		 final Context context = this;
		 
		 // Inflate and set the layout for the dialog
		 // Pass null as the parent view because its going in the dialog layout
		 builder.setView(layout)
		 // Add action buttons
		        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		                // create new team and open up the team activity
		            	EditText etName = (EditText) layout.findViewById(R.id.teamName);
		            	EditText etDescription = (EditText) layout.findViewById(R.id.teamDescription);
		            	
		            	Editable edName = etName.getText();
		            	Editable edDescrip = etDescription.getText();
		            	
		            	if(edName.toString().equals(""))
		            	{
		            		Toast.makeText(context, "Error: Please Input a Name", Toast.LENGTH_LONG).show();
		            	}
		            	else 
		            	{
			            	String name = etName.getText().toString();
		            		if (list.teams != null && list.teams.contains(new Team(name, null)))
		            		{
		            			Toast.makeText(context, "Error: Name in Use, Enter Unique Name", Toast.LENGTH_LONG).show();
		            		}
		            		else
		            		{
		            			Team team;
		            			if(edDescrip == null)
		            			{
		            				team = new Team(name, "");
		            			}
		            			else
		            			{
		            				team = new Team(name, edDescrip.toString());
		            			}
		            			list.teams.add(team);
		            			teamsList.setAdapter(new TeamListBaseAdapter(context, teams));
		            			list.write();
		            			Toast.makeText(context, "Team Created Successfully!", Toast.LENGTH_LONG).show();
		            			//Gotta open up TeamActivity and display the newly created team
		            			openTeamView(team, list);
		            		}
		            	}
		            }
		        })
		        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int id) {
		                
		            }
		        });
		 builder.setTitle("Create a Team");
		 builder.create();
		 builder.show();
		
	}
	
	public void createNewTeam(View view)
	{
		addNewTeam();
	}
	
	public void openPokedexView(View view)
	{
		list.write();
		Intent intent = new Intent(this, PokemonListActivity.class);
		startActivity(intent);
	}
	
	private void openTeamView(Team t, TeamList l)
	{
		TeamActivity.team = t;
		TeamActivity.list = l;
		Intent intent = new Intent(this, TeamActivity.class);
		startActivity(intent);
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
			View rootView = inflater.inflate(R.layout.fragment_all_teams,
					container, false);
			return rootView;
		}
	}

}
