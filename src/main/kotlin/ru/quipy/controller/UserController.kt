package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import ru.quipy.api.user.*
import ru.quipy.logic.user.*
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(
    val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>
) {

  @PostMapping()
  fun registerUser(
    @RequestParam nickname: String, 
    @RequestParam fullname: String, 
    @RequestParam password: String
  ): UserRegisteredEvent {
    return userEsService.create { it.register(UUID.randomUUID(), nickname, fullname, password) }
  }

  @GetMapping("/{userId}")
  fun getUser(@PathVariable userId: UUID): UserAggregateState? {
    return userEsService.getState(userId)
  }
}
