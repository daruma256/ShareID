package io.github.daruma256

import io.github.daruma256.app.AppMain
import io.github.daruma256.bot.DiscordListener
import io.github.daruma256.bot.DiscordUtil
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.stage.Stage
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import java.io.File
import java.io.InputStream
import java.lang.NumberFormatException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.regex.Pattern

object Main {
    lateinit var STAGE: Stage

    lateinit var PROPERTY: Properties

    fun loadConfig() {
        val protectionDomain = this.javaClass.protectionDomain
        val codeSource = protectionDomain.codeSource
        val uri = codeSource.location.toURI()
        val path = Paths.get(uri).parent
        val configFolder = File(path.resolve("config").toUri())
        val configFile = File(path.resolve("config/config.properties").toUri())

        //コンフィグディレクトリの存在を確認
        if (!configFolder.exists()) {
            configFolder.mkdir()
        }
        //コンフィグファイル存在確認
        if (!configFile.exists()) {
            //存在しなかった場合jar内からデフォルト値を展開
            val srcIs: InputStream = ClassLoader.getSystemResourceAsStream("config/config.properties")
                ?: return//jar内にもファイルが存在しない場合
            Files.copy(srcIs, path.resolve("config/config.properties"))
        }

        PROPERTY = Properties().apply {
            this.load(Files.newBufferedReader(path.resolve("config/config.properties"), StandardCharsets.UTF_8))
        }
    }

    fun connectDiscord() {
        DiscordUtil.botToken = PROPERTY.getProperty("bot_token", "")
        if (DiscordUtil.botToken.isEmpty()) {
            val alert = Alert(AlertType.ERROR)
            alert.headerText = null
            alert.contentText = "DiscordBotのtokenが未入力です"
            alert.showAndWait()
            Platform.exit()
            return
        }

        DiscordUtil.botGuildId = try {
            PROPERTY.getProperty("bot_guild", "0").toLong()
        } catch (e: NumberFormatException) {
            0L
        }
        if (DiscordUtil.botGuildId == 0L) {
            val alert = Alert(AlertType.ERROR)
            alert.headerText = null
            alert.contentText = "DiscordBotのGuildIdが未入力か不正な値です"
            alert.showAndWait()
            Platform.exit()
            return
        }

        DiscordUtil.botChannelId = try {
            PROPERTY.getProperty("bot_channel", "0").toLong()
        } catch (e: NumberFormatException) {
            0L
        }
        if (DiscordUtil.botChannelId == 0L) {
            val alert = Alert(AlertType.ERROR)
            alert.headerText = null
            alert.contentText = "DiscordBotのChannelIdが未入力か不正な値です"
            alert.showAndWait()
            Platform.exit()
            return
        }

        DiscordUtil.JDA = JDABuilder.createDefault(DiscordUtil.botToken)
            .setActivity(Activity.playing("古戦場"))
            .addEventListeners(DiscordListener())
            .build()
        DiscordUtil.JDA.awaitReady()
    }
}

fun main(args: Array<String>) {
    Main.loadConfig()

    Application.launch(AppMain().javaClass, *args)
}