package task

/*
If the function double("a") returns "aa",
then the function moveAndMergeEqual transforms the input in the following way:
  a, a, b -> aa, b
  b, null, a, a -> b, aa
  a, a, null, a -> aa, a
  a, null, a, a -> aa, a
Examples and tests in TestMoveAndMergeValues.kt
*/

fun <T : Any> List<T?>.moveAndMergeEqual(double: (T) -> T): List<T> =
        TODO()