**Загрузка фото в Firebase**

```Kotlin
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
```
И в кнопке теперь

```Kotlin
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
```
Результат

![logs](https://github.com/MishaNikolaev/FirebaseTestApplication/blob/upload-photo/firebasePhoto1.jpg)

![logs](https://github.com/MishaNikolaev/FirebaseTestApplication/blob/upload-photo/firebasePhoto2.jpg)
