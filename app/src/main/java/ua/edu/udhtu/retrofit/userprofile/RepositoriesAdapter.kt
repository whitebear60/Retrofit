package ua.edu.udhtu.retrofit.userprofile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ua.edu.udhtu.retrofit.R
import ua.edu.udhtu.retrofit.databinding.ListItemRepositoryBinding
import ua.edu.udhtu.retrofit.rest.GithubRepository
import java.text.MessageFormat

class RepositoriesAdapter(
    private val repositories: ArrayList<GithubRepository>?,
    private val context: Context
): RecyclerView.Adapter<RepositoriesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesViewHolder {
        return RepositoriesViewHolder(ListItemRepositoryBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) {
        val repo = repositories?.get(position) ?: return
        holder.name.text = repo.name
        holder.isForkIcon.visibility = if (repo.isFork) View.VISIBLE else View.GONE
        holder.description.text = repo.description
        holder.stars.text = MessageFormat.format(context.getString(R.string.tvStars), repo.stars).toString()
        holder.forks.text = MessageFormat.format(context.getString(R.string.tvForks), repo.forks).toString()
        if (repo.license != null) {
            holder.license.text = repo.license.name
        } else {
            holder.clLicense.visibility = View.GONE
        }
        if (repo.language != null) {
            holder.language.text = repo.language
        } else {
            holder.clLanguage.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return repositories?.size ?: 0
    }
}

class RepositoriesViewHolder(binding: ListItemRepositoryBinding): RecyclerView.ViewHolder(binding.root) {
    val root = binding.root
    val name = binding.tvRepoName
    val description = binding.tvRepoDescription
    val language = binding.tvLanguage
    val clLanguage = binding.clLanguage
    val forks = binding.tvForks
    val stars = binding.tvStars
    val license = binding.tvLicense
    val clLicense = binding.clLicense
    val isForkIcon = binding.ivFork
}
