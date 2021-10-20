package org.wit.assignment.rand.models

interface Entity {
    var id: Int
    var name: String
}

//Entity is a simple class that contains the data that is shared across all objects
//As data classes cannot be inherited I used an interface instead