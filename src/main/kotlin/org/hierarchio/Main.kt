package org.hierarchio

import org.slf4j.simple.SimpleLoggerFactory

fun main(args:Array<String>) {

    // todo pass port in as arg
    HiearchioServer(7000, InMemoryRepository(), SimpleLoggerFactory()).use {
        it.start()

        var stop = false
        while(!stop){
            println("press any key to stop server...")
            readLine()
            stop = true
        }
    }

    println("Server stopped... bye...")
}