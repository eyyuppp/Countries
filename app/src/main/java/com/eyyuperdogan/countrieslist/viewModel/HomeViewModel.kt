package com.eyyuperdogan.countrieslist.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.eyyuperdogan.countrieslist.model.CountryModel
import com.eyyuperdogan.countrieslist.service.CountryApiService
import com.eyyuperdogan.countrieslist.service.CountryDatabase
import com.eyyuperdogan.countrieslist.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application):BaseViewModel(application) {
    private val countryApiService=CountryApiService()
    private val disposable=CompositeDisposable()
    private var customPreferences=CustomSharedPreferences(getApplication())
    private var refreshTime=10*60*1000*1000*1000L

    //verilerimizi canlı olarak tutumak için
    val countries=MutableLiveData<List<CountryModel>>()
    val countryError=MutableLiveData<Boolean>()
    val countryLoading=MutableLiveData<Boolean>()


    fun refreshData() {
        val updateTime=customPreferences.getTime()
        if (updateTime !=null && updateTime!=0L && System.nanoTime()-updateTime<refreshTime){
          getDataFromSQLite()
        }else{
            getDataFromApi()
        }

    }
    fun refreshFromApi(){
        getDataFromApi()
    }
    fun getDataFromSQLite(){
        launch {
            countryLoading.value=true
            val countries=CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCounries(countries)
        }
    }

   private fun getDataFromApi(){
      countryLoading.value=true
      disposable.add(
          countryApiService.getData()
              .subscribeOn(Schedulers.newThread())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribeWith(object :DisposableSingleObserver<List<CountryModel>>(){
                  override fun onSuccess(t: List<CountryModel>) {
                 storeSQLite(t)
                  }

                  override fun onError(e: Throwable) {
                      countryLoading.value=false
                      countryError.value=true
                      e.printStackTrace()
                  }


              })

      )

   }
    private fun showCounries(countryList:List<CountryModel>){
        countries.value=countryList
        countryLoading.value=false
        countryError.value=false
    }
    private fun storeSQLite(list:List<CountryModel>){
     launch {
         val dao=CountryDatabase(getApplication()).countryDao()
         dao.deleteAllCountries()
       val listLong= dao.insertAll(*list.toTypedArray())
         var i=0
         while (i<list.size){
             list[i].id=listLong[i].toInt()
             i=i+1
         }
        showCounries(list)
     }
        customPreferences.saveTime(System.nanoTime())
    }

    //hafıza daha verimli hale gelir
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}