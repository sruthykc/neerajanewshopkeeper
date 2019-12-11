package com.diviso.graeshoppe.client.product.model;

import java.util.Arrays;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ProductDTO
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-28T15:55:43.394+05:30[Asia/Kolkata]")

public class ProductDTO   {
  @JsonProperty("brandId")
  private Long brandId = null;

  @JsonProperty("buyPrice")
  private Double buyPrice = null;

  @JsonProperty("categoryId")
  private Long categoryId = null;
  
	@JsonProperty("imagelink")
	private String imageLink = null;

  public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

@JsonProperty("discountId")
  private Long discountId = null;

  @JsonProperty("iDPcode")
  private String iDPcode = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("image")
  private byte[] image = null;

  @JsonProperty("imageContentType")
  private String imageContentType = null;

  @JsonProperty("isActive")
  private Boolean isActive = null;

  @JsonProperty("isAuxilaryItem")
  private Boolean isAuxilaryItem = null;

  @JsonProperty("isServiceItem")
  private Boolean isServiceItem = null;

  @JsonProperty("locationId")
  private Long locationId = null;

  @JsonProperty("manufacturerId")
  private Long manufacturerId = null;

  @JsonProperty("maxQuantityLevel")
  private Double maxQuantityLevel = null;

  @JsonProperty("minQuantityLevel")
  private Double minQuantityLevel = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("reference")
  private String reference = null;

  @JsonProperty("sellingPrice")
  private Double sellingPrice = null;

  @JsonProperty("showInCatalogue")
  private Boolean showInCatalogue = null;

  @JsonProperty("sku")
  private String sku = null;

  @JsonProperty("storageCost")
  private Double storageCost = null;

  @JsonProperty("supplierId")
  private Long supplierId = null;

  @JsonProperty("taxCategoryId")
  private Long taxCategoryId = null;

  @JsonProperty("unitId")
  private Long unitId = null;

  public ProductDTO brandId(Long brandId) {
    this.brandId = brandId;
    return this;
  }

  /**
   * Get brandId
   * @return brandId
  **/
  @ApiModelProperty(value = "")


  public Long getBrandId() {
    return brandId;
  }

  public void setBrandId(Long brandId) {
    this.brandId = brandId;
  }

  public ProductDTO buyPrice(Double buyPrice) {
    this.buyPrice = buyPrice;
    return this;
  }

  /**
   * Get buyPrice
   * @return buyPrice
  **/
  @ApiModelProperty(value = "")


  public Double getBuyPrice() {
    return buyPrice;
  }

  public void setBuyPrice(Double buyPrice) {
    this.buyPrice = buyPrice;
  }

  public ProductDTO categoryId(Long categoryId) {
    this.categoryId = categoryId;
    return this;
  }

  /**
   * Get categoryId
   * @return categoryId
  **/
  @ApiModelProperty(value = "")


  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public ProductDTO discountId(Long discountId) {
    this.discountId = discountId;
    return this;
  }

  /**
   * Get discountId
   * @return discountId
  **/
  @ApiModelProperty(value = "")


  public Long getDiscountId() {
    return discountId;
  }

  public void setDiscountId(Long discountId) {
    this.discountId = discountId;
  }

  public ProductDTO iDPcode(String iDPcode) {
    this.iDPcode = iDPcode;
    return this;
  }

  /**
   * Get iDPcode
   * @return iDPcode
  **/
  @ApiModelProperty(value = "")


  public String getIDPcode() {
    return iDPcode;
  }

  public void setIDPcode(String iDPcode) {
    this.iDPcode = iDPcode;
  }

  public ProductDTO id(Long id) {
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

  public ProductDTO image(byte[] image) {
    this.image = image;
    return this;
  }

  /**
   * Get image
   * @return image
  **/
  @ApiModelProperty(value = "")

//@Pattern(regexp="^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$") 
  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public ProductDTO imageContentType(String imageContentType) {
    this.imageContentType = imageContentType;
    return this;
  }

  /**
   * Get imageContentType
   * @return imageContentType
  **/
  @ApiModelProperty(value = "")


  public String getImageContentType() {
    return imageContentType;
  }

  public void setImageContentType(String imageContentType) {
    this.imageContentType = imageContentType;
  }

  public ProductDTO isActive(Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  /**
   * Get isActive
   * @return isActive
  **/
  @ApiModelProperty(value = "")


  public Boolean isIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public ProductDTO isAuxilaryItem(Boolean isAuxilaryItem) {
    this.isAuxilaryItem = isAuxilaryItem;
    return this;
  }

  /**
   * Get isAuxilaryItem
   * @return isAuxilaryItem
  **/
  @ApiModelProperty(value = "")


  public Boolean isIsAuxilaryItem() {
    return isAuxilaryItem;
  }

  public void setIsAuxilaryItem(Boolean isAuxilaryItem) {
    this.isAuxilaryItem = isAuxilaryItem;
  }

  public ProductDTO isServiceItem(Boolean isServiceItem) {
    this.isServiceItem = isServiceItem;
    return this;
  }

  /**
   * Get isServiceItem
   * @return isServiceItem
  **/
  @ApiModelProperty(value = "")


  public Boolean isIsServiceItem() {
    return isServiceItem;
  }

  public void setIsServiceItem(Boolean isServiceItem) {
    this.isServiceItem = isServiceItem;
  }

  public ProductDTO locationId(Long locationId) {
    this.locationId = locationId;
    return this;
  }

  /**
   * Get locationId
   * @return locationId
  **/
  @ApiModelProperty(value = "")


  public Long getLocationId() {
    return locationId;
  }

  public void setLocationId(Long locationId) {
    this.locationId = locationId;
  }

  public ProductDTO manufacturerId(Long manufacturerId) {
    this.manufacturerId = manufacturerId;
    return this;
  }

  /**
   * Get manufacturerId
   * @return manufacturerId
  **/
  @ApiModelProperty(value = "")


  public Long getManufacturerId() {
    return manufacturerId;
  }

  public void setManufacturerId(Long manufacturerId) {
    this.manufacturerId = manufacturerId;
  }

  public ProductDTO maxQuantityLevel(Double maxQuantityLevel) {
    this.maxQuantityLevel = maxQuantityLevel;
    return this;
  }

  /**
   * Get maxQuantityLevel
   * @return maxQuantityLevel
  **/
  @ApiModelProperty(value = "")


  public Double getMaxQuantityLevel() {
    return maxQuantityLevel;
  }

  public void setMaxQuantityLevel(Double maxQuantityLevel) {
    this.maxQuantityLevel = maxQuantityLevel;
  }

  public ProductDTO minQuantityLevel(Double minQuantityLevel) {
    this.minQuantityLevel = minQuantityLevel;
    return this;
  }

  /**
   * Get minQuantityLevel
   * @return minQuantityLevel
  **/
  @ApiModelProperty(value = "")


  public Double getMinQuantityLevel() {
    return minQuantityLevel;
  }

  public void setMinQuantityLevel(Double minQuantityLevel) {
    this.minQuantityLevel = minQuantityLevel;
  }

  public ProductDTO name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ProductDTO reference(String reference) {
    this.reference = reference;
    return this;
  }

  /**
   * Get reference
   * @return reference
  **/
  @ApiModelProperty(value = "")


  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public ProductDTO sellingPrice(Double sellingPrice) {
    this.sellingPrice = sellingPrice;
    return this;
  }

  /**
   * Get sellingPrice
   * @return sellingPrice
  **/
  @ApiModelProperty(value = "")


  public Double getSellingPrice() {
    return sellingPrice;
  }

  public void setSellingPrice(Double sellingPrice) {
    this.sellingPrice = sellingPrice;
  }

  public ProductDTO showInCatalogue(Boolean showInCatalogue) {
    this.showInCatalogue = showInCatalogue;
    return this;
  }

  /**
   * Get showInCatalogue
   * @return showInCatalogue
  **/
  @ApiModelProperty(value = "")


  public Boolean isShowInCatalogue() {
    return showInCatalogue;
  }

  public void setShowInCatalogue(Boolean showInCatalogue) {
    this.showInCatalogue = showInCatalogue;
  }

  public ProductDTO sku(String sku) {
    this.sku = sku;
    return this;
  }

  /**
   * Get sku
   * @return sku
  **/
  @ApiModelProperty(value = "")


  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public ProductDTO storageCost(Double storageCost) {
    this.storageCost = storageCost;
    return this;
  }

  /**
   * Get storageCost
   * @return storageCost
  **/
  @ApiModelProperty(value = "")


  public Double getStorageCost() {
    return storageCost;
  }

  public void setStorageCost(Double storageCost) {
    this.storageCost = storageCost;
  }

  public ProductDTO supplierId(Long supplierId) {
    this.supplierId = supplierId;
    return this;
  }

  /**
   * Get supplierId
   * @return supplierId
  **/
  @ApiModelProperty(value = "")


  public Long getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
  }

  public ProductDTO taxCategoryId(Long taxCategoryId) {
    this.taxCategoryId = taxCategoryId;
    return this;
  }

  /**
   * Get taxCategoryId
   * @return taxCategoryId
  **/
  @ApiModelProperty(value = "")


  public Long getTaxCategoryId() {
    return taxCategoryId;
  }

  public void setTaxCategoryId(Long taxCategoryId) {
    this.taxCategoryId = taxCategoryId;
  }

  public ProductDTO unitId(Long unitId) {
    this.unitId = unitId;
    return this;
  }

  /**
   * Get unitId
   * @return unitId
  **/
  @ApiModelProperty(value = "")


  public Long getUnitId() {
    return unitId;
  }

  public void setUnitId(Long unitId) {
    this.unitId = unitId;
  }

  
  

  @Override
public String toString() {
	return "ProductDTO [brandId=" + brandId + ", buyPrice=" + buyPrice + ", categoryId=" + categoryId + ", imageLink="
			+ imageLink + ", discountId=" + discountId + ", iDPcode=" + iDPcode + ", id=" + id + ", image="
			+ Arrays.toString(image) + ", imageContentType=" + imageContentType + ", isActive=" + isActive
			+ ", isAuxilaryItem=" + isAuxilaryItem + ", isServiceItem=" + isServiceItem + ", locationId=" + locationId
			+ ", manufacturerId=" + manufacturerId + ", maxQuantityLevel=" + maxQuantityLevel + ", minQuantityLevel="
			+ minQuantityLevel + ", name=" + name + ", reference=" + reference + ", sellingPrice=" + sellingPrice
			+ ", showInCatalogue=" + showInCatalogue + ", sku=" + sku + ", storageCost=" + storageCost + ", supplierId="
			+ supplierId + ", taxCategoryId=" + taxCategoryId + ", unitId=" + unitId + "]";
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((brandId == null) ? 0 : brandId.hashCode());
	result = prime * result + ((buyPrice == null) ? 0 : buyPrice.hashCode());
	result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
	result = prime * result + ((discountId == null) ? 0 : discountId.hashCode());
	result = prime * result + ((iDPcode == null) ? 0 : iDPcode.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + Arrays.hashCode(image);
	result = prime * result + ((imageContentType == null) ? 0 : imageContentType.hashCode());
	result = prime * result + ((imageLink == null) ? 0 : imageLink.hashCode());
	result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
	result = prime * result + ((isAuxilaryItem == null) ? 0 : isAuxilaryItem.hashCode());
	result = prime * result + ((isServiceItem == null) ? 0 : isServiceItem.hashCode());
	result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
	result = prime * result + ((manufacturerId == null) ? 0 : manufacturerId.hashCode());
	result = prime * result + ((maxQuantityLevel == null) ? 0 : maxQuantityLevel.hashCode());
	result = prime * result + ((minQuantityLevel == null) ? 0 : minQuantityLevel.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((reference == null) ? 0 : reference.hashCode());
	result = prime * result + ((sellingPrice == null) ? 0 : sellingPrice.hashCode());
	result = prime * result + ((showInCatalogue == null) ? 0 : showInCatalogue.hashCode());
	result = prime * result + ((sku == null) ? 0 : sku.hashCode());
	result = prime * result + ((storageCost == null) ? 0 : storageCost.hashCode());
	result = prime * result + ((supplierId == null) ? 0 : supplierId.hashCode());
	result = prime * result + ((taxCategoryId == null) ? 0 : taxCategoryId.hashCode());
	result = prime * result + ((unitId == null) ? 0 : unitId.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	ProductDTO other = (ProductDTO) obj;
	if (brandId == null) {
		if (other.brandId != null)
			return false;
	} else if (!brandId.equals(other.brandId))
		return false;
	if (buyPrice == null) {
		if (other.buyPrice != null)
			return false;
	} else if (!buyPrice.equals(other.buyPrice))
		return false;
	if (categoryId == null) {
		if (other.categoryId != null)
			return false;
	} else if (!categoryId.equals(other.categoryId))
		return false;
	if (discountId == null) {
		if (other.discountId != null)
			return false;
	} else if (!discountId.equals(other.discountId))
		return false;
	if (iDPcode == null) {
		if (other.iDPcode != null)
			return false;
	} else if (!iDPcode.equals(other.iDPcode))
		return false;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	if (!Arrays.equals(image, other.image))
		return false;
	if (imageContentType == null) {
		if (other.imageContentType != null)
			return false;
	} else if (!imageContentType.equals(other.imageContentType))
		return false;
	if (imageLink == null) {
		if (other.imageLink != null)
			return false;
	} else if (!imageLink.equals(other.imageLink))
		return false;
	if (isActive == null) {
		if (other.isActive != null)
			return false;
	} else if (!isActive.equals(other.isActive))
		return false;
	if (isAuxilaryItem == null) {
		if (other.isAuxilaryItem != null)
			return false;
	} else if (!isAuxilaryItem.equals(other.isAuxilaryItem))
		return false;
	if (isServiceItem == null) {
		if (other.isServiceItem != null)
			return false;
	} else if (!isServiceItem.equals(other.isServiceItem))
		return false;
	if (locationId == null) {
		if (other.locationId != null)
			return false;
	} else if (!locationId.equals(other.locationId))
		return false;
	if (manufacturerId == null) {
		if (other.manufacturerId != null)
			return false;
	} else if (!manufacturerId.equals(other.manufacturerId))
		return false;
	if (maxQuantityLevel == null) {
		if (other.maxQuantityLevel != null)
			return false;
	} else if (!maxQuantityLevel.equals(other.maxQuantityLevel))
		return false;
	if (minQuantityLevel == null) {
		if (other.minQuantityLevel != null)
			return false;
	} else if (!minQuantityLevel.equals(other.minQuantityLevel))
		return false;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	if (reference == null) {
		if (other.reference != null)
			return false;
	} else if (!reference.equals(other.reference))
		return false;
	if (sellingPrice == null) {
		if (other.sellingPrice != null)
			return false;
	} else if (!sellingPrice.equals(other.sellingPrice))
		return false;
	if (showInCatalogue == null) {
		if (other.showInCatalogue != null)
			return false;
	} else if (!showInCatalogue.equals(other.showInCatalogue))
		return false;
	if (sku == null) {
		if (other.sku != null)
			return false;
	} else if (!sku.equals(other.sku))
		return false;
	if (storageCost == null) {
		if (other.storageCost != null)
			return false;
	} else if (!storageCost.equals(other.storageCost))
		return false;
	if (supplierId == null) {
		if (other.supplierId != null)
			return false;
	} else if (!supplierId.equals(other.supplierId))
		return false;
	if (taxCategoryId == null) {
		if (other.taxCategoryId != null)
			return false;
	} else if (!taxCategoryId.equals(other.taxCategoryId))
		return false;
	if (unitId == null) {
		if (other.unitId != null)
			return false;
	} else if (!unitId.equals(other.unitId))
		return false;
	return true;
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

