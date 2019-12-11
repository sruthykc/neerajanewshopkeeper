package com.diviso.graeshoppe.service.mapper;

import org.mapstruct.*;
import com.diviso.graeshoppe.client.store.model.*;
/**
 * Mapper for the entity PreOrderSettings and its DTO PreOrderSettingsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PreOrderSettingsMapper extends EntityMapper<PreOrderSettingsDTO, PreOrderSettings> {

    PreOrderSettingsDTO toDto(PreOrderSettings preOrderSettings);


    PreOrderSettings toEntity(PreOrderSettingsDTO preOrderSettingsDTO);

    default PreOrderSettings fromId(Long id) {
        if (id == null) {
            return null;
        }
        PreOrderSettings preOrderSettings = new PreOrderSettings();
        preOrderSettings.setId(id);
        return preOrderSettings;
    }
}
