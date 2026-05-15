package com.example.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Hasta-Kala Shop",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        DashboardCard("Total Sales", "₹2500")
        DashboardCard("Best Seller", "Red Banana Bags")
        DashboardCard("Stock Alert", "Only 2 Blue Bags Left")
        DashboardCard("Monthly Income", "₹12,500")

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Sales Analytics",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(text = "Red Bags - 60%")
                Spacer(modifier = Modifier.height(6.dp))

                Text(text = "Blue Bags - 25%")
                Spacer(modifier = Modifier.height(6.dp))

                Text(text = "Keychains - 15%")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "AI Recommendation",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Increase production of Red Banana Bags. Sales increased by 60% this week."
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Add Sale")
        }
    }
}

@Composable
fun DashboardCard(title: String, value: String) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),

        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                fontSize = 20.sp,
                color = Color(0xFF6A1B9A)
            )
        }
    }
}