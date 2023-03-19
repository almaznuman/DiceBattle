package com.example.mobiledevcw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import java.util.*
import kotlin.concurrent.schedule

class Popups// Public, no-argument constructor required by the system
    () : DialogFragment() {
    private var layoutId: Int = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }
    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    companion object {
        private const val ARG_LAYOUT_ID = "layout_id"
        fun newInstance(layoutId: Int): Popups {
            val args = Bundle()
            args.putInt(ARG_LAYOUT_ID, layoutId)
            val popup = Popups()
            popup.arguments = args
            return popup
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            layoutId = it.getInt(ARG_LAYOUT_ID)
        }
        arguments?.let {
            layoutId = it.getInt(ARG_LAYOUT_ID)
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ARG_LAYOUT_ID, layoutId)
    }
}