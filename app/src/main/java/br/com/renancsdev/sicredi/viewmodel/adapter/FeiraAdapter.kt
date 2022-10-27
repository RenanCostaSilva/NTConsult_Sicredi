package br.com.renancsdev.sicredi.viewmodel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.renancsdev.sicredi.databinding.FeiraItemBinding
import br.com.renancsdev.sicredi.model.Feira
import br.com.renancsdev.sicredi.viewmodel.holder.FeiraViewHolder

class FeiraAdapter(var feira: List<Feira>): RecyclerView.Adapter<FeiraViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeiraViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FeiraItemBinding.inflate(layoutInflater)
        return FeiraViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeiraViewHolder, position: Int) {
        holder.bind(feira[position])
    }

    override fun getItemCount() = feira.size

}