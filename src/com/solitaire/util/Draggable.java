package com.solitaire.util;

import java.util.List;

import com.solitaire.controller.SolitaireMainController;
import com.solitaire.model.Card;
import com.solitaire.model.CardHeap;
import com.solitaire.model.CardStack;

import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class Draggable {
	
	private double mouseX;
	private double mouseY;
	private double oldX;
	private double oldY;
	private Card card;
	private Pane parent;
	
	public Draggable(Card card) {
		this.card = card;
		setDraggable();
	}
	
	/**
	 * Starts the handlers that will be responsible for the draggable behavior 
	 */
	private void setDraggable() {
		//when mouse is pressed
		card.setOnMousePressed(b -> {
			//gets the mouse position
			mouseX = b.getSceneX();
			mouseY = b.getSceneY();
			
			//gets the original card position
			oldX = card.getLayoutX();
			oldY = card.getLayoutY();
			parent = (Pane) card.getParent();

		});
		//when mouse is still pressed and now moving
		card.setOnMouseDragged(b -> {
			//just if the card draggable is set to true
			if(!card.isDragEnable()) {
				return;
			}
			card.setCursor(Cursor.CLOSED_HAND);
			card.toFront();
			card.relocate(b.getSceneX() - mouseX + oldX, b.getSceneY() - mouseY +  oldY);
		});
		//when the mouse button is hold
		card.setOnMouseReleased(e -> {
			//first verify if the card was dropped into one of the 4 foundation stacks
			if(droppedOnStack(card)){
				return;
			}
			//now verify if the card was dropped into a heap pile
			Card dropped = canDrop(card);
			if(dropped != null) {
				card.relocate(dropped.getLayoutX(), dropped.getLayoutY()+20);
				relocateCardHeap(card, dropped);
			}else {
				//if none, return the card to it's original pile
				card.relocate(oldX, oldY);				
			}
		});
	}

	private boolean droppedOnStack(Card card) {
		boolean dropped = false;
		//gets the bounds of the card
		Bounds boundsInScene = card.localToScene(card.getBoundsInLocal());		
		for (Node cards : parent.getChildren()) {
			//get all the stacks on the tableut
			if(cards instanceof CardStack) {
				CardStack cs = (CardStack) cards;
				//get the bounds of each stack and verify if it intersects with the dropped card
				Bounds csBounds = cs.localToScene(cs.getBoundsInLocal());
				if(csBounds.intersects(boundsInScene)) {
					//if is an "As" just realocate the cart into this stack
					if((cs.getCards() == null || cs.getCards().isEmpty()) && card.getCardNumber() == 1 ){
						card.relocate(cs.getLayoutX(), cs.getLayoutY());
						relocateCardStack(card, cs);
						return true;
					}
					//if is NOT an "As" and there is no card on the stack return the cart to it's original location
					if((cs.getCards() == null || cs.getCards().isEmpty()) && card.getCardNumber() > 1 ){
						return false;
					}
					// verify if the dropped card matches suit ...
					Card cd = cs.getCards().get(cs.getCards().size()-1);
					if(cd.getSuit() != card.getSuit()){
						return false;
					}
					//... and follows upward sequence
					if((cd.getCardNumber()+1) == card.getCardNumber()) {
						card.relocate(cs.getLayoutX(), cs.getLayoutY());
						relocateCardStack(card, cs);
						return true;
					}
				}
			}			
		}
		//verify if there are remained cards on tableut ...
		boolean existCards = false;
		for (CardHeap heap : SolitaireMainController.heaps) {
			if(heap.getCards() != null && !heap.getCards().isEmpty()){
				existCards = true;
			}
		}
		// ... if not, pops a game over message
		if(!existCards){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Game Over!");
			alert.setContentText("You win! Click restart to play again.");

			alert.showAndWait();
		}
		return dropped;
	}

	private void relocateCardStack(Card card, CardStack cs) {
		//gets all the stacks
		List<CardStack> stacks = SolitaireMainController.stacks;
		CardHeap source = null;
		CardStack target = null;
		//gets the target stacks from it's id
		for (CardStack cardStack : stacks) {
			if(cardStack.getHeapId() == cs.getHeapId()) {
				target = cardStack;
			}			
		}
		//gets all the heaps
		List<CardHeap> heaps = SolitaireMainController.heaps;
		//gets the source heap comparing the card's heap id
		for (CardHeap cardHeap : heaps) {
			if(cardHeap.getHeapId() == card.getHeapId()) {
				source = cardHeap;
			}			
		}
		//remove from source and attach to the target
		source.removeCard(card);
		target.addCard(card);
	}

	private void relocateCardHeap(Card card, Card dropped) {
		//gets all the heaps
		List<CardHeap> heaps = SolitaireMainController.heaps;
		CardHeap source = null;
		CardHeap target = null;
		//gets the target and the source heaps
		for (CardHeap cardHeap : heaps) {
			if(cardHeap.getHeapId() == card.getHeapId()) {
				source = cardHeap;
			}
			if(cardHeap.getHeapId() == dropped.getHeapId()) {
				target = cardHeap;
			}
		}
		//remove from source and attach to the target
		source.removeCard(card);
		target.addCard(card);
	}

	private Card canDrop(Card card) {
		//verify if this card is on the foundation stack already
		if(onStackAlready(card)){
			return null;
		}
		//get the bounds of the card
		Bounds boundsInScene = card.localToScene(card.getBoundsInLocal());
		for (Node cards : parent.getChildren()) {
			//get all the cards from the tableaut
			if(cards instanceof Card) {
				Card cd = (Card) cards;
				if(card == cd) {
					continue;
				}
				//gets each card's bounds
				Bounds cdBounds = cd.localToScene(cd.getBoundsInLocal());
				if(cdBounds.intersects(boundsInScene)) {
					//if intersects and the card follows downward sequence
					if(cd.isTop() && (cd.getCardNumber()-1) == card.getCardNumber()) {
						return cd;							
					}
				}
			}
		}
		return null;
	}

	private boolean onStackAlready(Card card) {
		//gets all the stacks
		List<CardStack> stacks = SolitaireMainController.stacks;
		//verify if this card is on the foundation stack already
		for (CardStack cardStack : stacks) {
			if(card.getHeapId() == cardStack.getHeapId()){
				return true;
			}
		}
		return false;
	}
}
