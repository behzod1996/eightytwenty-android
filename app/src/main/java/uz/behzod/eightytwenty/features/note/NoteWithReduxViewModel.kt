package uz.behzod.eightytwenty.features.note

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.behzod.eightytwenty.core.ReduxViewModel
import uz.behzod.eightytwenty.domain.interactor.note.FetchNotes
import uz.behzod.eightytwenty.domain.interactor.note.FetchNotesByCategoryId
import uz.behzod.eightytwenty.domain.interactor.note_category.FetchAllCategoriesAndNotes
import uz.behzod.eightytwenty.utils.extension.printDebug
import javax.inject.Inject

@HiltViewModel
class NoteWithReduxViewModel @Inject constructor(
    iFetchNotes: FetchNotes,
    private val iFetchNotesByCategoryId: FetchNotesByCategoryId,
    private val iFetchCategoryAndNotes: FetchAllCategoriesAndNotes
) : ReduxViewModel<NoteViewState>(initialState = NoteViewState()) {

    init {
        iFetchNotes.invoke()
            .onEach { result ->
                if (result.isNotEmpty()) {
                    modifyState { state ->
                        state.copy(
                            notes = result,
                            isLoading = false,
                            isEmpty = false
                        )
                    }
                    printDebug { "Result is $result" }
                } else {
                    modifyState { state ->
                        state.copy(
                            notes = emptyList(),
                            isLoading = false,
                            isEmpty = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}