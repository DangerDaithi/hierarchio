package org.hierarchio

import com.google.gson.JsonObject

class GsonEmployeeHierarchyResponseBuilder {
    fun build(current: EmployeeNode){
        if(current.children.isNullOrEmpty()){
            current.parent!!.jsonReports.add(current.id, JsonObject())
            return
        }
        else{
            current.children.forEach{
                build(it.value)
            }
            if(current.parent == null){
                var copy = current.jsonReports
                current.jsonReports = JsonObject()
                current.jsonReports.add(current.id, copy)
                return;
            }
            else{
                current.parent!!.jsonReports.add(current.id, current.jsonReports)
            }
        }
    }
}