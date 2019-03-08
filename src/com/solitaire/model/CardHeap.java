package com.solitaire.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.solitaire.util.Draggable;

/**
 *	This class holds the 13 heaps with initial 4 cards that in the tableau
 *
 */
public class CardHeap {
	
	List<Card> cards;
	double heapId;
	
	public CardHeap() {
		//create a random id for this heap
		heapId = Math.random();
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
		reorderCards();
		card.setHeapId(heapId);		
		setDragableAndDropable();
		setTop();
	}
	
	/**
	 * This method is responsible to set a flag into the card that is the first on the pile
	 */
	private void setTop() {
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setTop(false);
			if(i == cards.size()-1) {
				cards.get(i).setTop(true);
			}
		}
		
	}
	
	/**
	 * Remove this card from this Heap
	 * @param card
	 */
	public void removeCard(Card card) {
		int indexOf = cards.indexOf(card);
		cards.remove(indexOf);
		setDragableAndDropable();
		setTop();
	}
	
	/**
	 * the first of this Heap, will receive the Drag and drop ability
	 * @param card
	 */
	private void setDragableAndDropable() {
		for (int i = 0; i < cards.size(); i++) {
			new Draggable(cards.get(i));
			//sets a flat to define which one will be draggable 
			cards.get(i).setDragEnable(false);
			if(i == cards.size()-1) {
				cards.get(i).setDragEnable(true);
			}
		}
	}

	/**
	 * This method gets the kings of this heap and set they to back positions on the pile
	 */
	private void reorderCards() {
		if(cards.size() != 4){
			return;
		}
		for (int i = 0; i < cards.size(); i++) {
			if(cards.get(i).getCardNumber() == 13) {
				if(cards.get(0).cardNumber == 13){
					//move the element at position (i) to the fist or second position on the list
					Collections.swap(cards, 1, i);
				}else{
					Collections.swap(cards, 0, i);
				}
			}
		}
	}

	public double getHeapId() {
		return heapId;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
 
}
