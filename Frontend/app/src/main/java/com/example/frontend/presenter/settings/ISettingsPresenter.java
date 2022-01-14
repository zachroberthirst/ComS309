package com.example.frontend.presenter.settings;

import com.example.frontend.model.setting.Setting;

public interface ISettingsPresenter {
    void getSetting();
    void updateSetting(Setting setting);
    void createSetting(Setting setting);
    void deleteUser();
}
