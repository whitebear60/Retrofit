package ua.edu.udhtu.retrofit.searchresults

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.edu.udhtu.retrofit.R
import ua.edu.udhtu.retrofit.databinding.ListItemUserBinding
import ua.edu.udhtu.retrofit.rest.API
import ua.edu.udhtu.retrofit.rest.GithubUser
import ua.edu.udhtu.retrofit.rest.RetrofitClient
import ua.edu.udhtu.retrofit.userprofile.UserProfileActivity

class SearchResultAdapter (
    private val results: ArrayList<GithubUser>?,
    private val context: Context
): RecyclerView.Adapter<SearchResultViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(
            ListItemUserBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val result: GithubUser = results?.get(position) ?: return
        holder.avatar.load(result.avatarUrl) {
            crossfade(true)
            placeholder(com.google.android.material.R.color.material_dynamic_neutral80)
            error(R.color.red)
        }
        holder.name.text = result.username
        var isClicked = false
        holder.root.setOnClickListener {
            Log.i("RETROFIT_SEARCH", isClicked.toString())
            Log.i("RETROFIT_SEARCH", result.username)
            if (!isClicked) {
                isClicked = true
                val retrofit = RetrofitClient.instance.create(API::class.java)
                retrofit.getUser(result.username).enqueue(object : Callback<GithubUser> {
                    override fun onResponse(call: Call<GithubUser>, response: Response<GithubUser>) {
                        Log.i("RETROFIT_SEARCH", response.body().toString())
                        if (response.body() != null) {
                            val intent = Intent(context, UserProfileActivity ::class.java)
                            intent.putExtra("userProfile", response.body()!!)
                            context.startActivity(intent)
                            isClicked = false
                        }
                    }

                    override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                        Log.w("RETROFIT_SEARCH", "An error occured while parsing")
                    }
                })
            } else return@setOnClickListener
        }
    }

    override fun getItemCount(): Int {
        return results?.size ?: 0
    }
}

class SearchResultViewHolder(binding: ListItemUserBinding): RecyclerView.ViewHolder(binding.root) {
    val root = binding.root
    val avatar = binding.ivAvatar
    val name = binding.tvUserName
}
