package eu.benayoun.androidmoviedatabase.utils

import android.util.Log

object LogUtils {
        private val GLOBAL_TAG = "AMDB_"
        private val VERBOSE_TAG = "V_"
        private val DEBUG_TAG = "D_"
        private val ERROR_TAG = "E_"

        fun v(message : String){
            Log.v("$GLOBAL_TAG $VERBOSE_TAG",message)
        }

        fun d(message : String){
            Log.v(GLOBAL_TAG + DEBUG_TAG,message)
        }

        fun e(message : String){
            Log.e(GLOBAL_TAG + ERROR_TAG,message)
        }

    fun getTag():String{
        return GLOBAL_TAG
    }
}