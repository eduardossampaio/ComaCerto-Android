package apps.esampaio.com.comacerto.core.persistence

import android.content.Context
import apps.esampaio.com.comacerto.core.entity.MealType
import apps.esampaio.com.comacerto.core.entity.MealTypeIcon
import apps.esampaio.com.comacerto.core.persistence.entities.MealTypeEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.uiThread

class MealTypePersistence (val context: Context) {
    private val mealTypeDao = AppDatabase.getInstance(context)?.mealTypeDAO()

    fun saveMealType(mealType: MealType, onFinish: (() -> Unit) ?) {
        doAsync {
            if (mealTypeDao != null) {
                val mealEntity = MealTypeEntity(mealType.mealId.toLong(), mealType.name, mealType.mealTypeIcon.iconName)
                mealTypeDao.save(mealEntity);
                if (onFinish != null) {
                    context.runOnUiThread {
                        onFinish()
                    }
                };
            }
        }
    }


    fun getAll(onFinish: ((mealTypes:List<MealType>) -> Unit) ?) {
        doAsync {
            if (mealTypeDao != null) {
                val allMealTypeEntities = mealTypeDao.getAll()

                val allMealType = allMealTypeEntities.map {
                    MealType(it.primaryKey!!.toInt(), it.name, MealTypeIcon.getMealIcon(it.iconName))
                }.toTypedArray()

                if (onFinish != null) {
                    context.runOnUiThread {
                        onFinish(allMealType.toList())
                    }
                }
            }
        }

    }
}