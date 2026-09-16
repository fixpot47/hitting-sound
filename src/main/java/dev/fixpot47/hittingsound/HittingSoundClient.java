package dev.fixpot47.hittingsound;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

public final class HittingSoundClient implements ClientModInitializer {
    public static final String MOD_ID = "hittingsound";
    private static final float FULL_CHARGE_THRESHOLD = 0.9F;

    @Override
    public void onInitializeClient() {
        AttackEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            Minecraft minecraft = Minecraft.getInstance();

            if (player != minecraft.player) {
                return InteractionResult.PASS;
            }

            if (hand != InteractionHand.MAIN_HAND) {
                return InteractionResult.PASS;
            }

            if (!(entity instanceof Player target) || target == player) {
                return InteractionResult.PASS;
            }

            if (!player.getMainHandItem().is(ItemTags.SWORDS)) {
                return InteractionResult.PASS;
            }

            if (player.getAttackStrengthScale(0.5F) < FULL_CHARGE_THRESHOLD) {
                return InteractionResult.PASS;
            }

            player.playSound(SoundEvents.NOTE_BLOCK_PLING, 0.75F, 1.6F);
            return InteractionResult.PASS;
        });
    }
}
