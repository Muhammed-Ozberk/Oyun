package com.example.oyunyapimi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oyunyapimi.databinding.ActivitySonucEkraniBinding

class SonucEkraniActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySonucEkraniBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySonucEkraniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val skor = intent.getIntExtra("skor",0)
        binding.textViewToplamSkor.text = skor.toString()

        val sp = getSharedPreferences("Sonuc", Context.MODE_PRIVATE)
        val enYuksekSkor = sp.getInt("enYuksekSkor", 0)

        if (skor > enYuksekSkor){

            val editor = sp.edit()
            editor.putInt("enYuksekSkor", skor)
            editor.commit()

            binding.textViewEnYuksekSkor.text = skor.toString()
        }else{
            binding.textViewEnYuksekSkor.text = enYuksekSkor.toString()
        }

        binding.buttonTekrarOyna.setOnClickListener {
            startActivity(Intent(this@SonucEkraniActivity,MainActivity::class.java))
            finish()
        }
    }
}