package org.hierarchio

class KeyValueParser{
    /**
     * Provided a json string of key value pairs, parses that string to a mutable list of Kotlin key value pairs
     * There might be a way to do this in gson or another similar json parsing lib
     * @param keyValues the raw string to parse, must be a list of json key value pairs e.g.
     * "{'Pete':'Nick', 'Barbara':'Nick', 'Nick':'Sophie', 'Sophie':'Jonas', 'Sophie':'Dave'}"
     * @return a list of key value pairs based on the string provided
     */
    fun parse(raw: String): MutableList<Pair<String, String>> {
        try {

            var keyValues = raw.replace("\r", "").replace("\n", "")

            var tokenized = keyValues.replace("{", "").replace("}", "").replace(" ", "")
                .split(",")
            var keyValuesToReturn = mutableListOf<Pair<String, String>>()

            tokenized.forEach {
                var keyToValue = it.split(":")
                if(keyToValue.size != 2) {
                    throw ParsingException("Key value pair was not in correct format. Expected 'key':'value', " +
                            "but got $tokenized")
                }
                //todo fix this
                var toAdd = Pair(keyToValue[0].replace("'", ""),
                    keyToValue[1].replace("'", ""))

                if(toAdd.first.isNullOrEmpty() || toAdd.second.isNullOrEmpty()){
                    throw ParsingException("Must provide valid key and value, must not be empty. " +
                            "Actual was '${toAdd.first}':'${toAdd.second}")
                }

                keyValuesToReturn.add(toAdd)
            }

            return keyValuesToReturn
        }catch (exception : Exception){
            throw ParsingException(exception.message.toString())
        }
    }
}

class ParsingException(message: String) : Exception(message)
