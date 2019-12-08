package com.diviso.graeshoppe.service;

import org.springframework.http.ResponseEntity;
import com.diviso.graeshoppe.client.product.model.AddressDTO;
import com.diviso.graeshoppe.client.product.model.AuxilaryLineItemDTO;
import com.diviso.graeshoppe.client.product.model.CategoryDTO;
import com.diviso.graeshoppe.client.product.model.ComboLineItemDTO;
import com.diviso.graeshoppe.client.product.model.DiscountDTO;
import com.diviso.graeshoppe.client.product.model.EntryLineItemDTO;
import com.diviso.graeshoppe.client.product.model.LocationDTO;
import com.diviso.graeshoppe.client.product.model.ProductDTO;
import com.diviso.graeshoppe.client.product.model.ReasonDTO;
import com.diviso.graeshoppe.client.product.model.StockCurrentDTO;
import com.diviso.graeshoppe.client.product.model.StockEntryDTO;
import com.diviso.graeshoppe.client.product.model.UOMDTO;

public interface ProductCommandService {
	
	public ResponseEntity<ProductDTO> createProduct(ProductDTO productDTO);

	public ResponseEntity<ProductDTO> updateProduct(ProductDTO productDTO);

    public void deleteProduct(Long id);
    
    public ResponseEntity<CategoryDTO> createProductCategory(CategoryDTO categoryDTO);
    
    public ResponseEntity<CategoryDTO> updateCategory(CategoryDTO categoryDTO);
    
    public void deleteCategory(Long id);
    
    public ResponseEntity<UOMDTO> createUOM(UOMDTO uomDTO);

    public ResponseEntity<UOMDTO> updateUOM(UOMDTO uomDTO);
    
    public void deleteUOM(Long id);
    
    public ResponseEntity<StockEntryDTO> createStockEntry(StockEntryDTO stockEntryDTO);
    
    public ResponseEntity<StockEntryDTO> updateStockEntry(StockEntryDTO stockEntryDTO);
    
    public ResponseEntity<Void> deleteStockEntry(Long id);
    
    public ResponseEntity<StockCurrentDTO> createStockCurrent(StockCurrentDTO stockCurrent);
    
    public ResponseEntity<StockCurrentDTO> updateStockCurrent(StockCurrentDTO StockCurrent);
    
    public ResponseEntity<AuxilaryLineItemDTO> createAuxilaryLineItem(AuxilaryLineItemDTO auxilaryLineItemDTO);
    
    public ResponseEntity<AuxilaryLineItemDTO> updateAuxilaryLineItem(AuxilaryLineItemDTO auxilaryLineItemDTO);
    
    public ResponseEntity<Void> deleteAuxilaryLineIteam(Long id);
    
    public ResponseEntity<ComboLineItemDTO> createComboLineItem(ComboLineItemDTO comboLineItemDTO);
    
    public ResponseEntity<ComboLineItemDTO> updateComboLineItem(ComboLineItemDTO comboLineItemDTO);
    
    public ResponseEntity<Void> deleteComboLineItem(Long id);
    
    public ResponseEntity<DiscountDTO> createDiscount(DiscountDTO discountDTO);
    
    public ResponseEntity<DiscountDTO> updateDiscount(DiscountDTO discountDTO);
    
    public ResponseEntity<Void> deleteDiscount(Long id);
    
    public ResponseEntity<EntryLineItemDTO> createEntryLineItem(EntryLineItemDTO entrylineitemDTO);
    
    public ResponseEntity<EntryLineItemDTO> updateEntryLineItem(EntryLineItemDTO entrylineitemDTO);
 
    public ResponseEntity<Void> deleteEntryLineItem(Long id);
    
    public ResponseEntity<ReasonDTO> createReason(ReasonDTO reasonDTO);
    
    public ResponseEntity<ReasonDTO> updateReason(ReasonDTO reasonDTO);
    
    public ResponseEntity<Void> deleteReason(Long id);
    
    public ResponseEntity<LocationDTO> createLocation(LocationDTO locationDTO);
    
    public ResponseEntity<LocationDTO> updateLocation(LocationDTO locationDTO);
    
    public ResponseEntity<Void> deleteLocation(Long id);
    
    public ResponseEntity<AddressDTO> createProductAddress(AddressDTO addressDTO);
    
    public ResponseEntity<AddressDTO> updateProductAddress(AddressDTO addressDTO);
    
    public ResponseEntity<Void> deleteProductAddress(Long id);
    
    
}


