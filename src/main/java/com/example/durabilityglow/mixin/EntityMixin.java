package com.example.durabilityglow.mixin;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Colors the display name (nametag) of every player by the condition of
 * their worst armor piece: green = fresh, yellow/orange = worn, red = about to break.
 */
@Mixin(Entity.class)
public abstract class EntityMixin {

    private static final EquipmentSlot[] ARMOR = {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
    };

    @Inject(method = "getDisplayName", at = @At("RETURN"), cancellable = true)
    private void durabilityglow$tint(CallbackInfoReturnable<Text> cir) {
        if (!((Object) this instanceof PlayerEntity player)) return;
        if (!player.getWorld().isClient) return;

        float worst = 1.0f;
        boolean hasArmor = false;
        for (EquipmentSlot slot : ARMOR) {
            ItemStack stack = player.getEquippedStack(slot);
            if (!stack.isEmpty() && stack.isDamageable()) {
                hasArmor = true;
                float remaining = 1.0f - (float) stack.getDamage() / stack.getMaxDamage();
                worst = Math.min(worst, remaining);
            }
        }
        if (!hasArmor) return;

        // hue 0.0 = red, 0.33 = green
        int rgb = Color.HSBtoRGB(Math.max(0f, worst) * 0.33f, 1.0f, 1.0f) & 0xFFFFFF;
        cir.setReturnValue(cir.getReturnValue().copy().styled(s -> s.withColor(rgb)));
    }
}
