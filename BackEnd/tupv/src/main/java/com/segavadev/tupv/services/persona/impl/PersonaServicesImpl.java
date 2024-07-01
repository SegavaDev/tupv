package com.segavadev.tupv.services.persona.impl;

import org.springframework.stereotype.Service;

import com.segavadev.tupv.configurations.mapStruct.PersonaConverter;
import com.segavadev.tupv.repository.interfaces.PersonaRepository;
import com.segavadev.tupv.repository.models.dtos.persona.PersonaDTOcrear;
import com.segavadev.tupv.repository.models.entities.Persona;
import com.segavadev.tupv.services.persona.PersonaServices;
import com.segavadev.tupv.utils.errors.DataRepetida;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PersonaServicesImpl implements PersonaServices {

    // * Repository */
    private final PersonaRepository personaRepository;

    @Override
    public void registrarPersona(PersonaDTOcrear personaDTOcrear) throws DataRepetida {
        Persona persona = personaRepository.findByDni(
                personaDTOcrear.getDni()).orElseThrow(
                        () -> new DataRepetida("El usuario a crear ya existe."));

        personaRepository.save(persona);
    }

}
