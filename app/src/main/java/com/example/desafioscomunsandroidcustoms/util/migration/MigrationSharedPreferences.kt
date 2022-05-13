package com.example.desafioscomunsandroidcustoms.util.migration

import android.content.Context
import com.example.desafioscomunsandroidcustoms.util.getBooleanSharedPref
import com.example.desafioscomunsandroidcustoms.util.getStringSharedPref
import com.example.desafioscomunsandroidcustoms.util.migration.PreferenceKey.APP_BUILD_NUMBER
import com.example.desafioscomunsandroidcustoms.util.migration.PreferenceKey.APP_VERSION
import com.example.desafioscomunsandroidcustoms.util.migration.PreferenceKey.MIGRATION_COMPLETED
import com.example.desafioscomunsandroidcustoms.util.migration.PreferenceKey.USER_LOGIN_ACTIVE
import com.example.desafioscomunsandroidcustoms.util.migration.PreferenceKey.USER_LOGIN_AUTOMATIC
import com.example.desafioscomunsandroidcustoms.util.migration.PreferenceKey.USER_LOGIN_SAVE_ID
import com.example.desafioscomunsandroidcustoms.util.putBooleanSharedPref
import com.example.desafioscomunsandroidcustoms.util.putStringSharedPref
import com.example.desafioscomunsandroidcustoms.util.removeSharedPref

class MigrationSharedPreferences(val context: Context) {

    fun migrateOnlyOne(){
        if(!context.getBooleanSharedPref(MIGRATION_COMPLETED)){
            migrate()
        }
        context.putBooleanSharedPref(MIGRATION_COMPLETED, true)
    }

    private fun migrate() {
        //definições do legado que serão  modificadas para o novo app
        val version = "version"
        val build = "build"
        val autoLogBool = "AUTO_LOGON"
        val saveIdBool = "save-id"
        val active = "active"

        //migrar o legado para o novo app
        context.putStringSharedPref(APP_VERSION, context.getStringSharedPref(version))
        context.putStringSharedPref(APP_BUILD_NUMBER, context.getStringSharedPref(build))
        context.putBooleanSharedPref(USER_LOGIN_AUTOMATIC, context.getBooleanSharedPref(autoLogBool))
        context.putBooleanSharedPref(USER_LOGIN_SAVE_ID, context.getBooleanSharedPref(saveIdBool))
        context.putBooleanSharedPref(USER_LOGIN_ACTIVE, context.getBooleanSharedPref(active))

        //limpar dados do legado
        context.removeSharedPref(version)
        context.removeSharedPref(build)
        context.removeSharedPref(autoLogBool)
        context.removeSharedPref(saveIdBool)
        context.removeSharedPref(active)
    }
}