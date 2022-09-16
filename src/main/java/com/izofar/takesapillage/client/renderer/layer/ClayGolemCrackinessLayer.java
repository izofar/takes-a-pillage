package com.izofar.takesapillage.client.renderer.layer;

import com.google.common.collect.ImmutableMap;
import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.client.model.ClayGolemModel;
import com.izofar.takesapillage.entity.ClayGolemEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ClayGolemCrackinessLayer extends LayerRenderer<ClayGolemEntity, ClayGolemModel> {

    private static final Map<IronGolemEntity.Cracks, ResourceLocation> resourceLocations = ImmutableMap.of(IronGolemEntity.Cracks.LOW, new ResourceLocation(TakesAPillageMod.MODID, "textures/entity/clay_golem/clay_golem_crackiness_low.png"), IronGolemEntity.Cracks.MEDIUM, new ResourceLocation(TakesAPillageMod.MODID, "textures/entity/clay_golem/clay_golem_crackiness_medium.png"), IronGolemEntity.Cracks.HIGH, new ResourceLocation(TakesAPillageMod.MODID, "textures/entity/clay_golem/clay_golem_crackiness_high.png"));

    public ClayGolemCrackinessLayer(IEntityRenderer<ClayGolemEntity, ClayGolemModel> layer) { super(layer); }

    @Override
    public void render(MatrixStack stack, IRenderTypeBuffer buffer, int packedLight, ClayGolemEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible()) {
            IronGolemEntity.Cracks claygolem$crackiness = entity.getCrackiness();
            if (claygolem$crackiness != IronGolemEntity.Cracks.NONE) {
                ResourceLocation resourcelocation = resourceLocations.get(claygolem$crackiness);
                renderColoredCutoutModel(this.getParentModel(), resourcelocation, stack, buffer, packedLight, entity, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}
