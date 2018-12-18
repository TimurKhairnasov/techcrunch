package com.example.schooluser.test

import com.facebook.litho.ComponentContext
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext

@DslMarker
annotation class LithoMaker

fun children(c: ComponentContext, init: Children.Builder.() -> Unit){
	Children.create().apply(init)
}