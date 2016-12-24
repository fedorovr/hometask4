package task

/*
This function moves all the non-null elements to the beginning of the list (by removing nulls) and merges equal elements.
The parameter 'double' specifies the way how to merge equal elements:
it returns a new element that should be present in the result list instead of two merged elements.

If the function double("a") returns "aa",
then the function moveAndMergeEqual transforms the input in the following way:
  a, a, b -> aa, b
  b, null, a, a -> b, aa
  a, a, null, a -> aa, a
  a, null, a, a -> aa, a
Examples and tests in TestMoveAndMergeValues.kt
*/

fun <T : Any> List<T?>.moveAndMergeEqual(double: (T) -> T): List<T> {
    var pointer = 0
    val result = mutableListOf<T>()
    val withoutNulls = this.filterNotNull()
    while (pointer < withoutNulls.size - 1) {
        if (withoutNulls[pointer] == withoutNulls[pointer + 1]) {
            result.add(double(withoutNulls[pointer]))
            pointer++
        } else {
            result.add(withoutNulls[pointer])
        }
        pointer++
    }
    if (pointer == withoutNulls.size - 1) {
        result.add(withoutNulls[pointer])
    }
    return result
}
