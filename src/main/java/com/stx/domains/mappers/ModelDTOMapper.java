package com.stx.domains.mappers;

import com.stx.domains.models.AbstractEntity;

public interface ModelDTOMapper<F extends AbstractEntity, T> {
    T toDTO(F t);
    F toModel(T f);
}
