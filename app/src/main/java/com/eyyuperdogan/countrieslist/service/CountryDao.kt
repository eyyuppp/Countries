package com.eyyuperdogan.countrieslist.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.eyyuperdogan.countrieslist.model.CountryModel

@Dao
interface CountryDao {


    @Insert
    suspend fun insertAll(vararg countries:CountryModel):List<Long>


    @Query("SELECT * FROM countryTable")
    suspend fun getAllCountries():List<CountryModel>


    @Query("SELECT * FROM countryTable WHERE id = :countryId")
    suspend fun getCountry(countryId:Int):CountryModel


    @Query("DELETE FROM countryTable")
    suspend fun deleteAllCountries()

}