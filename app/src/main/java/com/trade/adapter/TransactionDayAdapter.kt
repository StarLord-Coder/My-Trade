package com.trade.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.trade.R
import com.trade.utils.ExchangeModel
import com.trade.utils.TransactionItem
import java.text.DecimalFormat

class TransactionDayAdapter(
    private val transactionItemList: ArrayList<ExchangeModel>,
    private val onItemClickItem: (Int, exchangeModel: ExchangeModel) -> Unit
) : RecyclerView.Adapter<TransactionDayAdapter.TransactionDayViewHolder>() {

    var tempDay = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionDayViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction_day, parent, false)
        return TransactionDayViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionDayViewHolder, position: Int) {
        val exchangeModel: ExchangeModel = transactionItemList[position]

        val shortDateArr = exchangeModel.tx_short_date.split("-")

        if (tempDay != shortDateArr[2]) {
            tempDay = shortDateArr[2]
            holder.tvDay.text = shortDateArr[2]
            holder.tvDayName.text = exchangeModel.tx_day
            holder.tvDay.visibility = View.VISIBLE
            holder.tvDayName.visibility = View.VISIBLE
        } else {
            holder.tvDay.visibility = View.INVISIBLE
            holder.tvDayName.visibility = View.INVISIBLE
        }

        holder.tvCat.text = exchangeModel.tx_cat
        holder.tvCounterPattyAccountName.text = exchangeModel.counterparty_acct_name
        val balance = String.format("%.2f", exchangeModel.acct_balance.toDouble())
        val amount = String.format("%.2f", exchangeModel.tx_amount.toDouble())
//        val balance = "余额 " + transactionItem.acct_balance.toString()
        holder.tvBalance.text = "余额 ${currencyFormat(balance)}"
        holder.tvCurrency.text = exchangeModel.currency

        if (exchangeModel.tx_type == "credit") {
            holder.tvAmount.setTextColor(holder.itemView.resources.getColor(R.color.color_debit, null))
            holder.tvAmount.text = "-${currencyFormat(amount)}"
        } else {
            holder.tvAmount.setTextColor(holder.itemView.resources.getColor(R.color.color_credit, null))
            holder.tvAmount.text = currencyFormat(amount)
        }

        if (position == transactionItemList.size -1) {
            holder.bottomLine.visibility = View.GONE
        } else {
            holder.bottomLine.visibility = View.VISIBLE
        }

        holder.itemView.setOnClickListener {
            onItemClickItem(position, exchangeModel)
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

    private fun currencyFormat(amount: String): String? {
        val formatter = DecimalFormat("###,###,##0.00")
        return formatter.format(amount.toDouble())
    }
}