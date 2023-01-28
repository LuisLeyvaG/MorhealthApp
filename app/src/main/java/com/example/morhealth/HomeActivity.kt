package com.example.morhealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.morhealth.databinding.ActivityHomeBinding
import com.example.morhealth.databinding.ActivityLoginBinding
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var drawer: DrawerLayout

    private val TAG: String = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initNavigationView()

    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.bar_title, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        //drawer.setScrimColor(resources.getColor(R.color.white))
        toggle.syncState()

    }

    private fun initNavigationView() {
        var navigationView: NavigationView = findViewById(R.id.nav_view)

        navigationView.setNavigationItemSelectedListener(this)

        var headerView: View = LayoutInflater.from(this).inflate(R.layout.nav_header_main, navigationView, false)
        navigationView.removeHeaderView(headerView)
        navigationView.addHeaderView(headerView)

        var headerTitle: TextView = headerView.findViewById(R.id.tvNavName)
        headerTitle.text = LoginActivity.user!!.name + " " + LoginActivity.user!!.lastname_p

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

    }
}