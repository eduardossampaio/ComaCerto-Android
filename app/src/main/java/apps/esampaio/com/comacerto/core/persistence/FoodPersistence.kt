package apps.esampaio.com.comacerto.core.persistence;

import apps.esampaio.com.comacerto.core.entity.Food

class FoodPersistence {
    
    fun getFoodsList() : List<Food> {
//        let realm = try! Realm()
//        let foodsEntityList =  realm.objects(FoodRealmEntity.self)
//        var foodsList = [Food]()
//        for entity in foodsEntityList{
//            foodsList.append(entity.toFood())
//        }
//        return foodsList
        return listOf()
    }
    
    fun saveFoodsList(foodList : List<Food>){
//        let realm = try! Realm()
//        for food in foodList{
//            let foodEntity = FoodRealmEntity()
//            foodEntity.fromFood(food: food)
//            try! realm.write {
//                realm.add(foodEntity)
//            }
//        }

    }
}
