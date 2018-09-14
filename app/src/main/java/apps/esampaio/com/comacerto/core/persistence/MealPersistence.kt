package apps.esampaio.com.comacerto.core.persistence;

import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.extensions.sameDay
import java.util.*

class MealPersistence {
    companion object {
        var mealsList = mutableListOf<Meal>()
    }
//    private fun getRealmObject(_ meal:Meal) : MealRealmEntity?{
//        val realm = try! Realm()
//        return realm.object(ofType: MealRealmEntity.self, forPrimaryKey: meal.primaryKey)
//    }
    fun saveOrUpdateMeal(meal: Meal){
        if(meal.primaryKey == null || (meal.primaryKey?.isEmpty())){
            saveMeal(meal)
        }else{
            updateMeal(meal)
        }
    }
    fun saveMeal( meal: Meal){
//        val realm = try! Realm()
//        val mealRealmEntity = MealRealmEntity()
//        mealRealmEntity.fromMeal(meal: meal)
//        
//        try! realm.write {
//            realm.add(mealRealmEntity)
//        }
        mealsList.add(meal);
    }
    fun updateMeal(newMeal : Meal){
        for(meal in mealsList){
            if (meal.primaryKey == newMeal.primaryKey){
                val index = mealsList.indexOf(meal);
                mealsList.add(index,newMeal);
            }
        }
//        if val mealRealmEntity = getRealmObject(meal){
//        val realm = try! Realm()
//            try! realm.write {
//                mealRealmEntity.fromMeal(meal: meal)
//                realm.add(mealRealmEntity, update: true)
//            }
//        }
    }
    
    fun getMeals(date: Date) : List<Meal> {
        val selectedMeals = mutableListOf<Meal>()
        for (meal in mealsList){
            if (meal.date.sameDay(date)){
                selectedMeals.add(meal)
            }
        }
        return selectedMeals
    }
    
    
    fun getMeals(initialDate: Date,finalDate:Date) : List<Meal> {
        val selectedMeals = mutableListOf<Meal>()
        for (meal in mealsList){
            if (meal.date.after(initialDate) && meal.date.before(finalDate)){
                selectedMeals.add(meal)
            }
        }
//        val realm = try! Realm()
//        val mealEntities = realm.objects(MealRealmEntity.self).filter("dateAndTime BETWEEN %@", [initialDate.beginOfDay(), finalDate.endOfDay()]).sorted(byKeyPath: "dateAndTime", ascending: true)
//        var allMeals = [Meal]()
//        for mealEntity in mealEntities{
//            val meal = mealEntity.toMeal()
//            allMeals.append(meal)
//        }
//        return allMeals
        return selectedMeals
    }
    
    
    fun deleteMeal(meal: Meal){
//        val realm = try! Realm()
//        if val mealRealmEntity = getRealmObject(meal){
//            try! realm.write {
//                realm.devale(mealRealmEntity)
//            }
//        }
    }
}
