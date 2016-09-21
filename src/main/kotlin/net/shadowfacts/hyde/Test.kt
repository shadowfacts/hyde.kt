import net.shadowfacts.hyde.html.*

fun main(args: Array<String>) {
	val builder = StringBuilder()

	html {
		head {
			title("Test")
			link("test.css")
			style("""
			|h1 {
			|	color: red;
			|}
			""".trimMargin())
		}
		body {
			h1("a title")
			a("a link", "https://google.com") {
				title = "some link"
			}
			br()
			strong("I'm bold")
			br()
			img("test.png") {
				title = "something or other"
			}
			script {
				+"""
				|// stuff
				|console.log('done');
				""".trimMargin()
			}
		}
	}.render(builder, "")

	println(builder.toString())
}