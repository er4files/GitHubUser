package com.dev.consumerapp

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.dev.githubuser"
    const val SCHEME = "content"

    internal class FavoriteUserColumns: BaseColumns{
        companion object{
            const val  TABLE_NAME = "favorite_user"
            const val  ID = "id"
            const val USERNAME = "login"
            const val AVATAR_URL = "avatar_url"

            val CONTENT_URL = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}