package ru.quipy.projections.project.interfaces

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.quipy.projections.project.ProjectViewDomain
import java.util.UUID

interface IProjectViewRepository : JpaRepository<ProjectViewDomain, UUID> {

    fun findByTitle(title: String): List<ProjectViewDomain>

    @Query("SELECT p FROM ProjectViewDomain p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    fun findByTitleContaining(keyword: String): List<ProjectViewDomain>

    fun findByCreatorId(creatorId: UUID): List<ProjectViewDomain>

    @Query("SELECT p FROM ProjectViewDomain p WHERE p.id IN :ids")
    fun findByIds(ids: List<UUID>): List<ProjectViewDomain>

    @Query("SELECT p.users FROM ProjectViewDomain p WHERE p.id = :projectId")
    fun findUsersByProjectId(@Param("projectId") projectId: UUID): List<UUID>

}
