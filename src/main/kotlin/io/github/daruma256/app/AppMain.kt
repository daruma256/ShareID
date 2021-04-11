package io.github.daruma256.app

import io.github.daruma256.Main
import io.github.daruma256.SceneGui
import io.github.daruma256.bot.DiscordUtil
import io.github.daruma256.extension.load
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import java.net.URL
import java.util.*

class AppMain: Application() {
    override fun start(primaryStage: Stage) {
        Main.STAGE = primaryStage

        primaryStage.title = "ペア狩り君"
        primaryStage.icons.add(Image(ClassLoader.getSystemResourceAsStream("icon.png")))
        primaryStage.scene = Scene(FXMLLoader().load(SceneGui.WINDOW))
        primaryStage.setOnCloseRequest { DiscordUtil.quit() }
        primaryStage.show()
    }
}