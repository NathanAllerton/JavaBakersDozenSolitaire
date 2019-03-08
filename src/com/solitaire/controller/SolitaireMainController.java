package com.solitaire.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.solitaire.ApplicationLoader;
import com.solitaire.model.Card;
import com.solitaire.model.CardHeap;
import com.solitaire.model.CardStack;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SolitaireMainController {
    
    @FXML
    private Pane stockPane;
    
    public static List<CardHeap> heaps;
    public static List<CardStack> stacks;
	private List<Card> deckList;

	private boolean GreenBackground = false;

    @FXML
    void initialize() {    	
      heaps = new ArrayList<>();
      getNewDeck();
      
      //create all the 13 heaps and put 4 card from the deck on each one
      int i = 0;
      CardHeap hp = new CardHeap();
      for (Card card : deckList) {
    	 hp.addCard(card);
     	 i++;
    	 if(i == 4) {
    		 heaps.add(hp);
    		 hp = new CardHeap();
    		 i = 0;
    	 }
      }
      
      //set the position of all the cards on the tableau
      int x = 75;
      int y = 20;
      
      for (int j = 0; j < heaps.size(); j++) {
    	  CardHeap heap = heaps.get(j);
    	  for (int j2 = 0; j2 < heap.getCards().size(); j2++) {
    		  Card card = heap.getCards().get(j2);
    		  card.setLayoutX(j * x);
    		  card.setLayoutY(130+ (j2 * y));
    		  stockPane.getChildren().add( card);
    	  }
      }
      addStacks();
    }
    
    /**
   	 * clean the stage and start a new and fresh game.
   	 */
    public void restart(ActionEvent e){
    	heaps.clear();
    	stacks.clear();
    	deckList.clear();
    	stockPane.getChildren().clear();
    	initialize();
    }
    
    /**
	 * exit the application
	 */
    public void exit(ActionEvent e){
    	Platform.exit();
    }
    
    /**
	 * Method responsible to change the stage color to/from classical green
	 */
    public void changeToGreen(ActionEvent e){
    	if(GreenBackground){
    		stockPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    		GreenBackground = false;
    	}else{
    		stockPane.setBackground(new Background(new BackgroundFill(Color.LIMEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    		GreenBackground = true;
    	}
    }
    
    /**
	 * Method responsible to create and show the instructions message
	 */
    public void openInstruction(ActionEvent e){
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Information Dialog");
    	alert.setHeaderText("How to play");
    	alert.setContentText("Playing the Game \n \n" +
    			"The cards will be dealt into thirteen columns with four cards in each column. Each King will be moved to the top of its column." +
    			"You may move only one card at a time to either another column or to one of the top four foundation stacks." +
    			"Each foundation stack must be started with an Ace. Cards may be moved to the foundation stacks in increasing numerical order and must be of the same suit. Each foundation stack ends with a King." +
    			" Cards of any suit may be moved to other columns, but they must be placed in decreasing numerical order. \n \n" +
    			"Play Another Game \n \n" +
    			"Click the New Game on File menu or Restart button if you want to start a new game at any time." +
    			" Click the Exit button if you want to quit the game.");

    	alert.showAndWait();
    }


	public void openCreators(ActionEvent e){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("About this program");
		alert.setContentText("This program was created for our Intro to Software Engineering Class 2018 \n \n" +
				"We are Group 5. Our team members are: Logan Lewis, Johnny Santana, Nawal Ahmed, Pouya Ranjbar, and Marlon Jones. \n \n" +
				"Thank you for playing, and for a great semester.");

		alert.showAndWait();
	}
    /**
	 * create a all the 4 stacks in center screen
	 */
    private void addStacks() {
    	stacks = new ArrayList<CardStack>();
    	int wt = ApplicationLoader.WIDTH / 2;
    	CardStack st1 = new CardStack((wt - 160));
    	CardStack st2 = new CardStack(wt - 80);
    	CardStack st3 = new CardStack((wt));
    	CardStack st4 = new CardStack((wt + 80));
    	stacks.add(st1);
    	stacks.add(st2);
    	stacks.add(st3);
    	stacks.add(st4);
    	stockPane.getChildren().add(st1);
    	stockPane.getChildren().add(st2);
    	stockPane.getChildren().add(st3);
    	stockPane.getChildren().add(st4);
	}

	/**
	 * create a fresh new 52 cards deck, and shuffle it
	 */
	private void getNewDeck() {
		deckList = new ArrayList<Card>();
		for (int i = 1; i < 53; i++) {
			deckList.add(new Card(i));
		}
		Collections.shuffle(deckList);
	}
}
