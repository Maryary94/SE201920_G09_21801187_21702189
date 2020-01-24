package pt.ulp.app_try

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import pt.ulp.app_try.Adapter.Model
import pt.ulp.app_try.Adapter.MyAdapter
import pt.ulp.app_try.Fragments.HomeFragment
import pt.ulp.app_try.Fragments.NotificationsFragment
import pt.ulp.app_try.Fragments.ProfileFragment
import pt.ulp.app_try.Fragments.SearchFragment

class MainActivity : AppCompatActivity() {



    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId){
            R.id.navigation_home -> {
                moveToFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true

            }
            R.id.nav_search -> {
                moveToFragment(SearchFragment())
                return@OnNavigationItemSelectedListener true

            }
//            R.id.nav_add -> {
//
//                return@OnNavigationItemSelectedListener true
//
//            }
//            R.id.nav_not -> {
//                moveToFragment(NotificationsFragment())
//                return@OnNavigationItemSelectedListener true
//
//            }
            R.id.nav_profile -> {
                moveToFragment(ProfileFragment(FirebaseAuth.getInstance().currentUser!!.uid))
                return@OnNavigationItemSelectedListener true

            }
        }

        false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener (onNavigationItemSelectedListener)
        //Por default vai estar no Home
        moveToFragment(HomeFragment())





    }

    private fun moveToFragment(fragment: Fragment){
        val fragmentsTrans =supportFragmentManager.beginTransaction()
        fragmentsTrans.replace(R.id.fragment_container, fragment)
        fragmentsTrans.commit()
    }



}
