package CryptoDetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.android.tools.screenshot.PreviewTest
import com.app.presentation.ui.cryptodetail.CryptoDetailSuccessContentPreview
import com.app.presentation.ui.cryptodetail.ErrorContentPreview
import com.app.presentation.ui.cryptodetail.LoadingContentPreview

@Preview
@PreviewTest
@Composable
@PreviewScreenSizes
fun CryptoDetailError() {
    ErrorContentPreview()
}

@Preview
@PreviewTest
@Composable
@PreviewScreenSizes
fun LoadingContent() {
    LoadingContentPreview()
}

@Preview
@PreviewTest
@Composable
@PreviewScreenSizes
fun CryptoDetailContent() {
    CryptoDetailSuccessContentPreview()
}