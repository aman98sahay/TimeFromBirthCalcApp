package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import java.util.Calendar
import android.widget.TextView
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate  :TextView ? = null
    private var tvAgeInMinutes  :TextView ? = null
    private var tvAgeInDays     :TextView ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // defining text views
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        tvAgeInDays     = findViewById(R.id.tvAgeInDays)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)

        btnDatePicker.setOnClickListener{
            clickDatePicker()
        }

    }

    private fun clickDatePicker () {

        val myCalendar = Calendar.getInstance ()
        val year  = myCalendar.get (Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val dayOfMonth     =   myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            {_,selectedYear, selectedMonth, selectedDayOfMonth->

                Toast.makeText(this,"Year is $year, month is ${month+ 1}, dayofMonth was $dayOfMonth",Toast.LENGTH_LONG).show()
                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"


                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat ("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse (selectedDate)

                theDate?.let {
                    val selectedDateInMinutes = theDate.time/60_000

                    val currentDate = sdf.parse (sdf.format (System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time/60_000

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                        tvAgeInMinutes?.text    = differenceInMinutes.toString()
                        tvAgeInDays?.text       = (differenceInMinutes/1_440).toString()
                    }

                }




            }
            ,year ,month, dayOfMonth)

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86_400_000

        dpd.show()

    }
}