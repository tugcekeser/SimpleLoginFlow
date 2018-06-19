package com.example.rabia.zumekotlin.view.menu

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.rabia.zumekotlin.MySharedPreferences
import com.example.rabia.zumekotlin.R
import com.example.rabia.zumekotlin.butterknife.bindView
import com.example.rabia.zumekotlin.models.User
import com.example.rabia.zumekotlin.view.home.HomeActivity

class MenuHomeActivity : AppCompatActivity() {

    val tvWelcome: TextView by bindView(R.id.tvWelcome)
    val btnLogout: Button by bindView(R.id.btnLogout)

    var mySharedPreferences: MySharedPreferences? = null

    companion object {

        private val INTENT_USER_NAME = "user_name"

        fun newIntent(context: Context, user: User?): Intent {
            val intent = Intent(context, MenuHomeActivity::class.java)
            intent.putExtra(INTENT_USER_NAME, user?.firstName + " " + user?.lastName)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_home)

        mySharedPreferences = MySharedPreferences(this)

        val name = intent.getStringExtra(INTENT_USER_NAME)
                ?: throw IllegalStateException("field $INTENT_USER_NAME missing in Intent")

        tvWelcome.text = "Welcome " + name

        btnLogout.setOnClickListener { v -> signOut() }

    }

    fun signOut() {
        mySharedPreferences?.logout()
        val intent = HomeActivity.newIntent(this)
        startActivity(intent)
        finish()
    }
}
