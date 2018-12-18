package com.example.schooluser.test


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import android.arch.lifecycle.ViewModelProviders
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.experimental.CoroutineExceptionHandler


class MainFragment : Fragment() {
    var listNewItem = emptyList<NewItem>()
    var listNewItem2 = emptyList<NewItemRus>()

    var country = "us"
    private val lithoView by lazy { LithoView(ComponentContext(context)) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return lithoView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val myViewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
//        myViewModel.data.observe(this, Observer { newItems ->
//            val context = ComponentContext(activity)
//            val component: Component = RecyclerCollectionComponent.create(context)
//                    .disablePTR(true)
//                    .section(ListSection.create(SectionContext(context))
//                            .newItems(newItems!!)
//                            .onClick { url ->
//                                activity?.startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url.substring(1, url.length - 1))))
//                            }
//                            .build())
//                    .build()
//            lithoView.setComponent(component)
//        })

        myViewModel.data.value = emptyList()



        launch(CommonPool + CoroutineExceptionHandler { _, e ->
            Log.e("TAG", "CoroutineExceptionHandler", e)
        }) {

            val client = OkHttpClient()

            country = arguments!!.getString("country")

            val url = "https://newsapi.org/v2/top-headlines?" +
                    "country=$country&" +
                    "apiKey=af4ab37303764b4ca7ab1e4b1f4e912d"
            val request = Request.Builder()
                    .url(url)
                    .build()
            val response = client.newCall(request).execute()
            (response.body()?.string())?.let { responseString ->
                val gson = Gson()
                val json = gson.fromJson<JsonObject>(responseString, JsonObject::class.java)
                val dao = DB.instance.newItemDao()
                val item = NewItem()
                json["articles"].asJsonArray.map { it.asJsonObject }.forEachIndexed { index, article ->
                    item.id = index.toLong()
                    item.title = article["title"].toString()
                    item.source = article["source"].asJsonObject["name"].toString()
                    item.uri = article["urlToImage"].toString()
                    item.url = article["url"].toString()
                    dao.insert(item)
                }
                listNewItem = dao.getAllList()
                withContext(UI) {
                    myViewModel.data.value = listNewItem
                }
            }

        }

    }

    private var sub: Disposable? = null

    override fun onStart() {
        super.onStart()

        sub = DB.instance.newItemDao().getAll().observeOn(AndroidSchedulers.mainThread())
                .subscribe { newItems ->
                    val context = ComponentContext(activity)
                    val component: Component = RecyclerCollectionComponent.create(context)
                            .disablePTR(true)
                            .section(ListSection.create(SectionContext(context))
                                    .newItems(newItems)
                                    .onClick { url ->
                                        activity?.startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url.substring(1, url.length - 1))))
                                    }
                                    .build())
                            .build()
                    lithoView.setComponent(component)
                }
    }

    override fun onStop() {
        super.onStop()

        sub?.dispose()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main_menu, menu)
    }
}
