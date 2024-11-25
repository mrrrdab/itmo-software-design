package ru.quipy.projections.user

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.api.user.UserAggregate
import ru.quipy.api.user.UserRegisteredEvent
import ru.quipy.projections.user.interfaces.IUserViewRepository
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.UUID
import javax.annotation.PostConstruct

@Service
class UserViewService(
    private val userViewRepository: IUserViewRepository,
    private val subscriptionsManager: AggregateSubscriptionsManager
) {
    private val logger: Logger = LoggerFactory.getLogger(UserViewService::class.java)

    @PostConstruct
    private fun init(): Unit {
        subscriptionsManager.createSubscriber(UserAggregate::class, "user-event-subscriber") {
            `when`(UserRegisteredEvent::class) { event ->
                createUser(event.userId, event.fullname, event.nickname)
                logger.info("User created {}", event.nickname)
            }
        }
    }

    private fun createUser(userId: UUID, userFullName: String, userNickname: String): Unit {
        val user = UserViewDomain(
            id = userId,
            fullName = userFullName,
            nickname = userNickname
        )

        userViewRepository.save(user)
    }
}