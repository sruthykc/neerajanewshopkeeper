package com.diviso.graeshoppe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.client.store.api.StoreResourceApi;
import com.diviso.graeshoppe.client.store.model.StoreDTO;
import com.diviso.graeshoppe.service.StoreCommandService;

@Service
public class StoreCommandServiceImpl implements StoreCommandService {

	@Autowired
	private StoreResourceApi storeResourceApi;

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

}
