package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import ru.quipy.api.user.*
import ru.quipy.logic.user.*
import ru.quipy.projections.user.UserViewDomain
import ru.quipy.projections.user.interfaces.IUserViewRepository
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(
    val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>,
    private val userRepository: IUserViewRepository
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

  @GetMapping("/nickname/{userNickname}")
  fun findByNickname(@PathVariable userNickname: String): UserViewDomain? {
    return userRepository.findByNickname(userNickname)
  }

  @GetMapping("/fullName/{userFullName}")
  fun findByFullName(@PathVariable userFullName: String): List<UserViewDomain> {
    return userRepository.findByFullName(userFullName)
  }
}
