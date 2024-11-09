package ru.quipy.api.task

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val TASK_CREATED_EVENT = "TASK_CREATED_EVENT"
const val TASK_TITLE_UPDATED_EVENT = "TASK_TITLE_UPDATED_EVENT"
const val TASK_DESCRIPTION_UPDATED_EVENT = "TASK_DESCRIPTION_UPDATED_EVENT"
const val TASK_EXECUTOR_ASSIGNED_EVENT = "TASK_EXECUTOR_ASSIGNED_EVENT"
const val TASK_EXECUTOR_UNASSIGNED_EVENT = "TASK_EXECUTOR_UNASSIGNED_EVENT"
const val TASK_STATUS_UPDATED_EVENT = "TASK_STATUS_UPDATED_EVENT"

@DomainEvent(name = TASK_CREATED_EVENT)
class TaskCreatedEvent(
  val taskId: UUID,
  val title: String,
  val description: String,
  val projectId: UUID,
  createdAt: Long = System.currentTimeMillis()
) : Event<TaskAggregate>(
  name = TASK_CREATED_EVENT,
  createdAt = createdAt
)

@DomainEvent(name = TASK_TITLE_UPDATED_EVENT)
class TaskTitleUpdatedEvent(
  val taskId: UUID,
  val title: String,
  updatedAt: Long = System.currentTimeMillis()
) : Event<TaskAggregate>(
  name = TASK_TITLE_UPDATED_EVENT,
  createdAt = updatedAt
)

@DomainEvent(name = TASK_DESCRIPTION_UPDATED_EVENT)
class TaskDescriptionUpdatedEvent(
  val taskId: UUID,
  val description: String,
  createdAt: Long = System.currentTimeMillis()
) : Event<TaskAggregate>(
  name = TASK_DESCRIPTION_UPDATED_EVENT,
  createdAt = createdAt
)

@DomainEvent(name = TASK_EXECUTOR_ASSIGNED_EVENT)
class TaskExecutorAssignedEvent(
    val taskId: UUID,
    val executorId: UUID,
    createdAt: Long = System.currentTimeMillis()
) : Event<TaskAggregate>(
    name = TASK_EXECUTOR_ASSIGNED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = TASK_EXECUTOR_UNASSIGNED_EVENT)
class TaskExecutorUnassignedEvent(
    val taskId: UUID,
    createdAt: Long = System.currentTimeMillis()
) : Event<TaskAggregate>(
    name = TASK_EXECUTOR_UNASSIGNED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = TASK_STATUS_UPDATED_EVENT)
class TaskStatusUpdatedEvent(
  val taskId: UUID,
  val statusId: UUID,
  createdAt: Long = System.currentTimeMillis()
) : Event<TaskAggregate>(
  name = TASK_STATUS_UPDATED_EVENT,
  createdAt = createdAt
)
