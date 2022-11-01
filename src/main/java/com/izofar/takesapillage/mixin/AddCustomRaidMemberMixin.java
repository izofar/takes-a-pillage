package com.izofar.takesapillage.mixin;

import com.izofar.takesapillage.init.ModEntityTypes;
import com.izofar.takesapillage.util.CustomRaidMember;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(Raid.RaiderType.class)
public final class AddCustomRaidMemberMixin {
    @Invoker("<init>")
    private static Raid.RaiderType newRaidMember(String internalName, int internalId, EntityType<? extends Raider> entityType, int[] countInWave) {
        throw new AssertionError();
    }

    @Shadow private static @Final @Mutable Raid.RaiderType[] $VALUES;

    @Inject(method = "<clinit>", at = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC, target = "Lnet/minecraft/world/entity/raid/Raid$RaiderType;$VALUES:[Lnet/minecraft/world/entity/raid/Raid$RaiderType;", shift = At.Shift.AFTER))
    private static void takesAPillage_addCustomRaidMembers(CallbackInfo ci) {
        var raidMembers = new ArrayList<>(Arrays.asList($VALUES));
        var lastRaidMember = raidMembers.get(raidMembers.size() - 1);

        var archerRaidMember = newRaidMember(
                CustomRaidMember.ARCHER_INTERNAL_NAME,
                lastRaidMember.ordinal() + 1,
                ModEntityTypes.ARCHER,
                CustomRaidMember.ARCHER_COUNT_IN_WAVES
        );
        CustomRaidMember.ARCHER = archerRaidMember;
        raidMembers.add(archerRaidMember);

        var skirmisherRaidMember = newRaidMember(
                CustomRaidMember.SKIRMISHER_INTERNAL_NAME,
                lastRaidMember.ordinal() + 2,
                ModEntityTypes.SKIRMISHER,
                CustomRaidMember.SKIRMISHER_COUNT_IN_WAVES
        );
        CustomRaidMember.SKIRMISHER = skirmisherRaidMember;
        raidMembers.add(skirmisherRaidMember);

        var legionerRaidMember = newRaidMember(
                CustomRaidMember.LEGIONER_INTERNAL_NAME,
                lastRaidMember.ordinal() + 3,
                ModEntityTypes.LEGIONER,
                CustomRaidMember.LEGIONER_COUNT_IN_WAVES
        );
        CustomRaidMember.LEGIONER = legionerRaidMember;
        raidMembers.add(legionerRaidMember);

        $VALUES = raidMembers.toArray(new Raid.RaiderType[0]);
    }
}
