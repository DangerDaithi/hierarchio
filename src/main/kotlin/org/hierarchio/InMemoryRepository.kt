package org.hierarchio

class InMemoryRepository : HierarchyRepository
{
    var tree: EmployeeNode? = null

    override fun create(raw: String) {
        if(this.tree != null){
            this.tree!!.reset()
        }
        this.tree = EmployeeTreeBuilder().build(raw)
    }

    override fun get(id: String): EmployeeNode? {
        if(this.tree == null)
            return null
        return EmployeeTreeLookup(this.tree!!).getEmployee(id)
    }

    override fun get(): EmployeeNode? {
        return this.tree
    }
}