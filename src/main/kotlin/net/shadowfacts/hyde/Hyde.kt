package net.shadowfacts.hyde

import com.intellij.openapi.util.Disposer
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageCollectorUtil
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.JVMConfigurationKeys
import org.jetbrains.kotlin.config.addKotlinSourceRoot
import org.jetbrains.kotlin.script.StandardScriptDefinition
import org.jetbrains.kotlin.utils.PathUtil
import java.io.File
import java.nio.file.Files
import java.util.*

/**
 * @author shadowfacts
 */

fun main(args: Array<String>) {
//	print("File: ")
//	val path = Scanner(System.`in`).next()
//	val file = File(path)
//	val lines: MutableList<String> = file.readLines().toMutableList()
//	lines.add(0, "import net.shadowfacts.hyde.html.*;")
//	val tempDir = Files.createTempDirectory("hyde")
//	val script = File(tempDir.toFile(), file.name)
//	script.writeText(lines.joinToString("\n"))

	val config = CompilerConfiguration()
//	config.addKotlinSourceRoot(script.absolutePath)
	config.addKotlinSourceRoot("src/main/kotlin/net/shadowfacts/hyde/Test.kt")
	config.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector())
	config.put(JVMConfigurationKeys.MODULE_NAME, "HydeTest")
	config.put(JVMConfigurationKeys.SCRIPT_DEFINITIONS, listOf(StandardScriptDefinition))

	val rootDisposable = Disposer.newDisposable()

	try {
		val env = KotlinCoreEnvironment.createForProduction(rootDisposable, config, listOf())
		val paths = PathUtil.getKotlinPathsForCompiler()
		val result = KotlinToJVMBytecodeCompiler.compileAndExecuteScript(config, paths, env, listOf())
		println("finished")
	} finally {
		rootDisposable.dispose()
	}
}