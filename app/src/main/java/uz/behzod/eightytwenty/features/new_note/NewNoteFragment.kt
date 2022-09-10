package uz.behzod.eightytwenty.features.new_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import uz.behzod.eightytwenty.R
import uz.behzod.eightytwenty.databinding.FragmentNewNoteBinding
import uz.behzod.eightytwenty.domain.model.NoteDomainModel
import uz.behzod.eightytwenty.utils.view.viewBinding
import java.time.ZonedDateTime

@AndroidEntryPoint
class NewNoteFragment : Fragment(R.layout.fragment_new_note) {

    private val binding by viewBinding(FragmentNewNoteBinding::bind)
    private val viewModel: NewNoteViewModel by viewModels()
    private val args: NewNoteFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        initTimestamp()
        insertNote()
    }

    private fun initTimestamp() {
        binding.tvDate.text = ZonedDateTime.now().toString()
    }

    private fun insertNote() {
        binding.btnSaveOrCancel.setOnClickListener {
            viewModel.insertNote(createNote()).run {
                Toast.makeText(requireContext(),"Note is saved",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_newNoteFragment_to_noteFragment)
            }
        }

    }

    private fun createNote(): NoteDomainModel {
        val title = binding.etTitle.text.toString()
        val description = binding.etDesc.text.toString()
        val timestamp = ZonedDateTime.now()
        val isTrashed = false
        val categoryId = args.categoryId

        return NoteDomainModel(
            title = title,
            description = description,
            timestamp = timestamp,
            isTrashed = isTrashed,
            categoryId = categoryId
        )
    }
}