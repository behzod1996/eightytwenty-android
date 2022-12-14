package uz.behzod.eightytwenty.features.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import uz.behzod.eightytwenty.R
import uz.behzod.eightytwenty.databinding.ActivityMainBinding
import uz.behzod.eightytwenty.utils.extension.gone
import uz.behzod.eightytwenty.utils.extension.show
import uz.behzod.eightytwenty.utils.view.viewBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val FLAG_FULL_SCREEN = 1024
    }

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullContent()
        setContentView(binding.root)
        setupNavHost()
    }

    private fun setupNavHost() {
        val navController = supportFragmentManager.findFragmentById(R.id.eighty_twenty_nav_host)
        navHost = navController as NavHostFragment?
            ?: return

        NavigationUI.setupWithNavController(binding.bottomNav, navHost.navController)

        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.newNoteFragment, R.id.categoryNoteFragment, R.id.newHabitFragment,
                R.id.signInFragment, R.id.signUpFragment, R.id.selectProductivityFragment,
                R.id.addPillFragment
                -> {
                    binding.bottomNav.gone()
                }
                else -> {
                    binding.bottomNav.show()
                }
            }
        }
    }

    private fun setFullContent() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setFlags(FLAG_FULL_SCREEN, FLAG_FULL_SCREEN)

        supportActionBar?.hide()
    }
}