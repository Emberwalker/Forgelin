# Kotlin Adapter for Minecraft Forge
_Kotlin support for Minecraft Forge 1.8-11.14.1.1371 or above_

[![Build Status](https://drone.io/github.com/Emberwalker/Forgelin/status.png)](https://drone.io/github.com/Emberwalker/Forgelin/latest)

## Using the Adapter
The basic workflow for using the Adapter in your project is;
1. Either shade (and repack) the adapter jar or manually copy the adapter + helpers you want into your project
2. Shade (via a Gradle plugin or with ForgeGradle trickery) the version of Kotlin you want
3. Hack away

### Buildscript Preparation
We require the Kotlin and Forge plugins to be enabled in `build.gradle`:
```
buildscript {
	[...]
	dependencies {
		[...]
		classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.0.0-rc-1036"
	}
	[...]
}

apply plugin: 'kotlin'
apply plugin: 'net.minecraftforge.gradle.forge'
[...]
```

### Repackaging
This technique requires tinkering with your `build.gradle` file.

As a second warning: **Doing this wrong could break other Kotlin-based mods!**

Now the warnings are out the way, we're going to use a technique called shading combined with repacking. Built jars of
the adapter do not come with Kotlin installations. This means your mod *must* include its own, repackaged version.

First, we need to add a new configuration:
```
configurations {
    shade
    compile.extendsFrom shade
}
```

Then on the lines where you would normally define `compile` in the main dependencies block for Kotlin, replace `compile`
with `shade` - below is an example dependency block (for Kotlin 1.0.0-rc-1036.) If you need the kotlin-reflect library,
add that in the same way (but be aware this'll add >1MB to your jar size!)
```
dependencies {
	shade 'org.jetbrains.kotlin:kotlin-stdlib:1.0.0-rc-1036'
	shade 'io.drakon.forge:kotlin-adapter:1.0.0-rc-1036+1.8.9'
}
```

Next, tell Gradle what to do with this new `shade` configuration inside the `jar {}` block:
```
jar {
	[...]
	// Shading
    configurations.shade.each { dep ->
        from(project.zipTree(dep)){
            exclude 'META-INF', 'META-INF/**'
        }
    }
	[...]
}
```

Finally, ask ForgeGradle to add some extra rules for reobfuscation, to avoid namespace issues with other Kotlin versions
that may be on the classpath:
```
reobf {
    jar {
	    // Kotlin+Adapter shading
	    extra "PK: kotlin your/package/here/repack/kotlin"
	    extra "PK: org/jetbrains/annotations your/package/here/repack/annotations"
	    extra "PK: io/drakon/forge/kotlin your/package/here/repack/adapter"
	}
}
```
The destination package (`your/package/here/repack` for this example) can be any package you think won't clash, either
with your code or anybody else's packages. As in the example, package paths use forward slash (`/`) in `PK` lines.

Your @Mod implementation *must* be an `object`. There is no reason to make it a class and the adapter will not work if
you do so.

## Licenses
The Kotlin Adapter is licensed under the ISC License (see `LICENSE`), which is just a modernised version of the MIT
License.

Kotlin is property of Jetbrains. Look at their site for licensing bumf.
