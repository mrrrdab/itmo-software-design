package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import ru.quipy.api.project.*
import ru.quipy.logic.project.*
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
  val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>
) {

	@PostMapping()
	fun createProject(@RequestParam title: String, @RequestParam creatorId: String): ProjectCreatedEvent {
		return projectEsService.create { it.create(UUID.randomUUID(), title, UUID.fromString(creatorId)) }
	}

	@GetMapping("/{projectId}")
	fun getProject(@PathVariable projectId: UUID): ProjectAggregateState? {
		return projectEsService.getState(projectId)
	}

	@PatchMapping("/{projectId}/title")
	fun updateProjectTitle(@PathVariable projectId: UUID, @RequestParam title: String): ProjectTitleUpdatedEvent {
		return projectEsService.update(projectId) { it.updateTitle(title) }
	}

	@PostMapping("/{projectId}/users")
	fun addUser(@PathVariable projectId: UUID, @RequestParam userId: String): UserAddedEvent {
		val userUUID = UUID.fromString(userId)
		return projectEsService.update(projectId) { it.addUser(userUUID) }
	}

	@DeleteMapping("/{projectId}/users/{userId}")
	fun deleteUser(@PathVariable projectId: UUID, @PathVariable userId: UUID): UserDeletedEvent {
		return projectEsService.update(projectId) { it.deleteUser(userId) }
	}

	@PostMapping("/{projectId}/tags")
	fun createTag(
		@PathVariable projectId: UUID,
		@RequestParam tagName: String,
		@RequestParam tagColor: String
	): TagCreatedEvent {
		return projectEsService.update(projectId) { it.createTag(tagName, tagColor) }
	}

	@DeleteMapping("/{projectId}/tags/{tagId}")
	fun deleteTag(
		@PathVariable projectId: UUID,
		@PathVariable tagId: UUID
	): TagDeletedEvent {
		return projectEsService.update(projectId) { it.deleteTag(tagId) }
	}

	@GetMapping("/{projectId}/users")
	fun getAllProjectParticipants(@PathVariable projectId: UUID): List<UUID> {
		val projectState = projectEsService.getState(projectId)
			?: throw IllegalArgumentException("Project not found")
		return projectState.users
	}

}
