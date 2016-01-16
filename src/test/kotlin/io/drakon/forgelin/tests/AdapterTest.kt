package io.drakon.forgelin.tests

import io.drakon.forgelin.KotlinAdapter
import io.drakon.forgelin.tests.dummy.Proxy
import io.drakon.forgelin.tests.dummy.ProxyClient
import io.drakon.forgelin.tests.dummy.ProxyServer
import net.minecraftforge.fml.common.SidedProxy
import kotlin.jvm.JvmStatic
import org.junit.After as post
import org.junit.Before as pre
import org.junit.Test as test

public class AdapterTest {

    val adapter: KotlinAdapter = KotlinAdapter()

    @pre fun setup() {}

    @test fun testNewInstanceObject() {
        val inst = adapter.getNewInstance(null, TestObject.javaClass, ClassLoader.getSystemClassLoader(), null)
        assert(inst == TestObject)
    }

    @test fun testNewInstanceClass() {
        val inst = adapter.getNewInstance(null, javaClass<TestClass>(), ClassLoader.getSystemClassLoader(), null)
        assert(inst is TestClass)
    }

    @test fun testSetInternalProxies() {} // NOOP

    @test fun testSetProxyObject() {
        val f = TestObject.javaClass.getField("proxy")

        adapter.setProxy(f, TestObject.javaClass, ProxyClient())
        assert(TestObject.proxy is ProxyClient)

        adapter.setProxy(f, TestObject.javaClass, ProxyServer())
        assert(TestObject.proxy is ProxyServer)
    }

    @test fun testSetProxyClass() {
        // For whatever reason calling 'javaClass' gets us the internal companion class, instead of the class itself
        val clazz = javaClass<TestClass>()
        val f = clazz.getField("proxy")

        adapter.setProxy(f, clazz, ProxyClient())
        assert(TestClass.proxy is ProxyClient)

        adapter.setProxy(f, clazz, ProxyServer())
        assert(TestClass.proxy is ProxyServer)
    }

    @post fun teardown() {}

    public object TestObject {
        @SidedProxy(clientSide = "io.drakon.forgelin.tests.dummy.ProxyClient", serverSide = "io.drakon.forgelin.tests.dummy.ProxyServer")
        public var proxy: Proxy? = null
    }

    public class TestClass {
        companion object {
            @JvmStatic
            @SidedProxy(clientSide = "io.drakon.forgelin.tests.dummy.ProxyClient", serverSide = "io.drakon.forgelin.tests.dummy.ProxyServer")
            public var proxy: Proxy? = null
        }
    }
}