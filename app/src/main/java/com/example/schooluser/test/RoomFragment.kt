package com.example.schooluser.test


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext


class RoomFragment : Fragment() {
    private val lithoView by lazy { LithoView(ComponentContext(context)) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return lithoView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch(CommonPool) {
            val db = DB.instance
            val newItemDao = db.newItemDao()
            val newItem = newItemDao.getById(1)
            val title = newItem.title.toString()
            val subtitle = newItem.source.toString()
            val uri = newItem.uri.toString()
            val url = newItem.url.toString()
            withContext(UI){
                val context = ComponentContext(activity)
                val component = ListItem.create(context)
                        .title(title)
                        .subtitle(subtitle)
                        .uri(uri)
                        .url(url)
                        .onClick { url ->
                                    activity?.startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url.substring(1, url.length - 1))))
                                    println("url = $url")
                                }
                        .build()
                lithoView.setComponent(component)
            }
        }
    }
}