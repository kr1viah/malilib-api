## 0.5.0 changelog

Note: Internally, a lot of things changed. I expect a lot of bugs to arise. Please report them [here](https://github.com/kr1viah/malilib-api/issues)

### Users

* 1.14.X support!
  * MaLiLib API now supports all major versions of fabric!
* Fix ConfigCycle crashes related to the value not being set

### Mod developers

Note: breaking!

* Now prints what mods get registered, and which mod has already been registered
* Remove `KeybindSettings` arguments for `ConfigHotkeyPlus` and `ConfigBooleanHotkeyedPlus`
  * Now, use `.setSettings()`
    * Or, even better, use `new ConfigHotkeyPlus("Name").setContext(Context.ANY)` instead of `new ConfigHotkeyPlus("Name").setSettings(KeybindSettings.ANY)`.
* Separate annotation processor branch from main branch
