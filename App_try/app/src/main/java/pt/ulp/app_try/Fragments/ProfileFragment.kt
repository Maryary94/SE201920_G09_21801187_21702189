package pt.ulp.app_try.Fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_account_settings.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import org.w3c.dom.Text
import pt.ulp.app_try.AccountSettingsActivity
import pt.ulp.app_try.Model.User

import pt.ulp.app_try.R

private const val ARG_PARAM1 ="param1"
private const val ARG_PARAM2 ="param2"

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment(val userIdentif:String) : Fragment() {
    private lateinit var profileId: String
    private lateinit var firebaseUser: FirebaseUser


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

//        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
//        if (pref != null) {
//            this.profileId = pref.getString("profileId", "none")!!
//            Log.println(Log.ASSERT,"ProfID",profileId)
//        }

        if (userIdentif == firebaseUser.uid) {
            Log.println(Log.ASSERT,"Perfil","Edit")
            profileId=userIdentif
            view.edit_account_settings_btn.text = "Edit Profile"
            //view.edit2_account_settings_btn.text = "Follow"

            view.edit_account_settings_btn.setOnClickListener {
                //startActivity(Intent(context, AccountSettingsActivity::class.java))
                //val getButtonText = view.edit_account_settings_btn.text.toString()
                //val getButtonText2 = view.edit2_account_settings_btn.text.toString()
                startActivity(Intent(context, AccountSettingsActivity::class.java))
            }

        } else{
            profileId=userIdentif
            Log.println(Log.ASSERT,"Perfil","Ver")
            checkFollowAndFollowingButtonStatus(view)
            view.edit_account_settings_btn.setOnClickListener {
                FollowVsFollowing(view)
            }
        }


        getFollowers(view)
        getFollowing(view)
        userInfo(view)
        return view

    }

    fun FollowVsFollowing(view:View){
        val getButtonText = view.findViewById<Button>(R.id.edit_account_settings_btn)
        Log.println(Log.ASSERT, "Perfil", getButtonText.text.toString())
        view.edit_account_settings_btn.setOnClickListener {
            when (getButtonText.text.toString()) {
                "Follow" -> {
                    Log.println(Log.ASSERT, "CHEGOU", "FOLLOW")

                    firebaseUser?.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference
                            .child("Follow").child(it1.toString())
                            .child("Following").child(profileId)
                            .setValue(true)
                    }

                    firebaseUser?.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference
                            .child("Follow").child(profileId)
                            .child("Followers").child(it1.toString())
                            .setValue(true)
                    }
                }

                "Following" -> {
                    Log.println(Log.ASSERT, "CHEGOU", "FOLLOWING")

                    firebaseUser?.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference
                            .child("Follow").child(it1.toString())
                            .child("Following").child(profileId)
                            .removeValue()
                        //                                .child("Follow").child(it1.toString())
                        //                                .child("Following").child(profileId)
                        //                                .removeValue()
                    }

                    firebaseUser?.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference
                            .child("Follow").child(profileId)
                            .child("Followers").child(it1.toString())
                            .removeValue()
                    }
                }
            }
        }
        getFollowing(view)
        getFollowers(view)
    }

    private fun checkFollowAndFollowingButtonStatus(view: View): Int{

        val followingRef = firebaseUser?.uid.let { it1 ->
            FirebaseDatabase.getInstance().reference
                .child("Follow").child(it1.toString())
                .child("Following")


        }

            followingRef.addValueEventListener(object: ValueEventListener{
                override fun onDataChange (p0: DataSnapshot) {
                    if(p0.child(profileId).exists()){
                        view.findViewById<Button>(R.id.edit_account_settings_btn).text = "Following"
                    }else{
                        view.findViewById<Button>(R.id.edit_account_settings_btn).text = "Follow"
                    }
                }

                override fun onCancelled (p0: DatabaseError) {

                }
            })
        return 0
    }

    private fun getFollowers (view: View) {
        val followersRef = FirebaseDatabase.getInstance().reference
            .child("Follow").child(profileId)
            .child("Followers")

        followersRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange (p0: DataSnapshot) {
                if(p0.exists()){
                    view.findViewById<TextView>(R.id.total_totalf).text = p0.childrenCount.toString()
                }
            }

            override fun onCancelled (p0: DatabaseError) {

            }
        })
    }

    private fun getFollowing (view: View) {
        val followingRef = FirebaseDatabase.getInstance().reference
                .child("Follow").child(profileId)
                .child("Following")

        followingRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange (p0: DataSnapshot) {
                if(p0.exists()){
                    view.findViewById<TextView>(R.id.total_following).text = p0.childrenCount.toString()
                }
            }

            override fun onCancelled (p0: DatabaseError) {

            }
        })
    }

    private fun userInfo (view: View){
        val usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(profileId)

        usersRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists()) {
                    val user = p0.getValue<User>(User::class.java)

                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile).into(view?.pro_image_profile_frag)
                    view.findViewById<TextView>(R.id.full_name_profile_frag).text= user!!.getFullname()
                    view.findViewById<TextView>(R.id.profile_fragment_username).text= user.getUsername()
                    //username_profile_frag.setText(user!!.getUsername())
                    // view?.bio_profile?.text =user!!.getBio()
                    // view?.bio_profile?.text =user!!.getBio()

                }
            }


            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    override fun onStop() {
        super.onStop()

//        val pref =context?.getSharedPreferences("PREFD", Context.MODE_PRIVATE)?.edit()
//        pref?.putString("profileId", firebaseUser.uid)
//        pref?.apply()
    }

    override fun onPause() {
        super.onPause()

//        val pref = context?.getSharedPreferences("PREFD", Context.MODE_PRIVATE)?.edit()
//        pref?.putString("profileId", firebaseUser.uid)
//        pref?.apply()
    }

    override fun onDestroy() {
        super.onDestroy()

//        val pref = context?.getSharedPreferences("PREFD", Context.MODE_PRIVATE)?.edit()
//        pref?.putString("profileId", firebaseUser.uid)
//        pref?.apply()
    }
}


// https://www.youtube.com/watch?v=OLLUcDLg-sI&list=PLxefhmF0pcPnPuhlBPBq_FdG2GXpgrhVJ&index=12