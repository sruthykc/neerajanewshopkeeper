package com.diviso.graeshoppe.service;

import org.springframework.http.ResponseEntity;
import com.diviso.graeshoppe.client.store.model.StoreDTO;

public interface StoreCommandService {

	public ResponseEntity<StoreDTO> createStore(StoreDTO storeDTO);
	
	public ResponseEntity<StoreDTO> updateStore(StoreDTO storeDTO);
	
	public void deleteStore(Long id);

	
}
