package uz.behzod.eightytwenty.data.repository

import kotlinx.coroutines.flow.Flow
import uz.behzod.eightytwenty.data.local.entities.NoteEntity
import uz.behzod.eightytwenty.data.source.LocalSourceManager
import uz.behzod.eightytwenty.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val sourceManager: LocalSourceManager
): NoteRepository {

    override suspend fun insertNote(note: NoteEntity) {
        return sourceManager.insertNote(note)
    }

    override suspend fun updateNote(note: NoteEntity) {
        return sourceManager.updateNote(note)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        return sourceManager.deleteNote(note)
    }

    override fun fetchTrashedNotes(): Flow<List<NoteEntity>> {
        return sourceManager.fetchTrashedNotes()
    }

    override fun fetchAllNotes(): Flow<List<NoteEntity>> {
        return sourceManager.fetchAllNotes()
    }
}