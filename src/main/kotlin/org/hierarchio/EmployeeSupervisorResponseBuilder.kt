package org.hierarchio

import java.io.Closeable

class EmployeeSupervisorResponseBuilder : Closeable {
    private var countOfLevelsTraversed = 0;
    fun build(current: EmployeeNode, jsonToReturn: StringBuilder, countOfLevelsToTraverse: Int = 2){
        if(current.parent == null){ // travelled as far as we can
            jsonToReturn.insert(0, "{\"${current.id}\":{")
            jsonToReturn.append("}}")
            return;
        }
        else if(countOfLevelsTraversed == countOfLevelsToTraverse){ // travelled as far as we were supposed too
            jsonToReturn.insert(0, "{")
            jsonToReturn.append("}")
            return;
        }
        else{
            if(jsonToReturn.isEmpty()){
                jsonToReturn.insert(0, "\"${current.id}\":{}")
            }
            else{
                jsonToReturn.insert(0, "\"${current.id}\":{")
                jsonToReturn.append("}")
                countOfLevelsTraversed++
            }
            build(current.parent!!, jsonToReturn, countOfLevelsToTraverse)
        }
    }

    override fun close() {
        countOfLevelsTraversed = 0;
    }
}