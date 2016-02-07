package io.drakon.forge.kotlin.tests.samplemod

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.LogManager

@Mod(modid = "Forgelin|Test", name = "Forgelin Test Mod", modLanguage = "kotlin", modLanguageAdapter = "KotlinAdapter")
object TestMod {

    @Mod.EventHandler
    fun preinit(evt: FMLPreInitializationEvent) {
        LogManager.getLogger().info("PRE")
    }

    @Mod.EventHandler
    fun init(evt: FMLInitializationEvent) {
        LogManager.getLogger().info("INIT")
    }

    @Mod.EventHandler
    fun postinit(evt: FMLPostInitializationEvent) {
        LogManager.getLogger().info("POST")
    }

}