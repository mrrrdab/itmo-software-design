package ru.quipy.api.project

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate
import ru.quipy.logic.project.ProjectAggregateState
import java.util.*

@AggregateType(aggregateEventsTableName = "project-aggregate")
class ProjectAggregate : Aggregate {
    lateinit var state: ProjectAggregateState

    fun createProject(id: UUID, title: String, creatorId: UUID): ProjectCreatedEvent {
        if (title.isBlank()) throw IllegalArgumentException("Project title cannot be empty.")
        state = ProjectAggregateState()
        val event = ProjectCreatedEvent(
            projectId = id,
            title = title,
            creatorId = creatorId,
            createdAt = System.currentTimeMillis()
        )
        state.projectCreatedApply(event)
        return event
    }

    fun updateProjectTitle(title: String): ProjectTitleUpdatedEvent {
        if (title.isBlank()) throw IllegalArgumentException("New project title cannot be empty.")
        val event = ProjectTitleUpdatedEvent(
            projectId = state.getId(),
            title = title,
            createdAt = System.currentTimeMillis()
        )
        state.projectTitleUpdatedApply(event)
        return event
    }

    fun addUser(userId: UUID): UserAddedEvent {
        if (state.users.contains(userId)) throw IllegalStateException("User already exists in the project.")
        val event = UserAddedEvent(
            projectId = state.getId(),
            userId = userId,
            createdAt = System.currentTimeMillis()
        )
        state.userAddedApply(event)
        return event
    }

    fun removeUser(userId: UUID): UserDeletedEvent {
        if (!state.users.contains(userId)) throw IllegalArgumentException("User not found in the project.")
        val event = UserDeletedEvent(
            projectId = state.getId(),
            userId = userId,
            createdAt = System.currentTimeMillis()
        )
        state.userDeletedApply(event)
        return event
    }

    fun createTag(tagName: String, tagColor: String): TagCreatedEvent {
        val tagId = UUID.randomUUID()
        if (state.projectTags.values.any { it.name == tagName }) {
            throw IllegalArgumentException("Tag with name '$tagName' already exists.")
        }
        val event = TagCreatedEvent(
            projectId = state.getId(),
            tagId = tagId,
            tagName = tagName,
            tagColor = tagColor,
            createdAt = System.currentTimeMillis()
        )
        state.tagCreatedApply(event)
        return event
    }

    fun deleteTag(tagId: UUID): TagDeletedEvent {
        if (!state.projectTags.containsKey(tagId)) {
            throw IllegalArgumentException("Tag with ID '$tagId' does not exist.")
        }
        val event = TagDeletedEvent(
            projectId = state.getId(),
            tagId = tagId,
            createdAt = System.currentTimeMillis()
        )
        state.tagDeletedApply(event)
        return event
    }
}
