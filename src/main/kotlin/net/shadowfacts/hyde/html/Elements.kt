package net.shadowfacts.hyde.html

/**
 * @author shadowfacts
 */
interface Element {
	fun render(builder: StringBuilder, indent: String = "")
}

class TextElement(val text: String) : Element {
	override fun render(builder: StringBuilder, indent: String) {
		builder.append("$indent$text\n")
	}
}

abstract class Tag(val name: String) : Element {
	val children = arrayListOf<Element>()
	val attributes = hashMapOf<String, String>()

	protected fun <T : Element> initTag(tag: T, init: T.() -> Unit): T {
		tag.init()
		children.add(tag)
		return tag
	}

	override fun render(builder: StringBuilder, indent: String) {
		val attributes = renderAttributes()
		builder.append("$indent<$name$attributes>\n")
		children.forEach {
			it.render(builder, "$indent\t")
		}
		builder.append("$indent</$name>\n")
	}

	private fun renderAttributes(): String {
		val builder = StringBuilder()
		attributes.forEach { key, value ->
			builder.append(" $key=\"$value\"")
		}
		return builder.toString()
	}

	override fun toString(): String {
		val builder = StringBuilder()
		render(builder, "")
		return builder.toString()
	}

}

abstract class TagWithText(name: String) : Tag(name) {
	operator fun String.unaryPlus() {
		children.add(TextElement(this))
	}
}

class HTML : Tag("html") {

	fun head(init: Head.() -> Unit) = initTag(Head(), init)
	fun body(init: Body.() -> Unit) = initTag(Body(), init)

}

class Head : Tag("head") {
	fun title(init: Title.() -> Unit) = initTag(Title(), init)
}

class Title : TagWithText("title")

abstract class BodyTag(name: String) : TagWithText(name) {

	fun b(init: Bold.() -> Unit) = initTag(Bold(), init)
	fun i(init: Italic.() -> Unit) = initTag(Italic(), init)
	fun strong(init: Strong.() -> Unit) = initTag(Strong(), init)
	fun em(init: Emphasis.() -> Unit) = initTag(Emphasis(), init)
	fun p(init: Paragraph.() -> Unit) = initTag(Paragraph(), init)
	fun h1(init: Header1.() -> Unit) = initTag(Header1(), init)
	fun h2(init: Header2.() -> Unit) = initTag(Header2(), init)
	fun h3(init: Header3.() -> Unit) = initTag(Header3(), init)
	fun h4(init: Header4.() -> Unit) = initTag(Header4(), init)
	fun h5(init: Header5.() -> Unit) = initTag(Header5(), init)
	fun h6(init: Header6.() -> Unit) = initTag(Header6(), init)
	fun a(href: String, init: Anchor.() -> Unit) = initTag(Anchor(href), init)
	fun br(init : Break.() -> Unit) = initTag(Break(), init)

}

class Body : BodyTag("body")
class Bold : BodyTag("b")
class Italic : BodyTag("i")
class Strong : BodyTag("strong")
class Emphasis : BodyTag("emphasis")
class Paragraph : BodyTag("p")
class Header1 : BodyTag("h1")
class Header2 : BodyTag("h2")
class Header3 : BodyTag("h3")
class Header4 : BodyTag("h4")
class Header5 : BodyTag("h5")
class Header6 : BodyTag("h6")

class Break : Element {
	override fun render(builder: StringBuilder, indent: String) {
		builder.append("$indent<br>\n")
	}
}

class Anchor(href: String = "") : BodyTag("a") {
	var href: String
		get() {
			return attributes["href"] ?: ""
		}
		set(value) {
			attributes["href"] = value
		}
	init {
		this.href = href
	}
}

fun html(init: HTML.() -> Unit): HTML {
	val html = HTML()
	html.init()
	return html
}