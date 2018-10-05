package apps.esampaio.com.comacerto.core.service

import st.lowlevel.storo.Storo



class CacheService {

    fun saveCache(key: String, value:String){
        Storo.put(key, value).execute()
    }


    fun getCache(key: String): String{
        return Storo.get(key,String::class.java).execute()
    }
}