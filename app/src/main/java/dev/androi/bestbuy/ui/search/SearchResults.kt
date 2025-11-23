package dev.androi.bestbuy.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.androi.bestbuy.data.search.ProductItem
import dev.androi.bestbuy.utils.formatCurrency

@Composable
fun ThumbnailBox(thumbnailImage: String?, itemName: String?) {
    if (!thumbnailImage.isNullOrBlank()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(thumbnailImage)
                .crossfade(true)
                .build(),
            contentDescription = itemName,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxHeight()
                .size(120.dp)
                .padding(all = 8.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Composable
fun DescriptionBox(item: ProductItem) {
    Column(modifier = Modifier.padding(start = 8.dp)) {
        ProductTitle(item.name)
        ProductPrices(
            regularPrice = item.regularPrice,
            salePrice = item.salePrice
        )
    }
}

@Composable
fun ProductTitle(itemName: String?) {
    if (itemName != null) {
        Text(
            itemName,
            modifier = Modifier.padding(vertical = 8.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ProductPrices(regularPrice: Double?, salePrice: Double?) {
    if (regularPrice != null) {
        if (salePrice != null && salePrice != regularPrice) {
            Row {
                Text(text = buildAnnotatedString {
                    pushStyle(SpanStyle(textDecoration = TextDecoration.LineThrough))
                    append(regularPrice.formatCurrency())
                    pop()
                }, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(salePrice.formatCurrency(), fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        } else {
            Text(regularPrice.formatCurrency(), fontSize = 16.sp)
        }
    }
}