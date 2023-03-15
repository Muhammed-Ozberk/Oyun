package com.example.oyunyapimi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.oyunyapimi.databinding.ActivityOyunEkraniBinding
import java.lang.Math.floor
import java.util.Timer
import java.util.logging.Handler
import kotlin.concurrent.schedule

class OyunEkraniActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOyunEkraniBinding

    //Pozisyonlar
    private var anakarakterX = 0.0f
    private var anakarakterY = 0.0f
    private var siyahkareX = 0.0f
    private var siyahkareY = 0.0f
    private var saridaireX = 0.0f
    private var saridaireY = 0.0f
    private var kirmiziucgenX = 0.0f
    private var kirmiziucgenY = 0.0f

    //Kontroller
    private var dokunmaKontrol = false
    private var baslangicKontrol = false

    //Boyutlara
    private var ekranGenisligi = 0
    private var ekranYuksekligi = 0
    private var anakarakterGenisligi = 0
    private var anakarakterYuksekligi = 0

    private val timer = Timer()

    private var skor = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOyunEkraniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Cisimleri ekranın dışına çıkarma
        binding.siyahKare.x = -800.0f
        binding.siyahKare.y = -800.0f
        binding.sariDaire.x = -800.0f
        binding.sariDaire.y = -800.0f
        binding.kirmiziUcgen.x = -800.0f
        binding.kirmiziUcgen.y = -800.0f

        binding.cl.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, event: MotionEvent?): Boolean {

                if (baslangicKontrol) {
                    if (event?.action == MotionEvent.ACTION_DOWN) {
                        dokunmaKontrol = true
                    }

                    if (event?.action == MotionEvent.ACTION_UP) {
                        dokunmaKontrol = false
                    }
                } else {

                    baslangicKontrol = true

                    //Kullanıcı başlama uyarısını görünmez yapar.
                    binding.textViewOyunBasla.visibility = View.INVISIBLE

                    anakarakterX = binding.anaKarakter.x
                    anakarakterY = binding.anaKarakter.y

                    anakarakterGenisligi = binding.anaKarakter.width
                    anakarakterYuksekligi = binding.anaKarakter.height
                    ekranGenisligi = binding.cl.width
                    ekranYuksekligi = binding.cl.height

                    timer.schedule(0, 20) {
                        android.os.Handler(Looper.getMainLooper()).post {
                            anaKarakteriHareketEttirme()
                            cisimlerinHareketEttir()
                            carpismaKontrol()
                        }
                    }
                }

                return true
            }

        })
    }

    fun anaKarakteriHareketEttirme() {

        val anakarakterHiz = ekranYuksekligi / 60.0f//1280 / 60.0f  = 20.0f

        if (dokunmaKontrol) {
            anakarakterY += anakarakterHiz
        } else {
            anakarakterY -= anakarakterHiz
        }

        if (anakarakterY <= 0.0f) {
            anakarakterY = 0.0f
        }

        if (anakarakterY >= ekranYuksekligi - anakarakterYuksekligi) {
            anakarakterY = (ekranYuksekligi - anakarakterYuksekligi).toFloat()
        }

        binding.anaKarakter.y = anakarakterY
    }

    fun cisimlerinHareketEttir() {

        siyahkareX -= ekranGenisligi / 44.0f//1080 / 44.0f  = 25.0f
        saridaireX -= ekranGenisligi / 54.0f//1080 / 54.0f  = 20.0f
        kirmiziucgenX -= ekranGenisligi / 36.0f//1080 / 36.0f  = 30.0f

        if (siyahkareX < 0.0f) {
            siyahkareX = ekranGenisligi + 20.0f
            siyahkareY = kotlin.math.floor(Math.random() * ekranYuksekligi).toFloat()
        }
        binding.siyahKare.x = siyahkareX
        binding.siyahKare.y = siyahkareY

        if (saridaireX < 0.0f) {
            saridaireX = ekranGenisligi + 20.0f
            saridaireY = kotlin.math.floor(Math.random() * ekranYuksekligi).toFloat()
        }
        binding.sariDaire.x = saridaireX
        binding.sariDaire.y = saridaireY

        if (kirmiziucgenX < 0.0f) {
            kirmiziucgenX = ekranGenisligi + 20.0f
            kirmiziucgenY = kotlin.math.floor(Math.random() * ekranYuksekligi).toFloat()
        }
        binding.kirmiziUcgen.x = kirmiziucgenX
        binding.kirmiziUcgen.y = kirmiziucgenY
    }

    fun carpismaKontrol() {

        val saridaireMerkezX = saridaireX + binding.sariDaire.width / 2.0f
        val saridaireMerkezY = saridaireY + binding.sariDaire.height / 2.0f

        if (0.0f <= saridaireMerkezX && saridaireMerkezX <= anakarakterGenisligi
            && anakarakterY <= saridaireMerkezY && saridaireMerkezY <= anakarakterY + anakarakterYuksekligi
        ) {
            skor += 20
            saridaireX = -10.0f
        }

        val kirmziucgenMerkezX = kirmiziucgenX + binding.kirmiziUcgen.width / 2.0f
        val kirmziucgenMerkezY = kirmiziucgenY + binding.kirmiziUcgen.height / 2.0f

        if (0.0f <= kirmziucgenMerkezX && kirmziucgenMerkezX <= anakarakterGenisligi
            && anakarakterY <= kirmziucgenMerkezY && kirmziucgenMerkezY <= anakarakterY + anakarakterYuksekligi
        ) {
            skor += 50
            kirmiziucgenX = -10.0f
        }

        val siyahkareMerkezX = siyahkareX + binding.siyahKare.width / 2.0f
        val siyahkareMerkezY = siyahkareY + binding.siyahKare.height / 2.0f

        if (0.0f <= siyahkareMerkezX && siyahkareMerkezX <= anakarakterGenisligi
            && anakarakterY <= siyahkareMerkezY && siyahkareMerkezY <= anakarakterY + anakarakterYuksekligi
        ) {
            siyahkareX = -10.0f

            timer.cancel()//Timer durdur.

            val intent = Intent(this@OyunEkraniActivity, SonucEkraniActivity::class.java)
            intent.putExtra("skor", skor)
            startActivity(intent)
            finish()
        }

        binding.textViewSkor.text = skor.toString()
    }
}