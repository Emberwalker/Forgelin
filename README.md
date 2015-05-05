# Forgelin
_Kotlin support for Minecraft Forge_

## Using Forgelin
There are two options for deploying Forgeline for use in your mod - installing Forgelin as a mod dependancy, or repackaging it.

### Required for both
Both these methods assume you have the Kotlin plugin enabled in `build.gradle`:
```
buildscript {
	[...]
	dependencies {
		[...]
		classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:0.11.91.4"
```

### Mod dependency
Simply compile against the Forgelin jar in place of the Kotlin stdlib/reflect jars, and require users to install Forgelin in the
mods folder like any other mod. There's a placeholder mod in the project you can use for dependency and version checking (the mod
ID is 'Forgelin')

### Repackaging
This technique is a little more fiddly, and requires tinkering with your `build.gradle` file. **If you aren't comfortable with Gradle shenanigans, use the dependancy option!**

As a second warning: **Doing this wrong could break other Kotlin-based mods!**

Now the warnings are out the way, we're going to use a technique called shading combined with repacking. Built jars of Forgelin use shading themselves, to pack the Kotlin stdlib/reflect jars. _This is important, as you'll see below._

First, we need to set up the Kotlin Gradle plugin.
```

```