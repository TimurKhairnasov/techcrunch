package com.example.schooluser.test

import com.chibatching.kotpref.KotprefModel

object UserInfo : KotprefModel() {

    var countryId by intPref(default = R.id.us)
}