package com.taskmanager.taskmicroservice.repository;

import com.taskmanager.taskmicroservice.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para operaciones de persistencia de tareas.
 * Extiende JpaRepository para operaciones CRUD básicas.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Encuentra todas las tareas ordenadas por fecha de creación descendente.
     *
     * @return Lista de tareas ordenadas
     */
    List<Task> findAllByOrderByCreatedAtDesc();

    /**
     * Encuentra tareas por estado de completitud.
     *
     * @param completed Estado de completitud
     * @return Lista de tareas filtradas
     */
    List<Task> findByCompleted(Boolean completed);
}