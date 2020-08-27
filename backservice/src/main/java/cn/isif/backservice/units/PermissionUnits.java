package cn.isif.backservice.units;

import android.content.ComponentName;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.HashSet;
import java.util.Set;

public class PermissionUnits {
    /**
     * 判断权限是否开启
     *
     * @param ct
     * @param serviceClass
     * @return
     */
    public static boolean hasServicePermission(Context ct, Class serviceClass) {
        int ok = 0;
        try {
            ok = Settings.Secure.getInt(ct.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
        }

        TextUtils.SimpleStringSplitter ms = new TextUtils.SimpleStringSplitter(':');
        if (ok == 1) {
            String settingValue = Settings.Secure.getString(ct.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                ms.setString(settingValue);
                while (ms.hasNext()) {
                    String accessibilityService = ms.next();
                    if (accessibilityService.contains(serviceClass.getSimpleName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Root环境下自动开启权限
     *
     * @param ct
     * @param service
     */
    public static void openServicePermissonRoot(Context ct, Class service) {
        String cmd1 = "settings put secure enabled_accessibility_services  " + ct.getPackageName() + "/" + service.getName();
        String cmd2 = "settings put secure accessibility_enabled 1";
        String[] cmds = new String[]{cmd1, cmd2};
        ShellUtils.execCommand(cmds, true);
    }


    /**
     * targetSdk 版本小于23的情况下，部分手机也可通过以下代码开启权限
     *
     * @param ct
     * @param serviceClass
     */
    public static void openServicePermission(Context ct, Class serviceClass) {
        Set<ComponentName> enabledServices = getEnabledServicesFromSettings(ct, serviceClass);
        if (null == enabledServices) {
            return;
        }
        ComponentName toggledService = ComponentName.unflattenFromString(ct.getPackageName() + "/" + serviceClass.getName());
        final boolean accessibilityEnabled = true;
        enabledServices.add(toggledService);
        // Update the enabled services setting.
        StringBuilder enabledServicesBuilder = new StringBuilder();
        for (ComponentName enabledService : enabledServices) {
            enabledServicesBuilder.append(enabledService.flattenToString());
            enabledServicesBuilder.append(":");
        }
        final int enabledServicesBuilderLength = enabledServicesBuilder.length();
        if (enabledServicesBuilderLength > 0) {
            enabledServicesBuilder.deleteCharAt(enabledServicesBuilderLength - 1);
        }
        Settings.Secure.putString(ct.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, enabledServicesBuilder.toString());
        // Update accessibility enabled.
        Settings.Secure.putInt(ct.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED, accessibilityEnabled ? 1 : 0);
    }

    public static Set<ComponentName> getEnabledServicesFromSettings(Context context, Class serviceClass) {
        String enabledServicesSetting = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        if (enabledServicesSetting == null) {
            enabledServicesSetting = "";
        }
        Set<ComponentName> enabledServices = new HashSet<ComponentName>();
        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter(':');
        colonSplitter.setString(enabledServicesSetting);
        while (colonSplitter.hasNext()) {
            String componentNameString = colonSplitter.next();
            ComponentName enabledService = ComponentName.unflattenFromString(componentNameString);
            if (enabledService != null) {
                if (enabledService.flattenToString().contains(serviceClass.getSimpleName())) {
                    return null;
                }
                enabledServices.add(enabledService);
            }
        }
        return enabledServices;
    }

    public static void openServicePermissonCompat(final Context ct, final Class service) {
        //辅助权限：如果root，先申请root权限
        if (RootUtil.isDeviceRooted()) {
            if (!hasServicePermission(ct, service)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        openServicePermissonRoot(ct, service);
                    }
                }).start();
            }
        } else {
            try {
                openServicePermission(ct, service);
            } catch (Exception e) {
                e.printStackTrace();
                if (!hasServicePermission(ct, service)) {
                    SettingAction.jumpAccessibility(ct);
                }
            }
        }
    }

}
