# Forgelin
_Kotlin support for Minecraft Forge 1.8-11.14.1.1371 or above_

## End user installation
1. Get Forgelin jar.
2. Put jar in `mods` folder.
3. Add mods which use Forgelin.
4. Punch wood.

## Using Forgelin
There are two options for deploying Forgeline for use in your mod - installing Forgelin as a mod dependancy, or repackaging it.

### Required for both
Both these methods assume you have the Kotlin and Forge plugins enabled in `build.gradle`:
```
buildscript {
	[...]
	dependencies {
		[...]
		classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:0.11.91.1"
	}
	[...]
}

apply plugin: 'kotlin'
apply plugin: 'forge'
[...]
```

### Mod dependency
This takes the least space on user systems, as it's shared between all mods using it, so only a single copy of the Kotlin stdlib is
present. Simply compile against the Forgelin jar in place of the Kotlin stdlib/reflect jars, and require users to install Forgelin
in the mods folder like any other mod. There's a placeholder mod in the project you can use for dependency and version checking (the
mod ID is 'Forgelin').

Once you've set up the dependancy, all you need to do is set `modLanguageAdapter = "io.drakon.forgelin.KotlinAdapter"` in your Mod
annotation. No, really, that's it. Not even setting `modLanguage`! (unless you want to, it won't break anything =P)

### Repackaging
This technique is a little more fiddly, and requires tinkering with your `build.gradle` file. **If you aren't comfortable with
Gradle shenanigans, use the dependancy option!**

As a second warning: **Doing this wrong could break other Kotlin-based mods!**

Now the warnings are out the way, we're going to use a technique called shading combined with repacking. Built jars of Forgelin
use shading themselves, to pack the Kotlin stdlib/reflect jars. Without Forgelin's install, we need to pack our own copies.

First, we need to add a new configuration:
```
configurations {
    shade
    compile.extendsFrom shade
}
```

Then on the lines where you would normally define `compile` in the main dependencies block for Kotlin, replace `compile` with
`shade` - below is an example dependency block (for Kotlin 0.11.91.1).
```
dependencies {
	shade 'org.jetbrains.kotlin:kotlin-stdlib:0.11.91.1'
	shade 'org.jetbrains.kotlin:kotlin-reflect:0.11.91.1'
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

Finally, ask ForgeGradle to add some extra rules for reobfuscation, to avoid namespacing issues with vanilla Forgelin/other
Kotlin versions on the classpath:
```
minecraft {
	[...]
	// Kotlin shading
	srgExtra "PK: kotlin your/package/here/repack/kotlin"
}
```
The destination package (`your/package/here/repack/kotlin` for this example) can be any package you think won't clash, either
with your code or anybody elses. As in the example, package paths use forward slash (`/`) in `PK` lines.

After you're done with Gradle, simply copy the `KotlinAdapter` source file (`src/main/kotlin/io/drakon/forgelin/KotlinAdapter.kt`)
somewhere in your project, changing the package path, then use the new path in the `Mod` annotation.

___If this seems too complicated, just use the mod dependency. It's really easier.___

## Classes or Objects?

___tl;dr - Use `object`, not `class`. It's better in every way for `@Mod`.___

This question is difficult to answer - for objects/classes outside your main `@Mod` object, use whatever you want. For the `@Mod` though,
things get a little more awkward. Forgelin is designed mostly to support `object`-style mods, and all the usual FML stuff works
with them. `class`-style works, but with one major caveat - `@SidedProxy` does **not** work right now due to the lack of statics in
Kotlin itself. Other stuff also might be broken, bug reports welcome!

## Licenses
Forgelin is licensed under the MIT License (see `LICENSE`).

Kotlin is property of Jetbrains. Look at their site for licensing bumf. This only really matters for redistributable jars which contain
the Kotlin stdlib/reflect classes.
