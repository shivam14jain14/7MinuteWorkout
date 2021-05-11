package com.example.a7minuteworkout

class Constants {
    companion object
    {
        fun defaultExerciseList():ArrayList<ExerciseModel>
        {
            val exerciseList=ArrayList<ExerciseModel>()

            val jumpingJacks=ExerciseModel(1,"JUMPING JACKS",R.drawable.jj,false,false)
            exerciseList.add(jumpingJacks)
            val lunges=ExerciseModel(2,"LUNGES",R.drawable.l,false,false)
            exerciseList.add(lunges)
            val highKnees=ExerciseModel(3,"HIGH KNEES RUNNING IN PLACE",R.drawable.hn,false,false)
            exerciseList.add(highKnees)
            val squats=ExerciseModel(4,"SQUATS",R.drawable.s,false,false)
            exerciseList.add(squats)
            val stepOnChair=ExerciseModel(5,"STEP UP ONTO CHAIR",R.drawable.soc,false,false)
            exerciseList.add(stepOnChair)
            val wallSit=ExerciseModel(6,"WALL SIT HOLD",R.drawable.wallsit,false,false)
            exerciseList.add(wallSit)
            val pushups=ExerciseModel(7,"PUSHUPS",R.drawable.push,false,false)
            exerciseList.add(pushups)
            val tricepDipsOnChair=ExerciseModel(8,"TRICEP DIPS ON CHAIR",R.drawable.tdoc,false,false)
            exerciseList.add(tricepDipsOnChair)
            val pushupAndRRotation=ExerciseModel(9,"PUSHUP AND R ROTATION",R.drawable.pandr,false,false)
            exerciseList.add(pushupAndRRotation)
            val abdominalHalfCrunches=ExerciseModel(10,"HALF CRUNCHES",R.drawable.crunch,false,false)
            exerciseList.add(abdominalHalfCrunches)
            val sidePlank=ExerciseModel(11,"SIDE PLANK",R.drawable.sp,false,false)
            exerciseList.add(sidePlank)
            val normalPlank=ExerciseModel(12,"PLANK",R.drawable.plankk,false,false)
            exerciseList.add(normalPlank)

            return exerciseList
        }
    }
}