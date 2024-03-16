package com.dev.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dev.githubuser.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        if (username != null) {
            viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                DetailUserViewModel::class.java
            )

            // Menambahkan ProgressBar ke layout
            val progressBar = binding.progressBar

            // Memuat data, atur ProgressBar menjadi VISIBLE
            progressBar.visibility = View.VISIBLE
            viewModel.setUserDetail(username)
            viewModel.getUserDetail().observe(this, { user ->
                if (user != null) {
                    binding.apply {
                        tvName.text = user.name
                        tvUsername.text = user.login
                        tvFollowers.text = "${user.followers} Followers"
                        tvFollowing.text = "${user.following} Following"
                        Glide.with(this@DetailUserActivity)
                            .load(user.avatar_url)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .centerCrop()
                            .into(ivProfile)

                        // Setelah data dimuat, atur ProgressBar menjadi GONE
                        progressBar.visibility = View.GONE
                    }
                }
            })

            val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
            binding.apply {
                viewPager.adapter = sectionPagerAdapter
                tabs.setupWithViewPager(viewPager)
            }
        } else {
            // Handle null username
            // For example, show an error message or finish the activity
            finish()
        }
    }
}
