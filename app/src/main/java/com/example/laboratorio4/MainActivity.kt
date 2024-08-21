package com.example.laboratorio4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.laboratorio4.ui.theme.Laboratorio4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio4Theme {
                RecipesApp()
            }
        }
    }
}

@Composable
fun RecipesApp(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleApp("AppGallery")
        RecipesContent()
    }

}

@Composable
fun TitleApp(name: String){
    Text(text = name,
        fontSize = 20.sp,
        color = Color.Red
    )
}

@Composable
fun RecipesContent(){
    val recipes = remember { mutableStateListOf<Recipe>() }
    Column {
        AddRecipes(recipes)
        ListRecipes(recipes)
    }
}

@Composable
fun AddRecipes(recipes: MutableList<Recipe>){
    var nameRecipe by remember { mutableStateOf("")}
    var urlImgRecipe by remember { mutableStateOf("")}
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DataRecipes(
            nameRecipe,
            urlImgRecipe,
            onNameChange = { newText -> nameRecipe = newText },
            onUrlChange = { newText -> urlImgRecipe = newText }
        )
        Spacer(modifier = Modifier.width(8.dp))
        ButtonAdd(nameRecipe, urlImgRecipe, recipes)
    }
}

@Composable
fun ListRecipes(recipes: List<Recipe>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(recipes.size) { index ->
            val recipe = recipes[index]
            RecipeItem(recipe)
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe){
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = recipe.name,
                fontSize = 18.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model    = ImageRequest.Builder(LocalContext.current)
                .data(recipe.imageUrl)
                .build(),
                contentDescription = recipe.name,
                modifier = Modifier.height(100.dp),
            )
        }
    }
}

@Composable
fun ButtonAdd(
    nameRecipe: String,
    urlImgRecipe: String,
    recipes: MutableList<Recipe>
){
    Button(
        onClick = {
            if (nameRecipe != "" && urlImgRecipe != ""){
                recipes.add(Recipe(nameRecipe, urlImgRecipe))
            }
        }
    ) {
        Text("Add recipe")
    }
}

@Composable
fun DataRecipes(
    nameRecipe: String,
    urlImgRecipe: String,
    onNameChange: (String) -> Unit,
    onUrlChange: (String) -> Unit
    ){
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(value = nameRecipe,
            onValueChange = onNameChange,
            label = {Text("Name of recipe")},
            modifier = Modifier
                .width(400.dp)
                .height(80.dp)
        )
        TextField(value = urlImgRecipe,
            onValueChange = onUrlChange,
            label = {Text("URL of image to the recipe")},
            modifier = Modifier
                .width(400.dp)
                .height(80.dp)
        )
    }
}

data class Recipe(val name: String, val imageUrl: String?)