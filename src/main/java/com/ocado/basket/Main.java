package com.ocado.basket;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        BasketSplitter basketSplitter = new BasketSplitter("/Users/karol/Documents/Projects/basket/config.json");
        System.out.println(basketSplitter.split(List.of("Fond - Chocolate", "Chocolate - Unsweetened", "Nut - Almond, Blanched, Whole", "Haggis")));
    }
}