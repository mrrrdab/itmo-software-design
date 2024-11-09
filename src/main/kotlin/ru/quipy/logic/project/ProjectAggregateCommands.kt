package ru.quipy.logic.project

import ru.quipy.api.project.*
import java.util.*

fun ProjectAggregateState.create(id: UUID, title: String, creatorId: UUID): ProjectCreatedEvent {
	if (title.isBlank()) {
    throw IllegalArgumentException("Project title cannot be empty or consist only of spaces")
  }

	return ProjectCreatedEvent(
		projectId = id,
		title = title,
		creatorId = creatorId,
	)
}

fun ProjectAggregateState.updateTitle(title: String): ProjectTitleUpdatedEvent {
	if (title.isBlank()) {
    throw IllegalArgumentException("Project title cannot be empty or consist only of spaces")
  }

  if (this.title == title) {
    throw IllegalArgumentException("New project title cannot be the same as the current title")
  }

	return ProjectTitleUpdatedEvent(projectId = this.getId(), title = title)
}

fun ProjectAggregateState.addUser(userId: UUID): UserAddedEvent {
	if (users.contains(userId)) {
    throw IllegalArgumentException("User is already part of the project: $userId")
  }

	return UserAddedEvent(projectId = this.getId(), userId = userId)
}

fun ProjectAggregateState.deleteUser(userId: UUID): UserDeletedEvent {
	if (!users.contains(userId)) {
    throw IllegalArgumentException("User not found in the project: $userId")
  }

	return UserDeletedEvent(projectId = this.getId(), userId = userId)
}

fun ProjectAggregateState.createTag(tagName: String, tagColor: String): TagCreatedEvent {
	if (tagName.isBlank()) {
    throw IllegalArgumentException("Tag name cannot be empty or consist only of spaces")
  }

	if (tagColor.isBlank()) {
    throw IllegalArgumentException("Tag color cannot be empty or consist only of spaces")
  }

	if (projectTags.values.any { it.name == tagName }) {
		throw IllegalArgumentException("Tag already exists: $tagName")
	}

	if (projectTags.values.any { it.color == tagColor }) {
		throw IllegalArgumentException("Tag color already exists: $tagColor")
	}

	return TagCreatedEvent(projectId = this.getId(), tagId = UUID.randomUUID(), tagName = tagName, tagColor = tagColor)
}

fun ProjectAggregateState.deleteTag(tagId: UUID): TagDeletedEvent {
	if (!projectTags.containsKey(tagId)) {
		throw IllegalArgumentException("Tag does not exist: &tagId")
	}

	return TagDeletedEvent(
		projectId = this.getId(),
		tagId = tagId
	)
}
