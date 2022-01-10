package com.trade.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trade.R
import com.trade.utils.Transaction

class TransactionAdapter(
    private val context: Context,
    private val transactionList: List<Transaction>,
    private val onItemClickItem: (Int, transaction: Transaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction: Transaction = transactionList[position]

        holder.tvMonthYear.text = transaction.month

        holder.rvTransactionDay.layoutManager = LinearLayoutManager(context)
        val adapter = TransactionDayAdapter(transaction.tx_in_month!!) { position, transactionItem ->

        }
        holder.rvTransactionDay.adapter = adapter
    }

    override fun getItemCount(): Int = transactionList.size

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvMonthYear = view.findViewById<TextView>(R.id.tvMonthYear)!!
        var rvTransactionDay = view.findViewById<RecyclerView>(R.id.rvTransactionDay)!!
    }
}