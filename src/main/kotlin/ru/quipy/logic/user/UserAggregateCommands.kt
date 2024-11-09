package ru.quipy.logic.user

import ru.quipy.api.user.*
import java.util.*

fun UserAggregateState.register(id: UUID, nickname: String, fullname: String, password: String): UserRegisteredEvent {
	if (nickname.isBlank()) {
		throw IllegalArgumentException("Nickname cannot be empty or consist only of spaces")
	}

	if (fullname.isBlank()) {
		throw IllegalArgumentException("Full name cannot be empty or consist only of spaces")
	}

	if (password.isBlank()) {
		throw IllegalArgumentException("Password cannot be empty or consist only of spaces")
	}

	return UserRegisteredEvent(userId = id, nickname = nickname, fullname = fullname, password = password)
}
