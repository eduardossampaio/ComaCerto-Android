package apps.esampaio.com.comacerto.core.firebase;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import apps.esampaio.com.comacerto.MyApplication;

public class RemoteConfig {
    private static final String HELP_TOPICS_CONFIG_KEY = "HELP_TOPICS";
    private static final String FOODS_LIST_CONFIG_KEY = "FOOD_LIST_JSON";
    private static final String LAST_USER_TERMS_VERSION = "LAST_USER_TERMS_VERSION";
    private static final String USER_TERMS_VERSION = "USER_TERMS_VERSION_";


    private final FirebaseRemoteConfig mFirebaseRemoteConfig =  FirebaseRemoteConfig.getInstance();
    private static RemoteConfig instance = new RemoteConfig();
    long cacheExpiration = 0;

    public static RemoteConfig getInstance() {
        return instance;
    }

    private RemoteConfig(){

        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mFirebaseRemoteConfig.activateFetched();
                        }
                    }
                });
    }
    public String getHelpTopics(){
      return mFirebaseRemoteConfig.getString(HELP_TOPICS_CONFIG_KEY);
    }

    public String getFoodsListJson(){
        return mFirebaseRemoteConfig.getString(FOODS_LIST_CONFIG_KEY);
    }

    public Integer getLastUserTermsVersion(){
        try {
            return Integer.parseInt(mFirebaseRemoteConfig.getString(LAST_USER_TERMS_VERSION));
        }catch (Exception e){
            return 1;
        }
    }

    public String getUserTermsURL(Integer version){
        return mFirebaseRemoteConfig.getString(USER_TERMS_VERSION+""+version);
    }
}
