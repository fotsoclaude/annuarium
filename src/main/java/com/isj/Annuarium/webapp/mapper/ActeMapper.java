package com.isj.Annuarium.webapp.mapper;


import com.isj.Annuarium.webapp.model.dto.ActeDto;
import com.isj.Annuarium.webapp.model.entities.Acte;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "Spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ActeMapper {
    Acte toEntity(ActeDto acteDto);
    ActeDto toDto(Acte acte);

    void copy(ActeDto acteDto, @MappingTarget Acte acte);

}
