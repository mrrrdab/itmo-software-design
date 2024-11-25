package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.api.task.*
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.task.*
import ru.quipy.projections.task.ITaskMetaViewRepository
import ru.quipy.projections.task.TaskMetaViewEntity
import java.util.*

@RestController
@RequestMapping("/tasks")
class TaskController(
  val taskRepository: ITaskMetaViewRepository,
  val taskEsService: EventSourcingService<UUID, TaskAggregate, TaskAggregateState>
) {

  @PostMapping()
  fun createTask(
    @RequestParam title: String,
    @RequestParam description: String,
    @RequestParam projectId: UUID
  ): TaskCreatedEvent {
    return taskEsService.create {
      it.createTask(UUID.randomUUID(), title, description, projectId)
    }
  }

  @GetMapping("/{taskId}")
  fun getTask(@PathVariable taskId: UUID): TaskAggregateState? {
    return taskEsService.getState(taskId)
  }

  @PatchMapping("/{taskId}/title")
  fun updateTaskTitle(
    @PathVariable taskId: UUID,
    @RequestParam title: String
  ): TaskTitleUpdatedEvent {
    return taskEsService.update(taskId) {
      it.updateTitle(title)
    }
  }

  @PatchMapping("/{taskId}/description")
  fun updateTaskDescription(
    @PathVariable taskId: UUID,
    @RequestParam description: String
  ): TaskDescriptionUpdatedEvent {
    return taskEsService.update(taskId) {
      it.updateDescription(description)
    }
  }

  @PatchMapping("/{taskId}/executor")
  fun assignExecutor(
    @PathVariable taskId: UUID,
    @RequestParam executorId: UUID
  ): TaskExecutorAssignedEvent {
    return taskEsService.update(taskId) {
      it.assignExecutor(executorId)
    }
  }

  @DeleteMapping("/{taskId}/executor")
  fun unassignExecutor(
    @PathVariable taskId: UUID
  ): TaskExecutorUnassignedEvent {
    return taskEsService.update(taskId) {
      it.unassignExecutor()
    }
  }

  @PatchMapping("/{taskId}/status")
  fun updateTaskStatus(
    @PathVariable taskId: UUID,
    @RequestParam statusId: UUID
  ): TaskStatusUpdatedEvent {
    return taskEsService.update(taskId) {
      it.updateStatus(statusId)
    }
  }

  @GetMapping("taskView/{taskId}")
  fun findById(@PathVariable taskId: UUID): Optional<TaskMetaViewEntity> {
    return taskRepository.findById(taskId)
  }
}
