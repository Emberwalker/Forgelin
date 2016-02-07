package io.drakon.forgelin.debug

import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.LogManager
import net.minecraftforge.fml.common.Mod as mod
import net.minecraftforge.fml.common.Mod.EventHandler as handler
import net.minecraftforge.fml.common.SidedProxy as proxy

/**
 * Test mod (object-style)
 */
mod(modid = "Forgelin-Workbench-Obj", name = "Forgelin Debug - Object", modLanguageAdapter = "KotlinAdapter")
public object ForgelinWorkbench {

    private val log = LogManager.getLogger("Workbench/Obj")

    proxy(clientSide = "io.drakon.forgelin.debug.ProxyClient", serverSide = "io.drakon.forgelin.debug.ProxyServer")
    private var proxy: Proxy? = null

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