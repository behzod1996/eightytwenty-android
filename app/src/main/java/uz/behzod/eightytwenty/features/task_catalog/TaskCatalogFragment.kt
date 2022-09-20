package uz.behzod.eightytwenty.features.task_catalog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.behzod.eightytwenty.R
import uz.behzod.eightytwenty.data.local.entities.TaskCatalogEntity
import uz.behzod.eightytwenty.databinding.FragmentCatalogTaskBinding
import uz.behzod.eightytwenty.utils.extension.gone
import uz.behzod.eightytwenty.utils.extension.hide
import uz.behzod.eightytwenty.utils.extension.navController
import uz.behzod.eightytwenty.utils.extension.show
import uz.behzod.eightytwenty.utils.view.viewBinding

@AndroidEntryPoint
class TaskCatalogFragment : Fragment(R.layout.fragment_catalog_task) {

    private val binding by viewBinding(FragmentCatalogTaskBinding::bind)
    private val viewModel: TaskCatalogViewModel by viewModels()

    private lateinit var adapter: TaskCatalogAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()



        addNewCatalog()

        onClickNewCatalog()
        onNavigateToTask()
        onNavigateToSearchTask()

    }

    private fun setupUI() {
        adapter = TaskCatalogAdapter()
        binding.rvTaskCatalogs.adapter = adapter

        fetchCatalogs()
    }

    private fun onNavigateToTask() {
        with(binding.ivBack) {
            setOnClickListener {
                val route = TaskCatalogFragmentDirections.actionTaskCatalogFragmentToTaskFragment()
                navController.navigate(route)
            }
        }
    }

    private fun onNavigateToSearchTask() {
        binding.ivSearch.setOnClickListener {
            val route =
                TaskCatalogFragmentDirections.actionTaskCatalogFragmentToSearchCatalogFragment()
            navController.navigate(route)
        }
    }

    private fun fetchCatalogs() = lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiState.collect { state ->
                when (state) {
                    TaskCatalogUiState.Empty -> {

                    }
                    is TaskCatalogUiState.Failure -> {

                    }
                    TaskCatalogUiState.Loading -> {

                    }
                    is TaskCatalogUiState.Success -> {
                        adapter.submitList(state.data)
                    }
                }
            }
        }
    }

    private fun onClickNewCatalog() {
        binding.btnNewCategoryTask.setOnClickListener {
            binding.btnNewCategory.show()
            binding.btnNewSubTask.show()
            binding.btnCancel.show()
            binding.btnNewCategoryTask.hide()
        }
    }

    private fun addNewCatalog() {
        binding.btnNewCategory.setOnClickListener {
            binding.etNewCategory.show()
            binding.btnNewCategory.setOnClickListener {
                insertNewCatalog().run {
                    Toast.makeText(
                        requireContext(),
                        "Category is successfully saved",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.btnNewCategory.hide()
                    binding.btnNewSubTask.hide()
                    binding.btnCancel.hide()
                    binding.btnNewCategoryTask.show()
                    binding.etNewCategory.gone()
                }

            }
        }
    }

    private fun insertNewCatalog() {
        viewModel.insertCatalog(
            TaskCatalogEntity(
                name = binding.etNewCategory.text.toString().trim()
            )
        )
    }
}