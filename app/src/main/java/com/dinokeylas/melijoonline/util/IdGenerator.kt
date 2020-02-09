package com.dinokeylas.melijoonline.util

import java.util.*

class IdGenerator {

    companion object{
        @JvmStatic
        fun generateId(random: Random): String {
            val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
            val text = CharArray(6)
            for (i in 0 until 6) {
                text[i] = characters[random.nextInt(characters.length)]
            }
            return String(text)
        }
    }

}