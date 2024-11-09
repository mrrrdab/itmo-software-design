package ru.quipy.logic.user

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.api.user.*
import java.util.*

class UserAggregateState : AggregateState<UUID, UserAggregate> {
	private lateinit var userId: UUID

	lateinit var nickname: String
	lateinit var fullname: String
	lateinit var password: String

	var createdAt: Long = System.currentTimeMillis()

	override fun getId(): UUID = userId

	@StateTransitionFunc
	fun userRegisteredApply(event: UserRegisteredEvent) {
		userId = event.userId
		nickname = event.nickname
		fullname = event.fullname
		password = event.password
		createdAt = event.createdAt
	}
}
