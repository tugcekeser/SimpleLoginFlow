package com.example.rabia.zumekotlin.view.home

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.rabia.zumekotlin.MySharedPreferences
import com.example.rabia.zumekotlin.R
import com.example.rabia.zumekotlin.api.LoginService
import com.example.rabia.zumekotlin.butterknife.bindView
import com.example.rabia.zumekotlin.models.RequestUserAuth
import com.example.rabia.zumekotlin.models.User
import com.example.rabia.zumekotlin.view.menu.MenuHomeActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeActivity : AppCompatActivity() {

    val etMobileNumber: EditText by bindView(R.id.etMobileNumber)
    val etPassword: EditText by bindView(R.id.etPassword)
    val btnLogin: Button by bindView(R.id.btnLogin)

    var mySharedPreferences: MySharedPreferences? = null
    private var disposable: Disposable? = null

    /*private val repository by lazy {
        LoginService.create(this)
    }
    */
    var repository = LoginService.create("")

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, HomeActivity::class.java)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mySharedPreferences = MySharedPreferences(this)

        if (!TextUtils.isEmpty(mySharedPreferences?.getUserToken())) {
            displayMenuHome(mySharedPreferences?.getUser())
        }
        btnLogin.setOnClickListener { v -> auth() }
    }

    fun auth() {

        var userPhone = etMobileNumber.text.toString().trim();
        var userPassword = etPassword.text.toString().trim();
        var requestUserAuth = RequestUserAuth(userPhone, userPassword)

        disposable = repository.userAuth(requestUserAuth)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { result -> getUserInfo(result.userToken) },
                        { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                )


    }

    fun getUserInfo(token: String?) {

        mySharedPreferences?.setUserToken(token)
        repository = LoginService.create(token)

        disposable = repository.showUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { result -> displayMenuHome(result.user?.get(0)) },
                        { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                )
    }

    fun displayMenuHome(user: User?) {

        mySharedPreferences?.setUser(user)

        val intent = MenuHomeActivity.newIntent(this, user)
        startActivity(intent)
        finish()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
