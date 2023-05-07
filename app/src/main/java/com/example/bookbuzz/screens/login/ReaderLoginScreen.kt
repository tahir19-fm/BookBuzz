package com.example.bookbuzz.screens.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bookbuzz.R
import com.example.bookbuzz.components.*
import com.example.bookbuzz.navigation.ReaderScreens
import com.example.bookbuzz.ui.theme.ExtraLightReaderColor
import com.example.bookbuzz.ui.theme.SecondaryReaderColor
import com.example.bookbuzz.utils.ReaderAppTextStyle

@Composable
fun ReaderLoginScreen(navController: NavHostController,viewModel: LoginScreenViewModel= hiltViewModel()) {
    val currScreen= navController.currentBackStackEntry?.destination?.route

    val showLoginForm= rememberSaveable {
        mutableStateOf(true)
    }
    val isLoading= remember {
        mutableStateOf(false)
    }
    val errorText= remember {
        mutableStateOf("")
    }
    val showSnackBar= remember {
        mutableStateOf(false)
    }
        //observe the loading state
        isLoading.value=viewModel.loading.observeAsState().value==true

        Surface(modifier = Modifier.fillMaxSize(), color = ExtraLightReaderColor) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
                val context= LocalContext.current
                ReaderAppLogo(modifier = Modifier.padding(top=30.dp))

                //if to show login screen else sign up
                if (showLoginForm.value) UserForm(loading = isLoading.value, isCreateAccount = false){email,pwd->
                    //TODO login
                    viewModel.signInWithEmailAndPassword(email=email, password = pwd, home = {
                        navController.navigate(ReaderScreens.ReaderHomeScreen.name){
                            if (currScreen != null) {
                                popUpTo(currScreen){
                                    inclusive=true
                                }
                            }
                        }
                    }){
                        errorText.value=it
                        showSnackBar.value=true
                    }
                }
                else {
                    UserForm(loading = isLoading.value,isCreateAccount = true){email,pwd->
                        //TODO create account
                        viewModel.createUserWithEmailAndPassword(email=email, password = pwd, home = {
                            navController.navigate(ReaderScreens.ReaderHomeScreen.name){
                                if (currScreen != null) {
                                    popUpTo(currScreen){
                                        inclusive=true
                                    }
                                }
                            }
                        }){
                            errorText.value=it
                            showSnackBar.value=true
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier.padding(15.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val text = if (showLoginForm.value) "Sign up" else "Login"
                    Text(text = "New User?", style = ReaderAppTextStyle.subText)
                    Text(text,
                        modifier = Modifier
                            .clickable {
                                showLoginForm.value = !showLoginForm.value

                            }
                            .padding(start = 5.dp),
                        fontWeight = FontWeight.Bold,
                        style = ReaderAppTextStyle.subText,
                        color = MaterialTheme.colors.secondaryVariant)

                }



            }

                CustomSnackBar(text = errorText.value,showSnackBar=showSnackBar)

        }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(loading:Boolean=false,
             isCreateAccount:Boolean=false,
             onDone:(String,String)->Unit={email,pwd->}
){
    val email= rememberSaveable {
        mutableStateOf("")
    }
    val password= rememberSaveable {
        mutableStateOf("")
    }
    val passwordVisiblity= rememberSaveable {
        mutableStateOf(false)
    }
    val passwordFocusRequest=FocusRequester.Default
    val keyboardController=LocalSoftwareKeyboardController.current
    val valid= remember(email.value,password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val modifier= Modifier
        .wrapContentSize()
        .padding(horizontal = 8.dp)
        .background(ExtraLightReaderColor)
        .verticalScroll(rememberScrollState())
    Column(modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        if (isCreateAccount) Text(text = stringResource(id = R.string.create_acct),
            modifier = Modifier.padding(4.dp), style = ReaderAppTextStyle.lightText) else Text("")
        EmailInput(emailState = email, enabled = !loading, onAction = KeyboardActions {
            passwordFocusRequest.requestFocus()
        })
        PasswordInput(
            modifier=Modifier.focusRequester(passwordFocusRequest),
            passwordState=password,
            labelId="Password",
            enabled=!loading,
            passwordVisibility=passwordVisiblity,
            onAction= KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(),password.value.trim())
                keyboardController?.hide()
            })
        SubmitButton(
            textId = if (isCreateAccount) "Create Account" else "Login",
            loading = loading,
            validInputs = valid
        ){
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}

@Composable
fun SubmitButton(textId: String,
                 loading: Boolean,
                 validInputs: Boolean,
                 onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = SecondaryReaderColor)
    ) {
        if (loading) DotsPulsing()
        else Text(text = textId, modifier = Modifier.padding(5.dp))

    }
}
