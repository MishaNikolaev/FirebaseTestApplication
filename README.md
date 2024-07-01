**Отправка и получение данных из Firebase**
Создаём data class Book с полями типа String

```Kotlin
data class Book(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val category: String = "",
    val imageUrl: String = ""
)
```
Весь остальной код проекта в MainActivity. 

``` Kotlin
val fs = Firebase.firestore
    val list = remember{
        mutableStateOf(emptyList<Book>())
    }

    val listener = fs.collection("books").addSnapshotListener{ snapshot, exeption ->
        list.value = snapshot?.toObjects(Book::class.java)!!
```

Функционал кнопки:
``` Kotlin
Button(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            onClick = {
            fs.collection("books").document().set(Book(
                "Cloud of war",
                "The book by Alexander Pokryshkin presents his biography during the war years.",
                "1000",
                "Memoirs",
                "url"
            ))
        })
        {
            Text(text = "Add book")
        }
```
![logs](https://github.com/MishaNikolaev/FirebaseTestApplication/blob/master/firebase2.jpg)
![logs](https://github.com/MishaNikolaev/FirebaseTestApplication/blob/master/firebase1.jpg)
