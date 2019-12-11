package io.atreydos.route4me.presentation.common.annotion

import androidx.annotation.MenuRes


@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class ActionMenu(@MenuRes val id: Int)
