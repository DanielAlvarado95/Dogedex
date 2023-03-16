package com.dalvarad.dogedex.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dalvarad.dogedex.R
import com.dalvarad.dogedex.auth.LoginActivity
import com.dalvarad.dogedex.databinding.ActivityLoginBinding
import com.dalvarad.dogedex.databinding.ActivitySettingsBinding
import com.dalvarad.dogedex.models.User

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutButton.setOnClickListener {
            logout()
        }

    }

    private fun logout() {
        User.logout(this)
        val intent = Intent(this, LoginActivity::class.java)
        //Va a iniciar el login activity como una nueva tarea
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}