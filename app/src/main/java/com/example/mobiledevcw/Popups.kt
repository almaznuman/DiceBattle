package com.example.mobiledevcw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment


class Popups : DialogFragment() {
    /**
     * variable to hold layout passed
     */
    private var layoutId: Int = 0

    /**
     * Oncreateview inflates a dialogfragment with the passed layout which returns a view
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    /**
     * OnStart sets the size of the pop-up to 75 percent of the screens width(both portrait and landscape)
     */
    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.75).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    companion object {
        private const val ARG_LAYOUT_ID = "layout_id"

        /**
         * Creates new instance of popup with the passed layout file
         */
        fun newInstance(layoutId: Int): Popups {
            val args = Bundle()
            args.putInt(ARG_LAYOUT_ID, layoutId)
            val popup = Popups()
            popup.arguments = args
            return popup
        }
    }

    /**
     * on save and restore overridden functions to save and restore states during orientation change
     */
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