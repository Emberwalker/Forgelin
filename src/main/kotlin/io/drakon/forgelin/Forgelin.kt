package io.drakon.forgelin

import org.apache.logging.log4j.LogManager
import net.minecraftforge.fml.common.Mod as mod

/**
 * Mod wrapper for the Kotlin adapter. Used for sharing libraries when not repacking. Doesn't actually *do* anything.
 *
 * @author Arkan <arkan@drakon.io>
 */
@mod(modid = "Forgelin", name = "Kotlin for Forge", version = "@VERSION@-@KOTLIN@", modLanguageAdapter = "io.drakon.forgelin.KotlinAdapter", acceptableRemoteVersions = "*")
public object Forgelin {}