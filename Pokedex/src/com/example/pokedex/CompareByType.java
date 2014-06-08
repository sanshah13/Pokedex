package com.example.pokedex;
import java.util.Comparator;
import java.util.HashMap;

public class CompareByType implements Comparator<Pokemon> {
	HashMap<String, Integer> map = new HashMap<String, Integer>();
	
	public CompareByType()
	{
		map.put("NORMAL", 0);
		map.put("FIRE", 1);
		map.put("WATER", 2);
		map.put("ELECTRIC", 3);
		map.put("GRASS", 4);
		map.put("ICE", 5);
		map.put("FIGHTING", 6);
		map.put("POISON", 7);
		map.put("GROUND", 8);
		map.put("FLYING", 9);
		map.put("PSYCHIC", 10);
		map.put("BUG", 11);
		map.put("ROCK", 12);
		map.put("GHOST", 13);
		map.put("DRAGON", 14);
		map.put("DARK", 15);
		map.put("STEEL", 16);
		map.put("FAIRY", 17);
	}
	
	@Override
	public int compare(Pokemon lhs, Pokemon rhs) {
		int left, right;
		if(lhs.getType().length == 2)
		{
			left = map.get(lhs.getType()[1]);
		}
		else
		{
			left = map.get(lhs.getType()[0]);
		}
		
		if(rhs.getType().length == 2)
		{
			right = map.get(rhs.getType()[1]);
		}
		else
		{
			right = map.get(rhs.getType()[0]);
		}
		
		if(left < right)
		{
			return -1;
		}
		else if(left > right)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

}
