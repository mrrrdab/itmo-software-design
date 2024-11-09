package ru.quipy.api.project

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType(aggregateEventsTableName = "project-aggregate")
class ProjectAggregate : Aggregate
