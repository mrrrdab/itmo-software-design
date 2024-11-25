package ru.quipy.projections.project.interfaces

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.quipy.projections.project.ProjectViewDomain
import java.util.UUID

interface IProjectViewRepository : JpaRepository<ProjectViewDomain, UUID> {

    // Получение проектов по названию
    fun findByTitle(title: String): List<ProjectViewDomain>

            // Поиск проектов, название которых содержит ключевое слово
    @Query("SELECT p FROM ProjectViewDomain p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    fun findByTitleContaining(keyword: String): List<ProjectViewDomain>

    // Получение проектов по идентификатору создателя
    fun findByCreatorId(creatorId: UUID): List<ProjectViewDomain>

            // Получение проектов по множеству идентификаторов
    @Query("SELECT p FROM ProjectViewDomain p WHERE p.id IN :ids")
    fun findByIds(ids: List<UUID>): List<ProjectViewDomain>

    @Query("SELECT p.users FROM ProjectViewDomain p WHERE p.id = :projectId")
    fun findUsersByProjectId(@Param("projectId") projectId: UUID): List<UUID>

}
