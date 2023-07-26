package com.example.tatetiandroid

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tatetiandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    enum class Turn {
        CIRCULO, CRUZ
    }

    private var firstTurn = Turn.CRUZ
    private var currentTurn = Turn.CIRCULO

    private var boardList = mutableListOf<Button>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()
    }

    private fun initBoard() {
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.b4)
        boardList.add(binding.b5)
        boardList.add(binding.b6)
        boardList.add(binding.b7)
        boardList.add(binding.b8)
        boardList.add(binding.b9)
    }


    fun boardTapped(view: View) {
        if (view !is Button)
            return
        addToBoard(view)

        if(checkForVictory(CIRCULO)) {
            result("O GANÓ")
        }
        if(checkForVictory(CRUZ)) {
            result("X GANÓ")
        }

        if(fullBoard()) {
            result("Draw")
        }
    }

    private fun checkForVictory(s: String): Boolean {
        //Horizontal Victory
        if(match(binding.b1, s) && match(binding.b2, s) && match(binding.b3, s))
            return true
        if(match(binding.b4, s) && match(binding.b5, s) && match(binding.b6, s))
            return true
        if(match(binding.b7, s) && match(binding.b8, s) && match(binding.b9, s))
            return true

        //Vertical Victory
        if(match(binding.b1, s) && match(binding.b4, s) && match(binding.b7, s))
            return true
        if(match(binding.b2, s) && match(binding.b5, s) && match(binding.b8, s))
            return true
        if(match(binding.b3, s) && match(binding.b6, s) && match(binding.b9, s))
            return true

        //Diagonal Victory
        if(match(binding.b1, s) && match(binding.b5, s) && match(binding.b9, s))
            return true
        if(match(binding.b3, s) && match(binding.b5, s) && match(binding.b7, s))
            return true

        return false
    }

    private fun match(button: Button, symbol: String): Boolean = button.text == symbol

    private fun result(title: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setPositiveButton("Reiniciar")
            {_,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()

    }

    private fun resetBoard() {
        for(button in boardList) {
            button.text = ""
        }

        if(firstTurn == Turn.CIRCULO)
            firstTurn = Turn.CRUZ
        else if(firstTurn == Turn.CRUZ)
            firstTurn = Turn.CIRCULO

        currentTurn = firstTurn
        setTurnLabel()
    }

    private fun fullBoard(): Boolean {
        for(button in boardList) {
            if(button.text == "")
                return false
        }
        return true
    }

    private fun addToBoard(button: Button) {
        if(button.text != "")
            return

        if(currentTurn == Turn.CIRCULO) {
            button.text = CIRCULO
            currentTurn = Turn.CRUZ
        } else if (currentTurn == Turn.CRUZ) {
            button.text = CRUZ
            currentTurn = Turn.CIRCULO
        }
        setTurnLabel()
    }

    private fun setTurnLabel() {
        var turnText = ""
        if (currentTurn == Turn.CRUZ)
            turnText = "Turn $CRUZ"
        else if (currentTurn == Turn.CIRCULO)
            turnText = "Turn $CIRCULO"

        binding.textTurn.text = turnText
    }

    companion object {
        const val CIRCULO = "O"
        const val CRUZ = "X"
    }
}