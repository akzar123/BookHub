package com.internshala.bookhub.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.internshala.bookhub.R
import com.internshala.bookhub.adapter.FavoriteRecyclerAdapter
import com.internshala.bookhub.database.BookDatabase
import com.internshala.bookhub.database.BookEntity


class FavoriteFragment : Fragment() {
    lateinit var recyclerFavorite: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var recyclerAdapter: FavoriteRecyclerAdapter
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    var dbBookList= listOf<BookEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_favorite, container, false)
        recyclerFavorite=view.findViewById(R.id.recyclerFavorite)
        progressLayout=view.findViewById(R.id.progressLayout)
        progressLayout.visibility=View.VISIBLE
        progressBar=view.findViewById(R.id.progressBar)
        layoutManager=GridLayoutManager(activity as Context,2)
        dbBookList=RetrieveFavorite(activity as Context).execute().get()
        if (activity!=null){
            progressLayout.visibility=View.GONE
            recyclerAdapter=FavoriteRecyclerAdapter(activity as Context,dbBookList)
            recyclerFavorite.adapter=recyclerAdapter
            recyclerFavorite.layoutManager=layoutManager
        }
        return view
    }
    class RetrieveFavorite(val context: Context):AsyncTask<Void,Void,List<BookEntity>>() {
        override fun doInBackground(vararg params: Void?): List<BookEntity> {
            val db=Room.databaseBuilder(context, BookDatabase::class.java,"books-db").build()
            return db.bookDao().getAllBook()
        }
    }

}
