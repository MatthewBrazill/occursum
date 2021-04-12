package de.brazill.occursum.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.brazill.occursum.R
import de.brazill.occursum.activities.editcontact.EditContactActivity
import de.brazill.occursum.main.MainApp
import de.brazill.occursum.models.ContactModel
import kotlinx.android.synthetic.main.activity_view_contact.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor

class ViewContactActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        info("View Contact Started...")

        val contact = intent.extras?.getParcelable<ContactModel>("contact")!!

        setContentView(R.layout.activity_view_contact)
        app = application as MainApp

        //Add the image to the view.
        if (contact.img == 0)  view_contact_img.setImageResource(R.drawable.ic_default_avatar)
        else view_contact_img.setImageResource(contact.img)

        //Concatenate the names and add them to the view.
        val fullName = "${contact.firstName} ${contact.lastName}"
        view_contact_name.text = fullName

        //Add the email to the view.
        view_contact_email.text = contact.email

        //Add the phone number to the view.
        view_contact_phone.text = contact.phone

        //Create the well formatted 'likes' String to add to the view.
        var likes = ""
        for (like in contact.likes) likes += "- $like\n"
        view_contact_likes.text = likes

        //Create the well formatted 'dislikes' String.
        var dislikes = ""
        for (dislike in contact.dislikes) dislikes += "- $dislike\n"
        view_contact_dislikes.text = dislikes

        view_contact_delete_button.setOnClickListener {
            app.contacts.delete(contact)
            setResult(RESULT_OK)
            finish()
        }

        view_contact_edit_button.setOnClickListener {
            startActivityForResult(intentFor<EditContactActivity>().putExtra("contact", contact), 1)
            setResult(RESULT_OK)
            finish()
        }
    }
}