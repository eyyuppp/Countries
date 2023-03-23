package com.eyyuperdogan.countrieslist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.eyyuperdogan.countrieslist.R
import com.eyyuperdogan.countrieslist.adapter.CountryAdapter
import com.eyyuperdogan.countrieslist.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
private lateinit var viewModel: HomeViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /*
       //aktiviteler arasında geçiş yapmak için
       fragment_button.setOnClickListener {
           val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
           Navigation.findNavController(it).navigate(action)
       }
        */

        //bu fragment classa bağladım ki data ları çekeyim
        viewModel=ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.refreshData()

        countryList.layoutManager=LinearLayoutManager(context)
        countryList.adapter=countryAdapter

        swipeRefreshLayout.setOnRefreshListener {
            countryList.visibility= View.GONE
            textAgain.visibility=View.GONE
            progresbar.visibility=View.VISIBLE

            viewModel.refreshFromApi()
            swipeRefreshLayout.isRefreshing=false
        }


      observeLiveData()
    }

   private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer {
            it?.let {
                countryList.visibility=View.VISIBLE
                countryAdapter.updateCountryList(it)
            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if (it) {
                 textAgain.visibility=View.VISIBLE
                 countryList.visibility=View.GONE
                }
                else{
                  textAgain.visibility=View.GONE
                }
            }
        })


        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it){
                    progresbar.visibility=View.VISIBLE
                    countryList.visibility=View.GONE
                    textAgain.visibility=View.GONE
                }else
                {
                    progresbar.visibility=View.GONE
                }
            }
        })
    }
}