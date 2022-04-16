@file:JvmName("Server")

package org.hierarchio

import org.slf4j.simple.SimpleLoggerFactory


class Server {
    companion object {
        @JvmStatic
        fun main(args:Array<String>) {

            // todo pass port in as arg
            HiearchioServer(7000, InMemoryRepository(), SimpleLoggerFactory()).use {
                it.start()
                while(true){

                }
            }

            println("Server stopped... bye...")
        }
    }
}
