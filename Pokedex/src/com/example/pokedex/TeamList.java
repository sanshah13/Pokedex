package com.example.pokedex;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Environment;
import android.util.Log;

public class TeamList implements Serializable{

	ArrayList<Team> teams;
	private static final long serialVersionUID = 1L;

	public TeamList(){
		teams = new ArrayList<Team>();
		//An example of loading this would be TeamList a = (TeamList)Utils.read();
	}

	//Write teams to a file
	public void write(){
		
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File (sdCard.getAbsolutePath() + "/pokedex27");
		dir.mkdirs();
		File file = new File(dir, "teams");

		try{
			FileOutputStream f = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(f);
			oos.writeObject(this); // save this to a file
			oos.flush(); // flush the stream to insure all of the information was written to 'team.bin'
			oos.close(); //close the stream
		}
		catch(Exception ex){
			Log.v("Team List", ex.getMessage());
				ex.printStackTrace();
		}
	}
	
}
