package com.example.withcontexttest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.VectorConverter
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.withcontexttest.ui.theme.WithContextTestTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WithContextTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DispatchersTest()
                }
            }
        }
    }
}

@Composable
fun DispatchersTest(vm: TestViewModel = hiltViewModel()) {
    var startAnimation by remember { mutableStateOf(false) }
    var usersCount by remember { mutableIntStateOf(0) }
    LazyColumn(modifier = Modifier.padding(top = 32.dp)) {
        item {
            SingleRunBallAnimation(trigger = startAnimation) { startAnimation = false }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), contentAlignment = Alignment.Center
            ) {
                TextField(
                    value = usersCount.toString(),
                    onValueChange = { newValue ->
                        newValue.toIntOrNull()?.let { usersCount = it }
                    },
                    label = { Text("Enter count of users") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    startAnimation = true
                    vm.saveUsers(usersCount)
                }) { Text("Save") }

                Button(onClick = {
                    startAnimation = true
                    vm.saveUsers(usersCount, Dispatchers.IO)
                }) { Text("Save IO") }

                Button(onClick = {
                    startAnimation = true
                    vm.saveUsers(usersCount, Dispatchers.Default)
                }) { Text("Save Default") }
            }
        }

        item {
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    startAnimation = true
                    vm.findOldestUser()
                }) { Text("Get") }

                Button(onClick = {
                    startAnimation = true
                    vm.findOldestUser(Dispatchers.IO)
                }) { Text("Get IO") }

                Button(onClick = {
                    startAnimation = true
                    vm.findOldestUser(Dispatchers.Default)
                }) { Text("Get Default") }
            }
        }

        item {
            var user = vm.user.collectAsState()
            user.value?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Max age of users: ${it.age}")
                }
            }
        }
    }
}


@Composable
fun SingleRunBallAnimation(trigger: Boolean, onFinished: () -> Unit) {
    val offsetX = remember { Animatable(0f) }
    val size = remember { Animatable(30f) }
    val color = remember { androidx.compose.animation.Animatable(Color.Gray) }
    var wasTriggered by remember { mutableStateOf(false) }

    LaunchedEffect(trigger) {
        if (trigger && !wasTriggered) {
            wasTriggered = true

            launch {
                color.animateTo(Color.Red, tween(10))
                size.animateTo(40f, tween(10))
            }

            offsetX.animateTo(
                targetValue = 300f,
                animationSpec = spring(dampingRatio = 0.5f, stiffness = 100f)
            )

            offsetX.animateTo(
                targetValue = 0f,
                animationSpec = spring(dampingRatio = 0.5f, stiffness = 100f)
            )

            offsetX.animateTo(15f, tween(1000))
            offsetX.animateTo(0f, tween(1000))

            launch {
                color.animateTo(Color.Gray, tween(1000))
                size.animateTo(30f, tween(1000))
            }

            onFinished()
            wasTriggered = false
        }
    }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .offset(x = offsetX.value.dp)
                .size(size.value.dp)
                .shadow(8.dp, CircleShape)
                .clip(CircleShape)
                .background(color.value, CircleShape)
        )
    }
}