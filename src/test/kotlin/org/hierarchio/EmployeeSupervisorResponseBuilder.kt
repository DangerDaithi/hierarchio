package org.hierarchio

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class EmployeeSupervisorResponseBuilderTest {
    @Nested
    class Build
    {
        @Test
        fun withTwoLevelsToTraverseToRootNode_ResponseIsValid() {

            var expected = "{\"Jonas\":{\"Sophie\":{\"Nick\":{}}}}"

            var rawHierarchy = mutableListOf<Pair<String, String>>()
            rawHierarchy.add(Pair("Pete", "Nick"))
            rawHierarchy.add(Pair("Barbara", "Nick"))
            rawHierarchy.add(Pair("Nick", "Sophie"))
            rawHierarchy.add(Pair("Sophie", "Jonas"))
            var tree = EmployeeTreeBuilder().build(rawHierarchy)

            var nick = EmployeeTreeLookup(tree).getEmployee("Nick")
            Assertions.assertNotNull(nick)

            var actual = StringBuilder()
            EmployeeSupervisorResponseBuilder().build(nick!!, actual)

            Assertions.assertEquals(expected, actual.toString())
        }

        @Test
        fun withTwoLevelsToTraverse_ResponseIsValid() {

            var expected = "{\"Jonas\":{\"Sophie\":{\"Nick\":{}}}}"

            var rawHierarchy = mutableListOf<Pair<String, String>>()
            rawHierarchy.add(Pair("Pete", "Nick"))
            rawHierarchy.add(Pair("Barbara", "Nick"))
            rawHierarchy.add(Pair("Nick", "Sophie"))
            rawHierarchy.add(Pair("Sophie", "Jonas"))
            rawHierarchy.add(Pair("Jonas", "Dave"))
            rawHierarchy.add(Pair("Dave", "Sarah"))
            var tree = EmployeeTreeBuilder().build(rawHierarchy)

            var nick = EmployeeTreeLookup(tree).getEmployee("Nick")
            Assertions.assertNotNull(nick)

            var actual = StringBuilder()
            EmployeeSupervisorResponseBuilder().build(nick!!, actual)

            Assertions.assertEquals(expected, actual.toString())
        }

        @Test
        fun CannotTraversePastRootNode_ResponseIsValid() {

            var expected = "{\"Sarah\":{\"Dave\":{}}}"

            var rawHierarchy = mutableListOf<Pair<String, String>>()
            rawHierarchy.add(Pair("Pete", "Nick"))
            rawHierarchy.add(Pair("Barbara", "Nick"))
            rawHierarchy.add(Pair("Nick", "Sophie"))
            rawHierarchy.add(Pair("Sophie", "Jonas"))
            rawHierarchy.add(Pair("Jonas", "Dave"))
            rawHierarchy.add(Pair("Dave", "Sarah"))
            var tree = EmployeeTreeBuilder().build(rawHierarchy)

            var dave = EmployeeTreeLookup(tree).getEmployee("Dave")
            Assertions.assertNotNull(dave)

            var actual = StringBuilder()
            EmployeeSupervisorResponseBuilder().build(dave!!, actual)

            Assertions.assertEquals(expected, actual.toString())
        }
    }
}