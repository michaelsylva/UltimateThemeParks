package me.toaster.mcamusementcore.utils.nms;

import java.util.Map;
import java.util.function.Function;

import com.mojang.datafixers.types.Type;

import me.toaster.mcamusementcore.entities.CEntityArmorstand;
import me.toaster.mcamusementcore.entities.CEntityBat;
import net.minecraft.server.v1_13_R2.DataConverterRegistry;
import net.minecraft.server.v1_13_R2.DataConverterTypes;
import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.EntityTypes;
import net.minecraft.server.v1_13_R2.World;

public class NMSUtils {

	public static <T extends Entity> void registerCustomEntity(Class<? extends T> _clazz,
            Function<? super World, ? extends T> _func, String id) {
        EntityTypes.a(id, EntityTypes.a.a(_clazz, _func).b());
    }
	
	public static <T extends Entity> void correctRegister() {
		
	}
	
}
