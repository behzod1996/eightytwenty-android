package uz.behzod.eightytwenty.domain.interactor.task

import kotlinx.coroutines.flow.Flow
import uz.behzod.eightytwenty.data.local.entities.TaskEntity

interface FetchTasksRecent {
    fun execute(): Flow<List<TaskEntity>>
}