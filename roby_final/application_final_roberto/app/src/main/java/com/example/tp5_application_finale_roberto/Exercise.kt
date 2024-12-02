data class TrainingExercise(
    val name: String,
    var sets: Int,
    var repetitions: Int,
    var weight: Int,
    val planDuration: Int,
    val history: MutableList<Triple<Int, Int, Int>> = mutableListOf()
) {
    fun getProgression(): Map<Int, Triple<Int, Int, Int>> {
        return history.mapIndexed { index, triple ->
            index + 1 to triple
        }.toMap()
    }


    fun addProgression(newSets: Int, newReps: Int, newWeight: Int): Boolean {
        val lastProgression = history.lastOrNull()
        history.add(Triple(newSets, newReps, newWeight))
        return if (lastProgression != null) {

            newSets > lastProgression.first || newReps > lastProgression.second ||
                    (lastProgression.third != 0 && newWeight > lastProgression.third)
        } else {
            true
        }
    }

}
