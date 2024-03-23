package com.dev.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dev.githubuser.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "exstra_url"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        if (username != null) {
            viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

            val progressBar = binding.progressBar

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

            var _isCheck = false
            CoroutineScope(Dispatchers.IO).launch {
                val count = viewModel.checkUser(id)
                withContext(Dispatchers.Main) {
                    if (count != null) {
                        if (count > 0) {
                            binding.toggleFavorite.isChecked = true
                            _isCheck = true
                        } else {
                            binding.toggleFavorite.isChecked = false
                            _isCheck = false
                        }
                    }
                }
            }

            binding.toggleFavorite.setOnClickListener {
                _isCheck = !_isCheck
                if (_isCheck) {
                    if (avatarUrl != null) {
                        viewModel.addToFavorite(username, id, avatarUrl)
                    }
                } else {
                    viewModel.removeFromFavorite(id)
                }
                binding.toggleFavorite.isChecked = _isCheck
            }


            val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
            binding.apply {
                viewPager.adapter = sectionPagerAdapter
                tabs.setupWithViewPager(viewPager)
            }
        } else {
            finish()
        }
    }
}
