package com.recipe_book.our_recipebook;



public class recipe {
    private String name;
    private String serves;
    private String difficulty;
    private String meal;
    private String ingredients;
    private String recipe;
    private String time;
    private String image;//url is String
    private String key;

    public recipe(){

    }

    public recipe(String name, String serves, String difficulty, String meal, String ingredients, String recipe, String time, String image) {
        this.name = name;
        this.serves = serves;
        this.difficulty = difficulty;
        this.meal = meal;
        this.ingredients = ingredients;
        this.recipe = recipe;
        this.time = time;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getServes() {
        return serves;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getMeal() {
        return meal;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getRecipe() {
        return recipe;
    }

    public String getTime() {
        return time;
    }



    public String getImage() {
        return image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
