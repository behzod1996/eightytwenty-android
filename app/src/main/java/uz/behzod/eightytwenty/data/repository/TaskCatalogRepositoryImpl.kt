package uz.behzod.eightytwenty.data.repository

import kotlinx.coroutines.flow.Flow
import uz.behzod.eightytwenty.data.local.entities.CatalogAndTasks
import uz.behzod.eightytwenty.data.local.entities.TaskCatalogEntity
import uz.behzod.eightytwenty.data.source.LocalSourceManager
import uz.behzod.eightytwenty.domain.repository.TaskCatalogRepository
import javax.inject.Inject

class TaskCatalogRepositoryImpl @Inject constructor(
    private val sourceManager: LocalSourceManager
) : TaskCatalogRepository {

    override suspend fun insertTaskCatalog(taskCatalog: TaskCatalogEntity) {
        return sourceManager.insertTaskCatalog(taskCatalog)
    }

    override suspend fun updateTaskCatalog(taskCatalog: TaskCatalogEntity) {
        return sourceManager.updateTaskCatalog(taskCatalog)
    }

    override suspend fun deleteTaskCatalog(taskCatalog: TaskCatalogEntity) {
        return sourceManager.deleteTaskCatalog(taskCatalog)
    }

    override suspend fun incrementTaskCount(catalogUid: Long) {
        return sourceManager.incrementTaskCount(catalogUid)
    }

    override suspend fun decrementTaskCount(catalogUid: Long) {
        return sourceManager.decrementTaskCount(catalogUid)
    }

    override fun fetchTaskCatalogs(): Flow<List<TaskCatalogEntity>> {
        TODO("Not yet implemented")
    }

    override fun fetchTaskAndCatalogs(): Flow<List<CatalogAndTasks>> {
        TODO("Not yet implemented")
    }
}