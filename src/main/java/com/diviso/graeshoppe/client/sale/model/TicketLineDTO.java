package com.diviso.graeshoppe.client.sale.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TicketLineDTO
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-12-08T11:51:40.670+05:30[Asia/Kolkata]")

public class TicketLineDTO   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("price")
  private Double price = null;

  @JsonProperty("productName")
  private String productName = null;

  @JsonProperty("quantity")
  private Integer quantity = null;

  @JsonProperty("saleId")
  private Long saleId = null;

  @JsonProperty("total")
  private Double total = null;

  public TicketLineDTO id(Long id) {
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

  public TicketLineDTO price(Double price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
  **/
  @ApiModelProperty(value = "")


  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public TicketLineDTO productName(String productName) {
    this.productName = productName;
    return this;
  }

  /**
   * Get productName
   * @return productName
  **/
  @ApiModelProperty(value = "")


  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public TicketLineDTO quantity(Integer quantity) {
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

  public TicketLineDTO saleId(Long saleId) {
    this.saleId = saleId;
    return this;
  }

  /**
   * Get saleId
   * @return saleId
  **/
  @ApiModelProperty(value = "")


  public Long getSaleId() {
    return saleId;
  }

  public void setSaleId(Long saleId) {
    this.saleId = saleId;
  }

  public TicketLineDTO total(Double total) {
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
    TicketLineDTO ticketLineDTO = (TicketLineDTO) o;
    return Objects.equals(this.id, ticketLineDTO.id) &&
        Objects.equals(this.price, ticketLineDTO.price) &&
        Objects.equals(this.productName, ticketLineDTO.productName) &&
        Objects.equals(this.quantity, ticketLineDTO.quantity) &&
        Objects.equals(this.saleId, ticketLineDTO.saleId) &&
        Objects.equals(this.total, ticketLineDTO.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, price, productName, quantity, saleId, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TicketLineDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    productName: ").append(toIndentedString(productName)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    saleId: ").append(toIndentedString(saleId)).append("\n");
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

