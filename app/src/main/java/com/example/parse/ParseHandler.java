package com.example.parse;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by tyleraskew on 11/9/15.
 *
 * Used to connect with the Parse database and perform crud operations.
 */
public class ParseHandler {

    // lists for items a user may want to access
    private ArrayList<MenuItem> menuItems;
    private ArrayList<UserReview> itemReviews;
    private ArrayList<UserReview> userReviews;
    private TreeSet<String> orderedItems;


    /**
     * Private constructor that enforces the use of the
     * other constructor to specify a context.
     */
    private ParseHandler() {
        // nothing needed here
    }

    /**
     * Constructor that initializes all of the lists above as
     * well as the connection to the Parse database. Takes in a context
     * parameter that is used to initialize the Parse connection.
     * @param context Context object needed to initialize Parse.
     */
    public ParseHandler(Context context) {
        menuItems = new ArrayList<MenuItem>();
        itemReviews = new ArrayList<UserReview>();
        userReviews = new ArrayList<UserReview>();
        orderedItems = new TreeSet<String>();

        // create connection to parse database
        Parse.enableLocalDatastore(context);
        Parse.initialize(context, "MEd9C5XGBe2Lab1n4CsOb03GGs5Qc4A5zlJuuacM", "EKlRqaNIp2tGJNz8G2jXehH41fdgMS1fQifCObJB");
    }

    /**
     * Attempts to log a user in with the given username and password.
     * Returns true if the user could be logged in and false otherwise.
     * @param username String that is the username for the account.
     * @param password String that is the password for the account.
     * @return boolean (true or false) depending on if the user account could be logged in.
     */
    public boolean loginUser(String username, String password) {
        boolean loggedIn = true;

        try {
            ParseUser.logIn(username, password);
        } catch (ParseException e) {
            e.printStackTrace();
            loggedIn = false;
        }

        return loggedIn;
    }

    /**
     * Registers a new user account with the provided parameters. Returns true
     * if the user was able to be registered and false otherwise.
     * @param firstName String holding the user's first name.
     * @param lastName String containing the user's last name.
     * @param username String holding the username the user has chosen.
     * @param password String that is the password for this new account.
     * @return boolean (true or false) depending on if the user could be registered.
     */
    public boolean registerUser(String firstName, String lastName, String username, String password) {
        boolean successfullyRegistered = true;

        // create new user object
        ParseUser newUser = new ParseUser();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.put("firstName", firstName);
        newUser.put("lastName", lastName);

        try {
            newUser.signUp();
        } catch (ParseException e) {
            e.printStackTrace();
            successfullyRegistered = false;
        }

        return successfullyRegistered;
    }

    /**
     * Attempts to log out the currently logged in user. If there is
     * no user currently logged in this function returns false, otherwise
     * it returns true.
     * @return boolean (true or false) depending on if a user is currently logged in to log out.
     */
    public boolean logoutUser() {
        boolean loggedOut = true;

        if (!isUserLoggedIn()) {
            loggedOut = false;
        } else {
            ParseUser.logOut();
        }

        return loggedOut;
    }

    /**
     * Determines if a user is currently logged in. If a user is
     * currently logged in it will return true, otherwise it will return false.
     * @return boolean (true or false) depending if a user is currently logged in.
     */
    public boolean isUserLoggedIn() {
        return ParseUser.getCurrentUser() != null;
    }

    /**
     * Constructs an ArrayList containing all of the menu items for the
     * wine bar that is specified by the given wineBarName. Converts the
     * retrieved ParseObject's into MenuItem model instances that provides
     * better readability.
     * @param wineBarName String holding the name of the wine bar.
     * @return ArrayList<MenuItem> holding the list of this wine bar's menu items.
     */
    public ArrayList<MenuItem> getMenuItems(String wineBarName) {
        menuItems.clear();

        // construct a query to get the items
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("WineBar");
        query.whereEqualTo("name", wineBarName);

        try {
            // obtain wine bar data and retrieve its menu items
            ParseObject wineBar = query.getFirst();
            ParseRelation<ParseObject> menuRelation = wineBar.getRelation("menu");
            List<ParseObject> parseMenuItems = menuRelation.getQuery().find();

            // go through parse objects and convert them into the MenuItem model
            for (ParseObject item : parseMenuItems) {
                menuItems.add(new MenuItem(item.getObjectId(),
                        item.getString("name"),
                        item.getString("description"),
                        item.getDouble("price"),
                        item.getParseFile("itemImage")));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return menuItems;
    }

    /**
     * Constructs an ArrayList containing all of the user reviews for the
     * menu item that is specified by the given itemId. Converts the
     * retrieved ParseObject's into UserReview model instances that provides
     * better readability.
     * @param itemId String holding the id of the menu item.
     * @return ArrayList<UserReview> holding the list of this menu item's user reviews.
     */
    public ArrayList<UserReview> getItemReviews(String itemId) {
        itemReviews.clear();

        // build query to get the item
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("MenuItems");
        query.whereEqualTo("objectId", itemId);

        try {
            ParseObject menuItem = query.getFirst();
            ParseRelation<ParseObject> reviewsRelation = menuItem.getRelation("userReviews");
            List<ParseObject> parseItemReviews = reviewsRelation.getQuery().find();

            // convert ParseObjects to UserReview models
            for (ParseObject review : parseItemReviews) {
                itemReviews.add(new UserReview(review.getObjectId(),
                        review.getString("review"),
                        review.getInt("starRating")));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return itemReviews;
    }

    /**
     * Retrieves the ordered items for the currently logged in user
     * and returns their menu item ids as a set.
     * @return TreeSet<String> containing the set of the user's ordered items ids.
     */
    public TreeSet<String> getOrderedItems() {
        orderedItems.clear();

        if (isUserLoggedIn()) {
            try {
                ParseUser.getCurrentUser().fetch();  // update current user information
                ParseRelation<ParseObject> orderedItemsRelation = ParseUser.getCurrentUser().getRelation("orderedItems");
                List<ParseObject> parseOrderedItems = orderedItemsRelation.getQuery().find();

                // extract the menu item ids from the ParseObjects
                for (ParseObject item : parseOrderedItems) {
                    orderedItems.add(item.getParseObject("menuItem").getObjectId());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return orderedItems;
    }

    /**
     * Retrieves all user reviews that the currently logged in user has created
     * and returns these reviews in a list.
     * @return ArrayList<UserReview> containing the list of the user's reviews.
     */
    public ArrayList<UserReview> getUserReviews() {
        userReviews.clear();

        if (isUserLoggedIn()) {
            try {
                ParseUser.getCurrentUser().fetch();  // update current user information
                ParseRelation<ParseObject> userReviewsRelation = ParseUser.getCurrentUser().getRelation("userReviews");
                List<ParseObject> parseUserReviews = userReviewsRelation.getQuery().find();

                // convert the ParseObjects into UserReview models
                for (ParseObject review : parseUserReviews) {
                    userReviews.add(new UserReview(review.getObjectId(),
                            review.getString("review"),
                            review.getInt("starRating")));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return userReviews;
    }

    /**
     * Retrieves the currently logged in user's reviews for the menu item
     * corresponding to the given itemId.
     * @param itemId String containing the menu item being examined.
     * @return ArrayList<UserReview> containing information the user's reviews of the item.
     */
    public ArrayList<UserReview> getUserReviews(String itemId) {
        userReviews.clear();

        if (isUserLoggedIn()) {
            try {
                ParseQuery<ParseObject> itemQuery = new ParseQuery<ParseObject>("MenuItems");
                itemQuery.whereEqualTo("objectId", itemId);
                ParseObject menuItem = itemQuery.getFirst();

                // obtain the the current user's review from the menu items reviews relation
                // by modifying the relation query
                ParseRelation<ParseObject> reviewsRelation = menuItem.getRelation("userReviews");
                ParseQuery<ParseObject> userQuery = reviewsRelation.getQuery();
                userQuery.whereEqualTo("reviewCreator", ParseUser.getCurrentUser());
                List<ParseObject> parseUserReviews = userQuery.find();

                // change parseUserReview into the UserReview model
                for (ParseObject userReview : parseUserReviews) {
                    userReviews.add(new UserReview(userReview.getObjectId(),
                            userReview.getString("review"),
                            userReview.getInt("starRating")));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return userReviews;
    }

    /**
     * Uploads a new user review to the Parse database. Does this by adding
     * the new review to the currently logged in user's review relation and by
     * adding the new review to the menu item reviews relation.
     * @param itemId String containing the id of the menu item that the review is for.
     * @param review String containing the user's review.
     * @param numberOfStars int holding the number of stars for the star rating.
     * @return boolean (true or false) depending on the success of the upload.
     */
    public boolean uploadUserReview(String itemId, String review, int numberOfStars) {
        boolean successfullyUploaded = true;

        // if no user is logged in it is impossible to upload a review
        if (ParseUser.getCurrentUser() == null) {
            successfullyUploaded = false;
        } else {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("MenuItems");
            query.whereEqualTo("objectId", itemId);

            try {
                ParseObject menuItem = query.getFirst();
                ParseRelation<ParseObject> itemRelation = menuItem.getRelation("userReviews");
                ParseRelation<ParseObject> userRelation = ParseUser.getCurrentUser().getRelation("userReviews");

                // creating new review object
                ParseObject newReview = new ParseObject("UserReview");
                newReview.put("review", review);
                newReview.put("starRating", numberOfStars);
                newReview.put("reviewCreator", ParseUser.getCurrentUser());
                newReview.put("menuItem", menuItem);
                newReview.save();

                // adding object to both relations
                itemRelation.add(newReview);
                userRelation.add(newReview);

                // save the newly updated objects
                menuItem.save();
                ParseUser.getCurrentUser().save();
            } catch (ParseException e) {
                e.printStackTrace();
                successfullyUploaded = false;
            }
        }

        return successfullyUploaded;
    }

    /**
     * Places an order for the currently logged in user by adding an item to
     * the OrderedItems database table. The new item will contain all of the
     * information the parameters provide.
     * @param wineBarName String containing the name of the wine bar.
     * @param itemId String holding the id of the menu item.
     * @param quantity Integer holding the quantity of the order.
     * @return boolean (true or false) depending on if the order was placed.
     */
    public boolean orderItem(String wineBarName, String itemId, Integer quantity) {
        boolean orderPlaced = true;

        if (ParseUser.getCurrentUser() == null) {
            orderPlaced = false;
        } else {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("WineBar");
            query.whereEqualTo("name", wineBarName);

            try {
                ParseObject wineBar = query.getFirst();
                ParseRelation<ParseObject> wineBarRelation = wineBar.getRelation("orderedItems");
                ParseRelation<ParseObject> userRelation = ParseUser.getCurrentUser().getRelation("orderedItems");

                // retrieve the menu item
                query = new ParseQuery<ParseObject>("MenuItems");
                query.whereEqualTo("objectId", itemId);
                ParseObject menuItem = query.getFirst();

                // create new ordered item object
                ParseObject orderedItem = new ParseObject("OrderedItems");
                orderedItem.put("orderFulfilled", false);
                orderedItem.put("orderQuantity", quantity);
                orderedItem.put("menuItem", menuItem);
                orderedItem.put("orderRequester", ParseUser.getCurrentUser());
                orderedItem.save();

                // adding the ordered item to the relations
                wineBarRelation.add(orderedItem);
                userRelation.add(orderedItem);

                // save these objects to update their relations
                wineBar.save();
                ParseUser.getCurrentUser().save();
            } catch (ParseException e) {
                e.printStackTrace();
                orderPlaced = false;
            }
        }

        return orderPlaced;
    }

}
