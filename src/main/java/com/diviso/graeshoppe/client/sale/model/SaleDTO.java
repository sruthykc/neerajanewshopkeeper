package com.diviso.graeshoppe.client.sale.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;
import java.time.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SaleDTO
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-12-08T11:51:40.670+05:30[Asia/Kolkata]")

public class SaleDTO   {
  @JsonProperty("customerId")
  private Long customerId = null;

  @JsonProperty("date")
  private Instant date = null;

  @JsonProperty("grandTotal")
  private Double grandTotal = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("idpCode")
  private String idpCode = null;

  @JsonProperty("paymentMode")
  private String paymentMode = null;

  @JsonProperty("paymentRef")
  private String paymentRef = null;

  @JsonProperty("saleUniqueId")
  private String saleUniqueId = null;

  @JsonProperty("storeName")
  private String storeName = null;

  public SaleDTO customerId(Long customerId) {
    this.customerId = customerId;
    return this;
  }

  /**
   * Get customerId
   * @return customerId
  **/
  @ApiModelProperty(value = "")


  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public SaleDTO date(Instant date) {
    this.date = date;
    return this;
  }

  /**
   * Get date
   * @return date
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Instant getDate() {
    return date;
  }

  public void setDate(Instant date) {
    this.date = date;
  }

  public SaleDTO grandTotal(Double grandTotal) {
    this.grandTotal = grandTotal;
    return this;
  }

  /**
   * Get grandTotal
   * @return grandTotal
  **/
  @ApiModelProperty(value = "")


  public Double getGrandTotal() {
    return grandTotal;
  }

  public void setGrandTotal(Double grandTotal) {
    this.grandTotal = grandTotal;
  }

  public SaleDTO id(Long id) {
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

  public SaleDTO idpCode(String idpCode) {
    this.idpCode = idpCode;
    return this;
  }

  /**
   * Get idpCode
   * @return idpCode
  **/
  @ApiModelProperty(value = "")


  public String getIdpCode() {
    return idpCode;
  }

  public void setIdpCode(String idpCode) {
    this.idpCode = idpCode;
  }

  public SaleDTO paymentMode(String paymentMode) {
    this.paymentMode = paymentMode;
    return this;
  }

  /**
   * Get paymentMode
   * @return paymentMode
  **/
  @ApiModelProperty(value = "")


  public String getPaymentMode() {
    return paymentMode;
  }

  public void setPaymentMode(String paymentMode) {
    this.paymentMode = paymentMode;
  }

  public SaleDTO paymentRef(String paymentRef) {
    this.paymentRef = paymentRef;
    return this;
  }

  /**
   * Get paymentRef
   * @return paymentRef
  **/
  @ApiModelProperty(value = "")


  public String getPaymentRef() {
    return paymentRef;
  }

  public void setPaymentRef(String paymentRef) {
    this.paymentRef = paymentRef;
  }

  public SaleDTO saleUniqueId(String saleUniqueId) {
    this.saleUniqueId = saleUniqueId;
    return this;
  }

  /**
   * Get saleUniqueId
   * @return saleUniqueId
  **/
  @ApiModelProperty(value = "")


  public String getSaleUniqueId() {
    return saleUniqueId;
  }

  public void setSaleUniqueId(String saleUniqueId) {
    this.saleUniqueId = saleUniqueId;
  }

  public SaleDTO storeName(String storeName) {
    this.storeName = storeName;
    return this;
  }

  /**
   * Get storeName
   * @return storeName
  **/
  @ApiModelProperty(value = "")


  public String getStoreName() {
    return storeName;
  }

  public void setStoreName(String storeName) {
    this.storeName = storeName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SaleDTO saleDTO = (SaleDTO) o;
    return Objects.equals(this.customerId, saleDTO.customerId) &&
        Objects.equals(this.date, saleDTO.date) &&
        Objects.equals(this.grandTotal, saleDTO.grandTotal) &&
        Objects.equals(this.id, saleDTO.id) &&
        Objects.equals(this.idpCode, saleDTO.idpCode) &&
        Objects.equals(this.paymentMode, saleDTO.paymentMode) &&
        Objects.equals(this.paymentRef, saleDTO.paymentRef) &&
        Objects.equals(this.saleUniqueId, saleDTO.saleUniqueId) &&
        Objects.equals(this.storeName, saleDTO.storeName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerId, date, grandTotal, id, idpCode, paymentMode, paymentRef, saleUniqueId, storeName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SaleDTO {\n");
    
    sb.append("    customerId: ").append(toIndentedString(customerId)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    grandTotal: ").append(toIndentedString(grandTotal)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    idpCode: ").append(toIndentedString(idpCode)).append("\n");
    sb.append("    paymentMode: ").append(toIndentedString(paymentMode)).append("\n");
    sb.append("    paymentRef: ").append(toIndentedString(paymentRef)).append("\n");
    sb.append("    saleUniqueId: ").append(toIndentedString(saleUniqueId)).append("\n");
    sb.append("    storeName: ").append(toIndentedString(storeName)).append("\n");
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

