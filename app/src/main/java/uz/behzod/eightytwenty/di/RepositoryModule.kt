package uz.behzod.eightytwenty.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.behzod.eightytwenty.data.repository.*
import uz.behzod.eightytwenty.domain.repository.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun providesNoteRepository(
        repo: NoteRepositoryImpl
    ): NoteRepository

    @Binds
    @Singleton
    fun providesNoteCategoryRepository(
        repo: NoteCategoryRepositoryImpl
    ): NoteCategoryRepository

    @Binds
    @Singleton
    fun providesHabitRepository(
        repository: HabitRepositoryImpl
    ): HabitRepository

    @Binds
    @Singleton
    fun providesHabitRecommendRepository(
        repository: HabitRecommendRepositoryImpl
    ): HabitRecommendRepository

    @Binds
    @Singleton
    fun providesScheduleRepository(
        repository: ScheduleRepositoryImpl
    ): ScheduleRepository

    @Binds
    @Singleton
    fun providesTaskCatalogRepository(
        repository: TaskCatalogRepositoryImpl
    ): TaskCatalogRepository

    @Binds
    @Singleton
    fun providesTaskRepository(
        repository: TaskRepositoryImpl
    ): TaskRepository
}