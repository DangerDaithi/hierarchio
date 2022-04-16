package org.hierarchio

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class KeyValueParserTest {
    @Nested
    class Parse
    {
        @Test
        fun providedValidInput_SuccessfullyParses() {
            var parser = KeyValueParser()
            var toParse = "{'Pete':'Nick', 'Barbara':'Nick', 'Nick':'Sophie', 'Sophie':'Jonas', 'Sophie':'Dave'}"
            var reportsToSupervisors = parser.parse(toParse)
            Assertions.assertNotNull(reportsToSupervisors)
            Assertions.assertEquals(5, reportsToSupervisors.size)

            Assertions.assertEquals("Pete", reportsToSupervisors[0].first)
            Assertions.assertEquals("Nick", reportsToSupervisors[0].second,)

            Assertions.assertEquals("Barbara", reportsToSupervisors[1].first)
            Assertions.assertEquals("Nick",reportsToSupervisors[1].second)

            Assertions.assertEquals("Nick", reportsToSupervisors[2].first)
            Assertions.assertEquals("Sophie", reportsToSupervisors[2].second)

            Assertions.assertEquals("Sophie", reportsToSupervisors[3].first)
            Assertions.assertEquals("Jonas", reportsToSupervisors[3].second)

            Assertions.assertEquals("Sophie", reportsToSupervisors[4].first)
            Assertions.assertEquals("Dave", reportsToSupervisors[4].second)
        }

        @Test
        fun providedInvalidInput_ThrowsParsingException() {
            var parser = KeyValueParser()

            // incorrect number of keys and values
            var invalidInput = "{'Pete':'Nick':'incorrect', 'Barbara':'Nick', 'Nick':'Sophie', 'Sophie':'Jonas', 'Sophie':'Dave'}"
            Assertions.assertThrows(ParsingException::class.java) {
                parser.parse(invalidInput)
            }

            // empty key
            invalidInput = "{'Pete':'Nick', '':'Nick', 'Nick':'Sophie', 'Sophie':'', 'Sophie':'Dave'}"
            Assertions.assertThrows(ParsingException::class.java) {
                parser.parse(invalidInput)
            }
        }
    }
}