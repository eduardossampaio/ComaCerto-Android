package apps.esampaio.com.comacerto.core.service.report

import android.content.Context
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.entity.Water
import apps.esampaio.com.comacerto.core.extensions.asString
import apps.esampaio.com.comacerto.core.extensions.sameDay
import java.util.*
import j2html.TagCreator.*
import j2html.tags.ContainerTag


class HtmlReportGenerator(val context: Context) {

    data  class ReportItem(val date: Date, var meals : MutableList<Meal> = mutableListOf(), var waters: MutableList<Water>  = mutableListOf()){

    }

    fun generateHtml(initialDate: Date, finalDate: Date, meals: List<Meal>, waters: List<Water>): String {

        val groupedMeals = groupByDate(meals, waters);
        val resultHtml = body(
                reportHeader(initialDate, finalDate),
                mealReportItem(groupedMeals)


        ).render();
        return resultHtml;


    }

    private fun getReportItemByDate(reportItems: List<ReportItem> ,date: Date) :ReportItem? {
        for (reportItem in reportItems){
            if(reportItem.date.sameDay(date)){
                return reportItem;
            }
        }
        return null;
    }

    private fun groupByDate(meals: List<Meal>, waters: List<Water>): MutableList<ReportItem> {

        val groupedList = mutableListOf<ReportItem>();

        for (meal in meals){
            var reportItem = getReportItemByDate(groupedList,meal.date)
            if(reportItem == null){
                reportItem = ReportItem(meal.date);
                groupedList.add(reportItem);
            }
            reportItem.meals.add(meal)
        }

        for (water in waters){
            var reportItem = getReportItemByDate(groupedList,water.dateAndTime)
            if(reportItem == null){
                reportItem = ReportItem(water.dateAndTime);
                groupedList.add(reportItem);
            }
            reportItem.waters.add(water)
        }

        groupedList.sortBy {
            it.date
        }
        return groupedList;
    }

    private fun reportHeader(initialDate: Date, finalDate: Date): ContainerTag {
        return div(
                div(
                        h1("Relatório Alimentar").withStyle("font-size: 30px; color: aliceblue; margin-top: 2px;margin-bottom: 2px;")
                ).withStyle("background-color: #42718b; text-align: center;padding: 5px "),
                div(
                        div(
                                label("De: "),
                                label(initialDate.asString("dd/MM/yyyy"))
                        ).withStyle("margin-left: 10px;margin-bottom: 10px;"),
                        div(
                                label("Até:"),
                                label(finalDate.asString("dd/MM/yyyy"))
                        ).withStyle("margin-left: 10px;margin-bottom: 10px;")

                ).withStyle("margin-top: 10px;margin-bottom: 10px")
        )
    }


    private fun mealReportItem(reportItems: List<ReportItem>): ContainerTag {

        val inner = each(reportItems) { reportItem ->
            div(
                    div(
                            h1(reportItem.date.asString("dd/MM/yyyy")).withStyle("font-size: 22px ;color: aliceblue; margin-top: 2px;margin-bottom: 2px;")
                    ).withStyle("background-color: #42718b; text-align: center; padding: 5px;"),
                    div(
                            each(reportItem.meals) { meal ->
                                div(
                                        mealHeader(meal),
                                        mealFoods(meal)
                                )
                            },

                            div(dailyWater(reportItem.waters))

                    )
            )
        }
        return div(inner)
    }

    private fun mealHeader(meal: Meal): ContainerTag {
        return div(
                div(
                        h1(meal.mealType.getName(context)).withStyle("font-size: 22px ;color: aliceblue; margin-top: 2px;margin-bottom: 2px;")
                ).withStyle("background-color: #42718b; text-align: center; padding: 5px;"),
                div("Resumo").withStyle("text-align: center; padding: 5px;"),

                div(
                        div(
                                div("Hora:").withStyle("width: 50%;"),
                                div(meal.date.asString("HH:mm")).withStyle("width: 50%")
                        ).withStyle("width: 100%;display: inline-flex;"),

                        div(

                                div("Sentimento:").withStyle("width: 50%;"),
                                div(meal.feeling.getName(context)).withStyle("width: 50%")
                        ).withStyle("width: 100%;display: inline-flex;"),

                        div(
                                div("Fome:").withStyle("width: 50%;"),
                                div(meal.hunger.selectedLevelName()).withStyle("width: 50%")
                        ).withStyle("width: 100%;display: inline-flex;"),

                        div(
                                div("Saciedade:").withStyle("width: 50%;"),
                                div(meal.satiety.selectedLevelName()).withStyle("width: 50%")
                        ).withStyle("width: 100%;display: inline-flex;"),

                        div(
                                div("O que estava fazendo:").withStyle("width: 50%;"),
                                div(meal.whatDoing).withStyle("width: 50%")

                        ).withStyle("width: 100%;display: inline-flex;")


                )
        )
    }

    private fun mealFoods(meal: Meal): ContainerTag {

        return div(
                div("Alimentos").withStyle("text-align: center; padding: 5px;"),

                each(meal.foods) { food ->
                    div(
                            div(food.name).withStyle("width: 50%;"),
                            div("${food.portion} porções").withStyle("width: 50%;")
                    ).withStyle("width: 100%;display: inline-flex;")

                }
        ).withStyle("margin-top: 5px; width: 100%;")
    }

    private fun dailyWater(waters: List<Water>): ContainerTag {
        if(waters.isEmpty()){
            return div();
        }
        return div(
                div("Liquidos").withStyle("text-align: center; padding: 5px;"),

                each(waters) { water ->
                    div(
                            div(water.dateAndTime.asString("HH:mm")).withStyle("width: 50%;"),
                            div("${water.quantity} copos").withStyle("width: 50%;")
                    ).withStyle("width: 100%;display: inline-flex;")

                }

        ).withStyle("margin-top: 5px; width: 100%;")
    }

}