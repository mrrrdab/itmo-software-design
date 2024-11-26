package ru.quipy.projections.task

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "task_meta_view", schema = "projection")
data class TaskMetaViewDomain(
    @Id
    val taskId: UUID,
    val projectId: UUID,
    val taskStatus: UUID?,
    val taskTitle: String?,
    val taskDescription: String?,
    val taskExecutor: UUID?
) {
    constructor() : this(UUID.randomUUID(), UUID.randomUUID(), null, null, null, null)
}
