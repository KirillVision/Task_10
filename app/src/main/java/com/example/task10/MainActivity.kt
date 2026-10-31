package com.example.task10

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlin.random.Random

data class Product(
    val id: Int,
    val name: String,
    val price: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var products by remember {
                mutableStateOf(
                    listOf(
                        Product(0, "Товар #1", 100),
                        Product(1, "Товар #2", 150),
                        Product(2, "Товар #3", 56)
                    )
                )
            }
            val context = LocalContext.current

            ShoppingCartScreen(
                products = products,
                onAddProduct = {
                    val sumBefore = products.sumOf { it.price } // Считаем сумму до добавления
                    products = products + Product(
                        id = products.size,
                        name = "Товар #${products.size + 1}",
                        price = Random.nextInt(1, 100)
                    )
                    val sumAfter = products.sumOf { it.price } // Считаем сумму после добавления

                    if (sumBefore <= 500 && sumAfter > 500) {
                        Toast.makeText(
                            context,
                            "Доставка бесплатная!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                onRemove = {
                    products = products.dropLast(1)
                }
            )
        }
    }
}

@Composable
fun ShoppingCartScreen(
    products: List<Product>,
    onAddProduct: () -> Unit,
    onRemove: () -> Unit
) {
    val totalSum = products.sumOf { product -> product.price }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (product in products) {
            Text(text = "${product.name} - ${product.price} рублей")
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Товаров на сумму: $totalSum рублей")

        Button(onClick = onAddProduct) {
            Text(text = "Добавить товар")
        }

        if (products.isNotEmpty()) {
            Button(onClick = onRemove) {
                Text(text = "Удалить товар")
            }
        }
    }
}