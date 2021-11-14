package me.wooy.game.nax.system;

import com.badlogic.gdx.graphics.Color;
import com.github.czyzby.noise4j.map.generator.room.AbstractRoomGenerator;
import me.wooy.game.nax.NoAccess;
import me.wooy.game.nax.player.Player;
import me.wooy.game.nax.world.WorldGenerator;
import me.wooy.game.nax.world.device.*;
import me.wooy.game.nax.world.organization.Organization;
import me.wooy.game.nax.world.organization.PlayerOrg;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class DeviceSystem {
    private static Map<Integer,Set<Integer>> connections = new HashMap<>();
    private static Map<Integer, AbstractRoomGenerator.Room> regionRoomMap = new HashMap<>();
    private final static Map<Device,Integer> deviceRegionMap = new HashMap<>();
    private final static List<Class<? extends Device>> deviceIndexList = new ArrayList<>();
    private final static Map<Class<? extends Device>, Integer> deviceColorMap = new HashMap<>();
    public static void load(){
        addDevice(Home.class,0f,Color.YELLOW);
        addDevice(WebServer.class, 0.5f,Color.SKY);
        addDevice(ChatServer.class,0.1f,Color.GREEN);
//        addDevice(EMailServer.class,0.1f,Color.PURPLE);
//        addDevice(DatabaseServer.class,0.1f,Color.RED);
//        addDevice(CityServer.class,0.1f,Color.BLUE);
    }
    private static void addDevice(Class<? extends Device> deviceClazz,float prob,Color color){
        WorldGenerator.DEVICE_LIST.add(Pair.of(deviceClazz,prob));
        deviceIndexList.add(deviceClazz);
        deviceColorMap.put(deviceClazz,Color.rgba8888(color));
    }

    public static int getIndex(Class<? extends Device> device){
        return deviceIndexList.indexOf(device)+1;
    }
    public static int getColor(Class<? extends Device> device){
        return deviceColorMap.get(device);
    }
    public static void own(Device device,Organization organization){
        if(device instanceof Home){
            return;
        }
        device.getOwners().add(organization);
        NoAccess.worldGenerator.ownRegions.add(deviceRegionMap.get(device));
        if(organization instanceof PlayerOrg){
            Player.getInstance().addOwnedDevices(Collections.singleton(device));
        }
    }

    public static boolean ownedBy(Device device,Organization organization){
        return device.getOwners().contains(organization);
    }
    
    public static List<Device> getNeighbors(Device device){
        int region = deviceRegionMap.get(device);
        Set<AbstractRoomGenerator.Room> rooms = new HashSet<>();
        Set<Integer> regions = connections.get(region);
        regions.forEach(r->findRooms(r,rooms,new HashSet<>()));
        return rooms.stream().map(it->it.device).collect(Collectors.toList());
    }

    private static void findRooms(Integer region, Set<AbstractRoomGenerator.Room> rooms, Set<Integer> found) {
        if (found.contains(region)) {
            return;
        }
        found.add(region);
        if (regionRoomMap.containsKey(region)) {
            rooms.add(regionRoomMap.get(region));
            return;
        }
        connections.get(region).forEach(r -> findRooms(r, rooms, found));
    }
    
    public static void afterGenerate(Map<Integer,Set<Integer>> connections, Map<Integer, AbstractRoomGenerator.Room> regionRoomMap){
        DeviceSystem.connections = connections;
        DeviceSystem.regionRoomMap = regionRoomMap;
        regionRoomMap.forEach((region,room)->{
            deviceRegionMap.put(room.device,region);
        });
    }

}
