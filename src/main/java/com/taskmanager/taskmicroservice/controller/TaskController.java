package com.taskmanager.taskmicroservice.controller;

import com.taskmanager.taskmicroservice.dto.TaskDto;
import com.taskmanager.taskmicroservice.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de tareas.
 *
 * Principios SOLID aplicados:
 * - Single Responsibility Principle (SRP): Solo maneja solicitudes HTTP y delega lógica al servicio
 * - Dependency Inversion Principle (DIP): Depende de la abstracción TaskService
 */
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    /**
     * Crea una nueva tarea.
     * POST /api/tasks
     *
     * @param taskDto Datos de la tarea a crear
     * @return Tarea creada con código 201 (CREATED)
     */
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto) {
        log.info("Solicitud POST recibida para crear tarea");
        TaskDto createdTask = taskService.createTask(taskDto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    /**
     * Obtiene todas las tareas.
     * GET /api/tasks
     *
     * @return Lista de todas las tareas con código 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        log.info("Solicitud GET recibida para obtener todas las tareas");
        List<TaskDto> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Obtiene una tarea por su ID.
     * GET /api/tasks/{id}
     *
     * @param id Identificador de la tarea
     * @return Tarea encontrada con código 200 (OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        log.info("Solicitud GET recibida para tarea con ID: {}", id);
        TaskDto task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    /**
     * Actualiza una tarea existente.
     * PUT /api/tasks/{id}
     *
     * @param id Identificador de la tarea
     * @param taskDto Nuevos datos de la tarea
     * @return Tarea actualizada con código 200 (OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskDto taskDto) {
        log.info("Solicitud PUT recibida para actualizar tarea con ID: {}", id);
        TaskDto updatedTask = taskService.updateTask(id, taskDto);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Elimina una tarea.
     * DELETE /api/tasks/{id}
     *
     * @param id Identificador de la tarea a eliminar
     * @return Respuesta vacía con código 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        log.info("Solicitud DELETE recibida para tarea con ID: {}", id);
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}