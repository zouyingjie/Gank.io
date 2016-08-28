package com.gank.io.girl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zouyingjie on 16/8/27.
 */

@Module
public class GirlModule {

    @Provides GirlAdapter providerGirlAdapter(){
        return new GirlAdapter();
    }
}
