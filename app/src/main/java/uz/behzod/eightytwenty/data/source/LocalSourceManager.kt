package uz.behzod.eightytwenty.data.source

import kotlinx.coroutines.flow.Flow
import uz.behzod.eightytwenty.data.local.entities.*

interface LocalSourceManager {
    // Note: Dao functions
    suspend fun insertNote(note: NoteEntity)
    suspend fun updateNote(note: NoteEntity)
    suspend fun deleteNote(note: NoteEntity)
    fun fetchTrashedNotes(): Flow<List<NoteEntity>>
    fun fetchAllNotes(): Flow<List<NoteEntity>>
    fun fetchNotesByCategoryId(categoryId: Long): Flow<List<NoteEntity>>
    fun fetchNoteById(noteId: Long): Flow<NoteEntity>
    fun searchNotes(query: String): Flow<List<NoteEntity>>

    // NoteCategory: Dao functions
    suspend fun insertNoteCategory(category: NoteCategoryEntity)
    suspend fun updateNoteCategory(category: NoteCategoryEntity)
    suspend fun deleteNoteCategory(category: NoteCategoryEntity)
    suspend fun incrementNoteCount(noteCategoryId: Long)
    suspend fun decrementNoteCount(noteCategoryId: Long)
    fun fetchAllCategories(): Flow<List<NoteCategoryEntity>>
    fun fetchAllCategoriesAndNotes(): Flow<List<CategoryAndNotes>>
    suspend fun fetchIfCategoryIdExists(noteCategoryId: Int): Boolean

    // Habit: Dao functions
    suspend fun insertHabit(habit: HabitEntity)
    suspend fun updateHabit(habit: HabitEntity)
    suspend fun deleteHabit(habit: HabitEntity)
    fun fetchHabitByUid(uid: Long): Flow<HabitEntity>
    fun fetchAllHabits(): Flow<List<HabitEntity>>
    fun fetchHabitsByDate(timestamp: Long): Flow<List<HabitEntity>>

    // HabitRecommend: Dao functions
    suspend fun insertHabitRecommend(habitRecommend: HabitRecommendEntity)
    suspend fun updateHabitRecommend(habitRecommend: HabitRecommendEntity)
    suspend fun deleteHabitRecommend(habitRecommend: HabitRecommendEntity)
    fun fetchHabitRecommendsByCategory(category: String):Flow<List<HabitRecommendEntity>>
}