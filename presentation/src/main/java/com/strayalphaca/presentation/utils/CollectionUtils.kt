package com.strayalphaca.presentation.utils

inline fun <T> Iterable<T>.mapIf(condition : (T) -> Boolean, transform : (T) -> T) : List<T> {
    return this.map {
        if (condition(it)) {
            transform(it)
        } else {
            it
        }
    }
}