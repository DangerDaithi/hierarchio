package org.hierarchio

class RootNodeValidator {

    /**
     * Provided an employee tree hierarchy as a hash map, verifies only one root node and returns the root
     *
     * @param hierarchyToValidate The hierarchy to validate
     * @return the single root node once found
     * @throws MultipleRootNodeException if more than one root node found
     * @throws NoRootNodeFoundException if no root node found
     */
    fun validateAndGetSingleRoot(hierarchyToValidate : MutableMap<String, EmployeeNode>) : EmployeeNode{
        val roots = hierarchyToValidate.filter { it.value.parent == null }

        if(roots.isEmpty()){
            throw NoRootNodeFoundException("No root node was found. Ensure exactly one root node.")
        }

        if(roots.size > 1) {
            val rootNamesStringBuilder  = StringBuilder()
            roots.keys.forEach { rootNamesStringBuilder.append(it).append(", ") }
            throw MultipleRootNodeException("Multiple root nodes detected. ${rootNamesStringBuilder.toString()}")
        }

        return roots.entries.iterator().next().value
    }
}

class MultipleRootNodeException(message: String) : Exception(message)
class NoRootNodeFoundException(message: String) : Exception(message)