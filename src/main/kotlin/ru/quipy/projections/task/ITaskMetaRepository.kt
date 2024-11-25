package ru.quipy.projections.task

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ITaskMetaViewRepository : JpaRepository<TaskMetaViewEntity, UUID>
