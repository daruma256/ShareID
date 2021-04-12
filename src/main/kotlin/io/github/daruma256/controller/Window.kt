package io.github.daruma256.controller

import io.github.daruma256.Main
import io.github.daruma256.bot.DiscordUtil
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.Transferable
import java.net.URL
import java.util.*
import java.util.regex.Pattern

class Window: Initializable {
    @FXML lateinit var checkbox: CheckBox

    companion object {
        var isChecked: Boolean = false
    }

    @FXML lateinit var button: Button

    fun onCheck() {
        isChecked = checkbox.isSelected
        when (isChecked) {
            //自発モード
            true -> {
                button.text = "IDを投稿"
            }
            //救援モード
            false -> {
                button.text = "IDをコピー"
            }
        }
    }

    fun onClick() {
        when (isChecked) {
            //自発モード
            true -> {
                val clipboard = Toolkit.getDefaultToolkit().systemClipboard
                val obj: Transferable = clipboard.getContents(null)
                val str = try {
                    obj.getTransferData(DataFlavor.stringFlavor) as String
                } catch (e: Exception) {
                    return
                }
                //救援IDと一致しない文字列の送信があった場合用
                val p = Pattern.compile("^[A-Z0-9]")
                val m = p.matcher(str)
                if (!m.find()) {
                    return
                }
                if (str.length > 8) {
                    return
                }
                DiscordUtil.sendMessage(str)
                return
            }
            //救援モード
            false -> {
                val clipboard = Toolkit.getDefaultToolkit().systemClipboard
                val id = StringSelection(DiscordUtil.newId)
                clipboard.setContents(id, null)
            }
        }
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        Main.connectDiscord()
    }
}