package ru.quipy.projections.project

import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "project_view", schema = "projection")
data class ProjectViewDomain(
    @Id
    val id: UUID,

    @Column(nullable = false, length = 100)
    var title: String,

    @Column(nullable = false)
    val creatorId: UUID,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_participants", joinColumns = [JoinColumn(name = "project_id")])
    @Column(name = "user_id")
    val users: MutableList<UUID> = mutableListOf()
) {
    constructor() : this(UUID.randomUUID(), "", UUID.randomUUID())
}

