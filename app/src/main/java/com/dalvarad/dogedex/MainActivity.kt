package com.dalvarad.dogedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dalvarad.dogedex.api.ApiServiceInterceptor
import com.dalvarad.dogedex.auth.LoginActivity
import com.dalvarad.dogedex.databinding.ActivityMainBinding
import com.dalvarad.dogedex.doglist.DogListActivity
import com.dalvarad.dogedex.models.User
import com.dalvarad.dogedex.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val user = User.getLoggedInUser(this)
        if (user == null){
            openLoginActivity()
            return
        }else{
            ApiServiceInterceptor.setSessionToken(user.authenticationToken)
        }


        binding.settingsFab.setOnClickListener {
            openSettingsActivity()
        }

        binding.dogListFab.setOnClickListener {
            openDogListActivity()
        }

    }

    private fun openDogListActivity() {
        startActivity(Intent(this, DogListActivity::class.java))

    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}