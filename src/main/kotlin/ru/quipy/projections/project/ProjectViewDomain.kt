package ru.quipy.projections.project

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Column
import javax.persistence.Id
import java.util.UUID

@Entity
@Table(name = "project_view", schema = "projection")
data class ProjectViewDomain(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, length = 100)
    var title: String = "",

    @Column(nullable = false)
    val creatorId: UUID = UUID.randomUUID()
) {
    // Нужен для JPA
    constructor() : this(UUID.randomUUID(), "", UUID.randomUUID())
}
