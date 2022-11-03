package uz.behzod.eightytwenty.features.note_detail

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.behzod.eightytwenty.R
import uz.behzod.eightytwenty.data.local.entities.asDomain
import uz.behzod.eightytwenty.databinding.FragmentNoteDetailBinding
import uz.behzod.eightytwenty.features.new_note.ImageAdapter
import uz.behzod.eightytwenty.features.new_note.NoteGroupPickerFragment
import uz.behzod.eightytwenty.features.new_note.NoteGroupPickerListener
import uz.behzod.eightytwenty.utils.extension.*
import uz.behzod.eightytwenty.utils.view.viewBinding
import uz.behzod.undo_redo.UndoStateListener
import uz.behzoddev.ui_toast.errorMessage

@AndroidEntryPoint
class NoteDetailFragment : Fragment(R.layout.fragment_note_detail) {

    companion object {
        private const val REQ_UID_KEY = "NOTE_GROUP_PICKER_UID_KEY"
        private const val REQ_UID_VALUE = "NOTE_GROUP_PICKER_UID_VALUE"
    }

    private val binding by viewBinding(FragmentNoteDetailBinding::bind)
    private val viewModel: NoteDetailViewModel by viewModels()
    private val args: NoteDetailFragmentArgs by navArgs()
    private lateinit var imageAdapter: ImageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        observerState()

        setUndoRedoListener()

        onUndoDescription()
        onRedoDescription()
        onDeleteNote()
        onMoveToGroup()
    }

    private fun setupView() {
        initArgs()

        setupRecyclerView()

        binding.etTitle.addTextChangedListener { title -> viewModel.updateTitle(title.toString()) }
        binding.etDesc.addTextChangedListener { desc -> viewModel.updateDesc(desc.toString()) }
        binding.tvDate.addTextChangedListener { time ->
            viewModel.updateTimestamp(
                time.asStringOrEmpty().asZoneDateTime()
            )
        }
    }

    private fun initArgs() {
        val uid = args.noteId
        viewModel.fetchNoteRelationByUid(uid)
        viewModel.updateNoteUid(uid)
    }

    private fun setupRecyclerView() {
        imageAdapter = ImageAdapter()
        binding.rvImage.adapter = imageAdapter
        viewModel.updateImages(imageAdapter.currentList)
    }

    private fun observerState() {
        viewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { renderState(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun renderState(state: NoteDetailState) {
        val noteState = state.note
        val isDeleted = state.isDeleted
        val isDeleteFailed = state.isDeleteFailed
        val isMoved = state.isMoved
        val isMoveFailed = state.isMoveFailed

        if (noteState != null) {
            binding.etTitle.setText(noteState.note.title)
            binding.etDesc.setText(noteState.note.description)
            binding.tvDate.text = noteState.note.timestamp.toString()
            viewModel.updateNotes(listOf(noteState.note.asDomain()))
            imageAdapter.submitList(noteState.images)
        }

        if (isDeleted) {
            val route = NoteDetailFragmentDirections.actionNoteDetailFragmentToNoteFragment()
            navigateTo(route)
        }
        if (isDeleteFailed) {
            showMessage("You cannot delete note. Please, try it!")
        }
        if (isMoved) {
            val route = NoteDetailFragmentDirections.actionNoteDetailFragmentToNoteFragment()
            navigateTo(route)
        }
        if (isMoveFailed) {
            errorMessage("Cannot move to group")
        }
    }

    private fun setUndoRedoListener() {
        binding.ivUndo.isEnabled = true
        binding.ivRedo.isEnabled = true

        binding.etDesc.setUndoStatusListener(object : UndoStateListener {

            override fun onUndoStatusChanged(canUndo: Boolean) {
                binding.ivUndo.isEnabled = if (canUndo) {
                    binding.ivUndo.drawable(R.drawable.bg_oval_undo_redo_fill)
                    true
                } else {
                    binding.ivUndo.drawable(R.drawable.bg_oval_undo_redo)
                    false
                }
            }

            override fun onRedoStatusChanged(canRedo: Boolean) {
                binding.ivRedo.isEnabled = if (canRedo) {
                    binding.ivRedo.drawable(R.drawable.bg_oval_undo_redo_fill)
                    true
                } else {
                    binding.ivRedo.drawable(R.drawable.bg_oval_undo_redo)
                    false
                }
            }
        })
    }

    private fun onUndoDescription() {
        binding.ivUndo.setOnClickListener {
            if (binding.etDesc.canUndo()) {
                binding.etDesc.undo()
            }
        }
    }

    private fun onRedoDescription() {
        binding.ivRedo.setOnClickListener {
            if (binding.etDesc.canRedo()) {
                binding.etDesc.redo()
            }
        }
    }

    private fun onDeleteNote() {
        binding.ivDelete.setOnClickListener {
            viewModel.deleteNote()
        }
    }

    private fun onTakeGroupListener() {
        supportFragmentManager.setFragmentResultListener(
            REQ_UID_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val result = bundle.getLong(REQ_UID_VALUE)
            printDebug { "NoteDetailFragment: Uid value is $result" }
            viewModel.updateGroupUid(result)
            viewModel.moveToGroup()
        }
    }

    private fun onMoveToGroup() {
        binding.ivMoveToGroup.setOnClickListener {
            val screen = NoteGroupPickerFragment()
            transaction(screen)
            screen.setOnClickListener(object : NoteGroupPickerListener {
                override fun onClickListener() {
                    onTakeGroupListener()
                }
            })
        }
    }

}
