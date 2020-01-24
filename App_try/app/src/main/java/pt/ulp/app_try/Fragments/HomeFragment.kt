package pt.ulp.app_try.Fragments

import android.app.ListActivity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pt.ulp.app_try.Adapter.Model
import pt.ulp.app_try.Adapter.MyAdapter
import pt.ulp.app_try.R
import pt.ulp.app_try.ui.home.HomeViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*

private const val ARG_PARAM1 ="param1"
private const val ARG_PARAM2 ="param2"

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       //Inflate the Layout for this fragment
        val v= inflater.inflate(R.layout.fragment_home, container, false)


//        var listView= v.findViewById<ListView>(R.id.listView_home)
//        var list= resources.getStringArray(R.array.jobs)
////        var list = emptyList<Model>()
////
////        list+= listOf(Model("Carpintaria", "Ricarde", "ricardo@gmail.com"))
////        list+= listOf(Model("Mecanico", "Marco", "marco@gmail.com"))
//
//        val adapter = ArrayAdapter<String>(this.activity!!, android.R.layout.simple_list_item_1, list)
//        (this.activity as ListActivity?)!!.listAdapter= adapter

//        var list = mutableListOf<Model>()
//
//        list.add(Model("Carpintaria", "Ricarde", "ricardo@gmail.com"))
//        listView?.adapter = MyAdapter(this.activity!!, R.layout.row,list)


//        listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position: Int, id: Long ->
//
//            if (position == 0) {
//
//                val TO = arrayOf("veoliveira94@gmail.com")
//
//                val emailIntent = Intent(Intent.ACTION_SEND)
//                emailIntent.data = Uri.parse("mailto:")
//                emailIntent.type = "text/plain"
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO)
//
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject")
//                emailIntent.putExtra(Intent.EXTRA_TEXT, "Some message added in here")
//
//                try {
//                    startActivity(Intent.createChooser(emailIntent, "Send mail..."))
//                } catch (ex: ActivityNotFoundException) {
//                    Toast.makeText(this.activity, "There is no email client installed.", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {


        var list= emptyList<Model>()

        list+= listOf(Model("Carpintaria", "Ricarde", "ricardo@gmail.com"))
        list+= listOf(Model("Mecanico", "Marco", "marco@gmail.com"))


        recyclerview_home.layoutManager = LinearLayoutManager(activity)
        recyclerview_home?.adapter = MyAdapter(list as ArrayList<Model>) { itemDto: Model, position: Int ->
            //Log.println(Log.ASSERT,"MyActivity", "Clicked on item  ${itemDto.name} at position $position")
            val TO = itemDto.email

            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO)

            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Some message added in here")

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this.activity, "There is no email client installed.", Toast.LENGTH_SHORT).show()
            }
        }
        Log.println(Log.ASSERT,"GONE","Recycler")


        //view!!.findViewById<RecyclerView>(R.id.recyclerview_home).setOnClickListener { view ->

//            if (position == 0) {
//
//                val TO = arrayOf("veoliveira94@gmail.com")
//
//                val emailIntent = Intent(Intent.ACTION_SEND)
//                emailIntent.data = Uri.parse("mailto:")
//                emailIntent.type = "text/plain"
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO)
//
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject")
//                emailIntent.putExtra(Intent.EXTRA_TEXT, "Some message added in here")
//
//                try {
//                    startActivity(Intent.createChooser(emailIntent, "Send mail..."))
//                } catch (ex: ActivityNotFoundException) {
//                    Toast.makeText(this.activity, "There is no email client installed.", Toast.LENGTH_SHORT).show()
//                }
//            }

        //}
        super.onActivityCreated(savedInstanceState)
    }
}