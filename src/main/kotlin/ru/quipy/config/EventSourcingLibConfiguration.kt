package ru.quipy.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.streams.AggregateEventStreamManager
import ru.quipy.streams.AggregateSubscriptionsManager
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.api.user.UserAggregate
import ru.quipy.api.project.ProjectAggregate
import ru.quipy.api.task.TaskAggregate
import ru.quipy.logic.user.UserAggregateState
import ru.quipy.logic.project.ProjectAggregateState
import ru.quipy.logic.task.TaskAggregateState
import ru.quipy.projections.project.AnnotationBasedProjectEventsSubscriber
import javax.annotation.PostConstruct
import java.util.*

@Configuration
class EventSourcingLibConfiguration {

	private val logger = LoggerFactory.getLogger(EventSourcingLibConfiguration::class.java)

	@Autowired
	private lateinit var subscriptionsManager: AggregateSubscriptionsManager

	@Autowired
	private lateinit var projectEventSubscriber: AnnotationBasedProjectEventsSubscriber

	@Autowired
	private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

	@Autowired
	private lateinit var eventStreamManager: AggregateEventStreamManager

	@Bean
	fun userEsService() = eventSourcingServiceFactory.create<UUID, UserAggregate, UserAggregateState>()

	@Bean
	fun projectEsService() = eventSourcingServiceFactory.create<UUID, ProjectAggregate, ProjectAggregateState>()

	@Bean
	fun taskEsService() = eventSourcingServiceFactory.create<UUID, TaskAggregate, TaskAggregateState>()

	@PostConstruct
	fun init() {
		subscriptionsManager.subscribe<ProjectAggregate>(projectEventSubscriber)

		eventStreamManager.maintenance {
			onRecordHandledSuccessfully { streamName, eventName ->
				logger.info("Stream $streamName successfully processed record of $eventName")
			}

			onBatchRead { streamName, batchSize ->
				logger.info("Stream $streamName read batch size: $batchSize")
			}
		}
	}
}
