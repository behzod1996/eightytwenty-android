package uz.behzod.eightytwenty.data.repository

import kotlinx.coroutines.flow.Flow
import uz.behzod.eightytwenty.data.local.entities.TaskEntity
import uz.behzod.eightytwenty.data.source.LocalSourceManager
import uz.behzod.eightytwenty.domain.repository.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val sourceManager: LocalSourceManager
): TaskRepository {

    override suspend fun insertTask(task: TaskEntity):Long {
        return sourceManager.insertTask(task)
    }

    override suspend fun updateTask(task: TaskEntity) {
        return sourceManager.updateTask(task)
    }

    override suspend fun deleteTask(task: TaskEntity) {
        return sourceManager.deleteTask(task)
    }

    override fun fetchTaskByUid(uid: Long): Flow<List<TaskEntity>> {
        return sourceManager.fetchTaskByUid(uid)
    }

    override fun fetchTasks(): Flow<List<TaskEntity>> {
        return sourceManager.fetchTasks()
    }

    override fun searchTasks(taskName: String): Flow<List<TaskEntity>> {
        return sourceManager.searchTasks(taskName)
    }

    override fun fetchTasksRecent(): Flow<List<TaskEntity>> {
        return sourceManager.fetchTasksRecent()
    }

    override fun fetchTasksNearTime(): Flow<List<TaskEntity>> {
        return sourceManager.fetchTasksNearTime()
    }

    override fun fetchLimitedTasks(): Flow<List<TaskEntity>> {
        return sourceManager.fetchLimitedTasks()
    }

    override fun fetchTasksByFolderUid(folderUid: Long): Flow<List<TaskEntity>> {
        return sourceManager.fetchTasksByFolderUid(folderUid)
    }

    override fun fetchCompletedTasksByFolderUid(folderUid: Long): Flow<List<TaskEntity>> {
        return sourceManager.fetchCompletedTasksByFolderUid(folderUid)
    }
}
