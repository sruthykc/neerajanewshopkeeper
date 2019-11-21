package com.diviso.graeshoppe.client.order.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OfferDTO
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-01T11:30:02.598930+05:30[Asia/Kolkata]")

public class OfferDTO   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("offerRef")
  private String offerRef = null;

  @JsonProperty("orderId")
  private Long orderId = null;

  public OfferDTO id(Long id) {
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

  public OfferDTO offerRef(String offerRef) {
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

  public OfferDTO orderId(Long orderId) {
    this.orderId = orderId;
    return this;
  }

  /**
   * Get orderId
   * @return orderId
  **/
  @ApiModelProperty(value = "")


  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OfferDTO offerDTO = (OfferDTO) o;
    return Objects.equals(this.id, offerDTO.id) &&
        Objects.equals(this.offerRef, offerDTO.offerRef) &&
        Objects.equals(this.orderId, offerDTO.orderId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, offerRef, orderId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OfferDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    offerRef: ").append(toIndentedString(offerRef)).append("\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
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

