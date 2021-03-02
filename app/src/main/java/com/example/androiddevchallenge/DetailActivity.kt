package com.example.androiddevchallenge

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.model.DoggyModel

class DetailActivity : AppCompatActivity() {

    private lateinit var selectedDoggy: DoggyModel
    private var selectedPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val doggy = intent.getParcelableExtra<DoggyModel>(SELECTED_DOGGY)
        selectedPosition = intent.getIntExtra(SELECTED_POSITION, 0)
        if (doggy == null) {
            finish()
            return
        }
        selectedDoggy = doggy
        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = selectedDoggy.name
                            )
                        },
                        backgroundColor = Color.Transparent, elevation = 0.dp,
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                val backIcon: Painter = painterResource(R.drawable.ic_arrow_back)
                                Icon(painter = backIcon, contentDescription = "ic_back")
                            }
                        }
                    )
                }
            ) {
                ShowDogDetail(dog = selectedDoggy)
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(SELECTED_POSITION, selectedPosition)
        intent.putExtra(ADOPTED, selectedDoggy.adopted)
        setResult(RESULT_OK, intent)
        super.onBackPressed()
    }
}

var showConfirmDialog by mutableStateOf(false)

@Composable
fun ShowDogDetail(dog: DoggyModel) {
    val stateDog by remember { mutableStateOf(dog) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        DoggyAvatar(
            avatar = stateDog.avatar,
            name = stateDog.name
        )
        Spacer(
            modifier = Modifier.requiredHeight(26.dp)
        )
        DoggyTemperament(
            temperament = stateDog.temperament
        )
        Spacer(
            modifier = Modifier.requiredHeight(26.dp)
        )
        DoggyDesc(
            introduction = stateDog.desc
        )
        Spacer(
            modifier = Modifier.requiredHeight(26.dp)
        )
        AdoptButton(
            adopted = stateDog.adopted
        )
    }
    if (showConfirmDialog) {
        AdoptConfirmDialog(dog = stateDog)
    }
}

@Composable
fun DoggyAvatar(avatar: String, name: String) {
    val imageIdentity = BaseApp.context.resources.getIdentifier(
        avatar, "drawable",
        BaseApp.context.packageName
    )
    val image: Painter = painterResource(imageIdentity)
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = image,
            contentDescription = name,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun DoggyTemperament(temperament: String) {
    Text(
        text = temperament,
        fontSize = 22.sp,
        color = Color.Blue,
        style = MaterialTheme.typography.subtitle1
    )
}

@Composable
fun DoggyDesc(introduction: String) {
    Text(
        text = introduction,
        fontSize = 18.sp,
        style = MaterialTheme.typography.body1
    )
}

@Composable
fun AdoptButton(adopted: Boolean) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { showConfirmDialog = true },
            enabled = !adopted
        ) {
            Text(text = if (adopted) "Adopted" else "Adopt")
        }
    }
}

@Composable
fun AdoptConfirmDialog(dog: DoggyModel) {
    AlertDialog(
        onDismissRequest = {
            showConfirmDialog = false
        },
        title = {
            Text(
                text = "Confirm"
            )
        },
        text = {
            Text(
                text = "Wanna adopt this doggy? ^_^",
                style = MaterialTheme.typography.body2
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    showConfirmDialog = false
                    dog.adopted = true
                }
            ) {
                Text(
                    text = BaseApp.context.getString(android.R.string.ok)
                )
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    showConfirmDialog = false
                }
            ) {
                Text(
                    text = BaseApp.context.getString(android.R.string.cancel)
                )
            }
        }
    )
}