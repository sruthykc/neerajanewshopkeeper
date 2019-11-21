package com.diviso.graeshoppe.client.store.domain;




import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;



import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Store.
 */

public class Store implements Serializable {

    private static final long serialVersionUID = 1L;
    
   

	private Long id;

   
    private String regNo;

  
    private String name;

    @Lob
    private byte[] image;

 
    private String imageContentType;

    private Double totalRating;


  
    private String location;


    private String locationName;

    private Long contactNo;

    private Instant openingTime;

    private String email;

    private Instant closingTime;


    private String info;

    private Double minAmount;

   
    private Instant maxDeliveryTime;

   
    private Propreitor propreitor;

   
    private StoreAddress storeAddress;
    private StoreSettings storeSettings;
    private Set<StoreType> storeTypes = new HashSet<>();
   
    private Set<Review> reviews = new HashSet<>();
   
    private Set<UserRating> userRatings = new HashSet<>();
   
    private Set<Banner> banners = new HashSet<>();
   
    private Set<DeliveryInfo> deliveryInfos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public Store regNo(String regNo) {
        this.regNo = regNo;
        return this;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getName() {
        return name;
    }

    public Store name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public StoreSettings getStoreSettings() {
		return storeSettings;
	}

	public void setStoreSettings(StoreSettings storeSettings) {
		this.storeSettings = storeSettings;
	}

	public Store image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Store imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Double getTotalRating() {
        return totalRating;
    }

    public Store totalRating(Double totalRating) {
        this.totalRating = totalRating;
        return this;
    }

    public void setTotalRating(Double totalRating) {
        this.totalRating = totalRating;
    }

    public String getLocation() {
        return location;
    }

    public Store location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationName() {
        return locationName;
    }

    public Store locationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getContactNo() {
        return contactNo;
    }

    public Store contactNo(Long contactNo) {
        this.contactNo = contactNo;
        return this;
    }

    public void setContactNo(Long contactNo) {
        this.contactNo = contactNo;
    }

    public Instant getOpeningTime() {
        return openingTime;
    }

    public Store openingTime(Instant openingTime) {
        this.openingTime = openingTime;
        return this;
    }

    public void setOpeningTime(Instant openingTime) {
        this.openingTime = openingTime;
    }

    public String getEmail() {
        return email;
    }

    public Store email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getClosingTime() {
        return closingTime;
    }

    public Store closingTime(Instant closingTime) {
        this.closingTime = closingTime;
        return this;
    }

    public void setClosingTime(Instant closingTime) {
        this.closingTime = closingTime;
    }

    public String getInfo() {
        return info;
    }

    public Store info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public Store minAmount(Double minAmount) {
        this.minAmount = minAmount;
        return this;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public Instant getMaxDeliveryTime() {
        return maxDeliveryTime;
    }

    public Store maxDeliveryTime(Instant maxDeliveryTime) {
        this.maxDeliveryTime = maxDeliveryTime;
        return this;
    }

    public void setMaxDeliveryTime(Instant maxDeliveryTime) {
        this.maxDeliveryTime = maxDeliveryTime;
    }

    public Propreitor getPropreitor() {
        return propreitor;
    }

    public Store propreitor(Propreitor propreitor) {
        this.propreitor = propreitor;
        return this;
    }

    public void setPropreitor(Propreitor propreitor) {
        this.propreitor = propreitor;
    }

    public StoreAddress getStoreAddress() {
        return storeAddress;
    }

    public Store storeAddress(StoreAddress storeAddress) {
        this.storeAddress = storeAddress;
        return this;
    }

    public void setStoreAddress(StoreAddress storeAddress) {
        this.storeAddress = storeAddress;
    }

    public Set<StoreType> getStoreTypes() {
        return storeTypes;
    }

    public Store storeTypes(Set<StoreType> storeTypes) {
        this.storeTypes = storeTypes;
        return this;
    }

    public Store addStoreType(StoreType storeType) {
        this.storeTypes.add(storeType);
        storeType.setStore(this);
        return this;
    }

    public Store removeStoreType(StoreType storeType) {
        this.storeTypes.remove(storeType);
        storeType.setStore(null);
        return this;
    }

    public void setStoreTypes(Set<StoreType> storeTypes) {
        this.storeTypes = storeTypes;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Store reviews(Set<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public Store addReview(Review review) {
        this.reviews.add(review);
        review.setStore(this);
        return this;
    }

    public Store removeReview(Review review) {
        this.reviews.remove(review);
        review.setStore(null);
        return this;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<UserRating> getUserRatings() {
        return userRatings;
    }

    public Store userRatings(Set<UserRating> userRatings) {
        this.userRatings = userRatings;
        return this;
    }

    public Store addUserRating(UserRating userRating) {
        this.userRatings.add(userRating);
        userRating.setStore(this);
        return this;
    }

    public Store removeUserRating(UserRating userRating) {
        this.userRatings.remove(userRating);
        userRating.setStore(null);
        return this;
    }

    public void setUserRatings(Set<UserRating> userRatings) {
        this.userRatings = userRatings;
    }

    public Set<Banner> getBanners() {
        return banners;
    }

    public Store banners(Set<Banner> banners) {
        this.banners = banners;
        return this;
    }

    public Store addBanner(Banner banner) {
        this.banners.add(banner);
        banner.setStore(this);
        return this;
    }

    public Store removeBanner(Banner banner) {
        this.banners.remove(banner);
        banner.setStore(null);
        return this;
    }

    public void setBanners(Set<Banner> banners) {
        this.banners = banners;
    }

    public Set<DeliveryInfo> getDeliveryInfos() {
        return deliveryInfos;
    }

    public Store deliveryInfos(Set<DeliveryInfo> deliveryInfos) {
        this.deliveryInfos = deliveryInfos;
        return this;
    }

    public Store addDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfos.add(deliveryInfo);
        deliveryInfo.setStore(this);
        return this;
    }

    public Store removeDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfos.remove(deliveryInfo);
        deliveryInfo.setStore(null);
        return this;
    }

    public void setDeliveryInfos(Set<DeliveryInfo> deliveryInfos) {
        this.deliveryInfos = deliveryInfos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

	@Override
	public String toString() {
		return "Store [id=" + id + ", regNo=" + regNo + ", name=" + name + ", image=" + Arrays.toString(image)
				+ ", imageContentType=" + imageContentType + ", totalRating=" + totalRating + ", location=" + location
				+ ", locationName=" + locationName + ", contactNo=" + contactNo + ", openingTime=" + openingTime
				+ ", email=" + email + ", closingTime=" + closingTime + ", info=" + info + ", minAmount=" + minAmount
				+ ", maxDeliveryTime=" + maxDeliveryTime + ", propreitor=" + propreitor + ", storeAddress="
				+ storeAddress + ", storeSettings=" + storeSettings + ", storeTypes=" + storeTypes + ", reviews="
				+ reviews + ", userRatings=" + userRatings + ", banners=" + banners + ", deliveryInfos=" + deliveryInfos
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((banners == null) ? 0 : banners.hashCode());
		result = prime * result + ((closingTime == null) ? 0 : closingTime.hashCode());
		result = prime * result + ((contactNo == null) ? 0 : contactNo.hashCode());
		result = prime * result + ((deliveryInfos == null) ? 0 : deliveryInfos.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Arrays.hashCode(image);
		result = prime * result + ((imageContentType == null) ? 0 : imageContentType.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((locationName == null) ? 0 : locationName.hashCode());
		result = prime * result + ((maxDeliveryTime == null) ? 0 : maxDeliveryTime.hashCode());
		result = prime * result + ((minAmount == null) ? 0 : minAmount.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((openingTime == null) ? 0 : openingTime.hashCode());
		result = prime * result + ((propreitor == null) ? 0 : propreitor.hashCode());
		result = prime * result + ((regNo == null) ? 0 : regNo.hashCode());
		result = prime * result + ((reviews == null) ? 0 : reviews.hashCode());
		result = prime * result + ((storeAddress == null) ? 0 : storeAddress.hashCode());
		result = prime * result + ((storeSettings == null) ? 0 : storeSettings.hashCode());
		result = prime * result + ((storeTypes == null) ? 0 : storeTypes.hashCode());
		result = prime * result + ((totalRating == null) ? 0 : totalRating.hashCode());
		result = prime * result + ((userRatings == null) ? 0 : userRatings.hashCode());
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
		Store other = (Store) obj;
		if (banners == null) {
			if (other.banners != null)
				return false;
		} else if (!banners.equals(other.banners))
			return false;
		if (closingTime == null) {
			if (other.closingTime != null)
				return false;
		} else if (!closingTime.equals(other.closingTime))
			return false;
		if (contactNo == null) {
			if (other.contactNo != null)
				return false;
		} else if (!contactNo.equals(other.contactNo))
			return false;
		if (deliveryInfos == null) {
			if (other.deliveryInfos != null)
				return false;
		} else if (!deliveryInfos.equals(other.deliveryInfos))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
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
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (locationName == null) {
			if (other.locationName != null)
				return false;
		} else if (!locationName.equals(other.locationName))
			return false;
		if (maxDeliveryTime == null) {
			if (other.maxDeliveryTime != null)
				return false;
		} else if (!maxDeliveryTime.equals(other.maxDeliveryTime))
			return false;
		if (minAmount == null) {
			if (other.minAmount != null)
				return false;
		} else if (!minAmount.equals(other.minAmount))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (openingTime == null) {
			if (other.openingTime != null)
				return false;
		} else if (!openingTime.equals(other.openingTime))
			return false;
		if (propreitor == null) {
			if (other.propreitor != null)
				return false;
		} else if (!propreitor.equals(other.propreitor))
			return false;
		if (regNo == null) {
			if (other.regNo != null)
				return false;
		} else if (!regNo.equals(other.regNo))
			return false;
		if (reviews == null) {
			if (other.reviews != null)
				return false;
		} else if (!reviews.equals(other.reviews))
			return false;
		if (storeAddress == null) {
			if (other.storeAddress != null)
				return false;
		} else if (!storeAddress.equals(other.storeAddress))
			return false;
		if (storeSettings == null) {
			if (other.storeSettings != null)
				return false;
		} else if (!storeSettings.equals(other.storeSettings))
			return false;
		if (storeTypes == null) {
			if (other.storeTypes != null)
				return false;
		} else if (!storeTypes.equals(other.storeTypes))
			return false;
		if (totalRating == null) {
			if (other.totalRating != null)
				return false;
		} else if (!totalRating.equals(other.totalRating))
			return false;
		if (userRatings == null) {
			if (other.userRatings != null)
				return false;
		} else if (!userRatings.equals(other.userRatings))
			return false;
		return true;
	}


}
