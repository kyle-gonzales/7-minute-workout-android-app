package com.example.a7minuteworkoutapp

object Constants {

    fun getDefaultWorkout(): ArrayList<Exercise> {
        val workout = ArrayList<Exercise>()

        val jumpingJacks = Exercise(1, "Jumping Jacks", R.drawable.ic_jumping_jacks)
        workout.add(jumpingJacks)
        val highKnees = Exercise(2, "High Knees", R.drawable.ic_high_knees_running_in_place)
        workout.add(highKnees)
        val pushUps = Exercise(3, "Push ups", R.drawable.ic_push_up)
        workout.add(pushUps)
        val plank = Exercise(4, "Plank", R.drawable.ic_plank)
        workout.add(plank)
        val pushUpAndRotate = Exercise(5, "Push up and rotate", R.drawable.ic_push_up_and_rotation)
        workout.add(pushUpAndRotate)
        val sidePlank = Exercise(6, "Side Plank", R.drawable.ic_side_plank)
        workout.add(sidePlank)
        val squats = Exercise(7, "Squats", R.drawable.ic_squat)
        workout.add(squats)
        val lunges = Exercise(8, "Forward Lunges", R.drawable.ic_lunge)
        workout.add(lunges)
        val stepUps = Exercise(9, "Box Step Ups", R.drawable.ic_step_up_onto_chair)
        workout.add(stepUps)
        val wallSit = Exercise(10, "Wall Sit", R.drawable.ic_wall_sit)
        workout.add(wallSit)
        val tricepsDips = Exercise(11, "Triceps Dips", R.drawable.ic_triceps_dip_on_chair)
        workout.add(tricepsDips)
        val abCrunches = Exercise(12, "Ab Crunches", R.drawable.ic_abdominal_crunch)
        workout.add(abCrunches)

        return workout
    }
}