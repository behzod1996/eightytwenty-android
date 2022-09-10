package uz.behzod.eightytwenty.domain.interactor.habit

import kotlinx.coroutines.withContext
import uz.behzod.eightytwenty.data.local.entities.asEntity
import uz.behzod.eightytwenty.domain.model.HabitDomainModel
import uz.behzod.eightytwenty.domain.repository.HabitRepository
import uz.behzod.eightytwenty.utils.providers.IDispatcherProvider
import javax.inject.Inject

class InsertHabitImpl @Inject constructor(
    private val repository: HabitRepository,
    private val dispatcherProvider: IDispatcherProvider
): InsertHabit {

    override suspend fun invoke(habit: HabitDomainModel) {
       return withContext(dispatcherProvider.io) {
           repository.insertHabit(habit.asEntity())
       }
    }
}