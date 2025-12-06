package com.example.swiftdrive.components.cars

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.ElectricCar
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swiftdrive.data.models.Car
import com.example.swiftdrive.data.models.EngineType
import com.example.swiftdrive.data.models.Tier

@Composable
fun CarCard(
    car: Car,
    modifier: Modifier = Modifier,
    onEventClick: (Car) -> Unit
    ) {
    Card(
       modifier = modifier
           .fillMaxWidth()
           .height(300.dp)
           .padding(top = 20.dp)
           .clickable{onEventClick(car)}
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ){
                Image(
                    //This will be updated with the image
                    painter = painterResource(id = car.imageRes),
                    contentDescription = "${car.make} ${car.model}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                if(car.isAvailable){
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp)
                            .background(
                                color = Color(0xFF2ECC71),
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 12.dp,vertical = 6.dp)
                    ){
                        Text(
                            text = "Availible",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)){
                Text(
                    text = "${car.year} ${car.make} ${car.model}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${car.category.toString()} • ${car.condition.toString()}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = when(car.engineType) {
                            EngineType.ELECTRIC -> Icons.Filled.FlashOn
                            EngineType.PETROL -> Icons.Filled.LocalGasStation
                            EngineType.DIESEL -> Icons.Filled.LocalGasStation
                            EngineType.HYBRID -> Icons.Filled.ElectricCar
                        },
                        contentDescription = car.engineType.toString(),
                        tint = when(car.engineType) {
                            EngineType.ELECTRIC -> Color(0xFF3498DB)
                            EngineType.PETROL -> Color(0xFFE74C3C)
                            EngineType.DIESEL -> Color(0xFF8B4513)
                            EngineType.HYBRID -> Color(0xFF2ECC71)
                        },
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        imageVector = when(car.tier) {
                            Tier.Economy -> Icons.Filled.AttachMoney
                            Tier.PREMIUM -> Icons.Filled.Star
                            Tier.LUXURY -> Icons.Filled.WorkspacePremium
                        },
                        contentDescription = car.tier.toString(),
                        tint = when(car.tier) {
                            Tier.Economy -> Color.Gray
                            Tier.PREMIUM -> Color(0xFFFFD700)
                            Tier.LUXURY -> Color(0xFFB9F2FF)
                        },
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Row(verticalAlignment = Alignment.Bottom){
                        Text(
                            text = "$ ${car.pricePerDay}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "/day",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }

                    Text(
                        text = "View Details",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }



}