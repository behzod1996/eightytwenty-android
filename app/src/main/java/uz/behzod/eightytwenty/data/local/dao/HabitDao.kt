package uz.behzod.eightytwenty.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.behzod.eightytwenty.data.local.entities.HabitEntity

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateHabit(habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    @Query("SELECT * FROM habit_table WHERE uid =:uid")
    fun fetchHabitByUid(uid: Long): Flow<HabitEntity>

    @Query("SELECT * FROM habit_table ORDER BY timestamp")
    fun fetchAllHabits(): Flow<List<HabitEntity>>

    @Transaction
    @Query("SELECT * FROM habit_table WHERE timestamp LIKE :timestamp AND is_complete <> 1")
    fun fetchHabitsByDate(timestamp: String): Flow<List<HabitEntity>>
}