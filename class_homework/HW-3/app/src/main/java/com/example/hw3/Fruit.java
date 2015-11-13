package com.example.hw3;

/**
 * Created by heath on 15-10-30.
 */
public class Fruit {
    private String fruitName;
    private int fruitImageId;

    public String getFruitName() {
        return fruitName;
    }

    public int getFruitImageId() {
        return fruitImageId;
    }

    Fruit(String fruitName_, int fruitImageId_) {
        fruitImageId = fruitImageId_;
        fruitName = fruitName_;
    }
}
