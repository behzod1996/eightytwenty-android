package uz.behzod.eightytwenty.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.behzod.eightytwenty.utils.ext.Empty
import uz.behzod.eightytwenty.utils.ext.Zero

@Entity(
    tableName = "task_catalog_table_name"
)
data class TaskCatalogEntity(
    @ColumnInfo(name = NAME)
    val name: String = String.Empty,

    @ColumnInfo(name = COUNT)
    val taskCount: Long = Long.Zero,

    @ColumnInfo(name = UID)
    @PrimaryKey(autoGenerate = true)
    val uid: Long = Long.Zero
) {
    companion object {
        private const val NAME = "catalog_name"
        private const val COUNT = "task_count"
        private const val UID = "catalog_uid"
    }
}