package org.hierarchio

interface HierarchyRepository{
    fun create(raw: String)
    fun get(id: String): EmployeeNode?
    fun get(): EmployeeNode?
}