package com.diviso.graeshoppe.service.mapper;

import org.mapstruct.*;
import com.diviso.graeshoppe.client.store.model.*;
/**
 * Mapper for the entity StoreSettings and its DTO StoreSettingsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StoreSettingsMapper extends EntityMapper<StoreSettingsDTO, StoreSettings> {



    default StoreSettings fromId(Long id) {
        if (id == null) {
            return null;
        }
        StoreSettings storeSettings = new StoreSettings();
        storeSettings.setId(id);
        return storeSettings;
    }
}