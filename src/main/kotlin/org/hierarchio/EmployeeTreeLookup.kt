package org.hierarchio

class EmployeeTreeLookup(val root: EmployeeNode) {

    fun getEmployee(id: String) : EmployeeNode? {
        return getEmployee(root, id)
    }

    private fun getEmployee(current: EmployeeNode, id: String): EmployeeNode? {
        if(current.id == id){
            return current
        }
        else if(current.children.isEmpty())
        {
            return null
        }
        else{
            var toReturn : EmployeeNode? = current.children[id]

            if(toReturn == null)
            {
                loop@ for (child in current.children) {
                    toReturn = getEmployee(child.value, id)
                    if(toReturn != null){
                        break@loop
                    }
                }
            }
            return toReturn
        }
    }
}