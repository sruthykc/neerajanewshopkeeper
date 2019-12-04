package com.diviso.graeshoppe.client.product.model;

import java.util.Objects;
import java.util.Set;

import com.diviso.graeshoppe.client.product.model.AuxilaryLineItem;
import com.diviso.graeshoppe.client.product.model.Brand;
import com.diviso.graeshoppe.client.product.model.Category;
import com.diviso.graeshoppe.client.product.model.ComboLineItem;
import com.diviso.graeshoppe.client.product.model.Discount;
import com.diviso.graeshoppe.client.product.model.Label;
import com.diviso.graeshoppe.client.product.model.Location;
import com.diviso.graeshoppe.client.product.model.Manufacturer;
import com.diviso.graeshoppe.client.product.model.Supplier;
import com.diviso.graeshoppe.client.product.model.TaxCategory;
import com.diviso.graeshoppe.client.product.model.UOM;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Product
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-28T15:55:43.394+05:30[Asia/Kolkata]")

public class Product   {
   private Long id;

	    
	    private String reference;

	 
	    private String name;

	
	    private Boolean showInCatalogue;

	    private byte[] image;

	 
	    private String imageContentType;

	
	    private String imageLink;

	  
	    private Boolean isActive;

	   
	    private String sku;

	  
	    private String iDPcode;

	   
	    private Boolean isServiceItem;

	   
	    private Boolean isAuxilaryItem;

	 
	    private Double minQuantityLevel;

	   
	    private Double maxQuantityLevel;

	 
	    private Double storageCost;

	  
	    private Double sellingPrice;

	  
	    private Double buyPrice;

	   
	    private Set<AuxilaryLineItem> auxilaryLineItems = new HashSet<>();
	  
	    private Set<ComboLineItem> comboLineItems = new HashSet<>();
	  
	    private Set<Label> labels = new HashSet<>();
	    
	    private TaxCategory taxCategory;

	   
	    private UOM unit;

	  
	    private Location location;

	   
	    private Supplier supplier;

	  
	    private Manufacturer manufacturer;

	   
	    private Brand brand;

	 
	    private Discount discount;

	   
	    private Category category;

	    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getReference() {
	        return reference;
	    }

	    public Product reference(String reference) {
	        this.reference = reference;
	        return this;
	    }

	    public void setReference(String reference) {
	        this.reference = reference;
	    }

	    public String getName() {
	        return name;
	    }

	    public Product name(String name) {
	        this.name = name;
	        return this;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public Boolean isShowInCatalogue() {
	        return showInCatalogue;
	    }

	    public Product showInCatalogue(Boolean showInCatalogue) {
	        this.showInCatalogue = showInCatalogue;
	        return this;
	    }

	    public void setShowInCatalogue(Boolean showInCatalogue) {
	        this.showInCatalogue = showInCatalogue;
	    }

	    public byte[] getImage() {
	        return image;
	    }

	    public Product image(byte[] image) {
	        this.image = image;
	        return this;
	    }

	    public void setImage(byte[] image) {
	        this.image = image;
	    }

	    public String getImageContentType() {
	        return imageContentType;
	    }

	    public Product imageContentType(String imageContentType) {
	        this.imageContentType = imageContentType;
	        return this;
	    }

	    public void setImageContentType(String imageContentType) {
	        this.imageContentType = imageContentType;
	    }

	    public String getImageLink() {
	        return imageLink;
	    }

	    public Product imageLink(String imageLink) {
	        this.imageLink = imageLink;
	        return this;
	    }

	    public void setImageLink(String imageLink) {
	        this.imageLink = imageLink;
	    }

	    public Boolean isIsActive() {
	        return isActive;
	    }

	    public Product isActive(Boolean isActive) {
	        this.isActive = isActive;
	        return this;
	    }

	    public void setIsActive(Boolean isActive) {
	        this.isActive = isActive;
	    }

	    public String getSku() {
	        return sku;
	    }

	    public Product sku(String sku) {
	        this.sku = sku;
	        return this;
	    }

	    public void setSku(String sku) {
	        this.sku = sku;
	    }

	    public String getiDPcode() {
	        return iDPcode;
	    }

	    public Product iDPcode(String iDPcode) {
	        this.iDPcode = iDPcode;
	        return this;
	    }

	    public void setiDPcode(String iDPcode) {
	        this.iDPcode = iDPcode;
	    }

	    public Boolean isIsServiceItem() {
	        return isServiceItem;
	    }

	    public Product isServiceItem(Boolean isServiceItem) {
	        this.isServiceItem = isServiceItem;
	        return this;
	    }

	    public void setIsServiceItem(Boolean isServiceItem) {
	        this.isServiceItem = isServiceItem;
	    }

	    public Boolean isIsAuxilaryItem() {
	        return isAuxilaryItem;
	    }

	    public Product isAuxilaryItem(Boolean isAuxilaryItem) {
	        this.isAuxilaryItem = isAuxilaryItem;
	        return this;
	    }

	    public void setIsAuxilaryItem(Boolean isAuxilaryItem) {
	        this.isAuxilaryItem = isAuxilaryItem;
	    }

	    public Double getMinQuantityLevel() {
	        return minQuantityLevel;
	    }

	    public Product minQuantityLevel(Double minQuantityLevel) {
	        this.minQuantityLevel = minQuantityLevel;
	        return this;
	    }

	    public void setMinQuantityLevel(Double minQuantityLevel) {
	        this.minQuantityLevel = minQuantityLevel;
	    }

	    public Double getMaxQuantityLevel() {
	        return maxQuantityLevel;
	    }

	    public Product maxQuantityLevel(Double maxQuantityLevel) {
	        this.maxQuantityLevel = maxQuantityLevel;
	        return this;
	    }

	    public void setMaxQuantityLevel(Double maxQuantityLevel) {
	        this.maxQuantityLevel = maxQuantityLevel;
	    }

	    public Double getStorageCost() {
	        return storageCost;
	    }

	    public Product storageCost(Double storageCost) {
	        this.storageCost = storageCost;
	        return this;
	    }

	    public void setStorageCost(Double storageCost) {
	        this.storageCost = storageCost;
	    }

	    public Double getSellingPrice() {
	        return sellingPrice;
	    }

	    public Product sellingPrice(Double sellingPrice) {
	        this.sellingPrice = sellingPrice;
	        return this;
	    }

	    public void setSellingPrice(Double sellingPrice) {
	        this.sellingPrice = sellingPrice;
	    }

	    public Double getBuyPrice() {
	        return buyPrice;
	    }

	    public Product buyPrice(Double buyPrice) {
	        this.buyPrice = buyPrice;
	        return this;
	    }

	    public void setBuyPrice(Double buyPrice) {
	        this.buyPrice = buyPrice;
	    }

	    public Set<AuxilaryLineItem> getAuxilaryLineItems() {
	        return auxilaryLineItems;
	    }

	    public Product auxilaryLineItems(Set<AuxilaryLineItem> auxilaryLineItems) {
	        this.auxilaryLineItems = auxilaryLineItems;
	        return this;
	    }

	    public Product addAuxilaryLineItems(AuxilaryLineItem auxilaryLineItem) {
	        this.auxilaryLineItems.add(auxilaryLineItem);
	        auxilaryLineItem.setProduct(this);
	        return this;
	    }

	    public Product removeAuxilaryLineItems(AuxilaryLineItem auxilaryLineItem) {
	        this.auxilaryLineItems.remove(auxilaryLineItem);
	        auxilaryLineItem.setProduct(null);
	        return this;
	    }

	    public void setAuxilaryLineItems(Set<AuxilaryLineItem> auxilaryLineItems) {
	        this.auxilaryLineItems = auxilaryLineItems;
	    }

	    public Set<ComboLineItem> getComboLineItems() {
	        return comboLineItems;
	    }

	    public Product comboLineItems(Set<ComboLineItem> comboLineItems) {
	        this.comboLineItems = comboLineItems;
	        return this;
	    }

	    public Product addComboLineItems(ComboLineItem comboLineItem) {
	        this.comboLineItems.add(comboLineItem);
	        comboLineItem.setProduct(this);
	        return this;
	    }

	    public Product removeComboLineItems(ComboLineItem comboLineItem) {
	        this.comboLineItems.remove(comboLineItem);
	        comboLineItem.setProduct(null);
	        return this;
	    }

	    public void setComboLineItems(Set<ComboLineItem> comboLineItems) {
	        this.comboLineItems = comboLineItems;
	    }

	    public Set<Label> getLabels() {
	        return labels;
	    }

	    public Product labels(Set<Label> labels) {
	        this.labels = labels;
	        return this;
	    }

	    public Product addLabels(Label label) {
	        this.labels.add(label);
	        label.setProduct(this);
	        return this;
	    }

	    public Product removeLabels(Label label) {
	        this.labels.remove(label);
	        label.setProduct(null);
	        return this;
	    }

	    public void setLabels(Set<Label> labels) {
	        this.labels = labels;
	    }

	    public TaxCategory getTaxCategory() {
	        return taxCategory;
	    }

	    public Product taxCategory(TaxCategory taxCategory) {
	        this.taxCategory = taxCategory;
	        return this;
	    }

	    public void setTaxCategory(TaxCategory taxCategory) {
	        this.taxCategory = taxCategory;
	    }

	    public UOM getUnit() {
	        return unit;
	    }

	    public Product unit(UOM uOM) {
	        this.unit = uOM;
	        return this;
	    }

	    public void setUnit(UOM uOM) {
	        this.unit = uOM;
	    }

	    public Location getLocation() {
	        return location;
	    }

	    public Product location(Location location) {
	        this.location = location;
	        return this;
	    }

	    public void setLocation(Location location) {
	        this.location = location;
	    }

	    public Supplier getSupplier() {
	        return supplier;
	    }

	    public Product supplier(Supplier supplier) {
	        this.supplier = supplier;
	        return this;
	    }

	    public void setSupplier(Supplier supplier) {
	        this.supplier = supplier;
	    }

	    public Manufacturer getManufacturer() {
	        return manufacturer;
	    }

	    public Product manufacturer(Manufacturer manufacturer) {
	        this.manufacturer = manufacturer;
	        return this;
	    }

	    public void setManufacturer(Manufacturer manufacturer) {
	        this.manufacturer = manufacturer;
	    }

	    public Brand getBrand() {
	        return brand;
	    }

	    public Product brand(Brand brand) {
	        this.brand = brand;
	        return this;
	    }

	    public void setBrand(Brand brand) {
	        this.brand = brand;
	    }

	    public Discount getDiscount() {
	        return discount;
	    }

	    public Product discount(Discount discount) {
	        this.discount = discount;
	        return this;
	    }

	    public void setDiscount(Discount discount) {
	        this.discount = discount;
	    }

	    public Category getCategory() {
	        return category;
	    }

	    public Product category(Category category) {
	        this.category = category;
	        return this;
	    }

	    public void setCategory(Category category) {
	        this.category = category;
	    }
	    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }
	        Product product = (Product) o;
	        if (product.getId() == null || getId() == null) {
	            return false;
	        }
	        return Objects.equals(getId(), product.getId());
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hashCode(getId());
	    }

	    @Override
	    public String toString() {
	        return "Product{" +
	            "id=" + getId() +
	            ", reference='" + getReference() + "'" +
	            ", name='" + getName() + "'" +
	            ", showInCatalogue='" + isShowInCatalogue() + "'" +
	            ", image='" + getImage() + "'" +
	            ", imageContentType='" + getImageContentType() + "'" +
	            ", imageLink='" + getImageLink() + "'" +
	            ", isActive='" + isIsActive() + "'" +
	            ", sku='" + getSku() + "'" +
	            ", iDPcode='" + getiDPcode() + "'" +
	            ", isServiceItem='" + isIsServiceItem() + "'" +
	            ", isAuxilaryItem='" + isIsAuxilaryItem() + "'" +
	            ", minQuantityLevel=" + getMinQuantityLevel() +
	            ", maxQuantityLevel=" + getMaxQuantityLevel() +
	            ", storageCost=" + getStorageCost() +
	            ", sellingPrice=" + getSellingPrice() +
	            ", buyPrice=" + getBuyPrice() +
	            "}";
	    }
}

