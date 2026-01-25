## 0.2.5 changelog

### Users

No new things for users

### Mod developers

* Remove data from `ConfigButton`
  - Is now not a generic anymore
* `ConfigList`
  - A config type that is a List of `IConfigBase`. Yes, that can be another ConfigList
* `ConfigPair`
  - A config type that is a Pair of 2 `IConfigBase`s. They will show up next to each other in game, each taking up half the width of the config. Yes, they both can be another `ConfigPair`
* Custom config option support
  - See `MalilibApi#registerWidgetBasedConfigType` and `MalilibApi#registerButtonBasedConfigType`, or these for examples:
    - (Button based) `kr1v.malilibApi.config._new.ConfigButton` + `kr1v.malilibApi.widget.ConfigButtonButton`
    - (Button based) `kr1v.malilibApi.config._new.ConfigList` + `kr1v.malilibApi.widget.ConfigListButton`
    - (Widget based) `kr1v.malilibApi.config._new.ConfigPair` + `kr1v.malilibApi.widget.WidgetPair`
