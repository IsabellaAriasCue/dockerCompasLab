package com.taskmanager.taskmicroservice.service;

import com.taskmanager.taskmicroservice.dto.TaskDto;

import java.util.List;

/**
 * Interfaz de servicio para la gestión de tareas.
 * Define el contrato para las operaciones de negocio.
 *
 * Principios SOLID aplicados:
 * - Interface Segregation Principle (ISP): Interfaz específica para tareas
 * - Dependency Inversion Principle (DIP): Los clientes dependen de abstracciones
 */
public interface TaskService {

    /**
     * Crea una nueva tarea.
     *
     * @param taskDto Datos de la tarea a crear
     * @return Tarea creada con su ID generado
     */
    TaskDto createTask(TaskDto taskDto);

    /**
     * Obtiene todas las tareas ordenadas por fecha de creación.
     *
     * @return Lista de todas las tareas
     */
    List<TaskDto> getAllTasks();

    /**
     * Busca una tarea por su identificador.
     *
     * @param id Identificador de la tarea
     * @return Tarea encontrada
     */
    TaskDto getTaskById(Long id);

    /**
     * Actualiza una tarea existente.
     *
     * @param id Identificador de la tarea a actualizar
     * @param taskDto Nuevos datos de la tarea
     * @return Tarea actualizada
     */
    TaskDto updateTask(Long id, TaskDto taskDto);

    /**
     * Elimina una tarea por su identificador.
     *
     * @param id Identificador de la tarea a eliminar
     */
    void deleteTask(Long id);
}