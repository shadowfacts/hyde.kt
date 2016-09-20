package net.shadowfacts.hyde.html

/**
 * @author shadowfacts
 */
interface Element {
	fun render(builder: StringBuilder, indent: String = "")
}

class TextElement(val text: String) : Element {
	override fun render(builder: StringBuilder, indent: String) {
		text.split("\n").forEach {
			builder.append("$indent$it\n")
		}
	}
}

abstract class TagSingle(val name: String) : Element {
	val attributes = hashMapOf<String, String>()

	operator fun get(key: String): String? {
		return attributes[key]
	}

	operator fun set(key: String, value: String) {
		attributes[key] = value
	}

	override fun render(builder: StringBuilder, indent: String) {
		builder.append("$indent<$name")
		attributes.forEach { key, value ->
			builder.append(" $key=\"$value\"")
		}
		builder.append(">\n")
	}

	override fun toString(): String {
		val builder = StringBuilder()
		render(builder, "")
		return builder.toString()
	}

}

abstract class Tag(name: String) : TagSingle(name) {
	val children = arrayListOf<Element>()
	var title: String
		get() = attributes["title"] ?: ""
		set(value) {
			attributes["title"] = value
		}

	protected fun <T : Element> initTag(tag: T, init: T.() -> Unit): T {
		tag.init()
		children.add(tag)
		return tag
	}

	override fun render(builder: StringBuilder, indent: String) {
		super.render(builder, indent)
		children.forEach {
			it.render(builder, "$indent\t")
		}
		builder.append("$indent</$name>\n")
	}

	operator open fun String.unaryPlus() {
		children.add(TextElement(this))
	}

}

abstract class TagText(name: String) : Tag(name) {
	override operator fun String.unaryPlus() {
		children.add(TextElement(this))
	}
}

class HTML : Tag("html") {

	fun head(init: Head.() -> Unit) = initTag(Head(), init)
	fun body(init: Body.() -> Unit) = initTag(Body(), init)

	override fun render(builder: StringBuilder, indent: String) {
		builder.append("<!DOCTYPE html>\n")
		super.render(builder, indent)
	}

}

class Head : Tag("head") {
	fun title(text: String = "", init: Title.() -> Unit = {}) = initTag(Title(), { +text; init() })
	fun link(href: String = "", rel: String = "stylesheet") = initTag(Link(href, rel), {})
}

class Title : TagText("title")

class Link(href: String, rel: String) : TagSingle("link") {
	var href: String
		get() = attributes["href"] ?: ""
		set(value) {
			attributes["href"] = value
		}
	var rel: String
		get() = attributes["rel"] ?: ""
		set(value) {
			attributes["rel"] = value
		}
	init {
		this.href = href
		this.rel = rel
	}
}

abstract class TagContent(name: String) : TagText(name) {

	private fun <T : Tag> doInit(text: String, init: T.() -> Unit): T.() -> Unit {
		return {
			if (!text.isEmpty()) +text
			init()
		}
	}

	fun b(text: String = "", init: Bold.() -> Unit = {}) = initTag(Bold(), doInit(text, init))
	fun i(text: String = "", init: Italic.() -> Unit = {}) = initTag(Italic(), doInit(text, init))
	fun strong(text: String = "", init: Strong.() -> Unit = {}) = initTag(Strong(), doInit(text, init))
	fun em(text: String = "", init: Emphasis.() -> Unit = {}) = initTag(Emphasis(), doInit(text, init))
	fun p(text: String = "", init: Paragraph.() -> Unit = {}) = initTag(Paragraph(), doInit(text, init))
	fun h1(text: String = "", init: Header1.() -> Unit = {}) = initTag(Header1(), doInit(text, init))
	fun h2(text: String = "", init: Header2.() -> Unit = {}) = initTag(Header2(), doInit(text, init))
	fun h3(text: String = "", init: Header3.() -> Unit = {}) = initTag(Header3(), doInit(text, init))
	fun h4(text: String = "", init: Header4.() -> Unit = {}) = initTag(Header4(), doInit(text, init))
	fun h5(text: String = "", init: Header5.() -> Unit = {}) = initTag(Header5(), doInit(text, init))
	fun h6(text: String = "", init: Header6.() -> Unit = {}) = initTag(Header6(), doInit(text, init))
	fun a(text: String = "", href: String = "", init: Anchor.() -> Unit) = initTag(Anchor(href), doInit(text, init))
	fun br(init: Break.() -> Unit = {}) = initTag(Break(), init)
	fun hr(init: HorizontalRule.() -> Unit) = initTag(HorizontalRule(), init)
	fun img(src: String = "", init: Image.() -> Unit) = initTag(Image(src), init)

}

class Body : TagContent("body") {

	fun script(src: String = "", init: Script.() -> Unit = {}) = initTag(Script(src), init)

}

class Script() : TagContent("script") {
	var src: String
		get() = attributes["src"] ?: ""
		set(value) {
			attributes["src"] = value
		}

	constructor(src: String = "") : this() {
		if (!src.isEmpty()) {
			this.src = src
		}
	}

}

class Bold : TagContent("b")
class Italic : TagContent("i")
class Strong : TagContent("strong")
class Emphasis : TagContent("emphasis")
class Paragraph : TagContent("p")
class Header1 : TagContent("h1")
class Header2 : TagContent("h2")
class Header3 : TagContent("h3")
class Header4 : TagContent("h4")
class Header5 : TagContent("h5")
class Header6 : TagContent("h6")

class Break : TagSingle("br")
class HorizontalRule : TagSingle("hr")

class Anchor(href: String = "") : TagContent("a") {
	var href: String
		get() = attributes["href"] ?: ""
		set(value) {
			attributes["href"] = value
		}
	var target: String
		get() = attributes["target"] ?: ""
		set(value) {
			attributes["target"] = value
		}
	init {
		this.href = href
	}
}

class Image(src: String = "") : TagSingle("img") {
	var src: String
		get() = attributes["src"] ?: ""
		set(value) {
			attributes["src"] = value
		}
	var title: String
		get() = attributes["title"] ?: ""
		set(value) {
			attributes["title"] = value
		}
	var width: Int
		get() = Integer.parseInt(attributes["width"] ?: "0")
		set(value) {
			attributes["width"] = Integer.toString(value)
		}
	var height: Int
		get() = Integer.parseInt(attributes["height"] ?: "0")
		set(value) {
			attributes["height"] = Integer.toString(value)
		}
	init {
		this.src = src
	}
}

fun html(init: HTML.() -> Unit): HTML {
	val html = HTML()
	html.init()
	return html
}