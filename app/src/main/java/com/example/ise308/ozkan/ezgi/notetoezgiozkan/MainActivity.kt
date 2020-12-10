package com.example.ise308.ozkan.ezgi.notetoezgiozkan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import  kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    //add chapter 17
    //private val noteList = ArrayList<Note>()

    private var mSerializer: JsonSerializer? = null

    private var noteList: ArrayList<Note>? = null

    private var recyclerView: RecyclerView? = null
    private var adapter: NoteAdapter? = null

    private var showDividers: Boolean = false

    //if sign-in screen for the user create a string called username
    //val username = "Bob"
    //private var tempNote = Note()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val dialog = DialogNewNote()
            dialog.show(supportFragmentManager, "")
        }

/*
        fab.setOnClickListener { view ->

        }*/
        mSerializer = JsonSerializer("NoteToSelf.json", applicationContext)


        try {
            noteList = mSerializer!!.load()
        } catch (e: Exception) {
            noteList = ArrayList()
            Log.e("Error loading notes: ", "", e)
        }



        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView

        adapter = NoteAdapter(this, this.noteList!!)

        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = layoutManager

        recyclerView!!.itemAnimator = DefaultItemAnimator()

        //recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
       // recyclerView!!.addItemDecoration(
             //   DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        recyclerView!!.adapter = adapter

       // val button = findViewById<View>(R.id.button) as Button
        //button.setOnClickListener {
            //chapter 14
            //val dialog = DialogShowNote()
           // dialog.sendNoteSelected(tempNote)
          //  dialog.show(supportFragmentManager, "123")

       // }

    }




    fun createNewNote(n: Note) {

        noteList!!.add(n)
        adapter!!.notifyDataSetChanged()

    }

    fun showNote(noteToShow: Int) {
        val dialog = DialogShowNote()
        dialog.sendNoteSelected(noteList!![noteToShow])
        dialog.show(supportFragmentManager, "")
    }


    //new Intent object
    //val myIntent = Intent(this, SettingsActivity::class.java)
    //myIntent.putExtra("USER_NAME",username)
    //new activity
    //startActivity(myIntent)
    private fun saveNotes() {
        try {
            mSerializer!!.save(this.noteList!!)


        } catch (e: Exception) {
            Log.e("Error Saving Notes", "", e)
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {



        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this,
                    SettingsActivity::class.java)

                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onPause() {
        super.onPause()

        saveNotes()
    }

    override fun onResume() {
        super.onResume()

        val prefs = getSharedPreferences("Note to self", Context.MODE_PRIVATE)

        showDividers = prefs.getBoolean(
            "dividers", true)


        if (showDividers)
            recyclerView!!.addItemDecoration(
                DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        else {

            if (recyclerView!!.itemDecorationCount > 0)
                recyclerView!!.removeItemDecorationAt(0)
        }

    }



}



