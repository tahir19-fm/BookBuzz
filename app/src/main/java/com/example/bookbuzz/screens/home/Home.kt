package com.example.bookbuzz.screens.home

import android.annotation.SuppressLint
import android.util.Log
import android.window.SplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.bookbuzz.R
import com.example.bookbuzz.components.FABContent
import com.example.bookbuzz.components.ListCard
import com.example.bookbuzz.components.ReaderAppBar
import com.example.bookbuzz.components.TitleSection
import com.example.bookbuzz.model.MBook
import com.example.bookbuzz.navigation.ReaderScreens
import com.example.bookbuzz.ui.theme.ExtraLightReaderColor
import com.example.bookbuzz.ui.theme.PrimaryReaderColor
import com.example.bookbuzz.utils.ReaderAppTextStyle
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import java.text.DateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(navController: NavHostController,viewModel: HomeScreenViewModel= hiltViewModel()) {
    val currScreen= navController.currentBackStackEntry?.destination?.route
    Scaffold(topBar = {
        ReaderAppBar(title = stringResource(id = R.string.app_name), navController = navController )


    },
        floatingActionButton = {
            FABContent{
                navController.navigate(ReaderScreens.SearchScreen.name)
            }

        }) { padding->
        //content
        Surface(modifier = Modifier.fillMaxSize(), color = ExtraLightReaderColor) {
            //home content
           HomeContent(navController, viewModel)

        }

    }

}

@Composable
fun HomeContent(navController: NavController, viewModel: HomeScreenViewModel) {
    var listOfBooks = emptyList<MBook>()
    val currentUser = FirebaseAuth.getInstance().currentUser

//    if (!viewModel.data.value.data.isNullOrEmpty()) {
//        listOfBooks = viewModel.data.value.data!!.toList().filter { mBook ->
//            mBook.userId == currentUser?.uid.toString()
//        }
//
//        Log.d("Books", "HomeContent: ${listOfBooks.toString()}")
//    }

    val listOfBooksss = listOf(
          MBook(id = "dadfa", title = "Hello Again", authors = "All of us", notes = null,
              photoUrl = "http://books.google.com/books/content?id=72CHDQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
              , rating = 4.4, startedReading = Timestamp.now()
          ),
        MBook(id = "dadfa", title = "Hello Again", authors = "All of us", notes = null,
            photoUrl = "http://books.google.com/books/content?id=72CHDQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            , rating = 4.4
        ),MBook(id = "dadfa", title = "Hello Again", authors = "All of us", notes = null,
            photoUrl = "http://books.google.com/books/content?id=72CHDQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            , rating = 4.4
        ),MBook(id = "dadfa", title = "Hello Again", authors = "All of us", notes = null,
            photoUrl = "http://books.google.com/books/content?id=72CHDQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            , rating = 4.4
        ),MBook(id = "dadfa", title = "Hello Again", authors = "All of us", notes = null,
            photoUrl = "http://books.google.com/books/content?id=72CHDQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            , rating = 4.4
        ),MBook(id = "dadfa", title = "Hello Again", authors = "All of us", notes = null,
            photoUrl = "http://books.google.com/books/content?id=72CHDQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            , rating = 4.4
        ),
                            )
    //me @gmail.com
    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if (!email.isNullOrEmpty())
        FirebaseAuth.getInstance().currentUser?.email?.split("@")
            ?.get(0)else
        "N/A"
    Column(Modifier.padding(horizontal = 2.dp),
        verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier
            .align(alignment = Alignment.Start)
            .fillMaxWidth()
            .background(
                ExtraLightReaderColor
            ), horizontalArrangement = Arrangement.SpaceBetween) {
            TitleSection(label = "Your reading \n" + "activity right now...", modifier = Modifier.background(
                ExtraLightReaderColor), color = ExtraLightReaderColor)
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ReaderScreens.ReaderStatsScreen.name)
                        }
                        .size(45.dp),
                    tint = MaterialTheme.colors.secondaryVariant)
                Text(text = currentUserName!!,
                    modifier = Modifier.padding(2.dp),
                    style = ReaderAppTextStyle.lightText,
                    color = PrimaryReaderColor,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip)
            }


        }

        ReadingRightNowArea(listOfBooks = listOfBooksss,
            navController =navController )
        TitleSection(label = "Reading List")
        BookListArea(listOfBooks = listOfBooksss,
            navController = navController)



    }

}

@Composable
fun BookListArea(listOfBooks: List<MBook>,
                 navController: NavController
) {
    val addedBooks = listOfBooks.filter { mBook ->
        mBook.startedReading == null && mBook.finishedReading == null
    }



    HorizontalScrollableComponent(addedBooks){
        navController.navigate(ReaderScreens.UpdateScreen.name +"/$it")

    }



}

@Composable
fun HorizontalScrollableComponent(listOfBooks: List<MBook>,
                                  viewModel: HomeScreenViewModel = hiltViewModel(),
                                  onCardPressed: (String) -> Unit) {

    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .heightIn(280.dp)) {
//        if (viewModel.data.value.loading == true) {
//            LinearProgressIndicator()
//
//        }else {
//            if (listOfBooks.isNullOrEmpty()){
//                Surface(modifier = Modifier.padding(23.dp)) {
//                    Text(text = "No books found. Add a Book",
//                        style = TextStyle(
//                            color = Color.Red.copy(alpha = 0.4f),
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 14.sp
//                        )
//                    )
//
//                }
//            }else {
//                for (book in listOfBooks) {
//                    ListCard(book) {
//                        onCardPressed(book.googleBookId.toString())
//
//                    }
//                }
//            }
//
//        }
        
        items(listOfBooks){item ->
            ListCard(book = item){
                onCardPressed(it)
            }
            
        }



    }


}


@Composable
fun ReadingRightNowArea(listOfBooks: List<MBook>,
                        navController: NavController
) {
    //Filter books by reading now
    val readingNowList = listOfBooks.filter { mBook ->
        mBook.startedReading != null && mBook.finishedReading == null
    }

    HorizontalScrollableComponent(readingNowList){
        Log.d("TAG", "BoolListArea: $it")
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }



}
