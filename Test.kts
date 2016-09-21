import net.shadowfacts.hyde.html.*
import java.util.*

val html = html {
	head {
		title {
			+"test"
		}
	}
	body {
		div {
			h1("a header")
			p("some text")
		}
	}
}

val builder = StringBuilder()
html.render(builder)
println(builder.toString())