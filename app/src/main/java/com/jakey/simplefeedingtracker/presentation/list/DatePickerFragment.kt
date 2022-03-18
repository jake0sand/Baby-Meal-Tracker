package com.jakey.simplefeedingtracker.presentation.list

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import kotlinx.coroutines.currentCoroutineContext
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private val c = Calendar.getInstance()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, day)

        val selectedDate = c.timeInMillis



        val selectedDateBundle = Bundle()
        selectedDateBundle.putLong("SELECTED_DATE", selectedDate)
//        val selectedTimeBundle = Bundle()
//        selectedTimeBundle.putLong("SELECTED_TIME", selectedTime)

        setFragmentResult("REQUEST_KEY", selectedDateBundle)
    }

}