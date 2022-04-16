package org.hierarchio

class EmployeeTreeBuilder() {

    fun build(rawHierarchyString : String): EmployeeNode
    {
        var parser = KeyValueParser()
        var keyValuePairHierarchy = parser.parse(rawHierarchyString)

        return build(keyValuePairHierarchy)
    }

    fun build(rawHierarchy: MutableList<Pair<String, String>>): EmployeeNode {

        var hierarchy = mutableMapOf<String, EmployeeNode>()

        rawHierarchy.forEach {

            var supervisor = hierarchy!![it.second]

            if (supervisor == null) {
                supervisor = EmployeeNode(it.second)
                hierarchy!![supervisor.id] = supervisor
            }

            // check to see if child already exists
            var child = hierarchy!![it.first]

            if (child == null) {
                child = EmployeeNode(it.first)
                hierarchy!![child.id] = child
            } else {
                // TODO if child already has a parent we need to reassign
                if (isChildSupervisor(supervisor, child)) {
                    throw LoopDetectionException(
                        "Supervisor to report loop detected. " +
                                "${child.id} is already a supervisor of ${supervisor.id}"
                    )
                }
            }
            supervisor.children[child.id] = child
            child.parent = supervisor
        }

        return RootNodeValidator().validateAndGetSingleRoot(hierarchy)
    }

    private fun isChildSupervisor(current: EmployeeNode, child: EmployeeNode): Boolean {
        return when {
            current.parent == null -> {
                false;
            }
            current.parent!!.id == child.id -> {
                true;
            }
            else -> {
                isChildSupervisor(current.parent!!, child)
            }
        }
    }
}

class LoopDetectionException(message: String) : Exception(message)
