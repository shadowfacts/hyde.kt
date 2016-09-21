import net.shadowfacts.hyde.html.*

fun main(args: Array<String>) {
	val builder = StringBuilder()

	val html = html {
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
			img("https://github.com/shadowfacts.png") {
				width = 50
				height = 50
				title = "something or other"
			}
			script {
				+"""
				|// stuff
				|console.log('done');
				""".trimMargin()
			}
		}
	}

	html.render(builder)
	println(builder.toString())
}