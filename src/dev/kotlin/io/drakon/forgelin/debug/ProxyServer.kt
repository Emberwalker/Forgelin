package io.drakon.forgelin.debug

import org.apache.logging.log4j.LogManager

/**
 * A test proxy, what do you want from me?! D=
 *
 * Server-side.
 */
public class ProxyServer : Proxy {

    init {
        LogManager.getLogger().info("Server proxy constructed.")
    }

}