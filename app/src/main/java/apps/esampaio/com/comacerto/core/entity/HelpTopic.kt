package apps.esampaio.com.comacerto.core.entity

import java.io.Serializable

data class HelpTopic(val topic:String,val contentUrl:String) : Serializable{

    override fun toString(): String {
        return topic
    }
}