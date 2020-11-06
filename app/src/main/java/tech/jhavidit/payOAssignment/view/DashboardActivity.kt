package tech.jhavidit.payOAssignment.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*
import tech.jhavidit.payOAssignment.R
import tech.jhavidit.payOAssignment.databinding.ActivityDashboardBinding
class DashboardActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{
    private lateinit var binding:ActivityDashboardBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding = DataBindingUtil.setContentView(this,R.layout.activity_dashboard)
        firebaseAuth =  FirebaseAuth.getInstance()
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
       binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

       binding.navView.setNavigationItemSelectedListener(this)

        displayScreen(R.id.nav_home)


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun displayScreen(id: Int){

        // val fragment =  when (id){

        when (id){
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction().replace(binding.relativeLayout.id, HomeFragment()).commit()
            }

            R.id.nav_profile -> {
                supportFragmentManager.beginTransaction().replace(binding.relativeLayout.id, ProfileFragment()).commit()
            }
            R.id.nav_logout->
            {
                firebaseAuth.signOut()
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
            }
        }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
         displayScreen(item.itemId)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}