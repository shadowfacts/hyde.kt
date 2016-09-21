package net.shadowfacts.hyde

import net.shadowfacts.hyde.Includes

import java.io.File

/**
 * @author shadowfacts
 */
object HydeSite {

	val properties: MutableMap<String, String> = mutableMapOf()
	val collections: MutableMap<String, HydeCollection> = mutableMapOf()

	val posts: HydeCollection
		get() = collections["posts"]!!

	val pages: HydeCollection
		get() = collections["pages"]!!

	val name: String
		get() = properties["name"] ?: ""

	val description: String
		get() = properties["description"] ?: ""

	val site: String
		get() = properties["site"] ?: ""

	val basepath: String
		get() = properties["basepath"] ?: ""


	fun url(path: String): String {
		return site + basepath + path
	}

	fun file(path: String): File {
		return File(path)
	}

	fun include(run: Includes.() -> Unit): String {
		val includes = Includes()
		includes.run()
		return includes.includes.joinToString("\n")
	}

}

fun include(run: Includes.() -> Unit): String = HydeSite.include(run)