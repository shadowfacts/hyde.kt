package net.shadowfacts.hyde

import java.io.File

/**
 * @author shadowfacts
 */
open class Includes {

	val includes: MutableList<String> = mutableListOf()

	fun partial(name: String) {
		includes.add(HydeSite.file("_partials/$name").readText())
	}

}