package ua.edu.udhtu.retrofit.searchresults

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import ua.edu.udhtu.retrofit.databinding.ActivitySearchResultsBinding
import ua.edu.udhtu.retrofit.rest.GithubUser

class SearchResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchResultsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val results: ArrayList<GithubUser>? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra("searchResults", GithubUser::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra("searchResults")
        }

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = SearchResultAdapter(results, this)
    }
}