package com.solitaire.model;

import com.solitaire.util.CardSuit;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends ImageView {
	
	int cardNumber;
	CardSuit suit;
	double heapId;
	
	boolean DragEnable = false;
	boolean isTop = false;

    public Card(int id) {
    	setCardNumberAndSuit(id);
    	//gets the file in the application folder
        setImage(new Image( getClass().getResource("../assets/cards/"+id+".png").toExternalForm()));

        //sets the size of the image card
        setFitHeight(80);
        setFitWidth(70);
    }

	/**
	 * Gets the id of the file, and sets the Card Number and The Card Suit
	 * 
	 * @param id
	 */
	private void setCardNumberAndSuit(int id) {
		if(id >= 1 && id <= 13) {
			this.suit = CardSuit.Spades;
			this.cardNumber = id;
		}else if(id >= 14 && id <= 26) {
			this.suit = CardSuit.Hearts;
			this.cardNumber = id - 13;
		}else if(id >= 27 && id <= 39) {
			this.suit = CardSuit.Diamonds;
			this.cardNumber = id - 26;
		}else {
			this.suit = CardSuit.Clubs;
			this.cardNumber = id - 39;
		}
	}

	public boolean isTop() {
		return isTop;
	}

	public void setTop(boolean isTop) {
		this.isTop = isTop;
	}

	public boolean isDragEnable() {
		return DragEnable;
	}

	public void setDragEnable(boolean dragEnable) {
		DragEnable = dragEnable;
	}

	public int getCardNumber() {
		return cardNumber;
	}
	
	public double getHeapId() {
		return heapId;
	}

	public void setHeapId(double heapId) {
		this.heapId = heapId;
	}

	public CardSuit getSuit() {
		return suit;
	}

}
