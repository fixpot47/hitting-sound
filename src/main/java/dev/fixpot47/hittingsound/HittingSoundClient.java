package dev.fixpot47.hittingsound;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

public final class HittingSoundClient implements ClientModInitializer {
    public static final String MOD_ID = "hittingsound";
    private static final float CHARGE_THRESHOLD = 0.84F;

    @Override
    public void onInitializeClient() {
        AttackEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            if (!level.isClientSide()) {
                return InteractionResult.PASS;
            }

            boolean validTarget = entity instanceof Player
                    || entity.getType().toString().contains("mannequin");

            if (!validTarget) {
                return InteractionResult.PASS;
            }

            boolean targetIsBlocking = entity instanceof Player target && target.isBlocking();
            if (targetIsBlocking) {
                return InteractionResult.PASS;
            }

            if (player.getAttackStrengthScale(0.5F) < CHARGE_THRESHOLD) {
                return InteractionResult.PASS;
            }

            level.playSound(
                    player,
                    entity.getX(),
                    entity.getY(),
                    entity.getZ(),
                    SoundEvents.ARROW_HIT_PLAYER,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );

            return InteractionResult.PASS;
        });
    }
}
