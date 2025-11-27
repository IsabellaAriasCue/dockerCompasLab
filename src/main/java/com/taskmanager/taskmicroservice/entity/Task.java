package com.taskmanager.taskmicroservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad que representa una tarea en el sistema.
 * Mapea la tabla 'tasks' en la base de datos.
 */
@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    /**
     * Identificador único de la tarea.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Título descriptivo de la tarea.
     * No puede ser nulo y tiene un máximo de 100 caracteres.
     */
    @Column(nullable = false, length = 100)
    private String title;

    /**
     * Descripción detallada de la tarea.
     * Campo opcional con máximo 500 caracteres.
     */
    @Column(length = 500)
    private String description;

    /**
     * Estado de completitud de la tarea.
     * Por defecto es false (no completada).
     */
    @Column(nullable = false)
    private Boolean completed = false;

    /**
     * Fecha y hora de creación de la tarea.
     * Se establece automáticamente al persistir.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Fecha y hora de la última actualización.
     * Se actualiza automáticamente en cada modificación.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Establece la fecha de creación antes de persistir.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * Actualiza la fecha de modificación antes de cada update.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}