package com.diviso.graeshoppe.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.diviso.graeshoppe.client.store.model.*;


public interface StoreQueryService {
	

	
	public Store findStoreByRegNo(String regNo);
	
	
	/**
	 * @param regNo
	 * @return
	 */
	public Page<Banner> findBannersByStoreId(String regNo,Pageable pageable );

	
	public StoreDTO findStoreDTOByRegNo( String regNo);
	
	public ResponseEntity<StoreBundleDTO> getStoreBundle(String regNo);
	public /*ResponseEntity<BannerDTO> */BannerDTO findBanner(Long id) ;
	

}
