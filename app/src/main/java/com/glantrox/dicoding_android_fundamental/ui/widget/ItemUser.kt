package com.glantrox.dicoding_android_fundamental.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.glantrox.dicoding_android_fundamental.core.models.Favourite
import com.glantrox.dicoding_android_fundamental.core.models.UsersResponseItem

@Composable
fun ItemUser(
    user: UsersResponseItem,
    onTap: (String) -> Unit?,
    isManage: Boolean = false,
    onTapDelete: (UsersResponseItem) -> Unit? = {}
) {
    return Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .clickable { onTap(user.login) }
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
       Row(
           verticalAlignment = Alignment.CenterVertically,
       ) {
           AsyncImage(
               model = user.avatarUrl,
               contentDescription =  "",
               modifier = Modifier
                   .clip(CircleShape)
                   .size(42.dp)
           )
           Spacer(modifier = Modifier.width(22.dp))
           Column {
               Text(text = user.login,
                   style = TextStyle(
                       color = Color.Gray,
                       fontWeight = FontWeight.Bold,
                       fontSize = 18.sp
                   )
               )
               Text(text = "ID-${user.id}",
                   style = TextStyle(
                       color = Color.Gray,
                       fontWeight = FontWeight.Light,
                       fontSize = 13.sp
                   )
               )
           }
       }
        if(isManage) {
            IconButton(onClick = {
                onTapDelete(user)
            }) {
                Icon(imageVector = Icons.Rounded.Delete, contentDescription = "")
            }
        }
    }
}

@Composable
fun ItemFavourite(
    user: Favourite,
    onTap: (String) -> Unit?,
    isManage: Boolean = false,
    onTapDelete: (Favourite) -> Unit? = {}
) {
    return Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .clickable { onTap(user.login) }
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = user.avatarUrl,
                contentDescription =  "",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(42.dp)
            )
            Spacer(modifier = Modifier.width(22.dp))
            Column {
                Text(text = user.login,
                    style = TextStyle(
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
                Text(text = "ID-${user.id}",
                    style = TextStyle(
                        color = Color.Gray,
                        fontWeight = FontWeight.Light,
                        fontSize = 13.sp
                    )
                )
            }
        }
        if(isManage) {
            IconButton(onClick = {
                onTapDelete(user)
            }) {
                Icon(imageVector = Icons.Rounded.Delete, contentDescription = "")
            }
        }
    }
}