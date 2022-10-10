package uz.behzod.eightytwenty.features.new_note

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import uz.behzod.eightytwenty.core.ReduxViewModel
import uz.behzod.eightytwenty.data.local.entities.NoteImageEntity
import uz.behzod.eightytwenty.domain.interactor.note.InsertNote
import uz.behzod.eightytwenty.domain.model.NoteDomainModel
import uz.behzod.eightytwenty.utils.extension.getUriExtension
import uz.behzod.eightytwenty.utils.extension.getUriMimeType
import uz.behzod.eightytwenty.utils.formatter.DateTimeFormatter
import uz.behzod.eightytwenty.utils.manager.ImageStoreManager
import java.io.File
import java.time.LocalDateTime
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class NewNoteWithReduxViewModel @Inject constructor(
    private val iInsertNote: InsertNote,
    private val imageManager: ImageStoreManager
) : ReduxViewModel<NewNoteViewState, NewNoteViewEffect>(initialState = NewNoteViewState()) {

    fun modifyTitle(title: String) {
        modifyState { state -> state.copy(title = title) }
    }

    fun modifyDesc(desc: String) {
        modifyState { state -> state.copy(description = desc) }
    }

    fun modifyTimestamp(timestamp: ZonedDateTime) {
        modifyState { state -> state.copy(timestamp = timestamp) }
    }

    fun modifyIsTrashed(isTrashed: Boolean) {
        modifyState { state -> state.copy(isTrashed = isTrashed) }
    }

    fun modifyGroupUid(uid: Long) {
        modifyState { state -> state.copy(categoryUid = uid) }
    }

    fun modifyUri(uri: Uri?) {
        modifyState { state -> state.copy(uri = uri) }
    }

    fun insertNote() {
        viewModelScope.launch {
            val title = state.value.title.trim()
            val desc = state.value.description.trim()
            val timestamp = state.value.timestamp
            val isTrashed = state.value.isTrashed
            val image = state.value.image

            val images = mutableListOf<NoteImageEntity>()

            image?.let { images.add(it) }

            modifyState { state -> state.copy(isLoading = true) }

            runCatching {
                iInsertNote.invoke(
                    NoteDomainModel(
                        title = title,
                        description = desc,
                        timestamp = timestamp,
                        isTrashed = isTrashed
                    ),
                    images
                )
            }.onSuccess {
                modifyState { state ->
                    state.copy(
                        isSuccess = true,
                        isFailure = false,
                        isLoading = false,
                    )
                }
            }.onFailure {
                modifyState { state -> state.copy(isFailure = true) }
            }
        }
    }

    suspend fun addImages(context: Context, uriSources: UriSources) {
        viewModelScope.launch {
            for (uri in uriSources) {
                val newUri = updateUriSource(context,uri)
                val image = NoteImageEntity(
                    uri = newUri,
                    mimeType = context.getUriMimeType(newUri) ?: "",
                    noteUid = state.value.image?.noteUid ?: 0L
                )
            }
        }
    }

    private suspend fun updateUriSource(context: Context, uriSource: Uri): Uri {
        val ext = context.getUriExtension(uriSource) ?: "jpeg"

        val fileName = buildString {
            append("IMG_")
            append(
                java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
                    .format(LocalDateTime.now())
            )
            append("_${(0..999).random()}.$ext")
        }

        val fullPath = imageManager.saveImage(context, uriSource, fileName)
        val file = File(fullPath)
        return FileProvider.getUriForFile(context, "uz.behzod.eightytwenty", file)
    }
}