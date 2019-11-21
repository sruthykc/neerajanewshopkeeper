package com.diviso.graeshoppe.client.store.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * StoreSettings
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-08-02T11:19:44.906+05:30[Asia/Calcutta]")

public class StoreSettings   {
  @JsonProperty("deliveryCharge")
  private Double deliveryCharge = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("orderAcceptType")
  private Double orderAcceptType = null;

  @JsonProperty("serviceCharge")
  private String serviceCharge = null;

  public StoreSettings deliveryCharge(Double deliveryCharge) {
    this.deliveryCharge = deliveryCharge;
    return this;
  }

  /**
   * Get deliveryCharge
   * @return deliveryCharge
  **/
  @ApiModelProperty(value = "")


  public Double getDeliveryCharge() {
    return deliveryCharge;
  }

  public void setDeliveryCharge(Double deliveryCharge) {
    this.deliveryCharge = deliveryCharge;
  }

  public StoreSettings id(Long id) {
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

  public StoreSettings orderAcceptType(Double orderAcceptType) {
    this.orderAcceptType = orderAcceptType;
    return this;
  }

  /**
   * Get orderAcceptType
   * @return orderAcceptType
  **/
  @ApiModelProperty(value = "")


  public Double getOrderAcceptType() {
    return orderAcceptType;
  }

  public void setOrderAcceptType(Double orderAcceptType) {
    this.orderAcceptType = orderAcceptType;
  }

  public StoreSettings serviceCharge(String serviceCharge) {
    this.serviceCharge = serviceCharge;
    return this;
  }

  /**
   * Get serviceCharge
   * @return serviceCharge
  **/
  @ApiModelProperty(value = "")


  public String getServiceCharge() {
    return serviceCharge;
  }

  public void setServiceCharge(String serviceCharge) {
    this.serviceCharge = serviceCharge;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StoreSettings storeSettings = (StoreSettings) o;
    return Objects.equals(this.deliveryCharge, storeSettings.deliveryCharge) &&
        Objects.equals(this.id, storeSettings.id) &&
        Objects.equals(this.orderAcceptType, storeSettings.orderAcceptType) &&
        Objects.equals(this.serviceCharge, storeSettings.serviceCharge);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deliveryCharge, id, orderAcceptType, serviceCharge);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StoreSettings {\n");
    
    sb.append("    deliveryCharge: ").append(toIndentedString(deliveryCharge)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    orderAcceptType: ").append(toIndentedString(orderAcceptType)).append("\n");
    sb.append("    serviceCharge: ").append(toIndentedString(serviceCharge)).append("\n");
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

