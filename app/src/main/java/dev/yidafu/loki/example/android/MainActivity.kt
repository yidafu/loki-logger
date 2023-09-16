package dev.yidafu.loki.example.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.yidafu.loki.core.LokiLoggerContext
import dev.yidafu.loki.example.android.ui.theme.Example_androidTheme
import org.slf4j.LoggerFactory

class MainActivity : ComponentActivity() {
    private val logger = LoggerFactory.getLogger("MainActivity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = LoggerFactory.getILoggerFactory() as LokiLoggerContext

        setContent {
            Example_androidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column {
                        Button(onClick = {
                            repeat(10) {
                                logger.warn("repeat message")
                            }
                        }) {
                            Text("Repeat Msg")
                        }
                        Spacer(Modifier.height(20.dp))

                        Button(onClick = {
                            context.startReporters()
                        }) {
                            Text("开始上报")
                        }

                        Spacer(Modifier.height(20.dp))

                        Button(onClick = {
                            context.stopReporters()
                        }) {
                            Text("停止上报")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Example_androidTheme {
        Greeting("Android")
    }
}
