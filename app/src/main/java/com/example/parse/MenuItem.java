package com.example.parse;

import com.parse.ParseFile;
import java.util.ArrayList;

/**
 * Created by tyleraskew on 11/9/15.
 *
 * Represents an item on a wine bar's menu.
 */
public class MenuItem {

    // menu item fields
    private String itemId, name, description;
    private Double price;
    private ParseFile itemImage;
    private ArrayList<UserReview> userReviews;

    /**
     * Creates a new menu item with the specified fields.
     * @param itemId String containing the menu item's id.
     * @param name String holding the name of the item.
     * @param description String holding a brief description of the item.
     * @param price Double specifying the price of the item.
     * @param itemImage ParseFile holding an image of the item.
     */
    public MenuItem(String itemId, String name, String description, Double price, ParseFile itemImage) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.itemImage = itemImage;
    }

    /**
     * Returns the menu item's id.
     * @return String containing the item's id.
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Allows for setting the id of the item.
     * @param itemId String containing new id for the item.
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Returns the menu item's name.
     * @return String specifying the name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item to the one specified.
     * @param name String holding the new name for the item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description for this item.
     * @return String containing the description of the item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of this item to the new description provided.
     * @param description String holding the new description of the item.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the price of the item.
     * @return Double containing the item's price.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the price of the item to the new price given.
     * @param price Double specifying the new price of the item.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Returns the item's image file.
     * @return ParseFile holding the image of the item.
     */
    public ParseFile getItemImage() {
        return itemImage;
    }

    /**
     * Allows the item's image to be changed to the new image provided.
     * @param itemImage ParseFile containing the new image for the item.
     */
    public void setItemImage(ParseFile itemImage) {
        this.itemImage = itemImage;
    }

    /**
     * Returns a list of the user reviews for this item.
     * @return ArrayList<UserReview> holding all user reviews for this item.
     */
    public ArrayList<UserReview> getUserReviews() {
        return userReviews;
    }

    /**
     * Sets the user reviews list for this item to the one given.
     * @param userReviews ArrayList<UserReview> containing the new list of reviews.
     */
    public void setUserReviews(ArrayList<UserReview> userReviews) {
        this.userReviews = userReviews;
    }

}
