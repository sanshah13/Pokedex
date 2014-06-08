package com.example.pokedex;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class TeamActivity extends Activity {

	static Team team = null;
	static TeamList list = null;
	
	TextView teamName;
	TextView teamDescrip;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team);

		final Context context = this;
		
		teamName = (TextView)findViewById(R.id.teamHeader);
		teamDescrip = (TextView)findViewById(R.id.teamDescription);
		final ListView pkmList = (ListView)findViewById(R.id.pkmList);
		TextView adv = (TextView)findViewById(R.id.advantages);
		TextView dis = (TextView)findViewById(R.id.disadvantages);
		
		teamName.setText(team.name);
	    
		if(!team.description.trim().equals(""))
		{
			teamDescrip.setTypeface(teamDescrip.getTypeface(),Typeface.BOLD);
			teamDescrip.setText(team.description);
		}
		else
		{
			teamDescrip.setTypeface(teamDescrip.getTypeface(), Typeface.ITALIC);
			teamDescrip.setText("No Description Available");
		}
		
		pkmList.setOnItemClickListener(new OnItemClickListener() {
		        @Override
		        public void onItemClick(AdapterView<?> a, View v, int position, long id) { 
			         Object o = pkmList.getItemAtPosition(position);
			         TeamPokemon fullObject = (TeamPokemon)o;
			         //would open up another view that shows pokemon details
			         openTeamPokemonActivity(fullObject, list);
			         //Toast.makeText(TeamActivity.this, "You have chosen: " + " " + fullObject.nickname, Toast.LENGTH_SHORT).show();
			    }  
		    });
		 
		 ArrayList<TeamPokemon> alist = new ArrayList<TeamPokemon>();
		 for(TeamPokemon t: team.pokemon)
		 {
			 if(t != null)
				 alist.add(t);
		 }
		 if(alist.size() != 0)
		 {
			 pkmList.setAdapter(new TeamPokemonListBaseAdapter(this, alist));
		 }
		 else
		 {
			 AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// Add the buttons'
			builder.setTitle("No Pokemon!");
			builder.setMessage("Add a Pokemon to your Team?");
			builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   addNewPokemon();
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
		 
		 if(alist.size() == 6)
		 {
			 Button add = (Button) findViewById(R.id.addPokemon);
			 add.setEnabled(false);
		 }
		 else
		 {
			 Button add = (Button) findViewById(R.id.addPokemon);
			 add.setEnabled(true);
		 }
		 //TODO put in info of type adv. and disadv.
		 adv.setText(team.typeAdvantages[0]);
		 dis.setText(team.typeAdvantages[1]);
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.team, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_team, container,
					false);
			return rootView;
		}
	}

	public void openTeamsListView(View view)
	{
		list.write();
		Intent intent = new Intent(this, AllTeamsActivity.class);
		startActivity(intent);
	}
	
	public void deleteTeam(View view)
	{
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final Context context = this;
		builder.setMessage("Are you sure you want to delete this team?")
			   .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					list.teams.remove(team);
					list.write();
					Intent intent = new Intent(context, AllTeamsActivity.class);
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
	
	public void addPokemon(View view)
	{
		addNewPokemon();
	}
	
	public void editText(View view)
	{
		//dialog that prompts user to input team name and description and on 
		//selecting create opens up a team activity
		 final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 // Get the layout inflater
		 LayoutInflater inflater = this.getLayoutInflater();
		 final View layout = inflater.inflate(R.layout.custom_add_team_view, null);
		 final Context context = this;
		 
		 final EditText etName = (EditText) layout.findViewById(R.id.teamName);
     	 final EditText etDescription = (EditText) layout.findViewById(R.id.teamDescription);
     	
     	 etName.setText(team.name);
     	 if(!team.description.equals(""))
     		 etDescription.setText(team.description);
		 
     	 // Inflate and set the layout for the dialog
		 // Pass null as the parent view because its going in the dialog layout
		 builder.setView(layout)
		 // Add action buttons
		        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int id) {
		                // change team name and/or description
		            	Editable edName = etName.getText();
		            	Editable edDescrip = etDescription.getText();
		            	
		            	if(edName.toString().equals(""))
		            	{
		            		Toast.makeText(context, "Error: Name Required", Toast.LENGTH_LONG).show();
		            	}
		            	else 
		            	{
			            	String name = etName.getText().toString();
			            	if(!list.teams.contains(new Team(name, null)))
			            	{	
			            		team.name = name; 
			            		list.write();
			            		
			            		Toast.makeText(context, "Team Name Changed Successfully!", Toast.LENGTH_LONG).show();
			            		teamName.setText(name);
			            	}
			            	else if(!name.equals(team.name))
			            	{
			            		Toast.makeText(context, "Error: Team Name Already In Use", Toast.LENGTH_LONG).show();
			            	}
		            	}
		            	
		            	team.description = edDescrip.toString();
		            	if(!team.description.trim().equals(""))
		        		{
		        			teamDescrip.setTypeface(teamDescrip.getTypeface(),Typeface.BOLD);
		        			teamDescrip.setText(team.description);
		        		}
		        		else
		        		{
		        			teamDescrip.setTypeface(teamDescrip.getTypeface(), Typeface.ITALIC);
		        			teamDescrip.setText("No Description Available");
		        		}
		            	list.write();
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
	
	protected void addNewPokemon() {
		
		 final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 // Get the layout inflater
		 LayoutInflater inflater = this.getLayoutInflater();
		 final View layout = inflater.inflate(R.layout.custom_add_pokemon_list_view, null);
		 
		 final ListView ll = (ListView) layout.findViewById(R.id.pkmList);
     	
		 Collections.sort(PokemonListActivity.pokemon, new CompareByNumber());
		 ll.setAdapter(new PokemonListBaseAdapter(this, PokemonListActivity.pokemon));
     	 	 
		 ll.setOnItemClickListener(new OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> a, View v, int position, long id) { 
		         Object o = ll.getItemAtPosition(position);
		         Pokemon fullObject = (Pokemon)o;
		         TeamPokemon tp = new TeamPokemon(fullObject, null, null, null, null, null, null);
		         selectMoves(tp);
		    }  
	    });
     	 // Inflate and set the layout for the dialog
		 // Pass null as the parent view because its going in the dialog layout
		 builder.setView(layout)
		 		.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
		 			
		 		});
		 builder.setTitle("Choose a Pokemon");
		 builder.create();
		 builder.show();
	}
	
	protected void giveNickname(final TeamPokemon pkm) {
		//final String[] nickname = new String[1];
		pkm.nickname = pkm.chosenPokemon.name;
		final Context context = this;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("Give a Nickname to the Pokemon?")
	           .setNegativeButton("No", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	            	   add(pkm);
	               }
	           	})
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	            	   AlertDialog.Builder build = new AlertDialog.Builder(context);
	            	   LayoutInflater inflater = ((Activity) context).getLayoutInflater();
	          		   final View layout = inflater.inflate(R.layout.custom_nickname_view, null);
	            	   
	          		   build.setView(layout)
	            	   		.setTitle("Give Nickname")
	            	   		.setPositiveButton("Set Nickname", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									EditText nick = (EditText) layout.findViewById(R.id.nickname);
									if(!nick.getText().toString().equals(""))
									{
										//nickname[0] = nick.getText().toString();
										pkm.nickname = nick.getText().toString();
									}
									add(pkm);
								}
							});
	          		   build.create();
	          		   build.show();
	               }
	           	});
	    		
	    builder.create();
	    builder.show();
	    //pkm.nickname = nickname[0];
	}

	protected void selectAbility(final TeamPokemon pkm) {
		
		//final ArrayList<String> ability = new ArrayList<String>();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle("Choose an Ability")
	           .setItems(pkm.chosenPokemon.ability, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	            	   //ability.add(abilities[which]);
	            	   pkm.chosenAbility = pkm.chosenPokemon.ability[which];
	            	   giveNickname(pkm);
	           }
	    });
	    builder.create();
	    builder.show();
	    
	   // pkm.chosenAbility = ability.get(0);
	}

	private void add(TeamPokemon tp)
	{
        team.addPokemon(tp);
        list.write();
        openTeamPokemonActivity(tp, list);
	}
	
	private void selectMoves(final TeamPokemon pkm)
	{
		final ArrayList<String> selectedItems = new ArrayList<String>();
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// Get the layout inflater
		LayoutInflater inflater = this.getLayoutInflater();
		final View layout = inflater.inflate(R.layout.custom_add_pokemon_list_view, null);
		 
		final ListView ll = (ListView) layout.findViewById(R.id.pkmList);
    	
		//ll.setAdapter(new MovesListBaseAdapter(this, pkm.moves));
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, pkm.chosenPokemon.moves);
		
		ll.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		ll.setAdapter(adapter); 	 
		
		final Context context = this;
		 // Inflate and set the layout for the dialog
		 // Pass null as the parent view because its going in the dialog layout
		 builder.setView(layout)
		 		.setPositiveButton("Add Moves", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						SparseBooleanArray checked = ll.getCheckedItemPositions();
						for (int i = 0; i < checked.size(); i++) {
							int position = checked.keyAt(i);
							if (checked.valueAt(i))
							{
								selectedItems.add(adapter.getItem(position));
							}
						}
						loadMoves();
					}

					private void loadMoves() {
						 if(selectedItems.size() > 4)
						 {
							 for(int i = 0; i < 4; i++)
							 {
								 pkm.chosenMoves[i] = selectedItems.get(i);
							 }
							 Toast.makeText(context, "More than 4 moves inputed, first 4 chosen", Toast.LENGTH_LONG).show();
							 selectAbility(pkm);
						 }
						 else if(selectedItems.size() != 0)
						 {
							 for(int i = 0; i < selectedItems.size();i++)
							 {
								 pkm.chosenMoves[i] = selectedItems.get(i);
							 }
							 selectAbility(pkm);
						 }
						 else
						 {
							 pkm.chosenMoves[0] = pkm.chosenPokemon.moves[0];
							 Toast.makeText(context, "Pokemon must have at least one move", Toast.LENGTH_LONG).show();
						 }
						
					}
		 			
		 		});
		 builder.setTitle("Choose Up to Four Moves");
		 builder.create();
		 builder.show();
		
	}
	private void openTeamPokemonActivity(TeamPokemon tp, TeamList t)
	{
		list.write();
		TeamPokemonActivity.pokemon = tp;
		TeamPokemonActivity.list = t;
		TeamPokemonActivity.team = team;
		Intent intent = new Intent(this, TeamPokemonActivity.class);
		startActivity(intent);
	}
	
}
