package com.satria.gymer.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.satria.gymer.R
import com.satria.gymer.ui.adapter.AnalysisAdapter
import com.satria.gymer.ui.model.AnalysisItem

class AnalysisListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var analysisAdapter: AnalysisAdapter
    private lateinit var analysisList: MutableList<AnalysisItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analysis_list)

        recyclerView = findViewById(R.id.recycler_view_recommendations)
        recyclerView.layoutManager = LinearLayoutManager(this)

        analysisList = mutableListOf(
            AnalysisItem("Leg Extension", "Paha", R.drawable.ic_launcher_background, isFavorite = false),
            AnalysisItem("Smith Machine Incline Press", "Dada", R.drawable.ic_launcher_background, isFavorite = true)
        )

        analysisAdapter = AnalysisAdapter(analysisList) { item ->
            // Handle item click: navigate to DetailExerciseActivity
            val intent = Intent(this, DetailExerciseActivity::class.java).apply {
                putExtra("EXERCISE_TITLE", item.title)
                putExtra("EXERCISE_DESCRIPTION", "Deskripsi latihan untuk ${item.title}") // Example description
            }
            startActivity(intent)
        }
        recyclerView.adapter = analysisAdapter

        findViewById<View>(R.id.btn_back).setOnClickListener {
            onBackPressed()
        }
    }
}
