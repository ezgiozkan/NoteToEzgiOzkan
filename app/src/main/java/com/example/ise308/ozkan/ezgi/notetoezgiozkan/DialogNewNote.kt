package com.example.ise308.ozkan.ezgi.notetoezgiozkan

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DialogNewNote: DialogFragment() {


    //create alert pop-up screen

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val builder = AlertDialog.Builder(requireActivity())


        //xml layout into a Kotlin object

        val inflater = requireActivity().layoutInflater

        val dialogView = inflater.inflate(R.layout.dialog_new_note, null)

        val editTitle = dialogView.findViewById(R.id.editTitle) as EditText

        val editDescription = dialogView.findViewById(R.id.editDescription) as EditText

        val checkBoxIdea = dialogView.findViewById(R.id.checkBoxIdea) as CheckBox

        val checkBoxTodo =  dialogView.findViewById(R.id.checkBoxTodo) as CheckBox

        val checkBoxImportant = dialogView.findViewById(R.id.checkBoxImportant) as CheckBox

        val btnCancel = dialogView.findViewById(R.id.btnCancel) as Button

        // the user taps to Ok button
        val btnOK = dialogView.findViewById(R.id.btnOK) as Button


        //User can add a new note
        builder.setView(dialogView).setMessage("Add a new note")

        // If user tap to cancel button dismiss will be used

        btnCancel.setOnClickListener {
            dismiss()
        }

        // If user taps Ok button
        btnOK.setOnClickListener {

            //Application creates a new note
            val newNote = Note()

            //user creates a title, description,idea, to do message and important notes

            newNote.title = editTitle.text.toString()
            newNote.description = editDescription.text.toString()
            newNote.idea = checkBoxIdea.isChecked
            newNote.todo = checkBoxTodo.isChecked
            newNote.important = checkBoxImportant.isChecked


            val callingActivity = activity as MainActivity?


            callingActivity!!.createNewNote(newNote)


            dismiss()
        }

        return builder.create()



    }
}