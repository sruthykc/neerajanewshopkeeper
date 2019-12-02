package com.diviso.graeshoppe.client.store.model;

import java.util.Objects;
import com.diviso.graeshoppe.client.store.model.Store;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UserRatingReview
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-12-02T11:55:32.913+05:30[Asia/Calcutta]")

public class UserRatingReview   {
  @JsonProperty("date")
  private OffsetDateTime date = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("rating")
  private Double rating = null;

  @JsonProperty("review")
  private String review = null;

  @JsonProperty("store")
  private Store store = null;

  @JsonProperty("userName")
  private String userName = null;

  public UserRatingReview date(OffsetDateTime date) {
    this.date = date;
    return this;
  }

  /**
   * Get date
   * @return date
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OffsetDateTime getDate() {
    return date;
  }

  public void setDate(OffsetDateTime date) {
    this.date = date;
  }

  public UserRatingReview id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UserRatingReview rating(Double rating) {
    this.rating = rating;
    return this;
  }

  /**
   * Get rating
   * @return rating
  **/
  @ApiModelProperty(value = "")


  public Double getRating() {
    return rating;
  }

  public void setRating(Double rating) {
    this.rating = rating;
  }

  public UserRatingReview review(String review) {
    this.review = review;
    return this;
  }

  /**
   * Get review
   * @return review
  **/
  @ApiModelProperty(value = "")


  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  public UserRatingReview store(Store store) {
    this.store = store;
    return this;
  }

  /**
   * Get store
   * @return store
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Store getStore() {
    return store;
  }

  public void setStore(Store store) {
    this.store = store;
  }

  public UserRatingReview userName(String userName) {
    this.userName = userName;
    return this;
  }

  /**
   * Get userName
   * @return userName
  **/
  @ApiModelProperty(value = "")


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserRatingReview userRatingReview = (UserRatingReview) o;
    return Objects.equals(this.date, userRatingReview.date) &&
        Objects.equals(this.id, userRatingReview.id) &&
        Objects.equals(this.rating, userRatingReview.rating) &&
        Objects.equals(this.review, userRatingReview.review) &&
        Objects.equals(this.store, userRatingReview.store) &&
        Objects.equals(this.userName, userRatingReview.userName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, id, rating, review, store, userName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserRatingReview {\n");
    
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
    sb.append("    review: ").append(toIndentedString(review)).append("\n");
    sb.append("    store: ").append(toIndentedString(store)).append("\n");
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

