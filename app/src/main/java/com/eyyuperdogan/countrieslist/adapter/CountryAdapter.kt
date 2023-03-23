package com.eyyuperdogan.countrieslist.adapter
import android.annotation.SuppressLint
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.navArgument
import androidx.recyclerview.widget.RecyclerView
import com.eyyuperdogan.countrieslist.R
import com.eyyuperdogan.countrieslist.databinding.ItemCoutryBinding
import com.eyyuperdogan.countrieslist.model.CountryModel
import com.eyyuperdogan.countrieslist.util.downloadFromUrl
import com.eyyuperdogan.countrieslist.util.placeHolderPrrogresBar
import com.eyyuperdogan.countrieslist.view.DetailsFragmentDirections
import com.eyyuperdogan.countrieslist.view.HomeFragmentDirections
import kotlinx.android.synthetic.main.item_coutry.view.*

class CountryAdapter(val countryList: ArrayList<CountryModel>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() ,CountryClickListener{
    class CountryViewHolder(val view:ItemCoutryBinding) : RecyclerView.ViewHolder(view.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=DataBindingUtil.inflate<ItemCoutryBinding>(inflater,R.layout.item_coutry,parent,false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.countryData=countryList[position]
        holder.view.listener=this

        /*
        holder.itemView.textName.text=countryList.get(position).countryName
        holder.itemView.textRegion.text=countryList.get(position).countryRegion

        holder.itemView.setOnClickListener {
            val action =HomeFragmentDirections.actionHomeFragmentToDetailsFragment()
            action.countryUuid=countryList[position].id
            Navigation.findNavController(it).navigate(action)
            println(action.countryUuid)

        }
        holder.itemView.imageView.downloadFromUrl(countryList.get(position).imageUrl.toString(),
            placeHolderPrrogresBar(holder.itemView.context))

         */
    }
    override fun getItemCount(): Int {
      return  countryList.size
    }
//verileri yenilemek için
    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList: List<CountryModel>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountyClicked(view: View) {
        val action =HomeFragmentDirections.actionHomeFragmentToDetailsFragment()
         val id=view.countryUuidText.text
        action.countryUuid=id.toString().toInt()
        Navigation.findNavController(view).navigate(action)
    }
}