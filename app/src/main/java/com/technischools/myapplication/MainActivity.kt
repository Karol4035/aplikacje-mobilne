package com.technischools.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var program = 0
    private var vacuumOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val padding = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            )
            v.setPadding(padding.left, padding.top, padding.right, padding.bottom)
            insets
        }

        program = savedInstanceState?.getInt("program") ?: 0
        vacuumOn = savedInstanceState?.getBoolean("vacuumOn") ?: false

        val programInput = findViewById<EditText>(R.id.programInput)
        val programResult = findViewById<TextView>(R.id.programResult)
        val toggleVacuum = findViewById<Button>(R.id.toggleVacuum)
        val vacuumStatus = findViewById<TextView>(R.id.vacuumStatus)

        if (program != 0) {
            programResult.text = getString(R.string.program_selected, program)
        }
        showVacuumStatus(toggleVacuum, vacuumStatus)

        findViewById<Button>(R.id.confirmProgram).setOnClickListener {
            val number = programInput.text.toString().toIntOrNull()
            if (number != null && number in 1..12) {
                program = number
                programResult.text = getString(R.string.program_selected, program)
            }
        }

        toggleVacuum.setOnClickListener {
            vacuumOn = !vacuumOn
            showVacuumStatus(toggleVacuum, vacuumStatus)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("program", program)
        outState.putBoolean("vacuumOn", vacuumOn)
    }

    private fun showVacuumStatus(button: Button, status: TextView) {
        if (vacuumOn) {
            button.setText(R.string.turn_off)
            status.setText(R.string.vacuum_on)
        } else {
            button.setText(R.string.turn_on)
            status.setText(R.string.vacuum_off)
        }
    }
}
