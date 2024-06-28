# HoYoI (Aka. HoYo UI, HoUI)
## Introduction
This mod simply adds miHoYo/HoYoVerse games UI for Minecraft (like Genshin Impact, Honkai: Star Rial) with vanilla-like style.  
Now HoYoI only implemented Genshin Style, Star Rail Style is still working in progress.  
Genshin style loading screen has two theme: light and dark. In reallife time 19:00 ~ 7:00 next day the ui will be dark theme.
## Screenshots
### Genshin Style
![Starting game](https://cdn.modrinth.com/data/cached_images/7055e4140676da4418473d64ecc5dd6323a0701f.jpeg)
This loading screen will only show once when the game starting. Because NeoForge has an early display, so this feature is broken on NeoForge platform.
![Ingame loading](https://cdn.modrinth.com/data/cached_images/5c5dde7a7d87e188d4780619e76bc0dd1ca61c63.jpeg)
This loading screen will be normally seen in the game, the picture in the center and the tips at the bottom are all random, customizable and localized (I'll talk about how to customize below).
![Pause screen](https://cdn.modrinth.com/data/cached_images/303a82fc6d43104791af6ba24e44811e489d1458.jpeg)
The ESC pause screen in the game, the "M" button in the sidebar is Mod List button, for Fabric, you need install Mod Menu to open the Mod List screen.
### Star Rail Style
**WIP**

## Customization
You can customize the center picture and bottom tips in loading screen.

**FIRST**: Create a resource pack (Check [Minecraft Wiki](https://minecraft.wiki/w/Resource_pack))

### Tips
**SECOND**: Create a file in assets/{yourpack}/tips folder with name {languageCode}.json like "en_us.json"  
**THIRD**: Edit the file like the example shown below
```json
[
  {
    "title": "Example Tip 1",
    "desc": [
      "Line 1",
      "Line 2"
    ]
  },
  {
    "title": "Example Tip 2",
    "desc": [
      "Line 1",
      "Line 2",
      "Line 3",
      "Line 4" // I don't suggest over 4 lines
    ]
  },
  ...
]
```  
**FOURTH**: Load your resource pack
### Center picture
**SECOND**: Put pictures with png format in assets/{yourpack}/textures/ui/regions folder with any filename you like  
**THIRD**: Load your resource pack

## Credits
- [7777777_4547](https://modrinth.com/user/7777777_4547), loading screen ui textures.
- [DeeChael](https://modrinth.com/user/DeeChael), programming and pause screen ui textures. Some tips.