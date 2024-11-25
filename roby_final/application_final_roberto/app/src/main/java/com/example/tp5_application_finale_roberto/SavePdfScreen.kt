package com.example.tp5_application_finale_roberto

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun SavePdfScreen(
    context: Context,
    exercise: TrainingExercise
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Sauvegarder le plan d'entraînement en PDF")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                savePdf(context, exercise)
            }) {
                Text("Sauvegarder PDF")
            }
        }
    }
}

fun savePdf(context: Context, exercise: TrainingExercise) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
    val page = pdfDocument.startPage(pageInfo)

    val canvas = page.canvas
    val paint = android.graphics.Paint()
    paint.textSize = 12f

    canvas.drawText("Plan d'entraînement : ${exercise.name}", 10f, 20f, paint)
    canvas.drawText("Nombre de sets : ${exercise.sets}", 10f, 40f, paint)
    canvas.drawText("Nombre de répétitions : ${exercise.repetitions}", 10f, 60f, paint)
    canvas.drawText("Poids : ${exercise.weight} kg", 10f, 80f, paint)
    canvas.drawText("Durée du plan : ${exercise.planDuration} semaines", 10f, 100f, paint)

    canvas.drawText("Historique des Progressions :", 10f, 120f, paint)
    var yOffset = 140f
    exercise.history.forEachIndexed { index, progression ->
        canvas.drawText(
            "Semaine ${index + 1} : ${progression.first} sets, " +
                    "${progression.second} répétitions, ${progression.third} kg",
            10f,
            yOffset,
            paint
        )
        yOffset += 20f
    }

    pdfDocument.finishPage(page)

    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadsDir, "${exercise.name}_PlanEntrainement.pdf")

    try {
        pdfDocument.writeTo(FileOutputStream(file))
        Toast.makeText(context, "PDF sauvegardé dans Downloads", Toast.LENGTH_LONG).show()
    } catch (e: IOException) {
        e.printStackTrace()
        Toast.makeText(context, "Erreur lors de la sauvegarde du PDF", Toast.LENGTH_LONG).show()
    }

    pdfDocument.close()
}
