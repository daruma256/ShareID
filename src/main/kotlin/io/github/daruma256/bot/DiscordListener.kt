package io.github.daruma256.bot

import io.github.daruma256.Main
import io.github.daruma256.controller.Window
import io.github.daruma256.sound.SoundUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.regex.Pattern

class DiscordListener: ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        //このBotからの送信でなければreturn
        if (!event.author.isBot) {
            return
        }
        val text: String = event.message.contentRaw
        //救援IDと一致しない文字列の送信があった場合用
        val p = Pattern.compile("^[A-Z0-9]")
        val m = p.matcher(text)
        if (!m.find()) {
            return
        }
        if (text.length > 8) {
            return
        }
        DiscordUtil.newId = text

        if (!Window.isChecked) {
            SoundUtil.play()
        }
    }
}