package com.trade.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.trade.R
import com.trade.utils.TransactionItem

class TransactionDayAdapter(
    private val transactionItemList: ArrayList<TransactionItem>,
    private val onItemClickItem: (Int, transactionItem: TransactionItem) -> Unit
) : RecyclerView.Adapter<TransactionDayAdapter.TransactionDayViewHolder>() {

    var tempDay = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionDayViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction_day, parent, false)
        return TransactionDayViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionDayViewHolder, position: Int) {
        val transactionItem: TransactionItem = transactionItemList[position]

        val shortDateArr = transactionItem.tx_short_date.split("/")

        if (tempDay != shortDateArr[1]) {
            tempDay = shortDateArr[1]
            holder.tvDay.text = shortDateArr[1]
            holder.tvDayName.text = transactionItem.tx_day
            holder.tvDay.visibility = View.VISIBLE
            holder.tvDayName.visibility = View.VISIBLE
        } else {
            holder.tvDay.visibility = View.INVISIBLE
            holder.tvDayName.visibility = View.INVISIBLE
        }

        holder.tvCat.text = transactionItem.tx_cat
        holder.tvCounterPattyAccountName.text = transactionItem.counterparty_acct_name
        val balance = String.format("%.2f", transactionItem.acct_balance)
//        val balance = "余额 " + transactionItem.acct_balance.toString()
        holder.tvBalance.text = "余额 $balance"
        holder.tvCurrency.text = transactionItem.currency

        if (transactionItem.tx_type == "debit") {
            holder.tvAmount.setTextColor(holder.itemView.resources.getColor(R.color.color_debit, null))
        } else {
            holder.tvAmount.setTextColor(holder.itemView.resources.getColor(R.color.color_credit, null))
        }

        holder.tvAmount.text = transactionItem.tx_amount.toString()

        if (position == transactionItemList.size -1) {
            holder.bottomLine.visibility = View.GONE
        } else {
            holder.bottomLine.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int = transactionItemList.size

    class TransactionDayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvDay = view.findViewById<TextView>(R.id.tvDay)!!
        var tvDayName = view.findViewById<TextView>(R.id.tvDayName)!!
        var tvCat = view.findViewById<TextView>(R.id.tvCat)!!
        var tvCounterPattyAccountName = view.findViewById<TextView>(R.id.tvCounterPattyAccountName)!!
        var tvAmount = view.findViewById<TextView>(R.id.tvAmount)!!
        var tvCurrency = view.findViewById<TextView>(R.id.tvCurrency)!!
        var tvBalance = view.findViewById<TextView>(R.id.tvBalance)!!
        var bottomLine = view.findViewById<View>(R.id.bottomLine)!!
    }
}