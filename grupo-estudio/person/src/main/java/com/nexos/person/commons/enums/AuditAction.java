package com.nexos.person.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum Audit action.
 */
@Getter
@AllArgsConstructor
public enum AuditAction {

    /**
     * Insert audit action.
     */
    INSERT('I', "INSERTAR"),
    /**
     * Update audit action.
     */
    UPDATE('U', "ACTUALIZAR"),
    /**
     * Delete audit action.
     */
    DELETE('D', "ELIMINAR"),
    /**
     * Select audit action.
     */
    SELECT('S', "BUSCAR");

    private final Character action;
    private final String descripcion;


}
