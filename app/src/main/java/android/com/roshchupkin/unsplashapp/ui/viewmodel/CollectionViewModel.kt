package android.com.roshchupkin.unsplashapp.ui.viewmodel

import android.com.roshchupkin.unsplashapp.repository.collection.CollectionRepository
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn

class CollectionViewModel
@ViewModelInject
constructor(private val collectionRepository: CollectionRepository) : ViewModel() {
    var collection = collectionRepository.getCollectionImage().cachedIn(viewModelScope)
}