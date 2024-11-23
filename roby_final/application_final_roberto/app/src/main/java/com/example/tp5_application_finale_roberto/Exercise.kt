package com.example.tp5_application_finale_roberto
import kotlin.collections.MutableList
import kotlin.Pair

data class TrainingExercise(
    val name: String,
    var sets: Int,
    var repetitions: Int,
    val planDuration: Int,
    val history: MutableList<Pair<Int, Int>> = mutableListOf()
)
{
    fun getProgression(): Map<Int, Pair<Int, Int>> {
        return history.mapIndexed { index, pair -> index + 1 to pair }.toMap()
    }

    fun addProgression(newSets: Int, newReps: Int): Boolean {
        val lastProgression = history.lastOrNull()
        history.add(newSets to newReps)
        return if (lastProgression != null) {
            newSets > lastProgression.first || newReps > lastProgression.second
        } else {
            false // Pas de progression si c'est la première entrée
        }
    }
}
