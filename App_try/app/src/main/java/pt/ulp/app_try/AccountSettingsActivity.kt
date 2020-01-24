package pt.ulp.app_try

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_account_settings.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.user_item_layout.*
import pt.ulp.app_try.Fragments.ProfileFragment
import pt.ulp.app_try.Model.User

class AccountSettingsActivity : AppCompatActivity() {
    private lateinit var firebaseUser: FirebaseUser
    private var checker = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        logout_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this@AccountSettingsActivity, SignInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        close_profile_btn.setOnClickListener{
            val intent = Intent(this@AccountSettingsActivity, MainActivity::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }


        save_infor_profile_btn.setOnClickListener {
            if (checker == "clicked"){

            } else {
                updateUserInfoOnly()
            }


        }

        userInfo()
    }

    private fun updateUserInfoOnly() {
            // https://www.youtube.com/watch?v=P9ArkxpA8TQ&list=PLxefhmF0pcPnPuhlBPBq_FdG2GXpgrhVJ&index=13

        when {
            full_name_profile_frag.text.toString() =="" -> {
                Toast.makeText(this, "Please write full name first", Toast.LENGTH_LONG).show()
            }
            username_profile_frag.text.toString() =="" -> {
                Toast.makeText(this, "Please write username first", Toast.LENGTH_LONG).show()
            }
            else -> {
                val usersRef = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser.uid)

                val userMap = HashMap<String, Any>()
                userMap["fullname"] = full_name_profile_frag.text.toString().toLowerCase()
                userMap["username"] = username_profile_frag.text.toString().toLowerCase()

                usersRef.updateChildren(userMap)

                Toast.makeText(this, "Account information has been updated successfully", Toast.LENGTH_LONG).show()

                val intent = Intent(this@AccountSettingsActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


    }

    private fun userInfo (){
        val usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.uid)

        usersRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists()) {
                    val user = p0.getValue<User>(User::class.java)

                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile).into(profile_image_view_profile_frag)
                    full_name_profile_frag.setText(user!!.getFullname())
                    username_profile_frag.setText(user!!.getUsername())
                    // view?.bio_profile?.text =user!!.getBio()
                    // view?.bio_profile?.text =user!!.getBio()

                }
            }


            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}
