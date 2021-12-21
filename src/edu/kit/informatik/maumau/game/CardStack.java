package edu.kit.informatik.maumau.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class CardStack {
    private final Stack<Card> stack;

    CardStack(Card[] cards, long seed) {
        List<Card> cardList = Arrays.asList(cards);
        Collections.shuffle(cardList, new Random(seed));
        // Stack gibt die Elemente in umgekehrter Reihenfolge zurück.
        // Das wäre zwar logisch komplett irrelevant aber könnte Probleme mit
        // Artemis auslösen, also einmal invertieren.
        Collections.reverse(cardList);
        stack = new Stack<>();
        stack.addAll(cardList);
    }

    CardStack() {
        stack = new Stack<>();
    }

    public void push(Card card) {
        stack.push(card);
    }

    public Card peek() {
        return stack.peek();
    }

    public Card pop() {
        return stack.pop();
    }

    public int size() {
        return stack.size();
    }
}
