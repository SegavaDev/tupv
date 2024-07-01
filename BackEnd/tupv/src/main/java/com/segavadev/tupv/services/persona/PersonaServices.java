package com.segavadev.tupv.services.persona;

import com.segavadev.tupv.repository.models.dtos.persona.PersonaDTOcrear;
import com.segavadev.tupv.utils.errors.DataRepetida;

public interface PersonaServices {

    void registrarPersona(PersonaDTOcrear personaDTOcrear) throws DataRepetida;
    
}
