data class TrainingExercise(
    val name: String,
    var sets: Int,
    var repetitions: Int,
    var weight: Int, // Nouveau champ pour le poids
    val planDuration: Int,
    val history: MutableList<Triple<Int, Int, Int>> = mutableListOf() // Historique avec sets, reps et poids
) {
    fun getProgression(): Map<Int, Triple<Int, Int, Int>> {
        return history.mapIndexed { index, triple ->
            index + 1 to triple // Semaine 1, 2, etc., avec sets, reps, poids
        }.toMap()
    }


    fun addProgression(newSets: Int, newReps: Int, newWeight: Int): Boolean {
        val lastProgression = history.lastOrNull()
        history.add(Triple(newSets, newReps, newWeight)) // Ajout au nouvel historique
        return if (lastProgression != null) {
            // Vérifie uniquement la progression si le poids est non nul
            newSets > lastProgression.first || newReps > lastProgression.second ||
                    (lastProgression.third != 0 && newWeight > lastProgression.third)
        } else {
            true // Première entrée considérée comme une progression
        }
    }

}
