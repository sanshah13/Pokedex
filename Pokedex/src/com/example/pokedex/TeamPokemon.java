package com.example.pokedex;

import java.io.Serializable;

public class TeamPokemon implements Serializable {
	
	Pokemon chosenPokemon;
	String nickname;
	String chosenAbility;
	String[] chosenMoves = new String[4];

	
	public TeamPokemon(Pokemon pkmn, String nick, String abil,
			String move1, String move2, String move3, String move4){
		chosenPokemon = pkmn;
		nickname = nick;
		chosenAbility = abil;
		chosenMoves[0] = move1;
		chosenMoves[1] = move2;
		chosenMoves[2] = move3;
		chosenMoves[3] = move4;
	}
	
	public void setNickname(String n)
	{
		nickname = n;
	}
	
	
	
}
