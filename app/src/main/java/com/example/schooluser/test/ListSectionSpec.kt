package com.example.schooluser.test


import com.facebook.litho.annotations.Prop
import com.facebook.litho.sections.common.SingleComponentSection
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.google.gson.JsonObject


@GroupSectionSpec
object ListSectionSpec {

    @OnCreateChildren
    fun onCreateChildren(c: SectionContext,
                         @Prop newItems: List<NewItem>,
                         @Prop onClick: (String) -> Unit): Children {
        val builder = Children.create()
        val style = R.style.SomeStyle

        newItems.forEachIndexed { index, article ->
            builder.child(
                    SingleComponentSection.create(c)
                            .key(index.toString())
                            .component(ListItem.create(c, 0, R.style.Some)
                                    .uri(article.uri.toString())
                                    .title(article.title.toString())
                                    .subtitle(article.source.toString())
                                    .url(article.url.toString())
                                    .onClick(onClick)
                                    .build()))
        }
        return builder.build()
    }
}