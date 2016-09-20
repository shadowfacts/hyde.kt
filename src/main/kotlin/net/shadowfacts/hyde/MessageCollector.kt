package net.shadowfacts.hyde

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector

/**
 * @author shadowfacts
 */
class MessageCollector : MessageCollector {

	override fun report(severity: CompilerMessageSeverity, message: String, location: CompilerMessageLocation) {
		System.err.println(String.format("[%s] [%s:%d:%d] %s", severity.name, location.path, location.line, location.column, message))
	}

	override fun hasErrors(): Boolean {
		return true
	}

}