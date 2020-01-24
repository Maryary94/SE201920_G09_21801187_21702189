package pt.ulp.app_try.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import pt.ulp.app_try.Fragments.ProfileFragment
import pt.ulp.app_try.Model.User
import pt.ulp.app_try.R

class UserAdapter (private var mContext: Context,
                   private var mUser: List <User>,
                   private var isFragment: Boolean =false) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.user_item_layout,parent,false)
        return UserAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mUser.size
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {

        val user = mUser[position]
        holder.userNameTextView.text = user.getUsername()
        holder.userFullnameTextView.text = user.getFullname()
        Picasso.get().load(user.getImage()).placeholder(R.drawable.profile).into(holder.userProfileImage)

        checkFollowingStatus(user.getUID(), holder.fButton)

        holder.itemView.setOnClickListener(View.OnClickListener {
//            val pref =mContext.getSharedPreferences("PREFD", Context.MODE_PRIVATE).edit()
//            pref.putString("profileId", user.getUID())
//            pref.apply()
            Log.println(Log.ASSERT,"Acertou",user.getUsername())
            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment(user.getUID())).commit()
        })

        holder.fButton.setOnClickListener {
            if (holder.fButton.text.toString() == "Follow"){

                firebaseUser?.uid.let { it1 ->
                    FirebaseDatabase.getInstance().reference
                        .child("Follow").child(it1.toString())
                        .child("Following").child(user.getUID())
                        .setValue(true).addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                firebaseUser?.uid.let { it1 ->
                                    FirebaseDatabase.getInstance().reference
                                        .child("Follow").child(user.getUID())
                                        .child("Followers").child(it1.toString())
                                        .setValue(true).addOnCompleteListener { task ->
                                            if(task.isSuccessful){

                                        }
                                    }
                                }

                            }
                        }
                }
            }
            else
            {
                firebaseUser?.uid.let { it1 ->
                    FirebaseDatabase.getInstance().reference
                        .child("Follow").child(it1.toString())
                        .child("Following").child(user.getUID())
                        .removeValue().addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                firebaseUser?.uid.let { it1 ->
                                    FirebaseDatabase.getInstance().reference
                                        .child("Follow").child(user.getUID())
                                        .child("Followers").child(it1.toString())
                                        .removeValue().addOnCompleteListener { task ->
                                            if(task.isSuccessful){

                                            }
                                        }
                                }

                            }
                        }
                }

            }
        }
    }



    class ViewHolder (@NonNull itemView: View): RecyclerView.ViewHolder(itemView){

        var userNameTextView : TextView =itemView.findViewById(R.id.user_name_search)
        var userFullnameTextView : TextView =itemView.findViewById(R.id.user_full_name_search)
        var userProfileImage : CircleImageView =itemView.findViewById(R.id.user_profile_image_search)
        var fButton : Button =itemView.findViewById(R.id.btn_search)

    }

    private fun checkFollowingStatus(uid: String, fButton: Button) {
        val followingRef = firebaseUser?.uid.let { it1 ->
            FirebaseDatabase.getInstance().reference
                .child("Follow").child(it1.toString())
                .child("Following")

        }

        followingRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if(datasnapshot.child(uid).exists()){
                    fButton.text = "Following"
                }else{
                    fButton.text ="Follow"
                }

            }

            override fun onCancelled(p0: DatabaseError) {
                //11
            }
        } )
    }

}