package com.diviso.graeshoppe.client.order.model;

import java.util.Objects;
import com.diviso.graeshoppe.client.order.model.Order;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Offer
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-09-05T10:57:27.574354+05:30[Asia/Kolkata]")

public class Offer   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("offerRef")
  private String offerRef = null;

  @JsonProperty("order")
  private Order order = null;

  public Offer id(Long id) {
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

  public Offer offerRef(String offerRef) {
    this.offerRef = offerRef;
    return this;
  }

  /**
   * Get offerRef
   * @return offerRef
  **/
  @ApiModelProperty(value = "")


  public String getOfferRef() {
    return offerRef;
  }

  public void setOfferRef(String offerRef) {
    this.offerRef = offerRef;
  }

  public Offer order(Order order) {
    this.order = order;
    return this;
  }

  /**
   * Get order
   * @return order
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Offer offer = (Offer) o;
    return Objects.equals(this.id, offer.id) &&
        Objects.equals(this.offerRef, offer.offerRef) &&
        Objects.equals(this.order, offer.order);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, offerRef, order);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Offer {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    offerRef: ").append(toIndentedString(offerRef)).append("\n");
    sb.append("    order: ").append(toIndentedString(order)).append("\n");
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

