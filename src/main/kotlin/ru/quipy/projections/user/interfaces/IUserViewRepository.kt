package ru.quipy.projections.user.interfaces

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.quipy.projections.user.UserViewDomain
import java.util.UUID

interface IUserViewRepository : JpaRepository<UserViewDomain, UUID> {
    fun findByNickname(nickname: String): UserViewDomain?

    @Query("SELECT u FROM UserViewDomain u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :fullName, '%'))")
    fun findByFullName(fullName: String): List<UserViewDomain>
}