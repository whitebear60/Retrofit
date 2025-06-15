package ua.edu.udhtu.retrofit.userprofile

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.edu.udhtu.retrofit.R
import ua.edu.udhtu.retrofit.databinding.ActivityUserProfileBinding
import ua.edu.udhtu.retrofit.rest.API
import ua.edu.udhtu.retrofit.rest.GithubRepository
import ua.edu.udhtu.retrofit.rest.GithubUser
import ua.edu.udhtu.retrofit.rest.RetrofitClient
import java.text.MessageFormat

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.i("RETROFIT_USER", "BEFORE USER")
        val user: GithubUser? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("userProfile", GithubUser::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("userProfile")
        }
        Log.i("RETROFIT_USER", user.toString())
        if (user != null) {
            binding.tvUserName.text = user.username
            binding.tvUserFullName.text = user.name
            binding.tvLocationPlaceName.text = user.location
            binding.tvRepoCount.text = MessageFormat.format("{0} repositories", user.repoCount)
            binding.tvOrganization.text = user.company
            binding.ivAvatar.load (user.avatarUrl) {
                crossfade(true)
                placeholder(com.google.android.material.R.color.material_dynamic_neutral80)
                error(R.color.red)
            }
            binding.btShowRepos.setOnClickListener {
                binding.pbProgress.visibility = View.VISIBLE
                val retrofit = RetrofitClient.instance.create(API::class.java)
                retrofit.getUserRepos(user.username, user.repoCount!!).enqueue(object : Callback<ArrayList<GithubRepository>> {
                    override fun onResponse(call: Call<ArrayList<GithubRepository>>, response: Response<ArrayList<GithubRepository>>) {
                        Log.i("RETROFIT_REPO", response.raw().request.url.toString())
                        if (response.body() != null) {
                            binding.rvRepositories.adapter = RepositoriesAdapter(response.body(), this@UserProfileActivity)
                            binding.rvRepositories.layoutManager = LinearLayoutManager(this@UserProfileActivity)
                            binding.rvRepositories.visibility = View.VISIBLE
                            binding.btShowRepos.visibility = View.GONE
                            binding.pbProgress.visibility = View.GONE
//
////                        binding.tvFact.text = response.body()!!.items.toString()
//                            binding.pbProgress.visibility = View.GONE
//                            Log.i("RETROFIT", response.raw().request.url.toString())
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<GithubRepository>>, t: Throwable) {
                        Log.w("RetrofitAppLog", "An error occured while parsing")
                    }

                })
            }
        }
    }
}