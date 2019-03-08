package com.solitaire.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;

/**
 *	This class holds the 4 foundation stacks
 *
 */
public class CardStack extends Pane {
	
	List<Card> cards;
	double heapId;
	
	/**
	 * Create a stack with a defined x position
	 * @param double x
	 */
	public CardStack(double x) {
		//create a random id for this heap
		heapId = Math.random();
		setLayoutX(x);
		setLayoutY(30);
		setMinHeight(85);
		setMinWidth(75);
		
		// set a specific style for this component (mapped on the aplication.css file)
		getStyleClass().add("scaled-image-view");
	}
	
	/**
	 * Adds this card to this heap
	 * @param card
	 */
	public void addCard(Card card) {
		if(cards == null) {
			cards = new ArrayList<>();
		}
		cards.add(card);
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public double getHeapId() {
		return heapId;
	}

}
