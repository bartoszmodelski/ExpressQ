package com.delta.expressq.util;


import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;

public class KeywordsGenerator {
	private static ArrayList<String> adjectives;
	private static ArrayList<String> nouns;
	private static Random randomGenerator;
	
	private static void setup() {
		adjectives = new ArrayList<String>();
		nouns = new ArrayList<String>();
		randomGenerator = new Random();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("nouns.txt"));
			String line = br.readLine();

			while (line != null) {
				nouns.add(line);
				line = br.readLine();
			}
			
			br = new BufferedReader(new FileReader("adjectives.txt"));
			line = br.readLine();

			while (line != null) {
				adjectives.add(line);
				line = br.readLine();
			}
			
		} catch (Exception e) {
			System.out.println("Exception from KeywordsGenerator: " + e.getMessage());
		}
	}
	
	public static String getKeywords() {
		if ((adjectives == null) || (nouns == null) || (randomGenerator == null))
			setup();
		
		return adjectives.get(randomGenerator.nextInt(adjectives.size())) + nouns.get(randomGenerator.nextInt(nouns.size()));
	}
}