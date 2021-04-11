package io.github.daruma256.extension

import io.github.daruma256.SceneGui
import javafx.fxml.FXMLLoader
import sun.reflect.CallerSensitive
import java.io.IOException

@CallerSensitive
@Throws(IOException::class)
fun <T> FXMLLoader.load(fxml: SceneGui): T {
    return load(ClassLoader.getSystemResourceAsStream(fxml.dir))
}