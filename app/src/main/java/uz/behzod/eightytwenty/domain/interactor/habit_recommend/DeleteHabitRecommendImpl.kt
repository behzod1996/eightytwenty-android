package uz.behzod.eightytwenty.domain.interactor.habit_recommend

import kotlinx.coroutines.withContext
import uz.behzod.eightytwenty.data.local.entities.asEntity
import uz.behzod.eightytwenty.domain.model.HabitRecommendDomainModel
import uz.behzod.eightytwenty.domain.repository.HabitRecommendRepository
import uz.behzod.eightytwenty.utils.providers.IDispatcherProvider
import javax.inject.Inject

class DeleteHabitRecommendImpl @Inject constructor(
    private val repository: HabitRecommendRepository,
    private val dispatcherProvider: IDispatcherProvider
) : DeleteHabitRecommend {

    override suspend fun invoke(params: HabitRecommendDomainModel) {
        return withContext(dispatcherProvider.io) {
            repository.deleteHabitRecommend(params.asEntity())
        }
    }
}