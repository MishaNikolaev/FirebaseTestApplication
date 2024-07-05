![Firebase](https://img.shields.io/badge/firebase-a08021?style=for-the-badge&logo=firebase&logoColor=ffcd34)

**Это пробный пример использования Firebase для доступа к хранилищу**

После добавления библиотек прописываем в MainActivity:

```Kotlin
  val fs = Firebase.firestore
  fs.collection("books").document("bla-bla").set(mapOf("name" to "my favourite book"))
```

Получаем на выходе:

![logs](https://github.com/MishaNikolaev/FirebaseTestApplication/blob/master/firebase.jpg)
