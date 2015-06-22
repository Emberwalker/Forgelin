package io.drakon.forgelin.debug

import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.LogManager
import kotlin.platform.platformStatic
import net.minecraftforge.fml.common.Mod as mod
import net.minecraftforge.fml.common.Mod.EventHandler as handler
import net.minecraftforge.fml.common.SidedProxy as proxy

/**
 * Test mod (class-style)
 */
mod(modid = "Forgelin-Workbench-Class", name = "Forgelin Debug - Class", modLanguageAdapter = "io.drakon.forgelin.KotlinAdapter")
public class ForgelinWorkbenchClass {

    private val log = LogManager.getLogger("Workbench/Cls")

    companion object {
        proxy(clientSide = "io.drakon.forgelin.debug.ProxyClient", serverSide = "io.drakon.forgelin.debug.ProxyServer")
        platformStatic var proxy: Proxy? = null // Cannot be private, crashes the Kotlin compiler
        // platformStatic will indicate to the compiler that the field should be a top-level static field
    }

    public handler fun preinit(evt:FMLPreInitializationEvent) {
        log.info("Preinit.")
    }

    public handler fun init(evt: FMLInitializationEvent) {
        log.info("Init.")
    }

    public handler fun postinit(evt: FMLPostInitializationEvent) {
        log.info("Postinit.")
    }

}