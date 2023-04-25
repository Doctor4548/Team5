package edu.qc.seclass.floorstore


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(mainViewModel: MainViewModel) {

    val searchWidgetState by mainViewModel.searchWidgetState
    val searchTextState by mainViewModel.searchTextState

    Scaffold(
        topBar = {
            MainAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    mainViewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {
                    Log.d("Searched Text", it)
                },
                onSearchTriggered = {
                    mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                }
            )
        }
    ) {}
}

@Composable
fun MainAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}
// filter by categories function
private fun Context.doFilterAll() {

    Toast.makeText(
        this,
        "Show all floors!",
        Toast.LENGTH_SHORT
    ).show()
}

private fun Context.doFilterTile() {

    Toast.makeText(
        this,
        "Show tile floors only!",
        Toast.LENGTH_SHORT
    ).show()
}
private fun Context.doFilterStone() {

    Toast.makeText(
        this,
        "Show stone floors only!",
        Toast.LENGTH_SHORT
    ).show()
}
private fun Context.doFilterWood() {

    Toast.makeText(
        this,
        "Show wood floors only!",
        Toast.LENGTH_SHORT
    ).show()
}
private fun Context.doFilterLaminate() {

    Toast.makeText(
        this,
        "Show laminate floors only!",
        Toast.LENGTH_SHORT
    ).show()
}
private fun Context.doFilterVinyl() {

    Toast.makeText(
        this,
        "Show vinyl floors only!",
        Toast.LENGTH_SHORT
    ).show()
}

@Composable
fun DefaultAppBar(onSearchClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Store"
            )
        },
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            ))
    }

    //filter by categories buttons
    val context = LocalContext.current
    ProvideWindowInsets {
        Column(
            Modifier
                .navigationBarsWithImePadding()
                .padding(55.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(1.dp, alignment = Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .navigationBarsWithImePadding()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    context.doFilterAll()
                }, modifier = Modifier.padding(horizontal = 10.dp)) {
                    Text("All", Modifier.padding(vertical = 7.dp))
                }

                Button(onClick = {
                    context.doFilterTile()
                }, modifier = Modifier.padding(horizontal = 10.dp)) {
                    Text("Tile", Modifier.padding(vertical = 7.dp))
                }
                Button(onClick = {
                    context.doFilterStone()
                }, modifier = Modifier.padding(horizontal = 10.dp)) {
                    Text("Stone", Modifier.padding(vertical = 7.dp))
                }
            }
            Row(
                Modifier
                    .navigationBarsWithImePadding()
                    .padding(2.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(onClick = {
                    context.doFilterWood()
                }, modifier = Modifier.padding(horizontal = 10.dp)) {
                    Text("Wood", Modifier.padding(vertical = 7.dp))
                }
                Button(onClick = {
                    context.doFilterLaminate()
                }, modifier = Modifier.padding(horizontal = 10.dp)) {
                    Text("Laminate", Modifier.padding(vertical = 7.dp))
                }
                Button(onClick = {
                    context.doFilterVinyl()
                }, modifier = Modifier.padding(horizontal = 7.dp)) {
                    Text("Vinyl", Modifier.padding(vertical = 7.dp))
                }
            }
            Divider(
                color = Color.White.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}


@Composable
@Preview
fun DefaultAppBarPreview() {
    DefaultAppBar(onSearchClicked = {})
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Some random text",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}