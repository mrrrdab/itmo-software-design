package ru.quipy.projections.project

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.streams.AggregateSubscriptionsManager
import javax.annotation.PostConstruct
import ru.quipy.api.project.*

@Service
class ProjectEventsSubscriber {

	val logger: Logger = LoggerFactory.getLogger(ProjectEventsSubscriber::class.java)

	@Autowired
	lateinit var subscriptionsManager: AggregateSubscriptionsManager

	@PostConstruct
	fun init() {
		subscriptionsManager.createSubscriber(ProjectAggregate::class, "project-event-subscriber") {
			`when`(TagCreatedEvent::class) {event ->
				logger.info("Tag created: {}", event.tagName)
			}
		}
	}
}
