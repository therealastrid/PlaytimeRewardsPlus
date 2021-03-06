// PlayerDatabase.java
//   - A database to keep track of players within the plugin
//
// (c) Owen Rummage 2020

package dev.astridlabs.playtimerewardsplus.Controllers;

import dev.astridlabs.playtimerewardsplus.DataTypes.PlayerData;
import dev.astridlabs.playtimerewardsplus.Plugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;


public class PlayerDatabase {
    public List<PlayerData> playerList = new ArrayList<PlayerData>();
    public Plugin plugin;

    public PlayerDatabase(Plugin plugin){
        this.plugin = plugin;
    }


    public PlayerData getPlayer(UUID uuid){
        PlayerData returnedPlayer = null;

        for (PlayerData player : playerList) {
            if(player.getUuid().toString().equals(uuid.toString())){
                returnedPlayer = player;
            }
        }
        return returnedPlayer;
    }

    public void loadPlayers(){
        for(PlayerData player : plugin.config.getPlayersFromConfig()){
            playerList.add(player);
        }
    }

    class Sortbyroll implements Comparator<PlayerData>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(PlayerData a, PlayerData b)
        {
            return b.getPlaytime() - a.getPlaytime();
        }
    }

    public List<PlayerData> getSortedPlayerlist(){
        List<PlayerData> sorted = new ArrayList<PlayerData>();

        sorted.addAll(playerList);
        Collections.sort(sorted, new Sortbyroll());

        return sorted;

    }


    public void updatePlayerDisplayName(UUID uuid, String displayName){
        PlayerData player = getPlayer(uuid);
        player.setDisplayName(displayName);
    }
    public void updatePlayerPlaytime(UUID uuid, Integer playtime){
        PlayerData player = getPlayer(uuid);
        player.setPlaytime(playtime);
    }
    public void updateCurrentLocation(UUID uuid, Location currentLocation){
        PlayerData player = getPlayer(uuid);
        player.setCurrentLocation(currentLocation);
    }
    public void updateLastLocation(UUID uuid, Location lastLocation){
        PlayerData player = getPlayer(uuid);
        player.setLastLocation(lastLocation);
    }

    public boolean playerExists(UUID uuid){
        boolean returnState = false;

        for (PlayerData player : playerList) {
            if(player.getUuid().toString().equals(uuid.toString())){
                returnState = true;
            }
        }

        return returnState;
    }

    public void createPlayer(Player player){
        if(playerExists(player.getUniqueId()) == false){
            playerList.add(new PlayerData(player.getUniqueId(), player.getDisplayName(), 0));
        }
        return;
    }

    public void CreatePlayerRaw(PlayerData playerData){
        playerList.add(playerData);
    }

}
