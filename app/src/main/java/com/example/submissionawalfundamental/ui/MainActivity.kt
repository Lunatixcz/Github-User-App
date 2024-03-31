package com.example.submissionawalfundamental.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawalfundamental.R
import com.example.submissionawalfundamental.databinding.ActivityMainBinding
import com.example.submissionawalfundamental.helper.SettingPreferences
import com.example.submissionawalfundamental.helper.SettingViewModelFactory
import com.example.submissionawalfundamental.helper.dataStore
import com.example.submissionawalfundamental.ui.adapter.SearchAdapter
import com.example.submissionawalfundamental.viewModel.MainViewModel
import com.example.submissionawalfundamental.viewModel.ThemeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: SearchAdapter
    private val mainViewModel by viewModels<MainViewModel>()
    private val themeViewModel: ThemeViewModel by viewModels {
        SettingViewModelFactory(SettingPreferences.getInstance(application.dataStore))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        val menuItem = menu.findItem(R.id.menu_setting)
        val menuIcon: Drawable? = menuItem.icon
        themeViewModel.getThemeSettings().observe(this) {isDarkModeActive ->
            if (isDarkModeActive) {
                menuIcon?.setTint(ContextCompat.getColor(this@MainActivity, R.color.white))
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            R.id.menu_setting -> {
                startActivity(Intent(this, ThemeSettingActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    binding.searchBar.setText(binding.searchView.text)
                    searchView.hide()
                    val query = searchView.text.toString()
                    mainViewModel.searchUsers(query)
                    false
                }
        }

        showRecyclerView()

        mainViewModel.userList.observe(this) { user ->
            userAdapter.submitList(user)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mainViewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showRecyclerView() {
        userAdapter = SearchAdapter()
        binding.rvUsers.adapter = userAdapter
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
