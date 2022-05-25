local listener = {}
local commands = {}

function listener.hello(event)
    event:setHello(1)
    print("Hello World!")
end

function commands.welcome(event)
    print("Welcome to the world of Lua!")
end

return {listener, commands}