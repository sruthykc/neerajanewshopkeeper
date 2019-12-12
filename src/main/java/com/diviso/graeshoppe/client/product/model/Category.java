package com.diviso.graeshoppe.client.product.model;

import java.util.Objects;
import java.util.Set;

import com.diviso.graeshoppe.client.product.model.Product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Category
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-28T15:55:43.394+05:30[Asia/Kolkata]")

public class Category{
private Long id;


private String iDPcode;


private String name;

private byte[] image;


private String imageContentType;


private String imageLink;


private String description;


private Set<Product> products = new HashSet<>();
// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getiDPcode() {
    return iDPcode;
}

public Category iDPcode(String iDPcode) {
    this.iDPcode = iDPcode;
    return this;
}

public void setiDPcode(String iDPcode) {
    this.iDPcode = iDPcode;
}

public String getName() {
    return name;
}

public Category name(String name) {
    this.name = name;
    return this;
}

public void setName(String name) {
    this.name = name;
}

public byte[] getImage() {
    return image;
}

public Category image(byte[] image) {
    this.image = image;
    return this;
}

public void setImage(byte[] image) {
    this.image = image;
}

public String getImageContentType() {
    return imageContentType;
}

public Category imageContentType(String imageContentType) {
    this.imageContentType = imageContentType;
    return this;
}

public void setImageContentType(String imageContentType) {
    this.imageContentType = imageContentType;
}

public String getImageLink() {
    return imageLink;
}

public Category imageLink(String imageLink) {
    this.imageLink = imageLink;
    return this;
}

public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
}

public String getDescription() {
    return description;
}

public Category description(String description) {
    this.description = description;
    return this;
}

public void setDescription(String description) {
    this.description = description;
}

public Set<Product> getProducts() {
    return products;
}

public Category products(Set<Product> products) {
    this.products = products;
    return this;
}

public Category addProducts(Product product) {
    this.products.add(product);
    product.setCategory(this);
    return this;
}

public Category removeProducts(Product product) {
    this.products.remove(product);
    product.setCategory(null);
    return this;
}

public void setProducts(Set<Product> products) {
    this.products = products;
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
    Category category = (Category) o;
    if (category.getId() == null || getId() == null) {
        return false;
    }
    return Objects.equals(getId(), category.getId());
}

@Override
public int hashCode() {
    return Objects.hashCode(getId());
}

@Override
public String toString() {
    return "Category{" +
        "id=" + getId() +
        ", iDPcode='" + getiDPcode() + "'" +
        ", name='" + getName() + "'" +
        ", image='" + getImage() + "'" +
        ", imageContentType='" + getImageContentType() + "'" +
        ", imageLink='" + getImageLink() + "'" +
        ", description='" + getDescription() + "'" +
        "}";
}
}