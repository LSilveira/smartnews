package com.lsilveira.smartnews.repository

import com.lsilveira.smartnews.model.settings.AggregatorMapping
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AggregatorMappingRepository : CrudRepository<AggregatorMapping, Long>
{
}