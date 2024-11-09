package ru.quipy.logic.task

import ru.quipy.api.task.*
import java.util.*

fun TaskAggregateState.createTask(
  taskId: UUID,
  title: String,
  description: String,
  projectId: UUID
): TaskCreatedEvent {
  if (title.isBlank()) {
    throw IllegalArgumentException("Task title cannot be empty or consist only of spaces")
  }

  if (description.isBlank()) {
    throw IllegalArgumentException("Task description cannot be empty or consist only of spaces")
  }

  return TaskCreatedEvent(
    taskId = taskId, 
    title = title, 
    description = description,
    projectId = projectId 
  )
}

fun TaskAggregateState.updateTitle(title: String): TaskTitleUpdatedEvent {
  if (title.isBlank()) {
    throw IllegalArgumentException("Task title cannot be empty or consist only of spaces")
  }

  if (this.title == title) {
    throw IllegalArgumentException("New title cannot be the same as the current title")
  }

  return TaskTitleUpdatedEvent(taskId = this.getId(), title = title)
}

fun TaskAggregateState.updateDescription(description: String): TaskDescriptionUpdatedEvent {
  if (description.isBlank()) {
    throw IllegalArgumentException("Task description cannot be empty or consist only of spaces")
  }

  if (this.description == description) {
    throw IllegalArgumentException("New description cannot be the same as the current description")
  }

  return TaskDescriptionUpdatedEvent(taskId = this.getId(), description = description)
}

fun TaskAggregateState.updateStatus(statusId: UUID): TaskStatusUpdatedEvent {
  if (statusId == this.statusId) {
    throw IllegalArgumentException("New status cannot be the same as the current status")
  }

  return TaskStatusUpdatedEvent(taskId = this.getId(), statusId = statusId)
}

fun TaskAggregateState.assignExecutor(executorId: UUID): TaskExecutorAssignedEvent {
  if (this.executorId == executorId) {
    throw IllegalArgumentException("User already assigned: $executorId")
  }

  return TaskExecutorAssignedEvent(taskId = this.getId(), executorId = executorId)
}

fun TaskAggregateState.unassignExecutor(): TaskExecutorUnassignedEvent {
  if (this.executorId == null) {
    throw IllegalArgumentException("Executor has not been assigned yet")
  }

  return TaskExecutorUnassignedEvent(taskId = this.getId())
}

