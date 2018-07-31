package cz.SkyWars.Entity;

import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.AddPlayerPacket;
import cn.nukkit.level.Level;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.*;

import cn.nukkit.Player;

import cn.nukkit.nbt.tag.CompoundTag;

import cn.nukkit.level.format.FullChunk;

import java.util.UUID;

public class SkyNPCEntity extends Entity{


    private static final int DATA_NO_AI = 0;

	public SkyNPCEntity(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
    
    public void initEntity() {
        super.initEntity();
        setDataFlag(0, 5);
        setDataFlag(0, 15);
        setDataFlag(0, 14);
        setDataProperty(new ByteEntityData(DATA_NO_AI, 1));
    }
    
    public int getNetworkId() {
        return 45;
    }
    
    public void spawnTo(Player player) {
            this.hasSpawned.put(player.getLoaderId(), player);
            AddPlayerPacket pk = new AddPlayerPacket();
            pk.uuid = UUID.randomUUID();
            pk.entityRuntimeId = this.getId();
            pk.entityUniqueId = this.getId();
            pk.x = (float) this.x;
            pk.y = (float) this.y;
            pk.z = (float) this.z;
            pk.yaw = 0.0f;
            pk.pitch = 0.0f;
            pk.metadata = this.dataProperties;
            player.dataPacket((DataPacket)pk);
    }
}
