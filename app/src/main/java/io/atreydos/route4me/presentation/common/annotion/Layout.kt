package io.atreydos.route4me.presentation.common.annotion

import androidx.annotation.LayoutRes


@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Layout(@LayoutRes val id: Int)
