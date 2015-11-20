package com.example.parse;

/**
 * Created by tyleraskew on 11/9/15.
 *
 * Represents a user review for a menu item.
 */
public class UserReview {

    // user review fields
    private String reviewId;
    private String review;
    private Integer numberOfStars;

    /**
     * Constructor to set up a user review object with specific fields.
     * @param reviewId String containing the id of the review.
     * @param review String holding the text for the review.
     * @param numberOfStars Integer that specifies the star rating.
     */
    public UserReview(String reviewId, String review, Integer numberOfStars) {
        this.reviewId = reviewId;
        this.review = review;
        this.numberOfStars = numberOfStars;
    }

    /**
     * Returns the id for this review.
     * @return String containing the review's id code.
     */
    public String getReviewId() {
        return reviewId;
    }

    /**
     * Allows for setting the id for the review.
     * @param reviewId String containing the new id.
     */
    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    /**
     * Returns the review message text.
     * @return String containing the body text of the review.
     */
    public String getReview() {
        return review;
    }

    /**
     * Sets the review text to the parameter be passed in.
     * @param review String containing the new review text.
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * Returns the number of stars this review has.
     * @return Integer containing the number of stars in the rating.
     */
    public Integer getNumberOfStars() {
        return numberOfStars;
    }

    /**
     * Sets the number of stars to the parameter being passed in.
     * @param numberOfStars Integer containing the new number of stars.
     */
    public void setNumberOfStars(Integer numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

}
