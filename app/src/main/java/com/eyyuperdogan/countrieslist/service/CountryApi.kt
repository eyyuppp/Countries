package com.eyyuperdogan.countrieslist.service

import com.eyyuperdogan.countrieslist.model.CountryModel
import io.reactivex.Single
import retrofit2.http.GET

interface CountryApi {
    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries():Single<List<CountryModel>>

}