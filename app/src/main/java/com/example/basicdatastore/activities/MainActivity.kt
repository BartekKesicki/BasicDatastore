package com.example.basicdatastore.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import com.example.basicdatastore.R
import com.example.basicdatastore.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var dataStore: DataStore<Preferences>
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataStore = createDataStore("data_store")
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btSave.setOnClickListener {
            performSaveData()
        }
        binding.btRead.setOnClickListener {
            performReadData()
        }
    }

    private fun performSaveData() {
        binding.tvResult.text = ""
        resetErrors()
        if (binding.etKeySave.text.isEmpty()) {
            binding.etKeySave.error = getString(R.string.this_field_can_not_be_empty)
            return
        }
        if (binding.etValueSave.text.isEmpty()) {
            binding.etValueSave.error = getString(R.string.this_field_can_not_be_empty)
            return
        }
        lifecycleScope.launch {
            saveData()
            binding.tvResult.text = getString(R.string.data_saved)
        }
    }

    private suspend fun saveData() {
        val key = binding.etKeySave.text.toString()
        val value = binding.etValueSave.text.toString()
        val dataStoreKey = preferencesKey<String>(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    private fun performReadData() {
        resetErrors()
        if (binding.etKeyRead.text.isEmpty()) {
            binding.etKeyRead.error = getString(R.string.this_field_can_not_be_empty)
            return
        }
        lifecycleScope.launch {
            val value = readData()
            binding.tvResult.text = "The result is: ${value}"
        }
    }

    private fun resetErrors() {
        binding.etKeySave.error = null
        binding.etValueSave.error = null
        binding.etKeyRead.error = null
    }

    private suspend fun readData() : String {
        val key = binding.etKeyRead.text.toString()
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey] ?: getString(R.string.value_doesnt_exist)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}