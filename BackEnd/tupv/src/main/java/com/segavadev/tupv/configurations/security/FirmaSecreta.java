package com.segavadev.tupv.configurations.security;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * La clase FirmaSecreta genera y almacena una firma secreta de manera segura utilizando un enfoque singleton.
 */
public class FirmaSecreta {

    // Instancia única de FirmaSecreta
    private static volatile FirmaSecreta key;

    // Longitud en bytes de la firma secreta
    private final int longitud = 32;

    // La firma secreta codificada en Base64
    private final String firma;

    private FirmaSecreta() {
        // Array de bytes para almacenar la firma secreta
        byte[] bites = new byte[longitud];

        // Instancia de SecureRandom para generar números aleatorios seguros
        SecureRandom secureRandom = new SecureRandom();

        // Rellena el array de bytes con valores aleatorios
        secureRandom.nextBytes(bites);

        // Codifica el array de bytes en una cadena Base64 y la asigna a la variable firma
        this.firma = Base64.getEncoder().encodeToString(bites);
    }

    public static String getFirma() {
        // Verifica si la instancia única de FirmaSecreta no ha sido creada
        if (key == null) {
            // Sincroniza el bloque para asegurar que solo un hilo puede ejecutar esta sección a la vez
            synchronized (FirmaSecreta.class) {
                // Verifica nuevamente si la instancia única de FirmaSecreta no ha sido creada
                if (key == null) {
                    // Crea una nueva instancia de FirmaSecreta y la asigna a la variable key
                    key = new FirmaSecreta();
                }
            }
        }

        // Devuelve la firma secreta
        return key.firma;
    }
}