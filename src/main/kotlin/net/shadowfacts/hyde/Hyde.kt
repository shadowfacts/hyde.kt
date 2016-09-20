package net.shadowfacts.hyde

import java.io.File
import java.util.*

/**
 * @author shadowfacts
 */

fun main(args: Array<String>) {
	print("File: ")
	val path = Scanner(System.`in`).next()
	val file = File(path)
	val lines: MutableList<String> = file.readLines().toMutableList()
	lines.add(0, "import net.shadowfacts.hyde.html.*;")

}