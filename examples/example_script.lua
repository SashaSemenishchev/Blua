EventHandler("BlockBreakEvent", function(event)
    Bukkit:broadcastMessage(event:getPlayer():getName() .. " broke a block!")
end)

CommandExecutor("/test", function(sender, sender_type, args)
    if sender_type == "Player" then
        sender:sendMessage("Hello World!")
    end
    if sender_type == "Console" then
        sender:sendMessage("Hello Console World!")
    end
end)
