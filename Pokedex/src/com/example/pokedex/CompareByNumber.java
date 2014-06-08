package com.example.pokedex;
import java.util.Comparator;

public class CompareByNumber implements Comparator<Pokemon> {

	@Override
	public int compare(Pokemon lhs, Pokemon rhs) {
		if(lhs.getDex() < rhs.getDex())
			return -1;
		else if(lhs.getDex() > rhs.getDex())
			return 1;
		else
			return 0;
	}

}
