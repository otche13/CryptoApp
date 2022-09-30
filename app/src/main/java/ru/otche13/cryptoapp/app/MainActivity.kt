package ru.otche13.cryptoapp.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.otche13.cryptoapp.R
import ru.otche13.cryptoapp.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
            ?: return

        with(navHostFragment.navController) {

            setupActionBarWithNavController(this )
        }
    }




    override fun onSupportNavigateUp(): Boolean {
        navHostFragment.navController.navigateUp()
        return super.onSupportNavigateUp()
    }

}