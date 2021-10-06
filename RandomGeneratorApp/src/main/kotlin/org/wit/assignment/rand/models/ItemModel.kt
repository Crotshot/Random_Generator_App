package org.wit.assignment.rand.models

import com.sun.jdi.IntegerType

data class ItemModel(var id: Long = 0,
                          var name: String = "",
                          var weight: Float  = 0.0F,
                          var usedIn: List<Int> = emptyList()
)