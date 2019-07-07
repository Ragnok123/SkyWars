package cz.SkyWars.entity;

import cn.nukkit.Player;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddPlayerPacket;
import cn.nukkit.network.protocol.DataPacket;

public class KitNPC extends EntityHuman{

	    public KitNPC(FullChunk chunk, CompoundTag nbt) {
	        super(chunk, nbt);
	    }
	    
	    public void initEntity() {
	    	super.initEntity();
	    	setNameTagVisible();
	        setNameTagAlwaysVisible();
	        this.setNameTag(getNameTag());
	    }

	    public void saveNBT() {
	        super.saveNBT();
	        this.setNameTag(this.getNameTag());
	    }

	    public void spawnTo(Player p) {
	        if (!this.hasSpawned.containsKey(p.getLoaderId())) {
	            this.hasSpawned.put(p.getLoaderId(), p);
	            this.getServer().updatePlayerListData(this.getUniqueId(), this.getId(), this.getName(), this.getSkin(), new Player[]{p});
	            AddPlayerPacket pk = new AddPlayerPacket();
	            pk.uuid = this.getUniqueId();//getUuid();
	            pk.username = this.getName();
	            pk.entityRuntimeId = this.getId();
	            pk.entityUniqueId = this.getId();
	            pk.x = (float)this.x;
	            pk.y = (float)this.y;
	            pk.z = (float)this.z;
	            pk.speedX = (float)this.motionX;
	            pk.speedY = (float)this.motionY;
	            pk.speedZ = (float)this.motionZ;
	            pk.yaw = (float)this.yaw;
	            pk.pitch = (float)this.pitch;
	            this.inventory.setItemInHand(Item.fromString(this.namedTag.getString("Item")));
	            pk.item = this.getInventory().getItemInHand();
	            pk.metadata = this.dataProperties;
	            p.dataPacket((DataPacket)pk);
	            this.inventory.sendArmorContents(p);
	            super.spawnTo(p);
	        }
	    }
	    
	    public String getKitId() {
	    	String s = "";
	    	if(this.namedTag.contains("KitId")) {
	    		 s=this.namedTag.getString("KitId");
	    	}
	    	return s;
	    }


}
