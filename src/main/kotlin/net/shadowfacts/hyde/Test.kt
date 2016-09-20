import net.shadowfacts.hyde.html.*

fun main(args: Array<String>) {
	val builder = StringBuilder()

	html {
		head {
			title { +"Test" }
		}
		body {
			a("https://google.com") { +"a link" }
			br {}
			strong { +"I'm bold" }
		}
	}.render(builder, "")

	println(builder.toString())
}