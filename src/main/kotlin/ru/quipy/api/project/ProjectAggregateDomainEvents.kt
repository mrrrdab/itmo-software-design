package ru.quipy.api.project

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val PROJECT_CREATED_EVENT = "PROJECT_CREATED_EVENT"
const val PROJECT_TITLE_UPDATED = "PROJECT_TITLE_UPDATED"
const val USER_ADDED_EVENT = "USER_ADDED_EVENT"
const val USER_DELETED_EVENT = "USER_DELETED_EVENT"
const val TAG_CREATED_EVENT = "TAG_CREATED_EVENT"
const val TAG_DELETED_EVENT = "TAG_DELETED_EVENT"

@DomainEvent(name = PROJECT_CREATED_EVENT)
class ProjectCreatedEvent(
    val projectId: UUID,
    val title: String,
    val creatorId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PROJECT_CREATED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = PROJECT_TITLE_UPDATED)
class ProjectTitleUpdatedEvent(
    val projectId: UUID,
    val title: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PROJECT_TITLE_UPDATED,
    createdAt = createdAt,
)

@DomainEvent(name = USER_ADDED_EVENT)
class UserAddedEvent(
    val projectId: UUID,
    val userId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = USER_ADDED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = USER_DELETED_EVENT)
class UserDeletedEvent(
    val projectId: UUID,
    val userId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = USER_DELETED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = TAG_CREATED_EVENT)
class TagCreatedEvent(
    val projectId: UUID,
    val tagId: UUID,
    val tagName: String,
    val tagColor: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = TAG_CREATED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = TAG_DELETED_EVENT)
class TagDeletedEvent(
    val projectId: UUID,
    val tagId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = TAG_DELETED_EVENT,
    createdAt = createdAt,
)
