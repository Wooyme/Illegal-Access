package me.wooy.game.nax.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.github.czyzby.noise4j.map.Grid;
import com.github.czyzby.noise4j.map.generator.room.dungeon.DungeonGenerator;
import com.github.czyzby.noise4j.map.generator.util.Generators;
import me.wooy.game.nax.event.worldgenerate.WorldGenerateEvent;
import me.wooy.game.nax.player.Player;
import me.wooy.game.nax.system.DeviceSystem;
import me.wooy.game.nax.world.device.Home;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Consumer;

import me.wooy.game.nax.world.device.Device;
import org.apache.commons.lang3.tuple.Pair;

public class WorldGenerator extends DungeonGenerator {
    public static final List<Pair<Class<? extends Device>,Float>> DEVICE_LIST = new LinkedList<>();
    public static final List<WorldGenerateEvent> EVENT_LIST = new LinkedList<>();
    private final Map<Integer, Room> regionRoomMap = new HashMap<>();
    private final Map<Integer, List<Point>> regionCorridorMap = new HashMap<>();
    private final Map<Integer, Set<Integer>> connections = new HashMap<>();
    private final Map<Integer, Set<Point>> regionJoinPointMap = new HashMap<>();
    public final Set<Integer> ownRegions = new HashSet<>();
    private Room home = null;
    private final int width;
    private final int height;
    private final Grid grid;
    private boolean generated = false;
    private final Map<Integer, Class<? extends Device>> indexDeviceMap = new HashMap<>();
    private float totalProb = 0f;

    public WorldGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Grid(width, height); // This algorithm likes odd-sized maps, although it works either way.

    }

    public void generate() {
        if (generated) {
            return;
        }
        for (Pair<Class<? extends Device>,Float> device : DEVICE_LIST) {
            indexDeviceMap.put(DeviceSystem.getIndex(device.getKey()), device.getKey());
        }
        totalProb = DEVICE_LIST.stream().map(Pair::getValue).reduce(Float::sum).orElse(0f);
        this.generate(grid);
        final Room room = regionRoomMap.get(new Random().nextInt(regionRoomMap.size()));
        home = room;
        room.setDevice(new Home());
        room.setType(grid);
        executeEvent();
        ownRegions.add(room.region);
        Player.getInstance().getOwnedDevices().add(room.device);
        generated = true;
        DeviceSystem.afterGenerate(connections,regionRoomMap);
        System.out.println("World Generated");
    }

    private void executeEvent(){
        rooms.forEach(room-> EVENT_LIST.forEach(event->{
            if(event.willExecute(room.device)){
                event.execute(room.device);
            }
        }));
    }

    public Point getStart(){
        return new Point(home.getX(), home.getY());
    }

    public Room getRoom(int x, int y) {
        float value = grid.get(x, y);
        if (value > floorThreshold) {
            return null;
        }
        for (Room room : rooms) {
            if (room.contains(x, y)) {
                return room;
            }
        }
        return null;
    }

    @Override
    protected void spawnRooms(Grid grid, int attempts) {
        for (int index = 0, maxRoomsAmount = getMaxRoomsAmount(); index < attempts; index++) {
            final Room newRoom = getRandomRoom(grid);
            final Device device = randomDevice(newRoom.getHeight()*newRoom.getWidth());
            newRoom.setDevice(device);
            if (!overlapsAny(newRoom)) {
                rooms.add(newRoom);
                carveRoom(grid, newRoom, floorThreshold);
                nextRegion();
                newRoom.fill(regions, currentRegion); // Assigning region values to all cells.
                newRoom.setType(grid);
                newRoom.region = currentRegion;
                regionRoomMap.put(currentRegion, newRoom);
            }
            if (maxRoomsAmount > 0 && rooms.size() >= maxRoomsAmount) {
                break;
            }
        }
        lastRoomRegion = currentRegion;
    }

    private Device randomDevice(int size){
        float p = (float)Math.random() * totalProb;
        float cumulativeProbability = 0f;
        for (Pair<Class<? extends Device>,Float> device : DEVICE_LIST) {
            cumulativeProbability+=device.getValue();
            if(cumulativeProbability>p){
                try {
                    return device.getKey().getConstructor(Integer.TYPE).newInstance(size);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new IllegalStateException("Device need constructor passed size");
                }
            }
        }
        throw new IllegalStateException("Must not happen");
    }


    public Pixmap update() {
        ownRegions.forEach(region -> {
            if (regionRoomMap.containsKey(region)) {
                regionRoomMap.get(region).show(grid);
                regionRoomMap.get(region).setType(grid);
                Set<Integer> regions = connections.get(region);
                Set<Room> rooms = new HashSet<>();
                regions.forEach(r -> findRooms(r, rooms, new HashSet<>()));
                showRooms(new ArrayList<>(rooms));
            }
        });

        return draw();
    }

    private Pixmap draw() {
        final Pixmap map = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        final Color color = new Color();
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                final boolean visible = grid.getVisibility(x, y);
                final float cell = visible ? 1f - grid.get(x, y) : 0f;
                final int type = grid.getType(x, y);
                if(type>0 && visible) {
                    Class<? extends Device> device = indexDeviceMap.get(type);
                    final int fixedColor = grid.getColor(x,y);
                    if(fixedColor!=0){
                        map.drawPixel(x, y, fixedColor);
                    }else {
                        map.drawPixel(x, y, DeviceSystem.getColor(device));
                    }
                }else{
                    color.set(cell, cell, cell, 1f);
                    map.drawPixel(x, y, Color.rgba8888(color));
                }
                if (cell == 0 && (x % 40 == 0 || y % 40 == 0)) {
                    map.drawPixel(x, y, Color.rgba8888(new Color(0x336666)));
                }
            }
        }

        return map;
    }

    private void findRooms(Integer region, Set<Room> rooms, Set<Integer> found) {
        if (found.contains(region)) {
            return;
        }
        found.add(region);
        if (regionJoinPointMap.containsKey(region)) {
            regionJoinPointMap.get(region).forEach(point -> {
                grid.setVisibility(point.x, point.y, true);
            });
        }
        if (regionRoomMap.containsKey(region)) {
            rooms.add(regionRoomMap.get(region));
            return;
        }
        if (regionCorridorMap.containsKey(region)) {
            showCorridors(Collections.singletonList(regionCorridorMap.get(region)));
        }
        connections.get(region).forEach(r -> findRooms(r, rooms, found));
    }

    private void showRooms(List<Room> rooms) {
        rooms.forEach(room ->{
            room.show(grid);
            room.setType(grid);
        });
    }

    private void showCorridors(List<List<Point>> corridors) {
        corridors.forEach(corridor -> corridor.forEach(point -> grid.setVisibility(point.x, point.y, true)));
    }

    @Override
    protected void carveMaze(final Grid grid, final Point point) {
        nextRegion();
        Direction lastDirection = null;
        List<Point> points = new ArrayList<>();
        while (true) {
            // Carving current point:
            carveCorridor(grid, point);
            points.add(new Point(point.x, point.y));
            regions.set(point.x, point.y, currentRegion);

            directions.clear();
            // Checking neighbors - getting possible carving directions:
            for (final Direction direction : Direction.values()) {
                if (isCarveable(point, grid, direction)) {
                    directions.add(direction);
                }
            }
            if (directions.isEmpty()) {
                regionCorridorMap.put(currentRegion, points);
                return;
            }
            Direction carvingDirection;
            // Getting actual carving direction:
            if (lastDirection != null && directions.contains(lastDirection)
                    && Generators.randomPercent() > windingChance) {
                carvingDirection = lastDirection;
            } else {
                carvingDirection = Generators.randomElement(directions);
            }
            lastDirection = carvingDirection;
            // Carving "ignored" even-indexed corridor cell:
            carvingDirection.next(point);
            carveCorridor(grid, point);
            points.add(new Point(point.x, point.y));
            regions.set(point.x, point.y, currentRegion);
            // Switching to next odd-index cell, repeating until no viable neighbors left:
            carvingDirection.next(point);
        }
    }


    @Override
    protected void joinRegions(final Grid grid) { // DRAGON.
        nextRegion();
        // Working on boxed primitives, because lawl, Java generics and collections.
        final Map<Point, Set<Integer>> connectorsToRegions = findConnectors(grid);
        final List<Point> connectors = new ArrayList<Point>(connectorsToRegions.keySet());
        final Integer[] merged = new Integer[currentRegion]; // Keeps track of merged regions.
        final Set<Integer> unjoined = new HashSet<Integer>(); // Keeps track of unconnected regions.
        for (int index = 0; index < currentRegion; index++) {
            // All regions point to themselves at first:
            merged[index] = index;
            // All regions start unjoined:
            unjoined.add(index);
        }
        Generators.shuffle(connectors);
        final Set<Integer> tempSet = new HashSet<Integer>();
        // Looping until all regions point to one source:
        for (final Iterator<Point> connectorIterator = connectors.iterator(); connectorIterator.hasNext()
                && unjoined.size() > 1; ) {
            final Point connector = connectorIterator.next();
            // These are the regions that the connector originally pointed to - we need to convert them to the "new",
            // merged region indexes:
            final Set<Integer> regions = connectorsToRegions.get(connector);
            tempSet.clear();
            for (final Integer region : regions) {
                tempSet.add(merged[region]);
            }
            if (tempSet.size() <= 1) { // All connector's regions point to the same region group...
                if (Generators.randomPercent() < randomConnectorChance) {
                    // This connector is not actually needed, but it got lucky - carving:
                    carveConnector(grid, connector.x, connector.y);
                }
                continue;
            }
            carveConnector(grid, connector.x, connector.y);
            createConnection(connector, regions);
            regions.clear();
            regions.addAll(tempSet);
            final Iterator<Integer> regionsIterator = regions.iterator();
            // Using first region as our "source":
            final Integer source = regionsIterator.next(); // Safe, has at least 2 regions.
            // Using the rest of the region as destinations - they will point to the source region in merged array:
            final Integer[] destinations = getDestinations(regions, regionsIterator, merged);
            // Changing merged status - all regions that currently point to destinations will now point to source:
            for (int regionIndex = 0; regionIndex < currentRegion; regionIndex++) {
                for (final Integer destination : destinations) {
                    if (merged[regionIndex].equals(destination)) {
                        // This region was previously connected with one of our joined regions (or itself). Now pointing
                        // to our source.
                        merged[regionIndex] = source;
                    }
                }
            }
            // Removing destinations - which were clearly joined - from unjoined regions:
            for (final Integer destination : destinations) {
                unjoined.remove(destination);
            }
        }
    }

    private void createConnection(Point point, Set<Integer> regions) {
        regions.forEach(region -> {
            if (!connections.containsKey(region)) {
                connections.put(region, new HashSet<>());
            }
            regions.forEach(otherRegion -> {
                if (!otherRegion.equals(region)) {
                    connections.get(region).add(otherRegion);
                }
            });
            if (!regionJoinPointMap.containsKey(region)) {
                regionJoinPointMap.put(region, new HashSet<>());
            }
            regionJoinPointMap.get(region).add(point);
        });
    }


}
