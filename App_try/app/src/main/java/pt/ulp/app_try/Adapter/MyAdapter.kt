package pt.ulp.app_try.Adapter

import android.content.Context
import android.util.Log
import android.view.Display

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.row.view.*
import pt.ulp.app_try.R
import pt.ulp.app_try.Adapter.Model


class MyAdapter(val ModelList: ArrayList<Model>, val clickListener:(Model,Int) -> Unit):RecyclerView.Adapter<MyAdapter.AdapterViewHolder>(){//var mCtx: Context, var resources:Int, var items:List<Model>): ArrayAdapter<Model>(mCtx, resources, items) {
//    override fun getView(position: Int, convertView: View?,parent: ViewGroup): View {
//        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
//        val view:View = layoutInflater.inflate(resources, null)
//
//        val titleTextView: TextView = view.findViewById(R.id.textView1)
//        val NameTextView:TextView = view.findViewById(R.id.textView3)
//        val emailTextView:TextView = view.findViewById(R.id.textView4)
//        var mItem: Model = items[position]
//        titleTextView.text = mItem.title
//        NameTextView.text = mItem.name
//        emailTextView.text = mItem.email
//
//
//        return view
//    }

    class AdapterViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        return AdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row , parent, false))
    }

    override fun getItemCount()= ModelList.size

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val ModelL= ModelList[position]
        holder.view.textView1.text= ModelL.title
        holder.view.textView3.text= ModelL.name
        holder.view.textView4.text= ModelL.email

        holder.view.setOnClickListener{ clickListener(ModelL,position) }
    }
}