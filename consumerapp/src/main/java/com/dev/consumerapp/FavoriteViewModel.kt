package com.dev.consumerapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
   private var list = MutableLiveData<ArrayList<User>>()

    fun setFavoriteUser(context: Context){
        val cursor = context.contentResolver.query(
            DatabaseContract.FavoriteUserColumns.CONTENT_URL,
            null,
            null,
            null,
            null
        )
        val lisConverted = MappingHelper.mapCursorToArrayList(cursor)
        list.postValue(lisConverted)
    }

    fun getFavoriteUser(): LiveData<ArrayList<User>> {

        return list
    }

}