package ru.quipy.projections.user

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Column
import javax.persistence.Id
import java.util.UUID

@Entity
@Table(name = "user_view", schema = "projection")
class UserViewDomain(
    @Id
    val id: UUID,

    @Column(nullable = false, length = 100)
    val fullName: String,

    @Column(unique = true, nullable = false, length = 50)
    val nickname: String
) {
    constructor() : this(UUID.randomUUID(), "", "")
}