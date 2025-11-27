package com.taskmanager.taskmicroservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para transferencia de datos de tareas.
 * Incluye validaciones para garantizar la integridad de los datos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private Long id;

    /**
     * Título de la tarea.
     * Obligatorio y con longitud entre 1 y 100 caracteres.
     */
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 1, max = 100, message = "El título debe tener entre 1 y 100 caracteres")
    private String title;

    /**
     * Descripción de la tarea.
     * Opcional, máximo 500 caracteres.
     */
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String description;

    /**
     * Estado de completitud de la tarea.
     */
    private Boolean completed;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}