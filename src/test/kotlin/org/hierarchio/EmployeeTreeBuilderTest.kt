import org.hierarchio.EmployeeTreeBuilder
import org.hierarchio.EmployeeTreeLookup
import org.hierarchio.LoopDetectionException
import org.hierarchio.MultipleRootNodeException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


// These are sociable unit tests, indirectly tests KeyValueParser, EmployeeTreeLookup and RootNodeValidator
class EmployeeTreeBuilderTest {
    @Nested
    class Build
    {
        @Test
        fun passingValidHierarchy_DoesNotThrowException() {
            var rawHierarchy = mutableListOf<Pair<String, String>>()
            rawHierarchy.add(Pair("Pete", "Nick"))
            rawHierarchy.add(Pair("Barbara", "Nick"))
            rawHierarchy.add(Pair("Nick", "Sophie"))
            rawHierarchy.add(Pair("Sophie", "Jonas"))

            Assertions.assertDoesNotThrow{
                EmployeeTreeBuilder().build(rawHierarchy)
            }
        }

        @Test
        fun passingValidHierarchyString_DoesNotThrowException() {
            var rawHierarchyString = "{'Pete':'Nick', 'Barbara':'Nick', 'Nick':'Sophie', 'Sophie':'Jonas'}"
            Assertions.assertDoesNotThrow{
                EmployeeTreeBuilder().build(rawHierarchyString)
            }
        }

        @Test
        fun passingValidHierarchy_TreeIsCreatedAndRootHasNoParent() {
            var rawHierarchy = mutableListOf<Pair<String, String>>()
            rawHierarchy.add(Pair("Pete", "Nick"))
            rawHierarchy.add(Pair("Barbara", "Nick"))
            rawHierarchy.add(Pair("Nick", "Sophie"))
            rawHierarchy.add(Pair("Sophie", "Jonas"))

            var employeeTree = EmployeeTreeBuilder().build(rawHierarchy)

            Assertions.assertNotNull(employeeTree)
            Assertions.assertNull(employeeTree.parent)
        }

        @Test
        fun passingValidHierarchyString_TreeIsCreatedAndRootHasNoParent() {
            var rawHierarchyString = "{'Pete':'Nick', 'Barbara':'Nick', 'Nick':'Sophie', 'Sophie':'Jonas'}"
            var employeeTree = EmployeeTreeBuilder().build(rawHierarchyString)
            Assertions.assertNotNull(employeeTree)
            Assertions.assertNull(employeeTree.parent)
        }

        @Test
        fun passingValidHierarchy_TreeIsCreatedAndLeafNodesAreValid() {

            var rawHierarchy = mutableListOf<Pair<String, String>>()
            rawHierarchy.add(Pair("Pete", "Nick")) // leaf node 1
            rawHierarchy.add(Pair("Barbara", "Nick")) // leaf node 2
            rawHierarchy.add(Pair("Nick", "Sophie"))
            rawHierarchy.add(Pair("Sophie", "Jonas"))

            var employeeTree = EmployeeTreeBuilder().build(rawHierarchy)

            Assertions.assertNotNull(employeeTree)

            var lookup = EmployeeTreeLookup(employeeTree)
            var barbara = lookup.getEmployee("Barbara")
            Assertions.assertNotNull(barbara)
            Assertions.assertEquals("Barbara", barbara!!.id)
            Assertions.assertEquals(0, barbara.children.size)
            Assertions.assertEquals("Nick", barbara.parent!!.id)

            var pete = lookup.getEmployee("Pete")
            Assertions.assertNotNull(pete)
            Assertions.assertEquals("Pete", pete!!.id)
            Assertions.assertEquals(0, pete.children.size)
            Assertions.assertEquals("Nick", pete.parent!!.id)
        }

        @Test
        fun passingValidHierarchyString_TreeIsCreatedAndLeafNodesAreValid() {
            var rawHierarchyString = "{'Pete':'Nick', 'Barbara':'Nick', 'Nick':'Sophie', 'Sophie':'Jonas'}"
            var employeeTree = EmployeeTreeBuilder().build(rawHierarchyString)
            Assertions.assertNotNull(employeeTree)
            var lookup = EmployeeTreeLookup(employeeTree)

            var barbara = lookup.getEmployee("Barbara")
            Assertions.assertNotNull(barbara)
            Assertions.assertEquals("Barbara", barbara!!.id)
            Assertions.assertEquals(0, barbara.children.size)
            Assertions.assertEquals("Nick", barbara.parent!!.id)

            var pete = lookup.getEmployee("Pete")
            Assertions.assertNotNull(pete)
            Assertions.assertEquals("Pete", pete!!.id)
            Assertions.assertEquals(0, pete.children.size)
            Assertions.assertEquals("Nick", pete.parent!!.id)
        }

        @Test
        fun passingValidHierarchy_TreeIsCreatedAndSupervisorsAreValid() {
            var employeeTreeBuilder = EmployeeTreeBuilder()

            var rawHierarchy = mutableListOf<Pair<String, String>>()
            rawHierarchy.add(Pair("Pete", "Nick"))
            rawHierarchy.add(Pair("Barbara", "Nick"))
            rawHierarchy.add(Pair("Nick", "Sophie"))
            rawHierarchy.add(Pair("Sophie", "Jonas"))

            var employeeTree = employeeTreeBuilder.build(rawHierarchy)

            Assertions.assertNotNull(employeeTree)

            var lookup = EmployeeTreeLookup(employeeTree)
            var jonas = lookup.getEmployee("Jonas")
            Assertions.assertNotNull(jonas)
            Assertions.assertEquals("Jonas", jonas!!.id)
            Assertions.assertEquals(1, jonas.children.size)
            Assertions.assertEquals("Sophie", jonas.children["Sophie"]?.id)

            var sophie = lookup.getEmployee("Sophie")
            Assertions.assertNotNull(sophie)
            Assertions.assertEquals("Sophie", sophie!!.id)
            Assertions.assertEquals(1, sophie.children.size)
            Assertions.assertEquals("Nick", sophie.children["Nick"]?.id)

            var nick = lookup.getEmployee("Nick")
            Assertions.assertNotNull(nick)
            Assertions.assertEquals("Nick", nick!!.id)
            Assertions.assertEquals(2, nick.children.size)
            Assertions.assertEquals("Pete", nick.children["Pete"]?.id)
            Assertions.assertEquals("Barbara", nick.children["Barbara"]?.id)
        }

        @Test
        fun passingMultipleRootNodes_ThrowsMultipleRootNodeException() {
            var employeeTreeBuilder = EmployeeTreeBuilder()

            var rawHierarchy = mutableListOf<Pair<String, String>>()
            rawHierarchy.add(Pair("Pete", "Nick"))
            rawHierarchy.add(Pair("Barbara", "Nick"))
            rawHierarchy.add(Pair("Nick", "Sophie"))
            rawHierarchy.add(Pair("Sophie", "Dave")) // <- multiple root node
            rawHierarchy.add(Pair("Sophie", "Jonas"))

            Assertions.assertThrows(MultipleRootNodeException::class.java) {
                employeeTreeBuilder.build(rawHierarchy)
            }
        }

        @Test
        fun hierarchyContainsLoop_ThrowsLoopDetectionException() {

            var employeeTreeBuilder = EmployeeTreeBuilder()

            var rawHierarchy = mutableListOf<Pair<String, String>>()
            rawHierarchy.add(Pair("Pete", "Nick"))
            rawHierarchy.add(Pair("Barbara", "Nick"))
            rawHierarchy.add(Pair("Nick", "Sophie"))
            rawHierarchy.add(Pair("Jonas", "Pete")) // Pete is already an in-direct report of Jonas
            rawHierarchy.add(Pair("Sophie", "Jonas"))

            Assertions.assertThrows(LoopDetectionException::class.java) {
                employeeTreeBuilder.build(rawHierarchy)
            }
        }
    }
}