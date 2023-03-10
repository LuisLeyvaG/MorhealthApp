package com.example.morhealth

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewSwitcher
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.morhealth.databinding.ActivityHomeBinding
import com.example.morhealth.homefragments.HealthFragment
import com.example.morhealth.homefragments.HomeFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var drawer: DrawerLayout
    lateinit var appBarLayout: AppBarLayout

    private val TAG: String = "HomeActivity"

    private lateinit var homeFragment: HomeFragment
    private lateinit var healthFragment: HealthFragment
    //private lateinit var nutritionFragment: NutritionFragment
    //private lateinit var fitnessFragment: FitnessFragment

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (LoginActivity.user == null) {
            goMainActivity()
            return
        }

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeFragment = HomeFragment()
        healthFragment = HealthFragment()
        //nutritionFragment = NutritionFragment()
        //fitnessFragment = FitnessFragment()

        initPreferences()
        initToolbar()
        initNavigationView()
        initBottomNavigationView()

    }

    override fun onStart() {
        super.onStart()

        when (binding.bottomNavigationView.selectedItemId) {
            R.id.action_home -> setFragment(homeFragment)
            R.id.action_health -> setFragment(healthFragment)
            //R.id.action_nutrition -> setFragment(nutritionFragment)
            //R.id.action_fitness -> setFragment(fitnessFragment)
            else -> setFragment(homeFragment)
        }
    }

    private fun initToolbar() {
        appBarLayout = findViewById(R.id.app_bar_layout)
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

    private fun initNavigationView() = GlobalScope.launch(Dispatchers.Main) {
        val navigationView: NavigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this@HomeActivity)

        val headerView: View =
            LayoutInflater.from(this@HomeActivity)
                .inflate(R.layout.nav_header_main, navigationView, false)
        navigationView.removeHeaderView(headerView)
        navigationView.addHeaderView(headerView)

        val headerTitle: TextView = headerView.findViewById(R.id.tvNavName)
        headerTitle.text = LoginActivity.user!!.name + " " + LoginActivity.user!!.lastname_p

        val headerDesc: TextView = headerView.findViewById(R.id.tvNavDesc)
        headerDesc.text = sharedPreferences.getString("userDesc", "No description")

        val icon: ImageView = headerView.findViewById(R.id.ivEditDesc)
        icon.setOnClickListener { toggleDesc(icon) }
    }

    private fun initBottomNavigationView() {
        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    setFragment(homeFragment)
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }
                R.id.action_health -> {
                    setFragment(healthFragment)
                    Toast.makeText(this, "Health", Toast.LENGTH_SHORT).show()
                }
                R.id.action_nutrition -> {
                    Toast.makeText(this, "Nutrition", Toast.LENGTH_SHORT).show()
                }
                R.id.action_fitness -> {
                    Toast.makeText(this, "Fitness", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        bottomNavigationView.setOnNavigationItemReselectedListener(null)

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
            R.id.action_health -> {
                Toast.makeText(this, "Health", Toast.LENGTH_SHORT).show()
            }
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

    private fun goMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }

    fun goMetric(v: View) {
        val intent = Intent(this, MetricActivity::class.java)
        intent.putExtra("metricTag", v.tag.toString())
        startActivity(intent)
    }

    private fun signOut() {
        LoginActivity.user = null
        goMainActivity()
    }
}