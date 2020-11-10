package com.adaptionsoft.games.uglytrivia;

import java.util.*;



public class Game {

	private static final int NUM_CELLS = 12;
	private static final int MAX_CELLS = 11;
	private static final Category[] CATEGORIES = new Category[]{Category.POP, Category.SCIENCE, Category.SPORTS, Category.ROCK};

	private final Map<Integer, Category> categoriesByPosition = new HashMap<>(NUM_CELLS);
	private final Map<Category, LinkedList<Object>> questionsByCategory = new HashMap<Category, LinkedList<Object>>();

    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
		for (Category category : CATEGORIES) {
			questionsByCategory.put(category, new LinkedList<>());
		}

		for (int i = 0; i < 50; i++) {
			for (Category category : CATEGORIES) {
				questionsByCategory.get(category).addLast(category.toString() + " Question " + i);
			}
    	}

		for (int i = 0; i < NUM_CELLS; i++) {
			categoriesByPosition.put(i, CATEGORIES[i % CATEGORIES.length]);
		}
    }


	public boolean add(String playerName) {
	    players.add(playerName);
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				movePlayer(roll);
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}
		} else {
			movePlayer(roll);
			askQuestion();
		}
	}

	private void movePlayer(int roll) {
		places[currentPlayer] = places[currentPlayer] + roll;
		if (places[currentPlayer] > MAX_CELLS) places[currentPlayer] = places[currentPlayer] - NUM_CELLS;

		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ places[currentPlayer]);
		System.out.println("The category is " + currentCategory());
	}

	private void askQuestion() {
		System.out.println(questionsByCategory.get(currentCategory()).removeFirst());
	}

	private Category currentCategory() {
		return categoriesByPosition.get(currentPlayer);
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				userWasRight();
				boolean notWinner = didNotPlayerWin();
				nextPlayer();
				return notWinner;
			} else {
				nextPlayer();
				return true;
			}
		} else {
			userWasRight();
			boolean notWinner = didNotPlayerWin();
			nextPlayer();
			return notWinner;
		}
	}

	private void nextPlayer() {
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
	}

	private void userWasRight() {
		System.out.println("Answer was correct!!!!");
		purses[currentPlayer]++;
		System.out.println(players.get(currentPlayer)
				+ " now has "
				+ purses[currentPlayer]
				+ " Gold Coins.");
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		nextPlayer();
		return true;
	}


	private boolean didNotPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}
}
