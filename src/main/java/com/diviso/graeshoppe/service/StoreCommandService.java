package com.diviso.graeshoppe.service;

import org.springframework.http.ResponseEntity;

import com.diviso.graeshoppe.client.store.model.BannerDTO;
import com.diviso.graeshoppe.client.store.model.DeliveryInfoDTO;
import com.diviso.graeshoppe.client.store.model.PreOrderSettingsDTO;
import com.diviso.graeshoppe.client.store.model.ReplyDTO;
import com.diviso.graeshoppe.client.store.model.ReviewDTO;
import com.diviso.graeshoppe.client.store.model.StoreDTO;
import com.diviso.graeshoppe.client.store.model.TypeDTO;
import com.diviso.graeshoppe.client.store.model.UserRatingDTO;

public interface StoreCommandService {

	public ResponseEntity<StoreDTO> createStore(StoreDTO storeDTO);
	
	public ResponseEntity<StoreDTO> updateStore(StoreDTO storeDTO);
	
	public void deleteStore(Long id);
	
	public ResponseEntity<ReplyDTO> createReply(ReplyDTO replyDTO);

	public ResponseEntity<ReplyDTO> updateReply(ReplyDTO replyDTO);

	public ResponseEntity<Void> deleteReply(Long id);
	
	public ResponseEntity<PreOrderSettingsDTO> createPreOrderSettings(PreOrderSettingsDTO preOrderSettingsDTO);
	
	public ResponseEntity<PreOrderSettingsDTO> updatePreOrderSettings(PreOrderSettingsDTO preOrderSettingsDTO);
	
	public ResponseEntity<Void> deletePreOrderSettings(Long id);
	
	
	
	
	
	public ResponseEntity<DeliveryInfoDTO> createDeliveryInfo(DeliveryInfoDTO deliveryInfoDTO);
	
	public ResponseEntity<DeliveryInfoDTO> updateDeliveryInfo(DeliveryInfoDTO deliveryInfoDTO);
	
	public void deleteDeliveryInfo(Long id);
	
	public ResponseEntity<TypeDTO> createType(TypeDTO typeDTO);
	
	public ResponseEntity<TypeDTO> updateType(TypeDTO typeDTO);
	
	public ResponseEntity<Void> deleteType(Long id);
	
	public ResponseEntity<BannerDTO> createBanner(BannerDTO bannerDTO);
	
	public ResponseEntity<BannerDTO> updateBanner(BannerDTO bannerDTO);
	
	public ResponseEntity<Void> deleteBanner(Long id);
	
	public ResponseEntity<Void> deleteStoreType(Long id);
}
