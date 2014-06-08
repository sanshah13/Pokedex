package com.example.pokedex;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Robert Williams
 * 
 * This is a Pokemon Team
 * A team consist of:
 * 	name
 * 	description
 *  Pokemon -> 6 or less
 *  typeAD -> 18 types initialized to 1, for resistance (Advantage) it decreases approaching 0 (MIN)
 *  		or Super Effective (Disadvantage) it increases approaching 24 (MAX)
 *  
 * Actions:
 * 	Add Pokemon
 * 	Edit Pokemon -> replace current Pokemon
 * 	Remove Pokemon
 * 	Rename Team 
 * 	Rearrange Pokemon (ordering) ->Maybe
 * */

public class Team implements Serializable, Comparable {
	
	/*Member Fields*/
	public String name;
	public String description;
	TeamPokemon[] pokemon;
	String[] typeAdvantages;
	int[] typeAD; //advantages&disadvantages
	HashMap<Integer, String> map;
	
	public Team(String name, String description){
		this.name = name;
		this.description = description;
		typeAdvantages = new String[2];
		typeAdvantages[0] = "";
		typeAdvantages[1] = "";
		typeAD  = new int[18];
		int i=0; do{typeAD[i] = 0;i++;}while(i<18);
		pokemon = new TeamPokemon[6];
		
		map = new HashMap<Integer, String>();
		map.put(0,"NORMAL");
		map.put(1,"FIRE");
		map.put(2,"WATER");
		map.put(3,"ELECTRIC");
		map.put(4,"GRASS");
		map.put(5,"ICE");
		map.put(6,"FIGHTING");
		map.put(7,"POISON");
		map.put(8,"GROUND");
		map.put(9,"FLYING");
		map.put(10,"PSYCHIC");
		map.put(11,"BUG");
		map.put(12,"ROCK");
		map.put(13,"GHOST");
		map.put(14,"DRAGON");
		map.put(15,"DARK");
		map.put(16, "STEEL");
		map.put(17,"FAIRY");
		
		
		advantages();
	}
	
	/**Calculate the type advantages and disadvantages for the team
	 * RESULT: a new typeAD array, with values reflecting the team build
	 * For every pokemon weak to a type, the array slot decrements
	 * for evey pokemon resistant to a type, the array slot increments*/
	public void typeCalculation(){
		typeAD  = new int[18];
		int i=0; do{typeAD[i] = 0;i++;}while(i<18);
		
		for(i=0;i<pokemon.length;i++){
			if(pokemon[i] != null)
			{
				for(int j = 0; j< typeAD.length;j++){			
					if(pokemon[i].chosenPokemon.resist[j] >1)
						typeAD[j]--;
					else if (pokemon[i].chosenPokemon.resist[j] < 1)
						typeAD[j]++;
				}
			}
		}
		advantages();
		
	}
	
	/**Add a Pokemon to the team
	 * RESULT: Place a Pokemon in the first available position of the team*/
	public void addPokemon(TeamPokemon pkmn){
		int position = teamSpot();
		
		if( position < 0)//No Open spot
			return;
		pokemon[position] = pkmn;
		typeCalculation();
		
	}
	
	/*Find an open position in the team, -1 if no position is available*/
	private int teamSpot(){
		for(int i = 0; i<6;i++)
			{if(pokemon[i]==null)return i;}
		return -1;
	}
	
	/**Remove the pokemon from the selected position
	 * Result: Team of Pokemon shifted so that the new empty position is at the end*/
	public void removePokemon(int position){
		pokemon[position] = null; //Remove the pokemon
		while(position < 6 && pokemon[position+1] != null)
			pokemon[position] = pokemon[position++]; //Shift remaining pokemon left && increment position
		
		typeCalculation();
	}
	
	/**Rename the team, noting that this doesn't check aginst other team names
	 * Result: The team with a different name*/
	public void renameTeam(String name){
		this.name = name;
	}
	
	/**Change the team's description
	 * Result: Adding a description to the team
	 * */
	public void redescribeTeam(String description){
		this.description = description; 
	}
	
	@Override
	/** check if this team is alphetabetically greater or lesser to another team
	 * Please only pass in teams, as I don't know what I would return for a value if this want' a team.
	 * */
	public int compareTo(Object another) {
		// TODO Auto-generated method stub
			if(another == null || !(another instanceof Team))
				return -42;
			
			return name.compareTo((String)another);
	}
	
	/** Check if this team shares the name of another team.
	 * Input: A Team object
	 * Return true if they share the same name, false otherwise 
	 * */
	public boolean equals(Object o)
	{
		if(o == null || !(o instanceof Team))
			return false;
		
		Team t = (Team) o;
		return t.name.equals(this.name);
	}
	
	public void advantages(){
		typeAdvantages[0] = "";
		typeAdvantages[1]="";
		for(int i=0;i<typeAD.length;i++){
			if(typeAD[i]>0){
				if(!typeAdvantages[0].contains(map.get(i)))
					this.typeAdvantages[0] = typeAdvantages[0] + " " + map.get(i);
			}
			else if(typeAD[i]<0){
				if(!typeAdvantages[1].contains(map.get(i)))
					this.typeAdvantages[1] = typeAdvantages[1] + " " + map.get(i);
			}
		}
		
	}
	
}
