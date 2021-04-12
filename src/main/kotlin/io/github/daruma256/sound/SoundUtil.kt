package io.github.daruma256.sound

import io.github.daruma256.Main
import javafx.application.Platform
import javafx.scene.control.Alert
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.Toolkit
import java.io.File
import java.nio.file.Paths
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.DataLine

object SoundUtil {
    fun play() {
        val protectionDomain = this.javaClass.protectionDomain
        val codeSource = protectionDomain.codeSource
        val uri = codeSource.location.toURI()
        val path = Paths.get(uri).parent
        val alertFile = File(path.resolve("config/alert.wav").toUri())

        try {
            val ais: AudioInputStream = AudioSystem.getAudioInputStream(alertFile)
            val af = ais.format
            val dataLine = DataLine.Info(Clip::class.java, af)
            val c = AudioSystem.getLine(dataLine) as Clip
            c.open(ais)
            GlobalScope.launch {
                c.start()
                delay(1000)
                c.close()
            }
        } catch (e: Exception) {
            val alert = Alert(Alert.AlertType.ERROR)
            alert.headerText = null
            alert.contentText = "アラート音の読み込みに失敗しました"
            Toolkit.getDefaultToolkit().beep()
            alert.showAndWait()
            Platform.exit()
            return
        }
    }
}