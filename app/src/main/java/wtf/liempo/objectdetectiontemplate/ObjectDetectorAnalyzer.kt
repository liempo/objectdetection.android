package wtf.liempo.objectdetectiontemplate

import android.content.Context
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import wtf.liempo.objectdetectiontemplate.utils.ImageConverter
import wtf.liempo.objectdetectiontemplate.utils.ObjectDetectorHelper
import kotlin.getValue
import kotlin.lazy


class ObjectDetectorAnalyzer(ctx: Context): ImageAnalysis.Analyzer {

    // Create a lazy instance of ObjectDetectorHelper
    private val helper by lazy {
        ObjectDetectorHelper(
            threshold = 0.5f,
            numThreads = 2,
            maxResults = 3,
            currentDelegate = 0,
            currentModel = "ts_quant_with_metadata.tflite",
            context = ctx,
            objectDetectorListener = null
        )
    }

    private val converter by lazy {
        ImageConverter(ctx)
    }

    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {

        image.image?.let { img ->
            val bitmap = converter.convert(img)
            helper.detect(bitmap, image.imageInfo.rotationDegrees)
        }

    }


}