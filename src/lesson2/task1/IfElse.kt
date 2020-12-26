@file:Suppress("UNUSED_PARAMETER")

package lesson2.task1

import lesson1.task1.discriminant
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

// Урок 2: ветвления (здесь), логический тип (см. 2.2).
// Максимальное количество баллов = 6
// Рекомендуемое количество баллов = 5
// Вместе с предыдущими уроками = 9/12

/**
 * Пример
 *
 * Найти число корней квадратного уравнения ax^2 + bx + c = 0
 */
fun quadraticRootNumber(a: Double, b: Double, c: Double): Int {
    val discriminant = discriminant(a, b, c)
    return when {
        discriminant > 0.0 -> 2
        discriminant == 0.0 -> 1
        else -> 0
    }
}

/**
 * Пример
 *
 * Получить строковую нотацию для оценки по пятибалльной системе
 */
fun gradeNotation(grade: Int): String = when (grade) {
    5 -> "отлично"
    4 -> "хорошо"
    3 -> "удовлетворительно"
    2 -> "неудовлетворительно"
    else -> "несуществующая оценка $grade"
}

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    val y3 = max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -sqrt(y3)           // 7
}

fun main() {
    println("ageDescription: ${ageDescription(13)}")
}

/**
 * Простая (2 балла)
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String {
    val strYears: String
    val lastDigit = age % 10
    val last2Digit = age % 100
    strYears = when {
        last2Digit in 11..14 -> "лет"
        lastDigit == 1 -> "год"
        lastDigit in 2..4 -> "года"
        else -> "лет"
    }
    return "$age $strYears"
}

/**
 * Простая (2 балла)
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(
    t1: Double, v1: Double,
    t2: Double, v2: Double,
    t3: Double, v3: Double
): Double {
    val l1 = t1 * v1
    val l2 = t2 * v2
    val l3 = t3 * v3
    val l = l1 + l2 + l3
    return when (val lHalf = l / 2) {
        in 0.0..l1 -> lHalf / v1
        in l1..(l1 + l2) -> t1 + (lHalf - l1) / v2
        else -> t1 + t2 + (lHalf - l1 - l2) / v3
    }
}

/**
 * Простая (2 балла)
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(
    kingX: Int, kingY: Int,
    rookX1: Int, rookY1: Int,
    rookX2: Int, rookY2: Int
): Int = when {
    ((rookX1 == kingX) or (rookY1 == kingY)) and ((rookX2 == kingX) or (rookY2 == kingY)) -> 3
    (rookX1 == kingX) or (rookY1 == kingY) -> 1
    (rookX2 == kingX) or (rookY2 == kingY) -> 2
    else -> 0
}


/**
 * Простая (2 балла)
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(
    kingX: Int, kingY: Int,
    rookX: Int, rookY: Int,
    bishopX: Int, bishopY: Int
): Int = when {
    ((rookX == kingX) or (rookY == kingY)) and (abs(bishopX - kingX) == abs(bishopY - kingY)) -> 3
    (rookX == kingX) or (rookY == kingY) -> 1
    abs(bishopX - kingX) == abs(bishopY - kingY) -> 2
    else -> 0
}

/**
 * Простая (2 балла)
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun triangleKind(a: Double, b: Double, c: Double): Int {
    val tExists: Boolean = (a < b + c) and (b < a + c) and (c < a + b)
    if (!tExists) return -1
    val alpha: Double = b.pow(2) + c.pow(2) - a.pow(2)
    val betta: Double = a.pow(2) + c.pow(2) - b.pow(2)
    val gamma: Double = a.pow(2) + b.pow(2) - c.pow(2)
    return when {
        (alpha == 0.0) or (betta == 0.0) or (gamma == 0.0) -> 1
        (alpha < 0.0) or (betta < 0.0) or (gamma < 0.0) -> 2
        else -> 0
    }
}


/**
 * Средняя (3 балла)
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int {
    val segmentStart = min(min(a, d), min(b, c))
    val segmentEnd = max(max(a, d), max(b, c))
    val lAD = abs(segmentEnd - segmentStart)
    val lAB = abs(b - a)
    val lCD = abs(d - c)
    if (lAB + lCD < lAD) return -1
    return when {
        (a > c) and (b < d) -> lAB
        (a < c) and (b > d) -> lCD
        (a > c) and (b > d) -> d - a
        else -> b - c
    }
}
