package com.satria.gymer.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.satria.gymer.R
import com.satria.gymer.data.model.ErrorResponse
import com.satria.gymer.data.model.history.AddHistoryRequest
import com.satria.gymer.data.model.history.HistoryResponse
import com.satria.gymer.data.model.history.ExerciseHistory
import com.satria.gymer.data.model.history.SetDetail
import com.satria.gymer.data.network.ApiConfig
import com.satria.gymer.databinding.ActivityAnalysisListBinding
import com.satria.gymer.utils.LoadingDialogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import java.util.Calendar

class AnalysisListActivity : AppCompatActivity() {
    data class SetView(
        val order:Int,
        val etRep:EditText,
        val etWeight:EditText
    )
    data class ExerciseView(
        val order:Int,
        val etName:EditText,
        val etSet:EditText,
        val listSets:ArrayList<SetView>
    )

    private var imageUri: String? = null
    private var name:String?=null
    private lateinit var binding: ActivityAnalysisListBinding
    val listExercises = arrayListOf<ExerciseView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalysisListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageUri = intent.getStringExtra("imageUri")

        // Display the captured image
        if (imageUri != null) {
            Glide.with(this)
                .load(Uri.parse(imageUri))
                .into(binding.ivAnalysisImage)
        } else {
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
        }


        name = intent.getStringExtra("name")
        binding.tvResultDescription.text = name

        binding.etDate.setOnClickListener {
            // Mendapatkan tanggal saat ini
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Menampilkan DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this,
                R.style.CustomDatePickerDialogTheme,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = "${selectedYear}-${selectedMonth + 1}-${selectedDay}"
                    binding.etDate.setText(selectedDate)
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        binding.etDuration.setOnClickListener {
            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                R.style.CustomTimePickerDialogTheme,
                { _, hourOfDay, minute ->
                    val formattedDuration = "${hourOfDay}h ${minute}m"
                    binding.etDuration.setText(formattedDuration)
                },
                currentHour,
                currentMinute,
                true // Set true for 24-hour format
            )
            timePickerDialog.show()
        }

        binding.etExercises.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val setCount = s?.toString()?.toIntOrNull()

                if (setCount == null || setCount < 0) {
                    binding.container.removeAllViews()
                    return
                }

                if (setCount > 10) {
                    Toast.makeText(this@AnalysisListActivity, "Maksimum 10 exercise", Toast.LENGTH_SHORT).show()
                    return
                }

                binding.container.removeAllViews()

                listExercises.clear()
                for (i in 1..setCount) {
                    val linearLayout = LinearLayout(this@AnalysisListActivity).apply {
                        orientation = LinearLayout.VERTICAL
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        setPadding(0.dpToPx(), 0.dpToPx(), 0.dpToPx(), 16.dpToPx())
                    }

                    val etName = AppCompatEditText(this@AnalysisListActivity).apply {
                        id = View.generateViewId()
                        hint = "Exercise Name $i"
                        setTextColor(resources.getColor(android.R.color.black, null))
                        setHintTextColor(resources.getColor(android.R.color.darker_gray, null))
                        background = resources.getDrawable(R.drawable.rounded_transparent_edittext, this@AnalysisListActivity.theme)
                        setPadding(16.dpToPx(), 16.dpToPx(), 16.dpToPx(), 16.dpToPx())

                        textSize = 16f
                        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                            .apply {
                                setMargins(0, 0, 0, 10.dpToPx()) // Set the margins here
                            }
                    }

                    val etSet = AppCompatEditText(this@AnalysisListActivity).apply {
                        id = View.generateViewId()
                        hint = "Number of Sets $i"
                        inputType = InputType.TYPE_NUMBER_FLAG_SIGNED
                        setTextColor(resources.getColor(android.R.color.black, null))
                        setHintTextColor(resources.getColor(android.R.color.darker_gray, null))
                        background = resources.getDrawable(R.drawable.rounded_transparent_edittext, this@AnalysisListActivity.theme)
                        setPadding(16.dpToPx(), 16.dpToPx(), 16.dpToPx(), 16.dpToPx())
                        textSize = 16f
                        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                            setMargins(0, 0, 0, 10.dpToPx()) // Set the margins here
                        }
                    }
                    val linearLayoutSet = LinearLayout(this@AnalysisListActivity).apply {
                        orientation = LinearLayout.VERTICAL
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            setMargins(0, 0, 0, 10.dpToPx()) // Set the margins here
                        }
                        setPadding(16.dpToPx(), 16.dpToPx(), 16.dpToPx(), 16.dpToPx())
                    }
                    linearLayout.addView(etName)
                    linearLayout.addView(etSet)
                    linearLayout.addView(linearLayoutSet)

                    binding.container.addView(linearLayout)

                    val exerciseView=ExerciseView(i, etName, etSet, arrayListOf())

                    etSet.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            val setCount = s?.toString()?.toIntOrNull()

                            if (setCount == null || setCount < 0) {
                                linearLayoutSet.removeAllViews()
                                return
                            }

                            if (setCount > 10) {
                                Toast.makeText(this@AnalysisListActivity, "Maksimum 10 exercise", Toast.LENGTH_SHORT).show()
                                return
                            }

                            linearLayoutSet.removeAllViews()

                            exerciseView.listSets.clear()

                            for (i in 1..setCount) {
                                val linearLayout = LinearLayout(this@AnalysisListActivity).apply {
                                    orientation = LinearLayout.HORIZONTAL
                                    layoutParams = LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                    ).apply {
                                        setMargins(0, 0, 0, 10.dpToPx()) // Set the margins here
                                    }
                                }

                                val etRepetition = AppCompatEditText(this@AnalysisListActivity).apply {
                                    id = View.generateViewId()
                                    hint = "Repetition $i"
                                    inputType = InputType.TYPE_NUMBER_FLAG_SIGNED
                                    setTextColor(resources.getColor(android.R.color.black, null))
                                    setHintTextColor(resources.getColor(android.R.color.darker_gray, null))
                                    background = resources.getDrawable(R.drawable.rounded_transparent_edittext, null)
                                    setPadding(16.dpToPx(), 16.dpToPx(), 16.dpToPx(), 16.dpToPx())
                                    textSize = 16f
                                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                                }

                                val etWeight = AppCompatEditText(this@AnalysisListActivity).apply {
                                    id = View.generateViewId()
                                    hint = "Weight $i"
                                    inputType = InputType.TYPE_NUMBER_FLAG_SIGNED
                                    setTextColor(resources.getColor(android.R.color.black, null))
                                    setHintTextColor(resources.getColor(android.R.color.darker_gray, null))
                                    background = resources.getDrawable(R.drawable.rounded_transparent_edittext, null)
                                    setPadding(16.dpToPx(), 16.dpToPx(), 16.dpToPx(), 16.dpToPx())
                                    textSize = 16f
                                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                                }

                                linearLayout.addView(etRepetition)
                                linearLayout.addView(etWeight)
                                linearLayoutSet.addView(linearLayout)

                                exerciseView.listSets.add(SetView(i, etRepetition,etWeight))
                            }
                        }

                        override fun afterTextChanged(s: Editable?) {}
                    })

                    listExercises.add(exerciseView)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        val loadingDialog = LoadingDialogUtils(this)
        binding.btnSubmit.setOnClickListener{
            if(listExercises.isEmpty()){
                Toast.makeText(this, "Minimum 1 exercise", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            with(binding){
                if(etExercises.text?.isEmpty() != false || etDate.text?.isEmpty() != false || etDuration.text?.isEmpty() != false){
                    Toast.makeText(this@AnalysisListActivity, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val listExercisesValue = arrayListOf<ExerciseHistory>()
                for(exercise in listExercises){
                    if(exercise.etName.text.isEmpty()||exercise.etSet.text.isEmpty()){
                        Toast.makeText(this@AnalysisListActivity, "Field exercise ${exercise.order} harus diisi!", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    val listSetsValue = arrayListOf<SetDetail>()
                    for (set in exercise.listSets){
                        if(set.etRep.text.isEmpty()||set.etWeight.text.isEmpty()){
                            Toast.makeText(this@AnalysisListActivity, "Field set ${set.order} exercise ${exercise.order} harus diisi!", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }

                        listSetsValue.add(SetDetail(set.etRep.text.toString().toInt(),set.etWeight.text.toString().toInt()))
                    }
                    listExercisesValue.add(ExerciseHistory(exercise.etName.text.toString(),listSetsValue))
                }
                val request = AddHistoryRequest(
                    37,
                    etDate.text.toString(),
                    etDuration.text.toString(),
                    listExercisesValue
                )

                loadingDialog.show()
                val client = ApiConfig.getApiService().addHistory(request)
                client.enqueue(object : Callback<HistoryResponse> {
                    override fun onResponse(
                        call: Call<HistoryResponse>,
                        response: Response<HistoryResponse>
                    ) {
                        loadingDialog.dismiss()
                        if (response.isSuccessful) {
                            response.body()?.let { loginResponse ->
                                Toast.makeText(this@AnalysisListActivity, "Simpan history berhasil!", Toast.LENGTH_SHORT).show()
                                finish() // Menutup AnalysisActivity setelah menyimpan
                            }
                        } else {
                            val errorBody = response.errorBody()?.string()
                            errorBody?.let {
                                try {
                                    val errorResponse = Gson().fromJson(it, ErrorResponse::class.java)
                                    Toast.makeText(this@AnalysisListActivity, errorResponse.message, Toast.LENGTH_SHORT).show()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                        loadingDialog.dismiss()
                    }
                })
            }
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }


    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}
