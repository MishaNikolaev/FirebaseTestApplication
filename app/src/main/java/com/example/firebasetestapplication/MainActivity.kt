package com.example.firebasetestapplication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat.JPEG
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.firebasetestapplication.data.Book
import com.example.firebasetestapplication.ui.theme.FirebaseTestApplicationTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun MainScreen() {

    val fs = Firebase.firestore
    val list = remember{
        mutableStateOf(emptyList<Book>())
    }
    val context = LocalContext.current

    val storage = Firebase.storage.reference.child("images")

    val listener = fs.collection("books").addSnapshotListener{ snapshot, exeption ->
        list.value = snapshot?.toObjects(Book::class.java)!!
    }

    //listener.remove()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            items(list.value){ book ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 10.dp, end = 10.dp,
                        top = 40.dp
                    )){
                    Row(modifier = Modifier.fillMaxWidth()){
                        AsyncImage(model = book.imageUrl, contentDescription = "",
                     )
                    }
                    Text(text = book.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth()
                            .padding(15.dp))
                    Text(text = book.description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth()
                            .padding(15.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            onClick = {

                val task = storage.child("su34plane.jpg").putBytes(
                    bitmapTobiteArray(context)
                )
                task.addOnSuccessListener { uploadTask ->
                    uploadTask.metadata!!.reference!!.downloadUrl.addOnCompleteListener{ uriTask ->
                        saveBook(fs, uriTask.result.toString())
                    }
                }
        })
        {
            Text(text = "Add book")
        }
    }
}

private fun bitmapTobiteArray(context: Context) : ByteArray{
    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.su34)
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    return baos.toByteArray()
}

private fun saveBook(fs: FirebaseFirestore, url: String){
    fs.collection("books").document().set(Book(
        "Cloud of war",
        "The book by Alexander Pokryshkin presents his biography during the war years.",
        "1000",
        "Memoirs",
        url
    ))
}