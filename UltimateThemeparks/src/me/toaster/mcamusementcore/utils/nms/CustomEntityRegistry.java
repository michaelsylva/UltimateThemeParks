package me.toaster.mcamusementcore.utils.nms;

import com.google.common.collect.Lists;
import com.mojang.datafixers.types.Type;

import me.toaster.mcamusementcore.utils.Logger;
import net.minecraft.server.v1_13_R2.BiomeBase;
import net.minecraft.server.v1_13_R2.Biomes;
import net.minecraft.server.v1_13_R2.DataConverterRegistry;
import net.minecraft.server.v1_13_R2.DataConverterTypes;
import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.EntityPositionTypes;
import net.minecraft.server.v1_13_R2.EntityTypes;
import net.minecraft.server.v1_13_R2.EnumCreatureType;
import net.minecraft.server.v1_13_R2.HeightMap;
import net.minecraft.server.v1_13_R2.IRegistry;
import net.minecraft.server.v1_13_R2.Item;
import net.minecraft.server.v1_13_R2.ItemFishBucket;
import net.minecraft.server.v1_13_R2.ItemMonsterEgg;
import net.minecraft.server.v1_13_R2.MinecraftKey;
import net.minecraft.server.v1_13_R2.RegistryID;
import net.minecraft.server.v1_13_R2.RegistryMaterials;
import net.minecraft.server.v1_13_R2.World;
import net.minecraft.server.v1_13_R2.WorldGenDungeons;
import net.minecraft.server.v1_13_R2.WorldGenFeatureSwampHut;
import net.minecraft.server.v1_13_R2.WorldGenMonument;
import net.minecraft.server.v1_13_R2.WorldGenNether;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.function.Function;

public class CustomEntityRegistry {
    private static Field registry_field_b;
    private static Field registry_field_c;
    private static Field registry_field_d;
    private static Field materials_field_b;
    private static Field materials_field_c;
    private static Field field_modifiers;
    private static Field item_fishType;
    private static Field item_entityType;
    private static Method registry_method_d;
    private static Method registry_method_e;
    private static Method biomebase_registerBiome;
    private static Method biomebase_addSpawn;
    private static Method entitypositiontypes_register;

    static {
        try {
            registry_field_b = RegistryID.class.getDeclaredField("b");
            registry_field_b.setAccessible(true);
            registry_field_c = RegistryID.class.getDeclaredField("c");
            registry_field_c.setAccessible(true);
            registry_field_d = RegistryID.class.getDeclaredField("d");
            registry_field_d.setAccessible(true);
            materials_field_b = RegistryMaterials.class.getDeclaredField("b"); // RegistryID<V>
            materials_field_b.setAccessible(true);
            materials_field_c = RegistryMaterials.class.getDeclaredField("c"); // BiMap<MinecraftKey,V>
            materials_field_c.setAccessible(true);
            field_modifiers = Field.class.getDeclaredField("modifiers");
            field_modifiers.setAccessible(true);
            item_fishType = ItemFishBucket.class.getDeclaredField("a");
            item_fishType.setAccessible(true);
            item_entityType = ItemMonsterEgg.class.getDeclaredField("d");
            item_entityType.setAccessible(true);
            registry_method_d = RegistryID.class.getDeclaredMethod("d", Object.class);
            registry_method_d.setAccessible(true);
            registry_method_e = RegistryID.class.getDeclaredMethod("e", int.class);
            registry_method_e.setAccessible(true);
            biomebase_registerBiome = BiomeBase.class.getDeclaredMethod("a", int.class, String.class, BiomeBase.class);
            biomebase_registerBiome.setAccessible(true);
            biomebase_addSpawn = BiomeBase.class.getDeclaredMethod("a", EnumCreatureType.class, BiomeBase.BiomeMeta.class);
            biomebase_addSpawn.setAccessible(true);
            entitypositiontypes_register = EntityPositionTypes.class.getDeclaredMethod("a", EntityTypes.class, EntityPositionTypes.Surface.class, HeightMap.Type.class);
            entitypositiontypes_register.setAccessible(true);

        } catch (NoSuchFieldException | NoSuchMethodException ignore) {
        }
    }

    public static void injectNewEntityTypes(String name, String extend_from, Class<? extends Entity> clazz, Function<? super World, ? extends Entity> function) {
        Logger.debug("Attempting to inject new entity: &3" + name);
        Logger.debug("Injecting new datatypes");
        Map<Object, Type<?>> dataTypes = (Map<Object, Type<?>>) DataConverterRegistry.a().getSchema(15190).findChoiceType(DataConverterTypes.n).types();
        dataTypes.put("minecraft:" + name, dataTypes.get("minecraft:" + extend_from));
        Logger.debug("Injecting new EntityTypes");
        EntityTypes.a(name, EntityTypes.a.a(clazz, function));
        Logger.info("Successfully injected new entity: &a" + name);
    }

    public static boolean injectReplacementEntityTypes(String name, EntityTypes entityTypes, MinecraftKey key, EntityTypes<?> newType, Material spawnEggMaterial, Material fishBucketMaterial) {
        Logger.debug("Attempting to inject replacement entity: &3" + name);
        try {
            RegistryID<EntityTypes<?>> registry = (RegistryID<EntityTypes<?>>) materials_field_b.get(IRegistry.ENTITY_TYPE);
            int id = registry.getId(entityTypes);

            Logger.debug("Detected original id: " + id);

            Object[] array_b = (Object[]) registry_field_b.get(registry);
            Object[] array_d = (Object[]) registry_field_d.get(registry);

            if (id < 0) {
                Logger.debug("&cInvalid id detected. Trying again!");
                for (int i = 0; i < array_d.length; i++) {
                    if (array_d[i] != null) {
                        if (array_d[i] == entityTypes) {
                            Logger.debug("Found EntityTypes id by reference");
                            id = i;
                            break;
                        }
                        if (((EntityTypes) array_d[i]).d().equals("minecraft:" + name)) {
                            Logger.debug("&cFound EntityTypes id using name but not reference! What?!");
                            id = i;
                            break;
                        }
                    }
                }
                Logger.debug("New detected id: " + id);
            }

            Logger.debug("EntityType at id " + id + ": " + ((EntityTypes) array_d[id]).d());

            int oldIndex = -1;
            for (int i = 0; i < array_b.length; i++) {
                if (array_b[i] != null) {
                    if (array_b[i] == entityTypes) {
                        //array_b[i] = null; // do not remove old reference (might be causing issues?)
                        oldIndex = i;
                        break;
                    }
                    if (((EntityTypes) array_b[i]).d().equals("minecraft:" + name)) {
                        Logger.debug("&cFound EntityTypes oldIndex using name but not reference! What?!");
                        //array_b[i] = null; // do not remove old reference (might be causing issues?)
                        oldIndex = i;
                        break;
                    }
                }
            }

            Logger.debug("Detected oldIndex: " + oldIndex);

            if (oldIndex < 0) {
                Logger.debug("&cInvalid oldIndex detected. Trying again!");
                array_b = (Object[]) registry_field_b.get(registry);
                for (int i = 0; i < array_b.length; i++) {
                    if (array_b[i] != null) {
                        if (array_b[i] == entityTypes) {
                            //array_b[i] = null; // do not remove old reference (might be causing issues?)
                            oldIndex = i;
                            break;
                        }
                        if (((EntityTypes) array_b[i]).d().equals("minecraft:" + name)) {
                            Logger.debug("&cFound EntityTypes oldIndex using name but not reference! What?!");
                            //array_b[i] = null; // do not remove old reference (might be causing issues?)
                            oldIndex = i;
                            break;
                        }
                    }
                }
                Logger.debug("New detected oldIndex: " + oldIndex);
            }

            Logger.debug("EntityType at oldIndex " + oldIndex + ": " + ((EntityTypes) array_b[oldIndex]).d());

            int newIndex = (int) registry_method_e.invoke(registry, registry_method_d.invoke(registry, newType));
            Logger.debug("Generated newIndex: " + newIndex);

            EntityTypes e = (EntityTypes) array_b[newIndex];
            Logger.debug("EntityType at newIndex " + newIndex + ": " + (e == null ? "null" : "&c" + e.d()));

            Logger.debug("Injecting new EntityTypes to b[newIndex]: " + newIndex);
            array_b[newIndex] = newType;
            Logger.debug("Injecting new EntityTypes to d[id]: " + id);
            array_d[id] = newType;

            int[] array_c = (int[]) registry_field_c.get(registry);
            if (oldIndex >= 0) {
                Logger.debug("Removing c[oldIndex] reference: " + oldIndex + ":0");
                array_c[oldIndex] = 0;
            } else {
                Logger.debug("&cSkipping c[oldIndex] reference: " + oldIndex + ":0");
            }
            Logger.debug("Injecting c[newIndex] reference: " + newIndex + ":" + id);
            array_c[newIndex] = id;

            Logger.debug("Updating RegistrySimple mapC");
            Map<MinecraftKey, EntityTypes<?>> map_c = (Map<MinecraftKey, EntityTypes<?>>) materials_field_c.get(IRegistry.ENTITY_TYPE);
            map_c.put(key, newType);

            Logger.debug("Updating EntityTypes static field");
            Field types_field = getField(entityTypes);
            types_field.setAccessible(true);
            field_modifiers.setInt(types_field, types_field.getModifiers() & ~Modifier.FINAL);
            types_field.set(null, newType);

            registry_field_b.set(registry, array_b);
            registry_field_c.set(registry, array_c);
            registry_field_d.set(registry, array_d);
            materials_field_b.set(IRegistry.ENTITY_TYPE, registry);
            materials_field_c.set(IRegistry.ENTITY_TYPE, map_c);

            if (spawnEggMaterial != null) {
                Logger.debug("Updating spawn egg reference");
                Item spawnEgg = CraftItemStack.asNMSCopy(new ItemStack(spawnEggMaterial)).getItem();
                item_entityType.set(spawnEgg, newType);
            }

            if (fishBucketMaterial != null) {
                Logger.debug("Updating fish bucket");
                Item fishBucket = CraftItemStack.asNMSCopy(new ItemStack(fishBucketMaterial)).getItem();
                field_modifiers.setInt(item_fishType, item_fishType.getModifiers() & ~Modifier.FINAL);
                item_fishType.set(fishBucket, newType);
            }
            return true;
        } catch (IllegalAccessException | InvocationTargetException | ArrayIndexOutOfBoundsException e) {
            Logger.error("Could not inject new ridable entity to registry! Restart your server to try again! "
                    + "(&e" + name + "&c)");
            e.printStackTrace();
            return false;
        }
    }

    private static Field getField(EntityTypes entityTypes) {
        for (Field field : EntityTypes.class.getDeclaredFields()) {
            try {
                if (entityTypes == field.get(null)) {
                    return field;
                }
            } catch (IllegalAccessException ignore) {
            }
        }
        throw new IllegalArgumentException("Could not get Field of " + entityTypes.getClass().getSimpleName());
    }

    public static void rebuildWorldGenMobs() {
        setPrivateFinalField(WorldGenDungeons.class, "b", new EntityTypes[]{EntityTypes.SKELETON, EntityTypes.ZOMBIE, EntityTypes.ZOMBIE, EntityTypes.SPIDER});
        setPrivateFinalField(WorldGenFeatureSwampHut.class, "b", Lists.newArrayList(new BiomeBase.BiomeMeta(EntityTypes.WITCH, 1, 1, 1)));
        setPrivateFinalField(WorldGenMonument.class, "b", Lists.newArrayList(new BiomeBase.BiomeMeta(EntityTypes.GUARDIAN, 1, 2, 4)));
        setPrivateFinalField(WorldGenNether.class, "b", Lists.newArrayList(new BiomeBase.BiomeMeta(EntityTypes.BLAZE, 10, 2, 3), new BiomeBase.BiomeMeta(EntityTypes.ZOMBIE_PIGMAN, 5, 4, 4), new BiomeBase.BiomeMeta(EntityTypes.WITHER_SKELETON, 8, 5, 5), new BiomeBase.BiomeMeta(EntityTypes.SKELETON, 2, 5, 5), new BiomeBase.BiomeMeta(EntityTypes.MAGMA_CUBE, 3, 4, 4)));
    }

    private static void setPrivateFinalField(Class clazz, String name, Object value) {
        Logger.debug("Rebuilding world gen mob features for: " + clazz.getSimpleName());
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            field_modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void rebuildBiomes() {
        Logger.info("Rebuilding biome mob lists");

        try {
            entitypositiontypes_register.invoke(null, EntityTypes.ILLUSIONER, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        rebuildBiome("OCEAN", 0, "ocean", new net.minecraft.server.v1_13_R2.BiomeOcean());
        try {
            Field biomes_field = Biomes.class.getDeclaredField("b");
            biomes_field.setAccessible(true);
            field_modifiers.setInt(biomes_field, biomes_field.getModifiers() & ~Modifier.FINAL);
            biomes_field.set(null, Biomes.OCEAN);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        rebuildBiome("PLAINS", 1, "plains", createNonPublicBiome(net.minecraft.server.v1_13_R2.BiomePlains.class));
        rebuildBiome("DESERT", 2, "desert", new net.minecraft.server.v1_13_R2.BiomeDesert());
        rebuildBiome("MOUNTAINS", 3, "mountains", createNonPublicBiome(net.minecraft.server.v1_13_R2.BiomeBigHills.class));
        rebuildBiome("FOREST", 4, "forest", new net.minecraft.server.v1_13_R2.BiomeForest());
        rebuildBiome("TAIGA", 5, "taiga", new net.minecraft.server.v1_13_R2.BiomeTaiga());
        rebuildBiome("SWAMP", 6, "swamp", createNonPublicBiome(net.minecraft.server.v1_13_R2.BiomeSwamp.class));
        rebuildBiome("RIVER", 7, "river", new net.minecraft.server.v1_13_R2.BiomeRiver());
        rebuildBiome("NETHER", 8, "nether", createNonPublicBiome(net.minecraft.server.v1_13_R2.BiomeHell.class));
        rebuildBiome("THE_END", 9, "the_end", new net.minecraft.server.v1_13_R2.BiomeTheEnd());
        rebuildBiome("FROZEN_OCEAN", 10, "frozen_ocean", new net.minecraft.server.v1_13_R2.BiomeFrozenOcean());
        rebuildBiome("FROZEN_RIVER", 11, "frozen_river", new net.minecraft.server.v1_13_R2.BiomeFrozenRiver());
        rebuildBiome("SNOWY_TUNDRA", 12, "snowy_tundra", new net.minecraft.server.v1_13_R2.BiomeIcePlains());
        rebuildBiome("SNOWY_MOUNTAINS", 13, "snowy_mountains", new net.minecraft.server.v1_13_R2.BiomeIceMountains());
        rebuildBiome("MUSHROOM_FIELDS", 14, "mushroom_fields", new net.minecraft.server.v1_13_R2.BiomeMushrooms());
        rebuildBiome("MUSHROOM_FIELD_SHORE", 15, "mushroom_field_shore", new net.minecraft.server.v1_13_R2.BiomeMushroomIslandShore());
        rebuildBiome("BEACH", 16, "beach", new net.minecraft.server.v1_13_R2.BiomeBeach());
        rebuildBiome("DESERT_HILLS", 17, "desert_hills", new net.minecraft.server.v1_13_R2.BiomeDesertHills());
        rebuildBiome("WOODED_HILLS", 18, "wooded_hills", new net.minecraft.server.v1_13_R2.BiomeForestHills());
        rebuildBiome("TAIGA_HILLS", 19, "taiga_hills", new net.minecraft.server.v1_13_R2.BiomeTaigaHills());
        rebuildBiome("MOUNTAIN_EDGE", 20, "mountain_edge", createNonPublicBiome(net.minecraft.server.v1_13_R2.BiomeExtremeHillsEdge.class));
        rebuildBiome("JUNGLE", 21, "jungle", new net.minecraft.server.v1_13_R2.BiomeJungle());
        rebuildBiome("JUNGLE_HILLS", 22, "jungle_hills", new net.minecraft.server.v1_13_R2.BiomeJungleHills());
        rebuildBiome("JUNGLE_EDGE", 23, "jungle_edge", new net.minecraft.server.v1_13_R2.BiomeJungleEdge());
        rebuildBiome("DEEP_OCEAN", 24, "deep_ocean", new net.minecraft.server.v1_13_R2.BiomeDeepOcean());
        rebuildBiome("STONE_SHORE", 25, "stone_shore", new net.minecraft.server.v1_13_R2.BiomeStoneBeach());
        rebuildBiome("SNOWY_BEACH", 26, "snowy_beach", new net.minecraft.server.v1_13_R2.BiomeColdBeach());
        rebuildBiome("BIRCH_FOREST", 27, "birch_forest", new net.minecraft.server.v1_13_R2.BiomeBirchForest());
        rebuildBiome("BIRCH_FOREST_HILLS", 28, "birch_forest_hills", new net.minecraft.server.v1_13_R2.BiomeBirchForestHills());
        rebuildBiome("DARK_FOREST", 29, "dark_forest", new net.minecraft.server.v1_13_R2.BiomeRoofedForest());
        rebuildBiome("SNOWY_TAIGA", 30, "snowy_taiga", new net.minecraft.server.v1_13_R2.BiomeColdTaiga());
        rebuildBiome("SNOWY_TAIGA_HILLS", 31, "snowy_taiga_hills", new net.minecraft.server.v1_13_R2.BiomeColdTaigaHills());
        rebuildBiome("GIANT_TREE_TAIGA", 32, "giant_tree_taiga", new net.minecraft.server.v1_13_R2.BiomeMegaTaiga());
        rebuildBiome("GIANT_TREE_TAIGA_HILLS", 33, "giant_tree_taiga_hills", new net.minecraft.server.v1_13_R2.BiomeMegaTaigaHills());
        rebuildBiome("WOODED_MOUNTAINS", 34, "wooded_mountains", createNonPublicBiome(net.minecraft.server.v1_13_R2.BiomeExtremeHillsWithTrees.class));
        rebuildBiome("SAVANNA", 35, "savanna", createNonPublicBiome(net.minecraft.server.v1_13_R2.BiomeSavanna.class));
        rebuildBiome("SAVANNA_PLATEAU", 36, "savanna_plateau", createNonPublicBiome(net.minecraft.server.v1_13_R2.BiomeSavannaPlateau.class));
        rebuildBiome("BADLANDS", 37, "badlands", new net.minecraft.server.v1_13_R2.BiomeMesa());
        rebuildBiome("WOODED_BADLANDS_PLATEAU", 38, "wooded_badlands_plateau", new net.minecraft.server.v1_13_R2.BiomeMesaPlataeu());
        rebuildBiome("BADLANDS_PLATEAU", 39, "badlands_plateau", new net.minecraft.server.v1_13_R2.BiomeMesaPlataeuClear());
        rebuildBiome("SMALL_END_ISLANDS", 40, "small_end_islands", new net.minecraft.server.v1_13_R2.BiomeTheEndFloatingIslands());
        rebuildBiome("END_MIDLANDS", 41, "end_midlands", new net.minecraft.server.v1_13_R2.BiomeTheEndMediumIsland());
        rebuildBiome("END_HIGHLANDS", 42, "end_highlands", new net.minecraft.server.v1_13_R2.BiomeTheEndHighIsland());
        rebuildBiome("END_BARRENS", 43, "end_barrens", new net.minecraft.server.v1_13_R2.BiomeTheEndBarrenIsland());
        rebuildBiome("WARM_OCEAN", 44, "warm_ocean", new net.minecraft.server.v1_13_R2.BiomeWarmOcean());
        rebuildBiome("LUKEWARM_OCEAN", 45, "lukewarm_ocean", new net.minecraft.server.v1_13_R2.BiomeLukewarmOcean());
        rebuildBiome("COLD_OCEAN", 46, "cold_ocean", new net.minecraft.server.v1_13_R2.BiomeColdOcean());
        rebuildBiome("DEEP_WARM_OCEAN", 47, "deep_warm_ocean", new net.minecraft.server.v1_13_R2.BiomeWarmDeepOcean());
        rebuildBiome("DEEP_LUKEWARM_OCEAN", 48, "deep_lukewarm_ocean", new net.minecraft.server.v1_13_R2.BiomeLukewarmDeepOcean());
        rebuildBiome("DEEP_COLD_OCEAN", 49, "deep_cold_ocean", new net.minecraft.server.v1_13_R2.BiomeColdDeepOcean());
        rebuildBiome("DEEP_FROZEN_OCEAN", 50, "deep_frozen_ocean", new net.minecraft.server.v1_13_R2.BiomeFrozenDeepOcean());
        rebuildBiome("THE_VOID", 127, "the_void", new net.minecraft.server.v1_13_R2.BiomeVoid());
        rebuildBiome("SUNFLOWER_PLAINS", 129, "sunflower_plains", createNonPublicBiome(net.minecraft.server.v1_13_R2.BiomeSunflowerPlains.class));
        rebuildBiome("DESERT_LAKES", 130, "desert_lakes", new net.minecraft.server.v1_13_R2.BiomeDesertMutated());
        rebuildBiome("GRAVELLY_MOUNTAINS", 131, "gravelly_mountains", createNonPublicBiome(net.minecraft.server.v1_13_R2.BiomeExtremeHillsMutated.class));
        rebuildBiome("FLOWER_FOREST", 132, "flower_forest", new net.minecraft.server.v1_13_R2.BiomeFlowerForest());
        rebuildBiome("TAIGA_MOUNTAINS", 133, "taiga_mountains", new net.minecraft.server.v1_13_R2.BiomeTaigaMutated());
        rebuildBiome("SWAMP_HILLS", 134, "swamp_hills", createNonPublicBiome(net.minecraft.server.v1_13_R2.BiomeSwamplandMutated.class));
        rebuildBiome("ICE_SPIKES", 140, "ice_spikes", new net.minecraft.server.v1_13_R2.BiomeIcePlainsSpikes());
        rebuildBiome("MODIFIED_JUNGLE", 149, "modified_jungle", new net.minecraft.server.v1_13_R2.BiomeJungleMutated());
        rebuildBiome("MODIFIED_JUNGLE_EDGE", 151, "modified_jungle_edge", new net.minecraft.server.v1_13_R2.BiomeJungleEdgeMutated());
        rebuildBiome("TALL_BIRCH_FOREST", 155, "tall_birch_forest", new net.minecraft.server.v1_13_R2.BiomeBirchForestMutated());
        rebuildBiome("TALL_BIRCH_HILLS", 156, "tall_birch_hills", new net.minecraft.server.v1_13_R2.BiomeBirchForestHillsMutated());
        rebuildBiome("DARK_FOREST_HILLS", 157, "dark_forest_hills", new net.minecraft.server.v1_13_R2.BiomeRoofedForestMutated());
        rebuildBiome("SNOWY_TAIGA_MOUNTAINS", 158, "snowy_taiga_mountains", new net.minecraft.server.v1_13_R2.BiomeColdTaigaMutated());
        rebuildBiome("GIANT_SPRUCE_TAIGA", 160, "giant_spruce_taiga", new net.minecraft.server.v1_13_R2.BiomeMegaSpruceTaiga());
        rebuildBiome("GIANT_SPRUCE_TAIGA_HILLS", 161, "giant_spruce_taiga_hills", new net.minecraft.server.v1_13_R2.BiomeRedwoodTaigaHillsMutated());
        rebuildBiome("MODIFIED_GRAVELLY_MOUNTAINS", 162, "modified_gravelly_mountains", createNonPublicBiome(net.minecraft.server.v1_13_R2.BiomeExtremeHillsWithTreesMutated.class));
        rebuildBiome("SHATTERED_SAVANNA", 163, "shattered_savanna", new net.minecraft.server.v1_13_R2.BiomeSavannaMutated());
        rebuildBiome("SHATTERED_SAVANNA_PLATEAU", 164, "shattered_savanna_plateau", new net.minecraft.server.v1_13_R2.BiomeSavannaPlateauMutated());
        rebuildBiome("ERODED_BADLANDS", 165, "eroded_badlands", new net.minecraft.server.v1_13_R2.BiomeMesaBryce());
        rebuildBiome("MODIFIED_WOODED_BADLANDS_PLATEAU", 166, "modified_wooded_badlands_plateau", new net.minecraft.server.v1_13_R2.BiomeMesaPlateauMutated());
        rebuildBiome("MODIFIED_BADLANDS_PLATEAU", 167, "modified_badlands_plateau", new net.minecraft.server.v1_13_R2.BiomeMesaPlateauClearMutated());
    }

    private static BiomeBase createNonPublicBiome(Class<? extends BiomeBase> clazz) {
        try {
            Constructor<? extends BiomeBase> biomebase_constructor = clazz.getDeclaredConstructor();
            biomebase_constructor.setAccessible(true);
            return biomebase_constructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void rebuildBiome(String field, int id, String name, BiomeBase biome) {
        Logger.debug(" - rebuilding biome: " + name);
        try {
            biomebase_registerBiome.invoke(null, id, name, biome);
            Field biomes_field = Biomes.class.getDeclaredField(field);
            biomes_field.setAccessible(true);
            field_modifiers.setInt(biomes_field, biomes_field.getModifiers() & ~Modifier.FINAL);
            biomes_field.set(null, biome);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}