package ru.quipy.api.user

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val USER_REGISTERED_EVENT = "USER_REGISTERED_EVENT"

@DomainEvent(name = USER_REGISTERED_EVENT)
class UserRegisteredEvent(
	val userId: UUID,
	val nickname: String,
	val fullname: String,
	val password: String,
	createdAt: Long = System.currentTimeMillis(),
) : Event<UserAggregate>(
	name = USER_REGISTERED_EVENT,
	createdAt = createdAt,
)
