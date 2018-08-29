package com.sag0ld.barcodegenerator.views

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sag0ld.barcodegenerator.BarcodeAdapter

import com.sag0ld.barcodegenerator.R
import kotlinx.android.synthetic.main.fragment_history.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HistoryFragment : Fragment() {

    companion object {
        val TAG = HistoryFragment.javaClass.canonicalName
    }

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        context?.let {
            val adapter = BarcodeAdapter(it)
            barcodeRecyclerView?.adapter = adapter
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener
}
