package com.example.pokedex;

import java.io.InputStream;
import java.io.Serializable;

/** This class creates a Pokemon for the user's team
 * Pokemon are "built" when their team is referenced
 * A Pokemon has:
 * 	name
 * 	dex - Pokedex Number 1-719
 * 	type -> 1 or 2
 * 	nature 
 * 	ability 
 * 	move set -> 0-4 moves
 * 
 * --
 * Pokemon may also need an extra marker to differentiate them
 * Current idea is an unique id number
 * Can be developed further later on.
 * 
 * */
public class Pokemon implements Serializable {
	
	/*Member fields*/
	String name;
	int dex;
	String[] type; //1 or 2
	String[] ability;
	String[] moves; //No more than 4, "could" be empty
	String[] stats;
	String[]statName = {"HP", "Atk", "Def", "Sp.Akt", "Sp.Def", "Spd"};
	String[] evo; //evloutions
	String description;//The flavor text stuff :)
	double[] resist; //The pokemon's resistence
	String[] typeNames = {"NORMAL", "FIRE", "WATER", "ELECTRIC",
						"GRASS","ICE", "FIGHTING", "POISON",
						"GROUND", "FLYING","PSYCHIC", "BUG",
						"ROCK", "GHOST","DRAGON","DARK",
						"STEEL", "FAIRY"};
	
	// public int id; //This id differentiates other built Pokemon from this Pokemon

	public Pokemon(String name, String dexNumber, String[] type, String[] stats,String[] evo, String[] ability, String[] moves, String description){
		this.name = name;
		this.dex = Integer.parseInt(dexNumber);
		this.type = type;
		this.stats = stats;
		this.evo = evo;
		this.ability = ability;
		this.moves = moves;
		this.description = description;
		
		//Set the individual Pokemon's resistances, 
		resist = Utils.typeChart(type[0]);
		
		//if the pokemon has 2 types, calculate the resistances for the second type, then multiply them together
		if(type.length > 1){
			double[] tmp = Utils.typeChart(type[1]);
			for(int i=0;i<18;i++)
				resist[i] = resist[i]*tmp[i];
		}
			
		
	}
	
	public Pokemon(InputStream is){
		String[][] data = Utils.parseDexFile(is);
		//id, types, stats, evo, abil, moves
		if(data!=null){
		dex = Integer.parseInt(data[0][0]);
		name = data[0][1];
		description = data[0][2];
		type = data[1];
		stats = data[2];
		evo = data[3];
		ability = data[4];
		moves = data[5];
		

		//Set the individual Pokemon's resistances, 
		resist = Utils.typeChart(type[0]);
		
		//if the pokemon has 2 types, calculate the resistances for the second type, then multiply them together
		if(type.length > 1){
			double[] tmp = Utils.typeChart(type[1]);
			for(int i=0;i<18;i++)
				resist[i] = resist[i]*tmp[i];
		}
		}
		
	}
	
	public Pokemon(String name, String num, String[] type)
	{
		this.name = name;
		this.dex = Integer.parseInt(num);
		this.type = type;
	}
	
	public String getName() {return name;}
	public int getDex() {return dex;}
	public String getFlavorText() {return description;}
	public String[] getType() {return type;}
	public String[] getMoves() {return moves;}
	public String[] getEvo(){return evo;}
	public String[] getStats(){return stats;}
	
	//I didn't add in more setters, don't think we'll need them
	public void setName(String name) {this.name = name;}
	public void setDex(int dex) {this.dex = dex;}
	public void setType(String[] type) {this.type = type;}
	public void setMoves(String[] moves) {this.moves = moves;}
	
	/**Return a Pokemon, with all information fields separated by ":" */
	public String toString(){
		String ret = name + "\n"+ dex+"\n";
		
		for(int i=0; i<type.length;i++)
			ret = ret + type[i] + ":";
		ret = ret.substring(0,ret.length()-1) + "\n";
		
		for(int i=0;i<evo.length;i++)
			ret = ret + evo[i] + "->";
		ret = ret.substring(0,ret.length()-2) + "\n";
		
		for(int i=0;i<ability.length;i++)
			ret = ret + ability[i] + ":";
		ret = ret.substring(0,ret.length()-1) + "\n";
		
		for(int i=0;i<stats.length;i++)
			ret = ret + stats[i] + ":";
		ret = ret.substring(0,ret.length()-1) + "\n";
		
		for(int i=0; i<moves.length;i++)
			ret = ret + moves[i] + ":";
			ret = ret.substring(0,ret.length()-1) + "\n";
		
		
		return ret;
	}
	
	public boolean equals(Object o)
	{
		if(o == null || !(o instanceof Pokemon))
		{
			return false;
		}
		
		Pokemon p = (Pokemon) o;
		
		return p.name.equals(this.name);
	}
	
	
}
