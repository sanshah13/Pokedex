package com.example.pokedex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import android.os.Environment;
import android.util.Log;


public class Utils {
	
	/** Find the effectiveness of a Pokemon Type, searches a look up table
	 * 	Input is the defensive type
	 * 	Result: a double array, with a multiplier for the value of damage to be
	 * 		dealt to the defensive pokemon*/
	public static double[] typeChart(String defType){
		double[] weak = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		
		switch(defType){
				case "NORMAL":
					weak[6] = 2; //FIGHTING
					weak[13] = 0; //GHOST
					break;
					
				case "FIRE":
					weak[1] = .5; //FIRE
					weak[2] = 2;  //WATER
					weak[4] = .5; //GRASS
					weak[5] = .5; //ICE
					weak[8] = 2;  // GROUND
					weak[11] = .5; //BUG
					weak[12] = 2;  //ROCK
					weak[16] = .5; //STEEL
					weak[17] = .5; // FAIRY
					break;
					
				case "WATER":
					weak[1]=.5; //FIRE
					weak[2]= .5; //WATER
					weak[3]= 2; //ELECTRIC
					weak[4]= 2; //GRASS
					weak[5]= .5; //ICE
					weak[16]= .5; //STEEL
					break;
					
				case "ELECTRIC":
					weak[3]= .5; //ELECTRIC
					weak[8]= 2;//GROUND
					weak[9]= .5;//FLYING
					weak[16]= .5;//STEEL
					break;
					
				case "GRASS":
					weak[1]=2;//fire
					weak[2]=.5;//water
					weak[3]=.5;//electric
					weak[4]=.5;//grass
					weak[5]=2;//ice
					weak[7]=2;//poison
					weak[8]=.5;//ground
					weak[9]=2;//flying
					weak[11]=2;//bug
					break;
					
				case "ICE":
					weak[1]=2; //fire
					weak[5]=.5;//ice
					weak[6]= 2;//fighting
					weak[12]=2; //rock
					break;
					
				case "FIGHTING":
					weak[9]=2;//flying
					weak[10]=2;//psychic
					weak[11]=.5;//bug
					weak[12]=.5;//rock
					weak[15]=.5;//dark
					weak[17]=2;//fairy
					break;
					
				case "POISON":
					weak[4]=.5;//grass
					weak[6]=.5;//fighting
					weak[7]=.5;//poison
					weak[8]=2;//ground
					weak[10]=2;//psychic
					weak[11]=.5;//bug
					weak[17]=.5;//fairy
					break;
					
				case "GROUND":
					weak[2]=2;//water
					weak[3]=0;//electric
					weak[4]=2;//ground
					weak[5]=2;//ice
					weak[7]=.5;//poison
					weak[12]=.5;//rock
					break;
					
				case "FLYING":
					weak[3]=2;//electric
					weak[4]=.5;//grass
					weak[5]=2;//ice
					weak[6]=.5;//Fighting
					weak[8]=0;//ground
					weak[11]=.5;//bug
					weak[12]=2;//rock
					break;
					
				case "PSYCHIC":
					weak[6]=.5;//fighting
					weak[10]=.5;//psychic
					weak[11]=2;//bug
					weak[13]=2;//ghost
					weak[15]=2;//dark
					break;
					
				case "BUG":
					weak[1]=2;//fire
					weak[4]=.5;//grass
					weak[6]=.5;//fighting
					weak[8]=.5;//ground
					weak[9]=2;//flying
					weak[12]=2;//rock
					break;
					
				case "ROCK":
					weak[0] = .5;//normal
					weak[1]=.5;//fire
					weak[2]=2;//water
					weak[4]=2;//grass
					weak[6]=2;//fighting
					weak[7]=.5;//poison
					weak[8]=2;//ground
					weak[9]=.5;//flying
					weak[16]=2;//steel
					break;
					
				case "GHOST":
					weak[0]=0;//normal
					weak[6]=0;//fighting
					weak[7]=.5;//poison
					weak[11]=.5;//bug
					weak[13]=2;//Ghost
					weak[15]=2;//dark
					break;
					
				case "DRAGON":
					weak[1]=.5;//fire
					weak[2]=.5;//water
					weak[3]=.5;//electric
					weak[4]=.5;//grass
					weak[5]=2;//ice
					weak[14]=2;//dragon
					weak[17]=2;//fairy
					break;
					
					case "DARK":
					weak[6]=2;//fighting
					weak[10]=0;//psychic
					weak[11]=2;//bug
					weak[13]=.5;//ghost
					weak[15]=.5;//dark
					weak[17]=2;//fairy
					break;
					
				case "STEEL":
					weak[0]=.5;//normal
					weak[1]=2;//fire
					weak[4]=.5;//grass
					weak[5]=.5;//ice
					weak[6]=2;//fighting
					weak[7]=0;//poison
					weak[8]=2;//ground
					weak[9]=.5;//flying
					weak[10]=.5;//psychic
					weak[11]=.5;//bug
					weak[12]=.5;//rock
					weak[14]=.5;//dragon
					weak[15]=.5;//steel
					weak[16]=.5;//fairy
					break;
					
				case "FAIRY":
					weak[6]=.5;//fighting
					weak[7]=2;//poison
					weak[11]=.5;//bug
					weak[16]=2;//steel
					break;
			}
		
		return weak;
		
	}


	/**Create a 2d Array comtaining all the information contained in the Pokedex Text File
	 * Input: The dexNumber of the pokemon, 3 digits, ie: 001
	 * Result: The file parsed into a  [6][?] size 2d array, For size of second Arrray, use array length
	 * In the event of badness (not in a good way) This will return null to the Caller, check for it.
	 * */
	public static String[][] parseDexFile(InputStream is){
		
		//This idea worked in normal Java... Cross our fingers for this one :)
		
		//Set the file Location
		/*
		fileName = "dexEntries"+File.pathSeparator+fileName+".txt";
		File file = new File(fileName);
	*/
		
		ArrayList<String> data = new ArrayList<String>();
		
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(inputStreamReader);
			String line;
			
			//Attempt to read the Pokedex entry
			while((line = br.readLine()) != null){
				data.add(line);
			}
			//Now, break the lines apart!
			String[] id = new String[3];
			id[0] = data.get(0); //dex Number
			id[1] = data.get(1); //Name
			id[2] = data.get(7); //General Description
		
			//The array list names should be self explanatory
			String[] types = data.get(2).split(":");
			String[] stats = data.get(3).split(":");
			String[] evo = data.get(4).split(":"); //Evolutions, some pokemon have none
			String[] abil = data.get(5).split(":");
			String[] moves = data.get(6).split(":");
		
			String[][] ret = {id,types, stats, evo, abil, moves};
		
			return ret;
			} catch (IOException e) {
			// We done goofed, you should check you get valid return stuffs
				return null;
			}
		}
	
	//Read teams from a file
	public static Object read(){
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File (sdCard.getAbsolutePath() + "/pokedex27");
		File file = new File(dir, "teams");
		try{
			ObjectInputStream ois = new ObjectInputStream(new  FileInputStream(file));
			Object o = ois.readObject();
			return o;
		}
		catch(Exception ex){
		   	Log.v("Address Book",ex.getMessage());
		   		ex.printStackTrace();
		   		return null;
		}
	}
}
