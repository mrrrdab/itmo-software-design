package ru.quipy.projections.project

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.api.project.ProjectAggregate
import ru.quipy.api.project.ProjectCreatedEvent
import ru.quipy.api.project.ProjectTitleUpdatedEvent
import ru.quipy.projections.project.interfaces.IProjectViewRepository
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.UUID
import javax.annotation.PostConstruct

@Service
class ProjectViewService(
    private val projectViewRepository: IProjectViewRepository,
    private val subscriptionsManager: AggregateSubscriptionsManager
) {
    private val logger: Logger = LoggerFactory.getLogger(ProjectViewService::class.java)

    @PostConstruct
    private fun init(): Unit {
        subscriptionsManager.createSubscriber(ProjectAggregate::class, "project-view-subscriber") {
            `when`(ProjectCreatedEvent::class) { event ->
                createProject(event.projectId, event.title, event.creatorId)
                logger.info("Project created with title: {}", event.title)
            }

            `when`(ProjectTitleUpdatedEvent::class) { event ->
                updateProjectTitle(event.projectId, event.title)
                logger.info("Project updated with new title: {}", event.title)
            }
        }
    }

    private fun createProject(projectId: UUID, title: String, creatorId: UUID): Unit {
        val project = ProjectViewDomain(
            id = projectId,
            title = title,
            creatorId = creatorId
        )
        projectViewRepository.save(project)
    }

    private fun updateProjectTitle(projectId: UUID, newTitle: String): Unit {
        val project = projectViewRepository.findById(projectId).orElseThrow {
            IllegalArgumentException("Project not found with id: $projectId")
        }
        project.title = newTitle
        projectViewRepository.save(project)
    }
}

