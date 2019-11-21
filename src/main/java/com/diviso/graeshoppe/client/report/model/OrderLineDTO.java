package com.diviso.graeshoppe.client.report.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OrderLineDTO
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-15T16:42:27.055+05:30[Asia/Kolkata]")

public class OrderLineDTO   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("item")
  private String item = null;

  @JsonProperty("orderMasterId")
  private Long orderMasterId = null;

  @JsonProperty("quantity")
  private Integer quantity = null;

  @JsonProperty("total")
  private Double total = null;

  public OrderLineDTO id(Long id) {
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

  public OrderLineDTO item(String item) {
    this.item = item;
    return this;
  }

  /**
   * Get item
   * @return item
  **/
  @ApiModelProperty(value = "")


  public String getItem() {
    return item;
  }

  public void setItem(String item) {
    this.item = item;
  }

  public OrderLineDTO orderMasterId(Long orderMasterId) {
    this.orderMasterId = orderMasterId;
    return this;
  }

  /**
   * Get orderMasterId
   * @return orderMasterId
  **/
  @ApiModelProperty(value = "")


  public Long getOrderMasterId() {
    return orderMasterId;
  }

  public void setOrderMasterId(Long orderMasterId) {
    this.orderMasterId = orderMasterId;
  }

  public OrderLineDTO quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Get quantity
   * @return quantity
  **/
  @ApiModelProperty(value = "")


  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public OrderLineDTO total(Double total) {
    this.total = total;
    return this;
  }

  /**
   * Get total
   * @return total
  **/
  @ApiModelProperty(value = "")


  public Double getTotal() {
    return total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderLineDTO orderLineDTO = (OrderLineDTO) o;
    return Objects.equals(this.id, orderLineDTO.id) &&
        Objects.equals(this.item, orderLineDTO.item) &&
        Objects.equals(this.orderMasterId, orderLineDTO.orderMasterId) &&
        Objects.equals(this.quantity, orderLineDTO.quantity) &&
        Objects.equals(this.total, orderLineDTO.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, item, orderMasterId, quantity, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderLineDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    item: ").append(toIndentedString(item)).append("\n");
    sb.append("    orderMasterId: ").append(toIndentedString(orderMasterId)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
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

