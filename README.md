# SettingsHelper
## Change location settings from runtime

In many cases, you may need to request user's location.
But before doing this, you need to make sure that a device has a correct settings for this.
And if it is not so, you should to promt user to change his location settings from runtime.

That few files will help you to do it simplify.
* [OnLocationSettingsListener](https://github.com/YakuninSerg/SettingsHelper/blob/master/app/src/main/java/com/example/siakunin/settingshelper/OnLocationSettingsListener.java)
* [LocationSettings](https://github.com/YakuninSerg/SettingsHelper/blob/master/app/src/main/java/com/example/siakunin/settingshelper/LocationSettings.java) 

All you need is to create <b>LocationSettings(activity)</b>
Than to add OnLocationSettingsListener via 
```
 locationSettings.addLocationSettingsListener(new OnLocationSettingsListener() {
            @Override
            public void onLocationSettingsGranted() {
                //You can start location request there
                Log.i("TAG","We got the necessary settings");
            }

            @Override
            public void onLocationSettingsDenied() {
                //Do something if settings is not granted
                Log.i("TAG","We did not receive the required settings ");
            }
        });
```

Check location settings

```
locationSettings.checkCurrentLocationSettings();

```

And finaly override the one method in your activity, and add two line in it:
```
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  super.onActivityResult(requestCode, resultCode, data);
        
  locationSettings.onActivityResult(requestCode,resultCode,data);

}
```

If this was useful for you, you can say thanks by clicking a star.
