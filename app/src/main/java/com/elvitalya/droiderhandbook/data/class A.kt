package com.elvitalya.droiderhandbook.data

class  A {
    val date: String = ""

    val age = 1

    val name = "puma"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as A

        if (date != other.date) return false
        if (age != other.age) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + age
        result = 31 * result + name.hashCode()
        return result
    }


}