package com.jennywu.fetchtakehome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jennywu.fetchtakehome.data.ListItem
import com.jennywu.fetchtakehome.ui.theme.FetchTakeHomeTheme
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // parse JSON file
        val assetManager = applicationContext.assets
        val inputStream = assetManager.open("hiring.json")
        val reader = InputStreamReader(inputStream)
        val jsonString = reader.readText()
        val parsedLists = parseJson(jsonString)
        val listsToDisplay = groupListsByIdAndSort(parsedLists)

        setContent {
            FetchTakeHomeTheme {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                    item {
                        for (list in listsToDisplay.values) {
                            ListSectionDivider(list.first().listId)
                            List(list)
                        }
                    }
                }
            }
        }
    }
}

fun parseJson(jsonString: String): List<ListItem> {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val listType = Types.newParameterizedType(List::class.java, ListItem::class.java)
    val jsonAdapter = moshi.adapter<List<ListItem>>(listType)

    return jsonAdapter.fromJson(jsonString) ?: emptyList()
}

fun groupListsByIdAndSort(list: List<ListItem>): Map<Int, ArrayList<ListItem>> {
    val groupedLists = HashMap<Int, ArrayList<ListItem>>()
    for (item in list) {
        if (item.name != null && item.name.isNotEmpty()) { // filter out items with null or empty names
            if (groupedLists[item.listId] == null) {
                groupedLists[item.listId] = arrayListOf<ListItem>()
            }
            groupedLists[item.listId]!!.add(item)
        }
    }
    for (key in groupedLists.keys) { // sort the lists by item name
        groupedLists[key]?.sortBy { listItem -> listItem.name }
    }
    return groupedLists
}

@Composable
fun ListItem(item: ListItem) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)) {
        Text(
            text = "- ${item.name} (List ID ${item.listId})"
        )
    }
}

@Composable
fun ListSectionDivider(listId: Int) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp)) {
        Text("List ID $listId:")
        Spacer(
            modifier = Modifier
                .background(Color.Gray)
                .height(2.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun List(listItems: ArrayList<ListItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        for (item in listItems) {
            ListItem(item)
        }
    }
}

/* Compose UI testing
@Preview
@Composable
fun ListSectionDividerPreview() {
    ListSectionDivider(listId = 0)
}
*/