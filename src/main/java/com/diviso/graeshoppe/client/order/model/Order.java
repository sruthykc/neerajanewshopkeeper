package com.diviso.graeshoppe.client.order.model;

import java.util.Objects;
import java.util.Set;

import com.diviso.graeshoppe.client.order.model.ApprovalDetails;
import com.diviso.graeshoppe.client.order.model.DeliveryInfo;
import com.diviso.graeshoppe.client.order.model.Offer;
import com.diviso.graeshoppe.client.order.model.OrderLine;
import com.diviso.graeshoppe.client.order.model.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Order
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-09-05T10:57:27.574354+05:30[Asia/Kolkata]")


public class Order   {
  @JsonProperty("appliedOffers")
  @Valid
  private List<Offer> appliedOffers = null;

  @JsonProperty("approvalDetails")
  private ApprovalDetails approvalDetails = null;

  @JsonProperty("customerId")
  private String customerId = null;

  @JsonProperty("date")
  private Instant date = null;

  @JsonProperty("deliveryInfo")
  private DeliveryInfo deliveryInfo = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("grandTotal")
  private Double grandTotal = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("orderId")
  private String orderId = null;

  @JsonProperty("orderLines")
  @Valid
  private Set<OrderLine> orderLines = null;

  @JsonProperty("paymentRef")
  private String paymentRef = null;

  @JsonProperty("status")
  private Status status = null;

  @JsonProperty("storeId")
  private String storeId = null;

  public Order appliedOffers(List<Offer> appliedOffers) {
    this.appliedOffers = appliedOffers;
    return this;
  }

  public Order addAppliedOffersItem(Offer appliedOffersItem) {
    if (this.appliedOffers == null) {
      this.appliedOffers = new ArrayList<Offer>();
    }
    this.appliedOffers.add(appliedOffersItem);
    return this;
  }

  /**
   * Get appliedOffers
   * @return appliedOffers
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Offer> getAppliedOffers() {
    return appliedOffers;
  }

  public void setAppliedOffers(List<Offer> appliedOffers) {
    this.appliedOffers = appliedOffers;
  }

  public Order approvalDetails(ApprovalDetails approvalDetails) {
    this.approvalDetails = approvalDetails;
    return this;
  }

  /**
   * Get approvalDetails
   * @return approvalDetails
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ApprovalDetails getApprovalDetails() {
    return approvalDetails;
  }

  public void setApprovalDetails(ApprovalDetails approvalDetails) {
    this.approvalDetails = approvalDetails;
  }

  public Order customerId(String customerId) {
    this.customerId = customerId;
    return this;
  }

  /**
   * Get customerId
   * @return customerId
  **/
  @ApiModelProperty(value = "")


  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public Order date(Instant date) {
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

  public Order deliveryInfo(DeliveryInfo deliveryInfo) {
    this.deliveryInfo = deliveryInfo;
    return this;
  }

  /**
   * Get deliveryInfo
   * @return deliveryInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DeliveryInfo getDeliveryInfo() {
    return deliveryInfo;
  }

  public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
    this.deliveryInfo = deliveryInfo;
  }

  public Order email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
  **/
  @ApiModelProperty(value = "")


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Order grandTotal(Double grandTotal) {
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

  public Order id(Long id) {
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

  public Order orderId(String orderId) {
    this.orderId = orderId;
    return this;
  }

  /**
   * Get orderId
   * @return orderId
  **/
  @ApiModelProperty(value = "")


  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Order orderLines(Set<OrderLine> orderLines) {
    this.orderLines = orderLines;
    return this;
  }

  public Order addOrderLinesItem(OrderLine orderLinesItem) {
    if (this.orderLines == null) {
      this.orderLines = new HashSet<OrderLine>();
    }
    this.orderLines.add(orderLinesItem);
    return this;
  }

  /**
   * Get orderLines
   * @return orderLines
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Set<OrderLine> getOrderLines() {
    return orderLines;
  }

  public void setOrderLines(Set<OrderLine> orderLines) {
    this.orderLines = orderLines;
  }

  public Order paymentRef(String paymentRef) {
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

  public Order status(Status status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Order storeId(String storeId) {
    this.storeId = storeId;
    return this;
  }

  /**
   * Get storeId
   * @return storeId
  **/
  @ApiModelProperty(value = "")


  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return Objects.equals(this.appliedOffers, order.appliedOffers) &&
        Objects.equals(this.approvalDetails, order.approvalDetails) &&
        Objects.equals(this.customerId, order.customerId) &&
        Objects.equals(this.date, order.date) &&
        Objects.equals(this.deliveryInfo, order.deliveryInfo) &&
        Objects.equals(this.email, order.email) &&
        Objects.equals(this.grandTotal, order.grandTotal) &&
        Objects.equals(this.id, order.id) &&
        Objects.equals(this.orderId, order.orderId) &&
        Objects.equals(this.orderLines, order.orderLines) &&
        Objects.equals(this.paymentRef, order.paymentRef) &&
        Objects.equals(this.status, order.status) &&
        Objects.equals(this.storeId, order.storeId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appliedOffers, approvalDetails, customerId, date, deliveryInfo, email, grandTotal, id, orderId, orderLines, paymentRef, status, storeId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Order {\n");
    
    sb.append("    appliedOffers: ").append(toIndentedString(appliedOffers)).append("\n");
    sb.append("    approvalDetails: ").append(toIndentedString(approvalDetails)).append("\n");
    sb.append("    customerId: ").append(toIndentedString(customerId)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    deliveryInfo: ").append(toIndentedString(deliveryInfo)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    grandTotal: ").append(toIndentedString(grandTotal)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    orderLines: ").append(toIndentedString(orderLines)).append("\n");
    sb.append("    paymentRef: ").append(toIndentedString(paymentRef)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    storeId: ").append(toIndentedString(storeId)).append("\n");
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

