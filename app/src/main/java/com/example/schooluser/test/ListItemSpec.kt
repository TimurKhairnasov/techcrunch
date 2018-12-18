package com.example.schooluser.test

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.litho.*
import com.facebook.litho.fresco.FrescoImage
import com.facebook.yoga.YogaEdge
import com.facebook.litho.ClickEvent
import com.facebook.litho.annotations.*
import com.facebook.litho.widget.TextStylesHelper
import kotlinx.coroutines.experimental.defer


@LayoutSpec
object ListItemSpec {
    @OnCreateLayout
    fun onCreateLayout(c : ComponentContext,
                       @Prop(optional = true) textColor: Int,
                       @Prop(optional = true) textSize: Float,
                       @Prop title: String,
                       @Prop subtitle: String,
                       @Prop uri: String,
                       @Prop url: String): Component {
        val controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri.substring(1, uri.length-1))
                .build()
        val column = children(c){

        }
//        val column = Column.create(c)
//                .paddingDip(YogaEdge.LEFT, 16F)
//                .child(
//                        com.facebook.litho.widget.Text.create(c)
//                                .text(title.substring(1, title.length-1))
//                                .textColor(textColor)
//                                .textSizeSp(textSize))
//                .child(
//                        com.facebook.litho.widget.Text.create(c)
//                                .text(subtitle.substring(1, subtitle.length-1))
//                                .textSizeSp(15F))
//                .build()
        return Row.create(c)
                .backgroundAttr(android.R.attr.selectableItemBackground)
                .paddingDip(YogaEdge.ALL, 16f)
                .child(FrescoImage.create(c)
                                .controller(controller)
                                .failureImageRes(R.drawable.ic_launcher_background)
                                .placeholderImageRes(R.drawable.ic_launcher_background)
                                .backgroundColor(Color.GRAY)
                                .widthDip(80f)
                                .heightDip(80f))
                .child(column)
                .clickHandler(ListItem.onClick(c, url))
                .build()

    }

    @OnEvent(ClickEvent::class)
    fun onClick(c: ComponentContext,
                @FromEvent view: View,
                @Param url: String,
                @Prop onClick: (String) -> Unit) {
        onClick(url)
    }

    @OnLoadStyle
    fun onLoadStyle(
            c: ComponentContext,
            textColor: Output<Int>,
            textSize: Output<Float>) {
        val a = c.obtainStyledAttributes(R.styleable.ListItem, 0)

        for (i in 0 until a.indexCount) {
            val attr = a.getIndex(i)

            when(attr) {
                R.styleable.ListItem_textColor -> textColor.set(a.getInt(attr, 0))
                R.styleable.ListItem_textSize -> textSize.set(a.getFloat(attr, 0F))
            }
        }

        a.recycle()
    }
}