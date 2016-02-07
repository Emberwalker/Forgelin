package io.drakon.forge.kotlin

import net.minecraftforge.fml.common.ILanguageAdapter
import net.minecraftforge.fml.common.FMLModContainer
import net.minecraftforge.fml.common.ModContainer
import net.minecraftforge.fml.relauncher.Side
import org.apache.logging.log4j.LogManager

import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Kotlin implementation of FML's ILanguageAdapter.
 *
 * Use by setting <pre>modLanguageAdapter = "io.drakon.forge.kotlin.KotlinAdapter"</pre> in the Mod annotation.
 * Your Kotlin @Mod implementation <b>must</b> be an <pre>object</pre> type.
 * (Forge 1.8-11.14.1.1371 and Kotlin RC1 or above required)
 *
 * @author Arkan <arkan@drakon.io>
 * @author Carrot <git@bunnies.io>
 */
@Suppress("UNUSED")
class KotlinAdapter : ILanguageAdapter {

    private val logger = LogManager.getLogger("ILanguageAdapter/Kotlin")

    override fun setProxy(target: Field, proxyTarget: Class<*>, proxy: Any) {
        logger.debug("Setting proxy on target: {}.{} -> {}", target.declaringClass.simpleName, target.name, proxy)

        val instanceField = findInstanceFieldOrThrow(proxyTarget)
        val modObject = findModObjectOrThrow(instanceField)

        target.set(modObject, proxy)
    }

    override fun getNewInstance(container: FMLModContainer?, objectClass: Class<*>, classLoader: ClassLoader, factoryMarkedAnnotation: Method?): Any? {
        logger.debug("Constructing new instance of {}", objectClass.simpleName)

        val instanceField = findInstanceFieldOrThrow(objectClass)
        val modObject = findModObjectOrThrow(instanceField)

        return modObject
    }

    override fun supportsStatics() = false
    override fun setInternalProxies(mod: ModContainer?, side: Side?, loader: ClassLoader?) = Unit

    private fun findInstanceFieldOrThrow(targetClass: Class<*>): Field {
        val instanceField: Field = try {
            targetClass.getField("INSTANCE")
        } catch (exception: NoSuchFieldException) {
            throw noInstanceFieldException(exception)
        } catch (exception: SecurityException) {
            throw instanceSecurityException(exception)
        }

        return instanceField
    }

    private fun findModObjectOrThrow(instanceField: Field): Any {
        val modObject = try {
            instanceField.get(null)
        } catch (exception: IllegalArgumentException) {
            throw unexpectedInitializerSignatureException(exception)
        } catch (exception: IllegalAccessException) {
            throw wrongVisibilityOnInitializerException(exception)
        }

        return modObject
    }

    private fun noInstanceFieldException(exception: Exception) = KotlinAdapterException("Couldn't find INSTANCE singleton on Kotlin @Mod container", exception)
    private fun instanceSecurityException(exception: Exception) = KotlinAdapterException("Security violation accessing INSTANCE singleton on Kotlin @Mod container", exception)
    private fun unexpectedInitializerSignatureException(exception: Exception) = KotlinAdapterException("Kotlin @Mod object has an unexpected initializer signature, somehow?", exception)
    private fun wrongVisibilityOnInitializerException(exception: Exception) = KotlinAdapterException("Initializer on Kotlin @Mod object isn't `public`", exception)

    private class KotlinAdapterException(message: String, exception: Exception): RuntimeException("Kotlin adapter error - do not report to Forge! " + message, exception)
}