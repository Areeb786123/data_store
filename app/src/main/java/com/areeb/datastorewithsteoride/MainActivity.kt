package com.areeb.datastorewithsteoride

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "user"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val firstName: EditText = findViewById(R.id.et_first)
        val surname: EditText = findViewById(R.id.et_surname)
        val age: EditText = findViewById(R.id.et_age)
        val submit = findViewById<Button>(R.id.submit)
        val tvFirstName: TextView = findViewById(R.id.tv_first)
        val tvSurName: TextView = findViewById(R.id.tv_surname)
        val tvAge: TextView = findViewById(R.id.tv_age)
        val getData: Button = findViewById(R.id.btn_get)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        submit.setOnClickListener {
            lifecycleScope.launch {
                userPreferencesDataStore.edit { prefernces ->
                    prefernces[stringPreferencesKey("user_first_name")] = firstName.text.toString()
                    prefernces[stringPreferencesKey("user_last_name")] = surname.text.toString()
                    prefernces[stringPreferencesKey("user_age")] = age.text.toString()
                }
            }
        }
        getData.setOnClickListener {
            lifecycleScope.launch {
                userPreferencesDataStore.data.collect { prefernces ->
                    tvFirstName.text = prefernces[stringPreferencesKey("user_first_name")]
                    tvSurName.text = prefernces[stringPreferencesKey("user_last_name")]
                    tvAge.text = prefernces[stringPreferencesKey("user_age")]
                }
            }
        }

    }


}