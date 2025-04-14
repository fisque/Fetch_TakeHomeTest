package com.jennywu.fetchtakehome

import com.jennywu.fetchtakehome.data.ListItem
import org.junit.Test

import org.junit.Assert.*

class MainActivityTest {

    @Test
    fun assertJsonListItemIsParsedCorrectly() {
        // arrange
        val expected = arrayListOf(
            ListItem(id = 684, listId = 1, name = "Item 684"),
            ListItem(id = 276, listId = 1, name = "Item 276")
        )

        // act
        val result = parseJson(TEST_JSON_STRING)

        // assert
        assertEquals(expected, result)
    }

    @Test
    fun testGroupingAndSorting() {
        // arrange
        val sortedListOne = arrayListOf(
            ListItem(id = 276, listId = 1, name = "Item 276"),
            ListItem(id = 684, listId = 1, name = "Item 684")
        )
        val sortedListTwo = arrayListOf(
            ListItem(id = 129, listId = 2, name = "Item 129")
        )

        // act
        val result = groupListsByIdAndSort(testParsedList)

        // assert
        assertEquals(result[1], sortedListOne)
        assertEquals(result[2], sortedListTwo)
    }

    companion object {

        const val TEST_JSON_STRING = "[\n" +
                "  {\"id\": 684, \"listId\": 1, \"name\": \"Item 684\"},\n" +
                "  {\"id\": 276, \"listId\": 1, \"name\": \"Item 276\"}\n" +
                "]"

        val testParsedList = arrayListOf(
            ListItem(id = 684, listId = 1, name = "Item 684"),
            ListItem(id = 276, listId = 1, name = "Item 276"),
            ListItem(id = 129, listId = 2, name = "Item 129")
        )
    }
}