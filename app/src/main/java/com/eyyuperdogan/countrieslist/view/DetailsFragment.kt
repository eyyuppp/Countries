package com.eyyuperdogan.countrieslist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.eyyuperdogan.countrieslist.R
import com.eyyuperdogan.countrieslist.databinding.FragmentDetailsBinding
import com.eyyuperdogan.countrieslist.util.downloadFromUrl
import com.eyyuperdogan.countrieslist.util.placeHolderPrrogresBar
import com.eyyuperdogan.countrieslist.viewModel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.item_coutry.*


class DetailsFragment : Fragment() {
 private lateinit var viewModel: DetailsViewModel
    private lateinit var dataBinding: FragmentDetailsBinding
 private var countryUuid=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        dataBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid=DetailsFragmentArgs.fromBundle(it).countryUuid
        }


     viewModel=ViewModelProviders.of(this).get(DetailsViewModel::class.java)
     viewModel.getDataFromRoom(countryUuid)

        observeLiveData()



    }
     private fun observeLiveData(){
         viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country->
             country?.let {
                 dataBinding.selectedCountry=country

                 /*
                 txtCountryName.text=country.countryName

                 txtCountryCapital.text=country.countryCapital

                 textCountryCurrency.text=country.countryCurrency

                 txtCountryLanguage.text=country.countryLanguage

                 txtCountryRegion.text=country.countryRegion

                 context?.let {
                     countryDetailsImage.downloadFromUrl(country.imageUrl!!,
                         placeHolderPrrogresBar(it))
                 }

                  */
             }

         })
     }
}

