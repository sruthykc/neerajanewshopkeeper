/*
* Copyright 2002-2016 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.diviso.graeshoppe.client.store.model;

import java.util.List;

/**
 * TODO Provide a detailed description here
 * 
 * @author MayaSanjeev mayabytatech, maya.k.k@lxisoft.com
 */
public class StoreBundleDTO {

	StoreDTO store;
	StoreAddressDTO storeAddress;
	StoreSettingsDTO storeSettings;
	PreOrderSettingsDTO preOrderSettings;

	List<DeliveryInfoDTO> deliveryInfos;
	List<TypeDTO> types;
	List<StoreTypeDTO> storeType;
	List<BannerDTO> banners;
	

	public StoreAddressDTO getStoreAddress() {
		return storeAddress;
	}

	public void setStoreAddress(StoreAddressDTO storeAddress) {
		this.storeAddress = storeAddress;
	}

	public StoreSettingsDTO getStoreSettings() {
		return storeSettings;
	}

	public void setPreOrderSettings(PreOrderSettingsDTO preOrderSettings) {
		this.preOrderSettings = preOrderSettings;
	}
	
	public PreOrderSettingsDTO getPreOrderSettings() {
		return preOrderSettings;
	}

	public void setStoreSettings(StoreSettingsDTO storeSettings) {
		this.storeSettings = storeSettings;
	}

	public StoreDTO getStore() {
		return store;
	}

	public void setStore(StoreDTO store) {
		this.store = store;
	}

	public List<DeliveryInfoDTO> getDeliveryInfos() {
		return deliveryInfos;
	}

	public void setDeliveryInfos(List<DeliveryInfoDTO> deliveryInfos) {
		this.deliveryInfos = deliveryInfos;
	}

	public List<TypeDTO> getTypes() {
		return types;
	}

	public void setTypes(List<TypeDTO> types) {
		this.types = types;
	}

	public List<StoreTypeDTO> getStoreType() {
		return storeType;
	}

	public void setStoreType(List<StoreTypeDTO> storeType) {
		this.storeType = storeType;
	}


	public List<BannerDTO> getBanners() {
		return banners;
	}

	public void setBanners(List<BannerDTO> banners) {
		this.banners = banners;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((banners == null) ? 0 : banners.hashCode());
		result = prime * result + ((deliveryInfos == null) ? 0 : deliveryInfos.hashCode());
		result = prime * result + ((preOrderSettings == null) ? 0 : preOrderSettings.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
		result = prime * result + ((storeAddress == null) ? 0 : storeAddress.hashCode());
		result = prime * result + ((storeSettings == null) ? 0 : storeSettings.hashCode());
		result = prime * result + ((storeType == null) ? 0 : storeType.hashCode());
		result = prime * result + ((types == null) ? 0 : types.hashCode());
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
		StoreBundleDTO other = (StoreBundleDTO) obj;
		if (banners == null) {
			if (other.banners != null)
				return false;
		} else if (!banners.equals(other.banners))
			return false;
		if (deliveryInfos == null) {
			if (other.deliveryInfos != null)
				return false;
		} else if (!deliveryInfos.equals(other.deliveryInfos))
			return false;
		if (preOrderSettings == null) {
			if (other.preOrderSettings != null)
				return false;
		} else if (!preOrderSettings.equals(other.preOrderSettings))
			return false;
		if (store == null) {
			if (other.store != null)
				return false;
		} else if (!store.equals(other.store))
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
		if (storeType == null) {
			if (other.storeType != null)
				return false;
		} else if (!storeType.equals(other.storeType))
			return false;
		if (types == null) {
			if (other.types != null)
				return false;
		} else if (!types.equals(other.types))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StoreBundleDTO [store=" + store + ", storeAddress=" + storeAddress + ", storeSettings=" + storeSettings
				+ ", preOrderSettings=" + preOrderSettings + ", deliveryInfos=" + deliveryInfos + ", types=" + types
				+ ", storeType=" + storeType + ", banners=" + banners + "]";
	}



}
