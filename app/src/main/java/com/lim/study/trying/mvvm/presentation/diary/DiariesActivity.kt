package com.lim.study.trying.mvvm.presentation.diary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lim.study.trying.mvvm.databinding.ActivityDiariesBinding

class DiariesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiariesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiariesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
