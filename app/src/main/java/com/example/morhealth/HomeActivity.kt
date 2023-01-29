package com.example.morhealth

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewSwitcher
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.morhealth.databinding.ActivityHomeBinding
import com.example.morhealth.databinding.ActivityLoginBinding
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var drawer: DrawerLayout

    private val TAG: String = "HomeActivity"

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (LoginActivity.user == null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        initPreferences()
    }

    override fun onResume() {
        super.onResume()
        initToolbar()
        initNavigationView()
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.bar_title,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        //drawer.setScrimColor(resources.getColor(R.color.white))
        toggle.syncState()

    }

    private fun initNavigationView() {
        var navigationView: NavigationView = findViewById(R.id.nav_view)

        navigationView.setNavigationItemSelectedListener(this)

        var headerView: View =
            LayoutInflater.from(this).inflate(R.layout.nav_header_main, navigationView, false)
        navigationView.removeHeaderView(headerView)
        navigationView.addHeaderView(headerView)

        var headerTitle: TextView = headerView.findViewById(R.id.tvNavName)
        headerTitle.text = LoginActivity.user!!.name + " " + LoginActivity.user!!.lastname_p

        val headerDesc: TextView = headerView.findViewById(R.id.tvNavDesc)
        headerDesc.text = sharedPreferences.getString("userDesc", "No description")

        val icon: ImageView = headerView.findViewById(R.id.ivEditDesc)
        icon.setOnClickListener { toggleDesc(icon) }
    }

    private fun initPreferences() {
        sharedPreferences = getSharedPreferences(LoginActivity.user!!.username, MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    private fun toggleDesc(icon: ImageView) {
        val switcher: ViewSwitcher = findViewById(R.id.vsNavDesc)
        val tvNavDesc: TextView = findViewById(R.id.tvNavDesc)
        val etNavDesc: EditText = findViewById(R.id.etNavDesc)

        if (switcher.currentView.id == tvNavDesc.id) {
            if (sharedPreferences.contains("userDesc")) {
                etNavDesc.text =
                    Editable.Factory.getInstance().newEditable(tvNavDesc.text.toString())
            } else {
                etNavDesc.hint = "No description"
            }
            icon.setImageDrawable(getDrawable(R.drawable.ic_list_check))
            switcher.showNext()

        } else {
            if (etNavDesc.text.toString().isNotBlank()) {
                editor.putString("userDesc", etNavDesc.text.toString()).apply()
            } else {
                editor.remove("userDesc").apply()
            }

            tvNavDesc.text = sharedPreferences.getString("userDesc", "No description")
            icon.setImageDrawable(getDrawable(R.drawable.ic_edit_note))
            switcher.showPrevious()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_item_home -> {
                // do something
            }
            R.id.nav_item_notif -> {
                goNotifActivity()
            }
            R.id.nav_item_calendar -> {
                goCalendarActivity()
            }
            R.id.nav_item_profile -> {
                goProfileActivity()
            }
            R.id.nav_item_settings -> {
                goSettingsActivity()
            }
            R.id.nav_item_logout -> {
                signOut()
            }
            R.id.nav_item_about -> {
                // do something
            }
        }

        drawer.closeDrawer(GravityCompat.START)

        return true
    }

    private fun goNotifActivity() {
        val intent = Intent(this, NotifActivity::class.java)
        startActivity(intent)
    }

    private fun goCalendarActivity() {
        val intent = Intent(this, CalendarActivity::class.java)
        startActivity(intent)
    }

    private fun goProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun goSettingsActivity() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun signOut() {
        val intent = Intent(this, MainActivity::class.java)
        LoginActivity.user = null
        startActivity(intent)
        finish()
    }
}