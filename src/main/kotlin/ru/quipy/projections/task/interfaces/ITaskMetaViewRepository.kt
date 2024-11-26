package ru.quipy.projections.task.interfaces

import org.springframework.data.jpa.repository.JpaRepository
import ru.quipy.projections.task.TaskMetaViewDomain
import java.util.*

interface ITaskMetaViewRepository : JpaRepository<TaskMetaViewDomain, UUID>
