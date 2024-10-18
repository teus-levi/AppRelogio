package com.example.apprelogio

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.apprelogio.databinding.ActivityFullscreenBinding

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullscreenBinding
    private var isChecked = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Retirar barra de menu
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        //emulador sempre visível e tela ligada
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val bateriaReceiver: BroadcastReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                if(intent != null){
                    //Caso não receba valor, recebe zero
                    val nivel: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0)
                    //Toast.makeText(applicationContext, nivel.toString(), Toast.LENGTH_SHORT).show()
                    binding.textNivelBateria.text = "${nivel.toString()}%"
                }
            }

        }

        registerReceiver(bateriaReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        
        binding.checkNivelBateria.setOnClickListener {
            if(isChecked){
                isChecked = false;
                binding.checkNivelBateria.isChecked = false
                binding.textNivelBateria.visibility = View.GONE
            } else {
                isChecked = true
                binding.checkNivelBateria.isChecked = true
                binding.textNivelBateria.visibility = View.VISIBLE
            }

        }

        binding.layoutMenu.animate().translationY(500F)

        binding.imageViewFechar.setOnClickListener {

            binding.layoutMenu.animate().translationY(binding.layoutMenu.measuredHeight.toFloat()).setDuration(
                resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
            )
        }
        binding.imageViewPreferencias.setOnClickListener {
            binding.layoutMenu.visibility = View.VISIBLE
            binding.layoutMenu.animate().translationY(0F).setDuration(
                resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
            )
        }
    }
}