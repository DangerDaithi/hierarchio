package org.hierarchio

import com.google.gson.JsonObject

class EmployeeNode(val id: String) {
    var parent: EmployeeNode? = null
    var children = mutableMapOf<String, EmployeeNode>()
    var jsonReports: JsonObject = JsonObject()

    fun reset(){
        this.parent = null
        this.children = mutableMapOf()
        this.jsonReports = JsonObject()
    }

    fun copy(): EmployeeNode {
        var copy = EmployeeNode(this.id)
        copy.parent = this.parent
        copy.children = mutableMapOf<String, EmployeeNode>()

        this.children.forEach{
            copy.children[it.key] = it.value
        }

        copy.jsonReports.add(this.id, jsonReports)
        return copy
    }
}