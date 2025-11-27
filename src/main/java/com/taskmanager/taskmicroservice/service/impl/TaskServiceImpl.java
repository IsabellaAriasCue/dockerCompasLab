package com.taskmanager.taskmicroservice.service.impl;

import com.taskmanager.taskmicroservice.dto.TaskDto;
import com.taskmanager.taskmicroservice.entity.Task;
import com.taskmanager.taskmicroservice.exception.ResourceNotFoundException;
import com.taskmanager.taskmicroservice.repository.TaskRepository;
import com.taskmanager.taskmicroservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de gestión de tareas.
 *
 * Principios SOLID aplicados:
 * - Single Responsibility Principle (SRP): Solo gestiona lógica de negocio de tareas
 * - Open/Closed Principle (OCP): Abierto a extensión mediante herencia
 * - Liskov Substitution Principle (LSP): Puede ser sustituido por cualquier implementación de TaskService
 * - Dependency Inversion Principle (DIP): Depende de abstracciones (TaskRepository, TaskService)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public TaskDto createTask(TaskDto taskDto) {
        log.info("Creando nueva tarea: {}", taskDto.getTitle());

        Task task = mapToEntity(taskDto);
        Task savedTask = taskRepository.save(task);

        log.info("Tarea creada exitosamente con ID: {}", savedTask.getId());
        return mapToDto(savedTask);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDto> getAllTasks() {
        log.info("Obteniendo todas las tareas");

        return taskRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDto getTaskById(Long id) {
        log.info("Buscando tarea con ID: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con ID: " + id));

        return mapToDto(task);
    }

    @Override
    @Transactional
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        log.info("Actualizando tarea con ID: {}", id);

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con ID: " + id));

        existingTask.setTitle(taskDto.getTitle());
        existingTask.setDescription(taskDto.getDescription());
        existingTask.setCompleted(Boolean.TRUE.equals(taskDto.getCompleted()));

        Task updatedTask = taskRepository.save(existingTask);

        log.info("Tarea actualizada exitosamente: {}", id);
        return mapToDto(updatedTask);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        log.info("Eliminando tarea con ID: {}", id);

        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tarea no encontrada con ID: " + id);
        }

        taskRepository.deleteById(id);
        log.info("Tarea eliminada exitosamente: {}", id);
    }

    /**
     * Convierte una entidad Task a TaskDto.
     * Método privado que encapsula la lógica de mapeo (SRP).
     *
     * @param task Entidad a convertir
     * @return DTO mapeado
     */
    private TaskDto mapToDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.getCompleted());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        return dto;
    }

    /**
     * Convierte un TaskDto a entidad Task.
     * Método privado que encapsula la lógica de mapeo (SRP).
     *
     * @param dto DTO a convertir
     * @return Entidad mapeada
     */
    private Task mapToEntity(TaskDto dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(Boolean.TRUE.equals(dto.getCompleted()));
        return task;
    }
}