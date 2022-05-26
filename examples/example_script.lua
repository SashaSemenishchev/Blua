EventHandler("BlockBreakEvent", function(event)
    Bukkit:broadcastMessage(event:getPlayer():getName() .. " broke a block!")
end)

CommandExecutor("/test", function(sender, args)
    sender:sendMessage("Hello World!")
end)
