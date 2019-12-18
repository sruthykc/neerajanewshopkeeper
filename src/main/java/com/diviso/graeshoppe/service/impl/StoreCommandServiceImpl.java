package com.diviso.graeshoppe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.client.store.api.BannerResourceApi;
import com.diviso.graeshoppe.client.store.api.DeliveryInfoResourceApi;
import com.diviso.graeshoppe.client.store.api.PreOrderSettingsResourceApi;
import com.diviso.graeshoppe.client.store.api.ReplyResourceApi;
import com.diviso.graeshoppe.client.store.api.ReviewResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreTypeResourceApi;
import com.diviso.graeshoppe.client.store.api.TypeResourceApi;
import com.diviso.graeshoppe.client.store.api.UserRatingResourceApi;
import com.diviso.graeshoppe.client.store.model.BannerDTO;
import com.diviso.graeshoppe.client.store.model.DeliveryInfoDTO;
import com.diviso.graeshoppe.client.store.model.PreOrderSettingsDTO;
import com.diviso.graeshoppe.client.store.model.ReplyDTO;
import com.diviso.graeshoppe.client.store.model.ReviewDTO;
import com.diviso.graeshoppe.client.store.model.StoreDTO;
import com.diviso.graeshoppe.client.store.model.TypeDTO;
import com.diviso.graeshoppe.client.store.model.UserRatingDTO;
import com.diviso.graeshoppe.service.StoreCommandService;

@Service
public class StoreCommandServiceImpl implements StoreCommandService {

	@Autowired
	private StoreResourceApi storeResourceApi;
	
	@Autowired
	private ReplyResourceApi replyResourceApi;
	
	@Autowired
	private PreOrderSettingsResourceApi preOrderSettingsResourceApi;


	
	@Autowired
	DeliveryInfoResourceApi deliveryInfoResourceApi;
	
	@Autowired
	TypeResourceApi typeResourceApi;
	
	@Autowired
	BannerResourceApi bannerResourceApi;	
	
	@Autowired
	private StoreTypeResourceApi storeTypeResourceApi;

	@Override
	public ResponseEntity<StoreDTO> createStore(StoreDTO storeDTO) {
		
		return storeResourceApi.createStoreUsingPOST(storeDTO);
	}

	@Override
	public ResponseEntity<StoreDTO> updateStore(StoreDTO storeDTO) {
		
		return storeResourceApi.updateStoreUsingPUT(storeDTO);
	}

	@Override
	public void deleteStore(Long id) {
		
	  storeResourceApi.deleteStoreUsingDELETE(id);
	}

	@Override
	public ResponseEntity<ReplyDTO> createReply(ReplyDTO replyDTO) {
		
		return replyResourceApi.createReplyUsingPOST(replyDTO);
	}

	@Override
	public ResponseEntity<ReplyDTO> updateReply(ReplyDTO replyDTO) {
		
		return replyResourceApi.updateReplyUsingPUT(replyDTO);
	}

	@Override
	public ResponseEntity<Void> deleteReply(Long id) {
		
		return replyResourceApi.deleteReplyUsingDELETE(id);
	}

	@Override
	public ResponseEntity<PreOrderSettingsDTO> createPreOrderSettings(PreOrderSettingsDTO preOrderSettingsDTO) {
		
		return preOrderSettingsResourceApi.createPreOrderSettingsUsingPOST(preOrderSettingsDTO);
	}

	@Override
	public ResponseEntity<PreOrderSettingsDTO> updatePreOrderSettings(PreOrderSettingsDTO preOrderSettingsDTO) {
		
		return preOrderSettingsResourceApi.updatePreOrderSettingsUsingPUT(preOrderSettingsDTO);
	}

	@Override
	public ResponseEntity<Void> deletePreOrderSettings(Long id) {
		
		return preOrderSettingsResourceApi.deletePreOrderSettingsUsingDELETE(id);
	}

	

	

	@Override
	public ResponseEntity<DeliveryInfoDTO> createDeliveryInfo(DeliveryInfoDTO deliveryInfoDTO) {
		
		return deliveryInfoResourceApi.createDeliveryInfoUsingPOST(deliveryInfoDTO);
	}

	@Override
	public ResponseEntity<DeliveryInfoDTO> updateDeliveryInfo(DeliveryInfoDTO deliveryInfoDTO) {
		
		return deliveryInfoResourceApi.updateDeliveryInfoUsingPUT(deliveryInfoDTO);
	}

	@Override
	public void deleteDeliveryInfo(Long id) {
		
		deliveryInfoResourceApi.deleteDeliveryInfoUsingDELETE(id);
	}

	@Override
	public ResponseEntity<TypeDTO> createType(TypeDTO typeDTO) {
		
		return typeResourceApi.createTypeUsingPOST(typeDTO);
	}

	@Override
	public ResponseEntity<TypeDTO> updateType(TypeDTO typeDTO) {
		
		return typeResourceApi.updateTypeUsingPUT(typeDTO);
	}

	@Override
	public ResponseEntity<Void> deleteType(Long id) {
		
		return typeResourceApi.deleteTypeUsingDELETE(id);
	}

	@Override
	public ResponseEntity<BannerDTO> createBanner(BannerDTO bannerDTO) {
		
		return bannerResourceApi.createBannerUsingPOST(bannerDTO);
	}

	@Override
	public ResponseEntity<BannerDTO> updateBanner(BannerDTO bannerDTO) {
		
		return bannerResourceApi.updateBannerUsingPUT(bannerDTO);
	}

	@Override
	public ResponseEntity<Void> deleteBanner(Long id) {
		
		return bannerResourceApi.deleteBannerUsingDELETE(id);
	}

	@Override
	public ResponseEntity<Void> deleteStoreType(Long id) {
		
		return storeTypeResourceApi.deleteStoreTypeUsingDELETE(id);
	}

}
