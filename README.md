![logs](https://github.com/MishaNikolaev/FirebaseTestApplication/blob/authentication/auth1%20(1).jpg)

```Kotlin
val auth = Firebase.auth

    val emailState = remember{
        mutableStateOf("")
    }

    val passwordState = remember{
        mutableStateOf("")
    }
```



![logs](https://github.com/MishaNikolaev/FirebaseTestApplication/blob/authentication/auth1%20(2).jpg)

```Kotlin
TextField(value = emailState.value, onValueChange = {
            emailState.value = it
        })

        Spacer(modifier = Modifier.height(10.dp))

        TextField(value = passwordState.value, onValueChange = {
            passwordState.value = it
        })

        Button(onClick = {
            signUp(auth, emailState.value, passwordState.value)
        }){
            Text(text = "Sign Up")
        }
```
```Kotlin
private fun signUp(auth : FirebaseAuth, email: String, password: String){
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener{
            if (it.isSuccessful){
                Log.d("MyLog", "Sign Up successful")
            }
            else{
                Log.d("MyLog", "Sign Up exception")
            }
        }
}
```
![logs](https://github.com/MishaNikolaev/FirebaseTestApplication/blob/authentication/auth1%20(3).jpg)
