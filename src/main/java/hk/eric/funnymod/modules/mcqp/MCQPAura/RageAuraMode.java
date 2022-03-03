package hk.eric.funnymod.modules.mcqp.MCQPAura;

import hk.eric.funnymod.utils.EntityUtil;
import hk.eric.funnymod.utils.MathUtil;
import hk.eric.funnymod.utils.PacketUtil;
import hk.eric.funnymod.utils.classes.XYRot;
import hk.eric.funnymod.utils.classes.lamdba.TriConsumer;
import hk.eric.funnymod.utils.classes.lamdba.TriFunction;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class RageAuraMode implements AuraMode {
    @Override
    public TriFunction<Stream<LivingEntity>, LocalPlayer, Double, List<LivingEntity>> getEntities() {
        return (entities, player, range) -> entities.filter(entity -> {
            if (EntityUtil.isHostile(entity) || EntityUtil.isPassive(entity)) {
                if (!entity.isAlive() || entity.getHealth() <= 0) return false;
                return MathUtil.compareDistance3D(player.getX(), player.getY(), player.getZ(), entity.getX(), entity.getY(), entity.getZ(), range) == -1;
            }
            return false;
        }).toList();
    }

    @Override
    public TriConsumer<LivingEntity, LocalPlayer, Consumer<Packet<?>>> getAttack() {
        return (entity, player, packetSender) -> {
            Vec3 targetLoc = entity.position();
            XYRot xyRot = MathUtil.getLookAtRotation(entity.getX(), entity.getY() + player.getEyeHeight(), entity.getZ(), entity.getX(), entity.getY(), entity.getZ());
            packetSender.accept(new ServerboundMovePlayerPacket.PosRot(targetLoc.x, targetLoc.y, targetLoc.z, xyRot.getYRot(), xyRot.getXRot(), false));
            HitResult result = player.pick(3,1,false);
            if (result.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult) result).getBlockPos();
                PacketUtil.send(new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK, blockPos, Direction.fromYRot(xyRot.getYRot())));
            }else {
                PacketUtil.send(new ServerboundSwingPacket(InteractionHand.MAIN_HAND));
            }
        };
    }
}
