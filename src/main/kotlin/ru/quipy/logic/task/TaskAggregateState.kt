package ru.quipy.logic.task

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.api.task.*
import java.util.*

class TaskAggregateState : AggregateState<UUID, TaskAggregate> {
  private lateinit var taskId: UUID

  lateinit var title: String
  lateinit var description: String
  lateinit var projectId: UUID

  var statusId: UUID? = null
	var executorId: UUID? = null

	var createdAt: Long = System.currentTimeMillis()
  var updatedAt: Long = System.currentTimeMillis()

  override fun getId(): UUID = taskId

	@StateTransitionFunc
  fun taskCreatedApply(event: TaskCreatedEvent) {
    taskId = event.taskId
    title = event.title
    description = event.description
		projectId = event.projectId
    createdAt = event.createdAt
	}

	@StateTransitionFunc
	fun taskTitleUpdatedApply(event: TaskTitleUpdatedEvent) {
		title = event.title
    updatedAt = event.createdAt
	}

	@StateTransitionFunc
	fun taskDescriptionUpdatedApply(event: TaskDescriptionUpdatedEvent) {
		description = event.description
    updatedAt = event.createdAt
	}

	@StateTransitionFunc
	fun taskExecutorAssignedApply(event: TaskExecutorAssignedEvent) {
		executorId = event.executorId
    updatedAt = event.createdAt
	}

	@StateTransitionFunc
	fun taskExecutorUnassignedApply(event: TaskExecutorUnassignedEvent) {
		executorId = null
    updatedAt = event.createdAt
	}

	@StateTransitionFunc
	fun taskStatusUpdatedApply(event: TaskStatusUpdatedEvent) {
		statusId = event.statusId
    updatedAt = event.createdAt
	}
}
