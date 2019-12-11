package com.diviso.graeshoppe.service.mapper;

import org.mapstruct.*;
import com.diviso.graeshoppe.client.store.model.*;
/**
 * Mapper for the entity Propreitor and its DTO PropreitorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PropreitorMapper extends EntityMapper<PropreitorDTO, Propreitor> {



    default Propreitor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Propreitor propreitor = new Propreitor();
        propreitor.setId(id);
        return propreitor;
    }
}
