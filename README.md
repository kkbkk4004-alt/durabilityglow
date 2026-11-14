# Durability Glow (Fabric, Minecraft 1.21.4, client-side)

Colors **every player's nametag** (yours and other players') by the condition of
their worst armor piece:

| Armor condition | Nametag color |
|-----------------|---------------|
| Full            | Green         |
| About half      | Yellow/Orange |
| Nearly broken   | Red           |

Players with no armor keep their normal nametag.

## Build

Requires JDK 21.

```
gradle wrapper        # once, if you don't have the wrapper yet
./gradlew build
```

The finished jar is in `build/libs/durabilityglow-1.0.0.jar`.
Put it in your `mods` folder together with Fabric Loader and Fabric API (1.21.4).

## Notes

- Client-side only: you don't need it on the server, and it works on any server.
- Other players' armor shows because the server sends every player's equipment to nearby clients.
- Your own nametag isn't normally drawn in first person; switch to F5 (third person) to see it.
- To change the colors, edit the `Color.HSBtoRGB(...)` line in `EntityMixin.java`.
