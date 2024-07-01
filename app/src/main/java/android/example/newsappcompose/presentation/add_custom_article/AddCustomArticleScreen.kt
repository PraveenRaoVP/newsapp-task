package android.example.newsappcompose.presentation.add_custom_article

import android.content.ClipDescription
import android.example.newsappcompose.presentation.navgraph.Route
import android.example.newsappcompose.ui.theme.NewsAppComposeTheme
import android.media.Image
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddCustomArticleScreen(
    onEvent: (AddCustomArticleEvent) -> Unit,
    state: AddCustomArticleState,
    navigateToPopUp: (String, String) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val getContent =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                onEvent(AddCustomArticleEvent.DragDropImage(it.toString()))
            }
        }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = state.title,
                onValueChange = { onEvent(AddCustomArticleEvent.TitleChange(it)) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Title") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable {
                        coroutineScope.launch {
                            getContent.launch("image/*")
                        }
                    }
                    .dragAndDropTarget(
                        shouldStartDragAndDrop = { startEvent: DragAndDropEvent ->
                            startEvent
                                .mimeTypes()
                                .any { it.startsWith("image/") }
                        },
                        target = object : DragAndDropTarget {
                            override fun onDrop(event: DragAndDropEvent): Boolean {
                                val clipData = event.toAndroidDragEvent().clipData
                                clipData?.getItemAt(0)?.uri?.let {
                                    onEvent(AddCustomArticleEvent.DragDropImage(it.toString()))
                                }
                                return true
                            }

                            override fun onStarted(event: DragAndDropEvent) {
                                super.onStarted(event)
                            }

                            override fun onEnded(event: DragAndDropEvent) {
                                super.onEnded(event)
                            }
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (state.imageUrl != null) {
                    Log.i("AddCustomArticleScreen", "AddCustomArticleScreen: ${state.imageUrl}")
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context)
                                .data(Uri.parse(state.imageUrl))
                                .build()
                        ),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text("Click to Select Image or Drag and Drop Here")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = state.author,
                onValueChange = { onEvent(AddCustomArticleEvent.AuthorChange(it)) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Author") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = state.description,
                onValueChange = { onEvent(AddCustomArticleEvent.DescriptionChange(it)) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Description") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    onEvent(AddCustomArticleEvent.AddCustomArticle)
                    navigateToPopUp(Route.HomeScreen.route, Route.AddCustomArticleScreen.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Custom Article")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddCustomArticleScreenPreview() {
    NewsAppComposeTheme {
        AddCustomArticleScreen(
            onEvent = {},
            state = AddCustomArticleState(
                title = "Custom Title",
                imageUrl = "custom",
                description = "Lorem ipsum",
                author = "Praveen"
            ),
            navigateToPopUp = { _, _ -> }
        )
    }
}