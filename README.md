# MB2 Plugin System
<img align="right" width="115" height="115" src="https://user-images.githubusercontent.com/86576295/167314810-c9b6a021-6a41-463f-b35f-6ac3b825be7a.png"></img>
MB2 Plugin System is a lightweight log interpreter tailor-made for the [Movie Battles II](https://community.moviebattles.org/) Jedi Knight: Jedi Academy mod. The system makes it easy for server mod developers to write plugins that can react to in-game events.

## Default in-game commands
```
    /smod say !plugins
        - List the currently loaded plugins in the in-game console
    /smod say !reload_plugins
        - Reloads all plugins
```
## For server owners
To run MB2 Log Reader we first need to set up some infrastructure. We need:
1. Install a [JRE](https://java.com/en/download/manual.jsp) that can run Java 11 or higher
2. Install [bully-mb2's fork of OpenJK](https://github.com/bully-mb2/mb2-log-reader-openjk/releases)
3. Configure MB2 server.cfg
    ```
    seta logremote "127.0.0.1:63336" // Default "" if set to an ip:port it will write log data to remote server, example = "1.1.1.1:50000"
    set g_logExplicit "3"
    set g_logClientInfo "1"
    set com_logChat "2"
    ```
4. Run MB2 Plugin System and configure the generated application.properties
    ```
    java -jar mb2-plugin-system-VERSION.jar
    ```
5. Put your plugins into the /plugins folder and restart the program or reload the plugins using the `/smod say !reload_plugins` in-game command!

## For plugin developers
To get started developing plugins begin by setting up the required [mb2-plugin-lib](https://github.com/bully-mb2/mb2-plugin-lib) and have a look at the [MB2 Plugin Example](https://github.com/bully-mb2/mb2-plugin-example)

## License
MB2 Plugin System is licensed under GPLv2 as free software. You are free to use, modify and redistribute MB2 Log Reader following the terms in LICENSE.txt
