package com.example.schooluser.test


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_settings.*

/**
 * A simple [Fragment] subclass.
 *
 */

class SettingsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectCountry.check(UserInfo.countryId)
        selectCountry.setOnCheckedChangeListener { radioGroup, id ->
            val country =  when (id) {
                R.id.rus -> "ru"
                R.id.gb -> "gb"
                else -> "us"
            }
            UserInfo.countryId = id
            val bundle = Bundle()
            bundle.putString("country", country)
            val navController = Navigation.findNavController(activity!!, R.id.selectCountry)
            navController.navigate(R.id.next_action, bundle)
        }
    }
}
