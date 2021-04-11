package io.github.daruma256.bot

import javafx.application.Platform
import net.dv8tion.jda.api.JDA

object DiscordUtil {
    lateinit var JDA: JDA
    lateinit var botToken: String
    var botGuildId: Long = 0L
    var botChannelId: Long = 0L

    var newId:String = ""

    fun sendMessage(text: String) {
        sendMessage(botGuildId, botChannelId, text)
    }

    fun sendMessage(guild: Long, channel: Long, text: String) {
        val textChannel = JDA.getGuildById(guild)?.getTextChannelById(channel) ?: return
        textChannel.sendMessage(text).queue()
    }

    fun quit(){
        JDA.shutdown()
        Platform.exit()
    }
}