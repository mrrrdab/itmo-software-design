package ru.quipy.logic.project

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.api.project.*
import java.util.*

class ProjectAggregateState : AggregateState<UUID, ProjectAggregate> {
	private lateinit var projectId: UUID

	lateinit var title: String

	var createdAt: Long = System.currentTimeMillis()
	var updatedAt: Long = System.currentTimeMillis()

	val users: MutableList<UUID> = mutableListOf()
	var projectTags = mutableMapOf<UUID, TagEntity>()

	override fun getId() = projectId

	@StateTransitionFunc
	fun projectCreatedApply(event: ProjectCreatedEvent) {
		projectId = event.projectId
		title = event.title
		users.add(event.creatorId)
		createdAt = event.createdAt
		updatedAt = createdAt
	}

	@StateTransitionFunc
	fun projectTitleUpdatedApply(event: ProjectTitleUpdatedEvent) {
		projectId = event.projectId
		title = event.title
		updatedAt = event.createdAt
	}

	@StateTransitionFunc
	fun userAddedApply(event: UserAddedEvent) {
		projectId = event.projectId
		users.add(event.userId)
		updatedAt = event.createdAt
	}

	@StateTransitionFunc
	fun userDeletedApply(event: UserDeletedEvent) {
		projectId = event.projectId
		users.remove(event.userId)
		updatedAt = event.createdAt
	}

	@StateTransitionFunc
	fun tagCreatedApply(event: TagCreatedEvent) {
		projectId = event.projectId
		projectTags[event.tagId] = TagEntity(event.tagId, event.tagName, event.tagColor)
		updatedAt = event.createdAt
	}

	@StateTransitionFunc
	fun tagDeletedApply(event: TagDeletedEvent) {
		projectId = event.projectId
		projectTags.remove(event.tagId)
		updatedAt = event.createdAt
	}
}

data class TagEntity(
	val id: UUID = UUID.randomUUID(),
	val name: String,
	val color: String
)
