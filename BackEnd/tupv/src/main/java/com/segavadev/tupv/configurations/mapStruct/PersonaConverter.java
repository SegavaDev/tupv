package com.segavadev.tupv.configurations.mapStruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.segavadev.tupv.repository.models.dtos.persona.PersonaDTO;
import com.segavadev.tupv.repository.models.dtos.persona.PersonaDTOcrear;
import com.segavadev.tupv.repository.models.dtos.persona.PersonaDTOmostrar;
import com.segavadev.tupv.repository.models.entities.Persona;

/** 
 ** Esta clase va a convertir de entity a DTO y viceversa 
 */

@Mapper
public interface PersonaConverter {

    PersonaConverter personConverter = Mappers.getMapper(PersonaConverter.class);

    // Dto a entity
    Persona toPersona(PersonaDTO personaDTO);
    // DtoCrear a entity
    Persona toPersona(PersonaDTOcrear personaDTOcrear);
    // Entity a dto
    PersonaDTO toDto(Persona persona);
    // Entity a dtoMOstrar
    PersonaDTOmostrar toDtomostrar(Persona persona);
    
}
