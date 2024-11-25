package ru.quipy.projections.task

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.api.task.*
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.task.TaskAggregateState
import ru.quipy.projections.ProjectEventsSubscriber
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Service
class TaskMetaService(
    val taskEsService: EventSourcingService<UUID, TaskAggregate, TaskAggregateState>,
    val taskMetaViewRepository: ITaskMetaViewRepository
) {

    val logger: Logger = LoggerFactory.getLogger(ProjectEventsSubscriber::class.java)

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(TaskAggregate::class, "task-aggregate-event-publisher-stream") {
            `when`(TaskCreatedEvent::class) { event ->
                logger.info("Task created {}", event.taskId)
                SaveTaskInRepository(event.taskId)
            }
            `when`(TaskTitleUpdatedEvent::class) { event ->
                val message =
                    String.format("Task title updated for taskId: %s, newTitle: %s", event.taskId, event.title)
                logger.info(message)
                SaveTaskInRepository(event.taskId)
            }
            `when`(TaskDescriptionUpdatedEvent::class) { event ->
                val message = String.format(
                    "Task description updated for taskId: %s, newTitle: %s",
                    event.taskId,
                    event.description
                )
                logger.info(message)
                SaveTaskInRepository(event.taskId)
            }
            `when`(TaskStatusUpdatedEvent::class) { event ->
                val message =
                    String.format("Task status updated for taskId: %s, newTitle: %s", event.taskId, event.statusId)
                logger.info(message)
                SaveTaskInRepository(event.taskId)
            }
            `when`(TaskExecutorAssignedEvent::class) { event ->
                val message = String.format(
                    "Task executor was assigned for taskId: %s, newTitle: %s",
                    event.taskId,
                    event.executorId
                )
                logger.info(message)
                SaveTaskInRepository(event.taskId)
            }
            `when`(TaskExecutorUnassignedEvent::class) { event ->
                val message = String.format("Task executor was unssigned for taskId: %s, newTitle: %s", event.taskId)
                logger.info(message)
                SaveTaskInRepository(event.taskId)
            }
        }
    }

    private fun SaveTaskInRepository(taskId: UUID) {
        val task = taskEsService.getState(taskId) ?: throw Exception("task does not exist")
        val taskMetaEntity = TaskMetaViewEntity(
            task.getId(),
            task.projectId,
            task.statusId,
            task.title,
            task.description,
            task.executorId
        )
        taskMetaViewRepository.save(taskMetaEntity)
    }
}