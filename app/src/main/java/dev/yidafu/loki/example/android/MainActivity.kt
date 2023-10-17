package dev.yidafu.loki.example.android

import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory

class MainActivity : ComponentActivity() {
    private val logger = LoggerFactory.getLogger("MainActivity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loggerContext = LoggerFactory.getILoggerFactory() as LokiLoggerContext

        setContent {
            Example_androidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Log.i("Main", "Render")
                    Column {
                        Button(onClick = {
                            GlobalScope.launch {
                                repeat(10) {
                                    delay(10)
                                    logger.warn("repeat message")
                                }
                            }
                        }) {
                            Text("Repeat Msg X 10")
                        }
                        Spacer(Modifier.height(20.dp))

                        Button(onClick = {
                            Log.i("Main", "开始上报")
                            loggerContext.startReporters()
                        }) {
                            Text("开始上报")
                        }

                        Spacer(Modifier.height(20.dp))

                        Button(onClick = {
                            loggerContext.stopReporters()
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
