package com.eyyuperdogan.countrieslist.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eyyuperdogan.countrieslist.model.CountryModel
import com.eyyuperdogan.countrieslist.service.CountryDatabase
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application):BaseViewModel(application) {

    val countryLiveData = MutableLiveData<CountryModel>()

    fun getDataFromRoom(id:Int) {
        launch {
            val dao=CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(id)
            countryLiveData.value = country
        }
    }

}