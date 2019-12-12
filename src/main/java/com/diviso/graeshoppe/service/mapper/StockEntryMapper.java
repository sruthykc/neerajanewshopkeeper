package com.diviso.graeshoppe.service.mapper;

import com.diviso.graeshoppe.client.product.model.StockEntry;
import com.diviso.graeshoppe.client.product.model.StockEntryDTO;
import org.mapstruct.*;
@Mapper(componentModel = "spring", uses = {ReasonMapper.class, LocationMapper.class})
public interface StockEntryMapper extends EntityMapper<StockEntryDTO, StockEntry> {

    @Override
	@Mapping(source = "reason.id", target = "reasonId")
    @Mapping(source = "location.id", target = "locationId")
    StockEntryDTO toDto(StockEntry stockEntry);

    @Override
	@Mapping(target = "entryLineItems", ignore = true)
    @Mapping(source = "reasonId", target = "reason")
    @Mapping(source = "locationId", target = "location")
    StockEntry toEntity(StockEntryDTO stockEntryDTO);

    default StockEntry fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockEntry stockEntry = new StockEntry();
        stockEntry.setId(id);
        return stockEntry;
    }
}