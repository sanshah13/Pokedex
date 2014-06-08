package com.example.pokedex;
import java.util.Comparator;

public class CompareByName implements Comparator<Pokemon> {

	@Override
	public int compare(Pokemon lhs, Pokemon rhs) {
		return lhs.getName().compareTo(rhs.getName());
	}

}
