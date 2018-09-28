package apps.esampaio.com.comacerto.core.entity

import java.io.Serializable

class Level : Serializable{

    var level: Int = 1
    var levelNames : Array<String> = emptyArray()

    companion object {
        val hungerStatusNames = arrayOf("Nada de fome", "Pouca fome", "Com Fome", "Muita Fome", "Morrendo de fome")
        val satietyStatusNames = arrayOf("Continuo com fome", "Poderia comer mais", "Estou satisfeito", "Comi demais", "Vou explodir")


        fun hunger(level: Int = 1) : Level
        {
            return Level(level,  hungerStatusNames)
        }

        fun satiety(level: Int = 1): Level
        {
            return Level(level,satietyStatusNames)
        }
    }


    constructor(levelNames: Array<String>) {
        this.levelNames = levelNames
    }
    constructor(level:Int,levelNames: Array<String>) {
        this.levelNames = levelNames
        this.level = level
    }

    fun selectedLevelName(): String{
        return levelNames[level - 1]
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Level

        if (level != other.level) return false

        return true
    }

    override fun hashCode(): Int {
        return level
    }


}