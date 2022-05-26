EventHandler("BlockBreakEvent", function(event)
    Bukkit:broadcastMessage(event:getPlayer():getName() .. " broke a block!")
end)

CommandExecutor("/test", function(event)
    event:getPlayer():sendMessage("Hello World!")
end)