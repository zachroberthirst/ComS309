package com.example.frontend.view.bottomTabScreens.actorProfile;

import com.example.frontend.model.setting.Setting;

public interface ISettingsPageView {
    void gotSetting(Setting setting);
    void close();
    void noSetting();
    void deleted();
}
